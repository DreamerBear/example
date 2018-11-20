package com.good.xuchao.example.aspect;

import java.lang.annotation.*;

/**
 * All rights Reserved, Designed By www.maihaoche.com
 *
 * @Package com.mhc.chevrolet.core.base.aspect
 * @author: 绪超（xuchao@maihaoche.com）
 * @date: 2018/7/3 下午3:22
 * @Copyright: 2017-2020 www.maihaoche.com Inc. All rights reserved.
 * 注意：本内容仅限于卖好车内部传阅，禁止外泄以及用于其他的商业目
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface FacadeAdvice {
}
