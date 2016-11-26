package com.scot.iframework.permission.utils;

import javax.validation.*;
import java.util.Set;

/**
 * bean Validation校验
 * Created by shengke on 2016/10/31.
 */
public class AnnotationValidationUtil {

    private static Validator validator;

    static {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }

    /**
     * 数据验证.
     * @param t     验证对象
     * @param <T>   泛型
     * @throws ValidationException  验证异常
     */
    public static <T> void validate(T t) throws ValidationException {
        Set<ConstraintViolation<T>> set =  validator.validate(t);
        if(set.size()>0){
            StringBuilder validateError = new StringBuilder();
            for(ConstraintViolation<T> val : set){
                validateError.append(val.getMessage() + " ;");
            }
            throw new ValidationException(validateError.toString());
        }
    }
}
