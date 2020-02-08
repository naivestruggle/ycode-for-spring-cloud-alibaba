package com.cup.ycode.commons.domain;

import com.cup.ycode.commons.dto.web.AbstractBaseDomain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "admin")
@Data
public class Admin extends AbstractBaseDomain implements Serializable {

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    @Override
    public String toString() {
        return super.toString();
    }
}