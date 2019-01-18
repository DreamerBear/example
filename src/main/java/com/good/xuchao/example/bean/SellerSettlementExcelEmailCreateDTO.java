package com.good.xuchao.example.bean;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * All rights Reserved, Designed By www.maihaoche.com
 *
 * @Package com.gtr.api.model
 * @author: 绪超（xuchao@maihaoche.com）
 * @date: 2018/11/22 10:05 AM
 * @Copyright: 2017-2020 www.maihaoche.com Inc. All rights reserved.
 * 注意：本内容仅限于卖好车内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
public class SellerSettlementExcelEmailCreateDTO implements Serializable {

    /**
     * 卖家宝结算单号
     */
    private String sellerSettlementNo;

    /**
     * 卖家宝订单号
     */
    private String sellerOrderNo;

    /**
     * 接收的邮箱地址
     */
    private String emailAddress;

    public static void main(String[] args) {
        System.out.println(Long.MAX_VALUE/100);
        System.out.println(1_000_111);
        Map<Integer, String> dict1 = Lists.newArrayList(12, 32).stream().collect(Collectors.toMap(Function.identity(), Object::toString));
        System.out.println(dict1);
    }
}
