package com.lyz.demo5.utils;

import com.lyz.demo5.model.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;


//@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtTokenUtils {

    @Value("${jwt.tokenPrefix}")
    public  String tokenPrefix; // = "Bearer ";
    @Value("${jwt.tokenHeader}")
    public  String tokenHeader; //= "Authorization";
    //令牌密码
    @Value("${jwt.secret}")
    private String secret; //="LSISDFOISDOFINN";
    // 过期时间 毫秒
    @Value("${jwt.expiration}")
    private long expiration; //=60*1000;


    private String header;
    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String generateToken(Map<String,Object> claims){
        Date expirationDate = new Date(System.currentTimeMillis()+expiration);
        String token = Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512,secret).compact();
        return token;
    }
    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims getClaimsFromToken(String token){
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e){
            claims = null;
        }
        return claims;
    }

    /**
     * 生成令牌
     *
     * @param userDetails 用户
     * @return 令牌
     */
    public String generateToken(UserDetails userDetails){
        Map<String ,Object> claims = new HashMap<>(2);
        claims.put("username", userDetails.getUsername());
        claims.put(Claims.ISSUED_AT , new Date());

        return generateToken(claims);
    }

    /**
     * 生成令牌 带角色
     * @param userDetails
     * @return
     */
    public String generateTokenRole(UserDetails userDetails){
        Map<String ,Object> claims = new HashMap<>(10);
        claims.put("username", userDetails.getUsername());
        claims.put("userId",((JwtUser)userDetails).getId()  );
        claims.put(Claims.ISSUED_AT , new Date());
        Collection<GrantedAuthority> collection = (Collection<GrantedAuthority>) userDetails.getAuthorities();
        String roleStr = "";
        for(GrantedAuthority gra : collection){
            roleStr = roleStr+","+gra.getAuthority();

        }
        claims.put("roles",roleStr);

        return generateToken(claims);
    }

    public String generateTokenByUserName(String userName){
        Map<String ,Object> claims = new HashMap<>(2);
        claims.put("username", userName);
        claims.put(Claims.ISSUED_AT , new Date());

        return generateToken(claims);
    }
    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token){
        String username;
        try{
            Claims claims = getClaimsFromToken(token);
            //username = claims.getSubject();
            username = claims.get("username").toString();
        }catch (Exception e){
            username = null;
        }
        return username;
    }
    /**
     * 从令牌中获取用户ID
     */
    public String getUserIdFromToken(String token){
        String userId;
        try{
            Claims claims = getClaimsFromToken(token);
            //username = claims.getSubject();
            userId = claims.get("userId").toString();
        }catch (Exception e){
            userId = null;
        }
        return userId;
    }

    /**
     * 从令牌中获取roles
     * @param token
     * @return roles
     */
    public String getRolesFromToken(String token){
        String roles;
        try{
            Claims claims = getClaimsFromToken(token);
            //username = claims.getSubject();
            roles = claims.get("roles").toString();
        }catch (Exception e){
            roles = null;
        }
        return roles;
    }
    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
    /**
     * 刷新令牌
     *
     * @param token 原令牌
     * @return 新令牌
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put(Claims.ISSUED_AT, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }
    /**
     * 验证令牌
     *
     * @param token       令牌
     * @param userDetails 用户
     * @return 是否有效
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    /**
     * 验证令牌
     * @param token 令牌
     * @param name  用户名
     * @return
     */
    public Boolean validateToken(String token, String name) {

        String username = getUsernameFromToken(token);
        return (username.equals(name) && !isTokenExpired(token));
    }



}
