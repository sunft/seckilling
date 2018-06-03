package com.sunft.controller;

import com.sunft.result.CodeMsg;
import com.sunft.result.Result;
import com.sunft.service.SecKillingUserService;
import com.sunft.util.ValidatorUtil;
import com.sunft.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * Created by sunft on 2018/5/27.
 * 登录控制器
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private SecKillingUserService userService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    /**
     * 注解@Valid用于参数校验
     * @param loginVo
     * @return
     */
    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(@Valid LoginVo loginVo) {
        logger.info(loginVo.toString());
        //登录
        userService.login(loginVo);
        return Result.success(true);
    }

}
