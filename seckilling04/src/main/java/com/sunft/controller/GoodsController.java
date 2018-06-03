package com.sunft.controller;

import com.sunft.domain.SecKillingUser;
import com.sunft.redis.RedisService;
import com.sunft.service.SecKillingUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * 商品列表
 * Created by sunft on 2018/6/3.
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private SecKillingUserService userService;

    @Autowired
    private RedisService redisService;

    /**
     * 跳转到商品列表页
     * @param model 模型
     * @param cookieToken 从浏览器穿过来的token
     * @param paramToken 从移动端直接用参数传过来的token,移动端优先
     * @return
     */
    /**
     * 跳转到商品列表页
     * @param model 模型
     * @param user 用户
     * @return 商品列表页
     */
    @GetMapping("/to_list")
    public String list(Model model, SecKillingUser user) {
        model.addAttribute("user", user);
        return "goods_list";
    }

}
