package com.good.xuchao.example.bean;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * All rights Reserved, Designed By www.maihaoche.com
 *
 * @Package com.good.xuchao.example.bean
 * @author: 绪超（xuchao@maihaoche.com）
 * @date: 2018/11/6 上午10:20
 * @Copyright: 2017-2020 www.maihaoche.com Inc. All rights reserved.
 * 注意：本内容仅限于卖好车内部传阅，禁止外泄以及用于其他的商业目
 */
@Slf4j
public class BeanD {
    private static BeanD ourInstance = new BeanD();

    public static BeanD getInstance() {
        return ourInstance;
    }

    private BeanD() {
    }

    public static void main(String[] args) {

        try {
            throw new RuntimeException("12313");
        } catch (Exception e) {
            log.error("demo error",e);
        }
    }
}
