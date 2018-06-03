package com.sunft.service;

import com.sunft.dao.SecKillingUserDao;
import com.sunft.domain.SecKillingUser;
import com.sunft.exception.GlobalException;
import com.sunft.result.CodeMsg;
import com.sunft.util.MD5Util;
import com.sunft.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sunft on 2018/5/27.
 * 秒杀系统用户服务
 */
@Service
public class SecKillingUserService {

    @Autowired
    private SecKillingUserDao secKillingUserDao;

    /**
     * 根据id获取用户信息
     * @param id 根据id获取用户信息
     * @return
     */
    public SecKillingUser getById(long id) {
        return secKillingUserDao.getById(id);
    }

    /**
     * 登录方法,出现异常直接往外抛即可
     * @param loginVo 登录信息
     * @return
     */
    public boolean login(LoginVo loginVo) {
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

        return true;
    }
}
