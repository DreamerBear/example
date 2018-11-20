package com.good.xuchao.example.bean;

import com.good.xuchao.example.aspect.FacadeAdvice;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * All rights Reserved, Designed By www.maihaoche.com
 *
 * @Package com.good.xuchao.example.bean
 * @author: 绪超（xuchao@maihaoche.com）
 * @date: 2018/11/2 上午10:56
 * @Copyright: 2017-2020 www.maihaoche.com Inc. All rights reserved.
 * 注意：本内容仅限于卖好车内部传阅，禁止外泄以及用于其他的商业目
 */
@FacadeAdvice
@Service
public class BeanB {
    @Resource
    private BeanA beanA;

    public String helloB(String name){
        return getClass().getSimpleName() + name;
    }

    public String helloA(String name){
        return getClass().getSimpleName() + beanA.helloA(name);
    }
}
