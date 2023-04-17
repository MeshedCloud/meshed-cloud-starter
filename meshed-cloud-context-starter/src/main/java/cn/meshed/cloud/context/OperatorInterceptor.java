package cn.meshed.cloud.context;

import cn.meshed.cloud.context.config.ServiceConfig;
import cn.meshed.cloud.dto.Operator;
import cn.meshed.cloud.exception.security.SysSecurityException;
import cn.meshed.cloud.security.AccessTokenService;
import cn.meshed.cloud.security.config.SecurityConfig;
import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class OperatorInterceptor implements HandlerInterceptor {
    private final ServiceConfig serviceConfig;
    private final SecurityConfig securityConfig;
    @Value("${spring.profiles.active:prod}")
    private String env;
    private final AccessTokenService accessTokenService;
    private final AntPathMatcher antPathMatcher;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String sign = request.getHeader("sign");
        /**
         * 模拟数据优先处理
         * 存在签名校验
         * 非排除接口禁止访问抛出
         */
        if (isMock()) {
            //模拟用户，仅在测试使用
            Operator operator = mockOperator();
            SecurityContext.setOperator(operator);
        } else if (StringUtils.isNotBlank(sign)){
            //校验签名
            String data = accessTokenService.verifyToken(sign);
            Operator operator = parsingOperator(data);
            //解析数据
            SecurityContext.setOperator(parsingOperator(data));
        } else if (!isExcludeUris(request)){
            //不是放行接口抛出异常
            throw new SysSecurityException("服务禁止访问");
        }
        return true;
    }

    private boolean isMock() {
        if (serviceConfig.getMock() == null || serviceConfig.getMock().getEnable() == null){
            return false;
        }
        return !"prod".equals(env) && serviceConfig.getMock().getEnable();
    }

    private boolean isExcludeUris(HttpServletRequest request) {
        if (securityConfig == null){
            return false;
        }
        Set<String> excludeUris = securityConfig.getExcludeUris();
        if (CollectionUtils.isEmpty(excludeUris)){
            return false;
        }
        String requestUri = request.getRequestURI();
        log.debug("{} ",requestUri);
        return excludeUris.stream().anyMatch(excludeUri -> antPathMatcher.match(excludeUri, requestUri));
    }

    private Operator parsingOperator(String data){
        JSONObject jsonObject = JSONObject.parseObject(data);
        //目前仅存在有用户信息和无用户信息两种签名
        if (jsonObject.size() == 0){
            return null;
        }
        Operator operator = new Operator(jsonObject.getString("id"), jsonObject.getString("realName"));
        Set<String> grantedAuthority = getSet(jsonObject,"grantedAuthority");
        Set<String> grantedRole = getSet(jsonObject,"grantedRole");

        operator.setAccess(grantedAuthority);
        operator.setRoles(grantedRole);
        log.debug("操作用户: {}| {} ", operator, JSONObject.toJSONString(operator));
        return operator;
    }

    private Set<String> getSet(JSONObject jsonObject, String key) {
        String str = jsonObject.getString(key);
        if (StringUtils.isNotBlank(str)){
            return JSONObject.parseObject(str, Set.class);
        }
        return Collections.emptySet();
    }

    private Operator mockOperator() {
        ServiceConfig.MockConfig mockConfig = serviceConfig.getMock();
        Operator operator = new Operator(mockConfig.getUserId(), mockConfig.getUsername());
        if (StringUtils.isNotBlank(mockConfig.getAccess())){
            String[] split = mockConfig.getAccess().split(",");
            operator.setAccess(Arrays.stream(split).collect(Collectors.toSet()));
        }
        if (StringUtils.isNotBlank(mockConfig.getRoles())){
            String[] split = mockConfig.getRoles().split(",");
            operator.setRoles(Arrays.stream(split).collect(Collectors.toSet()));
        }
        log.warn("采用模拟用户: {} | {} ", operator, JSONObject.toJSONString(operator));
        return operator;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        SecurityContext.clear();
    }
}
