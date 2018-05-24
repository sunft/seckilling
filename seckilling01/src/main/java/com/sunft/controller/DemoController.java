package com.sunft.controller;

import com.sunft.domain.User;
import com.sunft.result.CodeMsg;
import com.sunft.result.Result;
import com.sunft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by sunft on 2018/5/21.
 * demo测试类
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }
    //1.rest api json输出 2.页面
    @GetMapping("/hello")
    @ResponseBody
    public Result<String> hello() {
        return Result.success("hello,sunft");
    }

    @GetMapping("/helloError")
    @ResponseBody
    public Result<String> helloError() {
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    @GetMapping("/thymeleaf")
    public String  thymeleaf(Model model) {
        model.addAttribute("name", "sunft");
        return "hello";
    }

    @GetMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet() {
        User user = userService.getById(1);
        return Result.success(user);
    }

    @GetMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx() {
        userService.tx();
        return Result.success(true);
    }

}
