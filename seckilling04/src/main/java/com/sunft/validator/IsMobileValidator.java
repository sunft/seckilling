package com.sunft.validator;

import com.sunft.util.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义校验器
 * Created by sunft on 2018/6/2.
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;

    @Override
    public void initialize(IsMobile isMobile) {
        required = isMobile.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        if(required) {
            return ValidatorUtil.isMobile(s);
        }else {
//            if(StringUtils.isEmpty(s)) {
//                return true;
//            }else {
//                return ValidatorUtil.isMobile(s);
//            }
            //上述两行代码简写为这种形式
            return StringUtils.isEmpty(s) || ValidatorUtil.isMobile(s);
        }
    }
}
