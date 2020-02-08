package com.cup.ycode.commons.domain;

import com.cup.ycode.commons.dto.web.AbstractBaseDomain;
import com.cup.ycode.commons.utils.RegexpUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Table(name = "user")
@Data
public class User extends AbstractBaseDomain implements Serializable {
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 账号 可以用来登录
     */
    @NotNull(message = "请输入账号")
    @Length(min = 4, max = 8, message = "用户名长度必须介于 4 到 8 之间")
    private String account;

    /**
     * 邮箱
     */
    @NotNull(message = "请输入邮箱")
    @Pattern(regexp = RegexpUtils.EMAIL, message = "邮箱格式不正确")
    private String email;

    /**
     * 手机号 可以用来登录
     */
    private String phone;

    /**
     * 密码
     */
    @NotNull(message = "请输入密码")
    @JsonIgnore
    private String password;

    /**
     * 姓名 需要认证
     */
    private String name;

    /**
     * 身份证号码 需要认证
     */
    @Column(name = "id_card")
    private String idCard;

    /**
     * 地区
     */
    private String address;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别 0表示男性，1表示女性
     */
    private Integer sex;

    /**
     * 头像路径
     */
    @Column(name = "head_image")
    private String headImage;

    /**
     * 生日
     */
    private Date birthday;

    @Override
    public String toString() {
        return super.toString();
    }
}