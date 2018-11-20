package com.good.xuchao.example.form;

import lombok.Getter;

/**
 * All rights Reserved, Designed By www.maihaoche.com
 *
 * @Package com.gtr.core.exception
 * @author: 绪超（xuchao@maihaoche.com）
 * @date: 2018/10/15 下午4:35
 * @Copyright: 2017-2020 www.maihaoche.com Inc. All rights reserved.
 * 注意：本内容仅限于卖好车内部传阅，禁止外泄以及用于其他的商业目
 */
@Getter
public class SellerSettlementBizException extends RuntimeException {
    private static final long serialVersionUID = 3452345246238989L;

    /**
     * 异常code
     */
    private String code;

    /**
     * 调用接口的请求参数
     */
    private Object request;

    /**
     * response, 调用接口的返回结果
     */
    private Object response;

    /**
     * 错误的信息
     */
    private String message;

    public SellerSettlementBizException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public SellerSettlementBizException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public SellerSettlementBizException(String code, String messageFormat, Object... args) {
        String message = String.format(messageFormat, args);
        this.code = code;
        this.message = message;
    }


    public SellerSettlementBizException(Throwable cause) {
        super(cause);
    }

    public SellerSettlementBizException setCode(String code) {
        this.code = code;
        return this;
    }

    public SellerSettlementBizException setRequest(Object request) {
        this.request = request;
        return this;
    }

    public SellerSettlementBizException setResponse(Object response) {
        this.response = response;
        return this;
    }

    public SellerSettlementBizException setMessage(String message) {
        this.message = message;
        return this;
    }

    public static void main(String[] args) {
        throw new SellerSettlementBizException("9999", "syncOldSettlement(同步数据至老的结算单) syncOldSettlementByOrder return empty, sellerOrderNo:%s", "123456");
    }

}
