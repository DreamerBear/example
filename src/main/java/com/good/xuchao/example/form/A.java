package com.good.xuchao.example.form;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By www.maihaoche.com
 *
 * @Package com.good.xuchao.example.form
 * @author: 绪超（xuchao@maihaoche.com）
 * @date: 2018/9/26 上午11:13
 * @Copyright: 2017-2020 www.maihaoche.com Inc. All rights reserved.
 * 注意：本内容仅限于卖好车内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
public class A {
    private String attrA;

    private Map<String, C> attrAMap1 = new HashMap() {{
        put("k1", new C());
        put("k2", new C());
    }};

}
