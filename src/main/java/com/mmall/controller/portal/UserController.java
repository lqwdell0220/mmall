package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session, HttpServletResponse httpServletResponse){
int a =2/0;
        ServerResponse<User> response = iUserService.login(username,password);
        if(response.isSuccess()){
            //session.setAttribute(Const.CURRENT_USER,response.getData());
            CookieUtil.writeLoginToken(httpServletResponse,session.getId());
            RedisShardedPoolUtil.setEx(session.getId(), JsonUtil.obj2String(response.getData()),Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
        }
        return response;

    }

    @RequestMapping(value = "logout.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> logout(HttpServletRequest request,HttpServletResponse response){
        //session.removeAttribute(Const.CURRENT_USER);
        String loginToken = CookieUtil.readLoginToken(request);
        CookieUtil.delLoginToken(request,response);
        RedisShardedPoolUtil.del(loginToken);
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "register.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(User user){
        return iUserService.register(user);
    }

    @RequestMapping(value = "check_valid.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValid(String str,String type){
        return iUserService.checkValid(str,type);
    }

    @RequestMapping(value = "get_user_info.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpServletRequest request){
        //Object userObject = session.getAttribute(Const.CURRENT_USER);
        String loginToken = CookieUtil.readLoginToken(request);
        String userJson = RedisShardedPoolUtil.get(loginToken);
        if (userJson==null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        User user = JsonUtil.string2Obj(userJson,User.class);
        if (user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return ServerResponse.createBySuccess(user);
    }

    @RequestMapping(value = "forget_get_question.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username){
        return iUserService.selectQuestion(username);
    }

    @RequestMapping(value = "forget_check_answer.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
        return iUserService.checkAnswer(username,question,answer);
    }

    @RequestMapping(value = "forget_reset_password.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetRestPassword(String username,String passwordNew,String forgetToken){
        return iUserService.forgetResetPassword(username,passwordNew,forgetToken);
    }

    @RequestMapping(value = "reset_password.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpServletRequest request,String passwordOld,String passwordNew){
        //Object userObject = session.getAttribute(Const.CURRENT_USER);
        String loginToken = CookieUtil.readLoginToken(request);
        String userJson = RedisShardedPoolUtil.get(loginToken);
        if (userJson==null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        User user = JsonUtil.string2Obj(userJson,User.class);
        if (user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }

        return iUserService.resetPassword(passwordOld,passwordNew,user);
    }

    @RequestMapping(value = "update_information.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> update_information(HttpServletRequest request,User user){
        //Object userObject = session.getAttribute(Const.CURRENT_USER);
        String loginToken = CookieUtil.readLoginToken(request);
        String userJson = RedisShardedPoolUtil.get(loginToken);
        if (userJson==null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        User userObject = JsonUtil.string2Obj(userJson,User.class);
        if (userObject==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }

        user.setId(userObject.getId());
        ServerResponse<User> response = iUserService.updateInformation(user);
        if(response.isSuccess()){
            response.getData().setUsername(userObject.getUsername());
            RedisShardedPoolUtil.setEx(loginToken, JsonUtil.obj2String(response.getData()),Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
        }
        return response;
    }

    @RequestMapping(value = "get_information.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> get_information(HttpServletRequest request){
        //Object userObject = session.getAttribute(Const.CURRENT_USER);
        String loginToken = CookieUtil.readLoginToken(request);
        String userJson = RedisShardedPoolUtil.get(loginToken);
        if (userJson==null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        User userObject = JsonUtil.string2Obj(userJson,User.class);
        if (userObject==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录,需要强制登录status=10");
        }
        return iUserService.getInformation(userObject.getId());
    }
}
