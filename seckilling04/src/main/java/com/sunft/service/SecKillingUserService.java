package com.sunft.service;

import com.sunft.dao.SecKillingUserDao;
import com.sunft.domain.SecKillingUser;
import com.sunft.exception.GlobalException;
import com.sunft.redis.RedisService;
import com.sunft.redis.SecKillingUserKey;
import com.sunft.result.CodeMsg;
import com.sunft.util.MD5Util;
import com.sunft.util.UUIDUtil;
import com.sunft.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by sunft on 2018/5/27.
 * 秒杀系统用户服务
 */
@Service
public class SecKillingUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    private SecKillingUserDao secKillingUserDao;

    @Autowired
    private RedisService redisService;

    /**
     * 根据id获取用户信息
     * @param id 根据id获取用户信息
     * @return
     */
    public SecKillingUser getById(long id) {
        return secKillingUserDao.getById(id);
    }

    /**
     * 根据token获取用户信息,直接从缓存中获取
     * @param token
     * @return
     */
    public SecKillingUser getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)) {
            return null;
        }
        SecKillingUser user = redisService.get(SecKillingUserKey.token, token, SecKillingUser.class);

        if(user != null) {
            //重新生成一个与Cookie对应的用户信息,达到延长有效期的目的
            addCookie(response, user);
        }

        return user;
    }

    /**
     * 登录方法,出现异常直接往外抛即可
     * @param loginVo 登录信息
     * @return
     */
    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if(loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }

        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword(); //该密码经过一次MD5加密

        //判断手机号是否存在
        SecKillingUser user = getById(Long.parseLong(mobile));
        if(user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String dbSalt = user.getSalt();
        //通过前端传过来的密码对密码进行计算
       String calcPass = MD5Util.formPassToDBPass(formPass, dbSalt);

        //查看密码是否匹配
        if(!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }

        //生成Cookie
        addCookie(response, user);

        return true;
    }

    /**
     * 将用户和token对应起来保存到Redis缓存和Cookie中
     * @param response 响应
     * @param user 秒杀用户
     */
    public void addCookie(HttpServletResponse response, SecKillingUser user) {
        //生成token
        String token = UUIDUtil.uuid();
        //将用户与对应的token保存到缓存中
        redisService.set(SecKillingUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        //设置cookie过期时间
        cookie.setMaxAge(SecKillingUserKey.token.expireSeconds());
        //设置路径为网站根目录
        cookie.setPath("/");
        //将cookie写到客户端
        response.addCookie(cookie);
    }

}
