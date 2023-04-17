package cn.meshed.cloud.advice;

import cn.meshed.cloud.exception.security.AuthenticationException;
import cn.meshed.cloud.exception.security.SysSecurityException;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.exception.SysException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public Response exception(Exception exception){
        log.error("系统出现异常：{}",exception.getMessage(),exception);
        return Response.buildFailure("500",exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = NullPointerException.class)
    public Response exception(NullPointerException exception){
        log.error("系统出现异常：NPE",exception);
        return Response.buildFailure("500","系统产生异常| 错误代码: NPE | 请联系管理员处理");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = SysException.class)
    public Response sysException(SysException exception){
        log.error("业务异常：{}",exception.getMessage(),exception);
        return Response.buildFailure(exception.getErrCode(),exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = AuthenticationException.class)
    public Response sysException(AuthenticationException exception){
        log.error("业务异常：{}",exception.getMessage(),exception);
        return Response.buildFailure(exception.getErrCode(),exception.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = SysSecurityException.class)
    public Response sysException(SysSecurityException exception){
        log.error("业务异常：{}",exception.getMessage(),exception);
        return Response.buildFailure(exception.getErrCode(),exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BindException.class)
    public Response bindException(BindException exception){
        log.debug("参数校验失败：{}",exception.getMessage(),exception);
        List<ObjectError> allErrors = exception.getAllErrors();
        if (CollectionUtils.isNotEmpty(allErrors)){
            List<String> errs = allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
            return Response.buildFailure("400",String.join(",", errs));
        }
        return Response.buildFailure("400",exception.getMessage());
    }

}
