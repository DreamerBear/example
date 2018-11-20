package com.good.xuchao.example.form;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

/**
 * All rights Reserved, Designed By www.maihaoche.com
 *
 * @Package com.good.xuchao.example.form
 * @author: 绪超（xuchao@maihaoche.com）
 * @date: 2018/9/26 下午1:05
 * @Copyright: 2017-2020 www.maihaoche.com Inc. All rights reserved.
 * 注意：本内容仅限于卖好车内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
public class C {
    private String name = "xx";

    private String phone = "123";

    private Map<String, String> attrAMap1 = new HashMap() {{
        put("k1", "3");
        put("k2", "33");
    }};

    private List<Date> dates = Lists.newArrayList(new Date(),new Date());

}
