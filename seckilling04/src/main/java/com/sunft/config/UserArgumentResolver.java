package com.sunft.config;

import com.sunft.domain.SecKillingUser;
import com.sunft.service.SecKillingUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户参数解析器,用于获取Cookie中的token信息
 * Created by sunft on 2018/6/3.
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private SecKillingUserService userService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //获取参数的类类型
        Class<?> clazz = methodParameter.getParameterType();
        //当返回true,会调用resolveArgument进行解析
        return clazz == SecKillingUser.class;
    }

    /**
     * 解析参数,以后获取token的方式如果改变了,直接修改该方法即可
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);

        String paramToken = request.getParameter(SecKillingUserService.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request, SecKillingUserService.COOKIE_NAME_TOKEN);

        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return null;
        }

        String token = StringUtils.isEmpty(paramToken)? cookieToken : paramToken;
        //根据cookie返回用户
        return userService.getByToken(response, token);
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
