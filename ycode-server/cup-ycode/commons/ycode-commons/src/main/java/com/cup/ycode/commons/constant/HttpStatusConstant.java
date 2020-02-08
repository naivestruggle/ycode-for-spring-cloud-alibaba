package com.cup.ycode.commons.constant;

import org.springframework.http.HttpStatus;

public enum HttpStatusConstant {
    OK(200, "请求成功。"),
    CREATED(201, "请求已经被实现，而且有一个新的资源已经依据请求的需要而建立, 且其 URI 已经随Location 头信息返回。"),
    MOVE_TEMPORARILY(302, "请求的资源临时从不同的 URI响应请求。"),
    BAD_GATEWAY(502,  "从上游服务器接收到无效的响应。"),
    UNAUTHORIZED(401, "请求参数错误。"),
    PAYMENT_REQUIRED(402, "未登录。"),
    INSUFFICIENT_STORAGE(507, "服务器无法存储完成请求所必须的内容，这个状态是临时的。");

    private int status;
    private String message;

    HttpStatusConstant(int status, String message) {
        this.status = status;
        this.message = message;

    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
