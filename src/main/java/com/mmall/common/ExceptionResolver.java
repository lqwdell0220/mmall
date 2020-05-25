package com.mmall.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Discription
 * @Author:liqiwen
 * @Date:2020/5/23 22:11
 */
@Slf4j
@Component
public class ExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        log.error("{} Exception",request.getRequestURI(),ex);
        ModelAndView mv = new ModelAndView(new MappingJacksonJsonView());
        mv.addObject("status",ResponseCode.ERROR.getCode());
        mv.addObject("msg","接口异常，详情请查看服务端的日志记录");
        mv.addObject("data",ex.toString());

        return mv;
    }
}
