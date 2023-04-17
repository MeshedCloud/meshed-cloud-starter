package cn.meshed.cloud.security.impl;

import cn.meshed.cloud.exception.security.AuthenticationException;
import cn.meshed.cloud.security.AccessTokenService;
import cn.meshed.cloud.security.config.SecurityConfig;
import com.alibaba.cola.exception.SysException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;


/**
 * <h1></h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements AccessTokenService {

    private final SecurityConfig securityConfig;

    @Override
    public String generateToken(String payloadStr) {
        try {
            //创建JWS头，设置签名算法和类型
            JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.HS256).
                    type(JOSEObjectType.JWT)
                    .build();
            //将负载信息封装到Payload中
            Payload payload = new Payload(payloadStr);
            //创建JWS对象
            JWSObject jwsObject = new JWSObject(jwsHeader, payload);
            if (securityConfig == null || securityConfig.getSecret() == null){
                throw new SysException("未配置安全密钥");
            }
            //创建HMAC签名器
            JWSSigner jwsSigner = new MACSigner(securityConfig.getSecret());
            //签名
            jwsObject.sign(jwsSigner);
            return jwsObject.serialize();
        } catch (Exception e){
            throw new AuthenticationException("签名生成失败");
        }
    }

    @Override
    public String verifyToken(String token) {
        try {
            //从token中解析JWS对象
            JWSObject jwsObject = JWSObject.parse(token);
            if (securityConfig == null || securityConfig.getSecret() == null){
                throw new SysException("未配置安全密钥");
            }
            //创建HMAC验证器
            JWSVerifier jwsVerifier = new MACVerifier(securityConfig.getSecret());
            if (!jwsObject.verify(jwsVerifier)) {
                throw new AuthenticationException("token签名不合法！");
            }
            return jwsObject.getPayload().toString();
        } catch (Exception e){
            throw new AuthenticationException("签名校验失败");
        }
    }

}
