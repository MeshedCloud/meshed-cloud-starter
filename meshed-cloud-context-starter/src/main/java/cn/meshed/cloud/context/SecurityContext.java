package cn.meshed.cloud.context;

import cn.meshed.cloud.dto.Operator;
import cn.meshed.cloud.exception.security.AuthenticationException;

import java.util.Optional;

/**
 * <h1>安全上下文</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
public class SecurityContext {

    private static ThreadLocal<Operator> local = new ThreadLocal<>();

    public static void setOperator(Operator operator){
        local.set(operator);
    }

    public static void clear() {
        local.remove();
    }

    public static Operator getOperator(){
        return Optional.ofNullable(local.get())
                .orElseThrow(() -> new AuthenticationException("未登入,无用户信息"));
    }

    public static Operator getOperatorOfNullable(){
        return local.get();
    }

    public static String getOperatorString(){
        return getOperator().toString();
    }

    public static String getOperatorStringOfNullable(){
        return local.get().toString();
    }

    public static Long getUserId(){
        return Long.parseLong(getOperator().getId());
    }

    public static Long getUserIdOfNullable(){
        return Optional.ofNullable(local.get())
                .map(Operator::getId).map(Long::parseLong).orElse(null);
    }

    public static String getUserIdString(){
        return getOperator().getId();
    }

    public static String getUserIdStringOfNullable(){
        return Optional.ofNullable(local.get())
                .map(Operator::getId).orElse(null);
    }

}
