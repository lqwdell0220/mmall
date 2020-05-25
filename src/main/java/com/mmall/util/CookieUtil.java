package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Discription
 * @Author:liqiwen
 * @Date:2020/5/23 8:13
 */
@Slf4j
public class CookieUtil {

    private static final String COOKIE_DOMAIN=".happymmall.com";
    private static final String COOKIE_NAME="mmall_login_token";

    public static String readLoginToken(HttpServletRequest request){
        Cookie[] cks = request.getCookies();
        if (cks!=null){
            for (Cookie ck : cks) {
                log.info("read cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                if(StringUtils.equals(COOKIE_NAME,ck.getName())){
                    log.info("return cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    public static void writeLoginToken(HttpServletResponse response,String token){
        Cookie cookie = new Cookie(COOKIE_NAME,token);
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24*365);
        cookie.setHttpOnly(true);
        log.info("write cookieName:{},cookieValue:{}");
        response.addCookie(cookie);
    }

    public static void delLoginToken(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (cookies!=null) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(COOKIE_NAME,cookie.getName())) {
                    cookie.setMaxAge(0);
                    log.info("delete cookieName:{},cookieValue:{}",cookie.getName(),cookie.getValue());
                    response.addCookie(cookie);
                }
            }
        }
    }
}
