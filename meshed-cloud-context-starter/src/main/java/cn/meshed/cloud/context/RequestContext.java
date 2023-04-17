package cn.meshed.cloud.context;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
public class RequestContext {

    public static HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
    }
}
