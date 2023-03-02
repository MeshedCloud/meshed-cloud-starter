package cn.meshed.context;

import cn.meshed.cloud.dto.Operator;
import cn.meshed.exception.security.AuthenticationException;

/**
 * <h1>安全上下文</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
public class SecurityContext {


    public static Operator getOperatorWithNullable(){
        return new Operator(1000L,"sys");
    }
    public static Operator getOperator(){
        Operator operator = new Operator(1000L, "sys");
        if (operator == null){
            throw new AuthenticationException("未登入异常");
        }
        return operator;
    }

    public static String getOperatorString(){
        return getOperator().toString();
    }

    public static Long getOperatorUserId(){
        return getOperator().getId();
    }

    public static Long getOperatorUserIdWithNullable(){
        Operator operator = getOperatorWithNullable();
        return operator == null ? null : operator.getId();
    }

    public static String getOperatorString(String type){
        Operator operator = getOperatorWithNullable();
        return operator == null ? null : operator.toString(type);
    }
}
