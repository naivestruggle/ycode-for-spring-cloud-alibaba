package com.cup.ycode.commons.dto;

import com.cup.ycode.commons.constant.HttpStatusConstant;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 服务提供者的返回对象
 * 因为 服务消费者 可能通过 RPC  调用 服务提供者，
 * 所以 这个类中不能出现 HTTP 相关的API
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBody implements Serializable {
    private int status;
    private String message;
    private String detail;
    private Object data;

    private ResponseBody(){}

    public ResponseBody(int status, String message, String detail, Object data) {
        this.status = status;
        this.message = message;
        this.detail = detail;
        this.data = data;
    }

    /**
     * 200 请求已成功  请求所希望的响应头或数据体将随此响应返回
     * @param data 数据
     * @Param detail 详细信息
     * @return  响应对象
     */
    public static ResponseBody success_OK_200(String detail, Object data){
        return new ResponseBody(HttpStatusConstant.OK.getStatus(), HttpStatusConstant.OK.getMessage(), detail, data);
    }

    /**
     * 201 请求已经被实现
     * @param data  数据
     * @param detail  详细信息
     * @return  响应对象
     */
    public static ResponseBody success_CREATED_201(String detail, Object data){
        return new ResponseBody(HttpStatusConstant.CREATED.getStatus(),
                HttpStatusConstant.CREATED.getMessage(), detail, data);
    }


    /**
     * 302 请求的资源临时从不同的 URI响应请求。
     * @param detail 详细信息
     * @return  响应对象
     */
    public static ResponseBody error_MOVE_TEMPORARILY_302(String detail){
        return new ResponseBody(HttpStatusConstant.MOVE_TEMPORARILY.getStatus(), HttpStatusConstant.MOVE_TEMPORARILY.getMessage(), detail, null);
    }


    /**
     * 401 请求参数错误   自定义响应信息
     * @param detail 响应详细信息
     * @return  响应对象
     */
    public static ResponseBody error_UNAUTHORIZED_401(String detail){
        return new ResponseBody(HttpStatusConstant.UNAUTHORIZED.getStatus(),
                HttpStatusConstant.UNAUTHORIZED.getMessage(), detail, null);
    }

    /**
     * 402 未登录
     * @param detail 响应详细信息
     * @return  响应对象
     */
    public static ResponseBody error_PAYMENT_REQUIRED_402(String detail){
        return new ResponseBody(HttpStatusConstant.PAYMENT_REQUIRED.getStatus(),
                HttpStatusConstant.PAYMENT_REQUIRED.getMessage(), detail, null);
    }

    /**
     * 502 从上游返回响应无效
     * @param detail 详细信息
     * @return  响应对象
     */
    public static ResponseBody error_BAD_GATEWAY_502(String detail){
        return new ResponseBody(HttpStatusConstant.BAD_GATEWAY.getStatus(), HttpStatusConstant.BAD_GATEWAY.getMessage(), detail, null);
    }

    /**
     * 507 服务器无法存储完成请求所必须的内容，这个状态是临时的。
     * @param detail 详细信息
     * @return  响应对象
     */
    public static ResponseBody error_INSUFFICIENT_STORAGE_507(String detail){
        return new ResponseBody(HttpStatusConstant.INSUFFICIENT_STORAGE.getStatus(),
                HttpStatusConstant.INSUFFICIENT_STORAGE.getMessage(), detail, null);
    }

}
