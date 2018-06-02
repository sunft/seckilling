package com.sunft.vo;

import com.sunft.validator.IsMobile;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;


/**
 * Created by sunft on 2018/5/27.
 * 登录VO
 */
public class LoginVo {

    @NotNull(message = "手机号码不能为空")
    @IsMobile(message = "手机号码格式错误")
    private String mobile;

    @NotNull(message = "密码不能为空")
    @Length(min = 32, message = "密码长度不够")
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginVo{" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
