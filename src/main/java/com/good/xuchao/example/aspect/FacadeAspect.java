package com.good.xuchao.example.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * All rights Reserved, Designed By www.maihaoche.com
 *
 * @Package com.mhc.chevrolet.core.base.aspect
 * @author: 绪超（xuchao@maihaoche.com）
 * @date: 2018/7/3 下午3:22
 * @Copyright: 2017-2020 www.maihaoche.com Inc. All rights reserved.
 * 注意：本内容仅限于卖好车内部传阅，禁止外泄以及用于其他的商业目
 */
@Component
@Aspect
@Slf4j
public class FacadeAspect {

    @Pointcut("@within(com.good.xuchao.example.aspect.FacadeAdvice)")
    private void facadePointCut() {
    }

    @Around("facadePointCut()")
    public Object aroundController(ProceedingJoinPoint joinPoint) throws Throwable {
        return around(joinPoint);
    }

    /**
     * 环绕通知,不代理非public方法
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Method realMethod = joinPoint.getTarget().getClass().getDeclaredMethod(
                joinPoint.getSignature().getName(),
                ((MethodSignature) joinPoint.getSignature()).getParameterTypes()
        );
        if (!Modifier.isPublic(realMethod.getModifiers())) {
            return joinPoint.proceed();
        } else {
            return doAround(joinPoint, realMethod);
        }
    }

    /**
     * 执行通知, 主要是统一异常处理,日志打印
     *
     * @param joinPoint
     * @param realMethod
     * @return
     * @throws Throwable
     */
    private Object doAround(ProceedingJoinPoint joinPoint, Method realMethod) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = null;
        try {
            result = joinPoint.proceed();
        }
        finally {
            doInfoLog(joinPoint, result, start);
        }
        return result;
    }

    /**
     * 统一调试日志打印
     *
     * @param joinPoint
     * @param result
     * @param start
     */
    private void doInfoLog(ProceedingJoinPoint joinPoint, Object result, long start) {
        if (log.isInfoEnabled()) {
            Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
            String targetMethodDesc = joinPoint.getSignature().getName();
            String paramsString = parseParams(joinPoint.getArgs());
            logger.info(String.join("|",
                    targetMethodDesc,
                    "cost:" + (System.currentTimeMillis() - start),
                    paramsString,
                    "result:" + JSON.toJSONString(result)));
        }
    }


    /**
     * 解析入参
     *
     * @param params
     * @return
     */
    private String parseParams(Object[] params) {
        if (params != null && params.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (Object p : params) {
                if (p != null) {
                    sb.append(JSONObject.toJSONString(p, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat) + ",");
                } else {
                    sb.append("null,");
                }
            }
            String res = sb.toString();
            if (res.length() > 1) {
                return res.substring(0, res.length() - 1);
            }
        }
        return "";
    }

}
