package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @Discription
 * @Author:liqiwen
 * @Date:2020/4/20 11:29
 */
@Controller
@RequestMapping("/manage/user")
public class UserManageController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value="login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response = iUserService.login(username,password);
        if(response.isSuccess()){
            if(response.getData().getRole()==Const.Role.ROLE_ADMIN){
                session.setAttribute(Const.CURRENT_USER,response.getData());
                return ServerResponse.createBySuccessMessage("登录后台成功");
            }else{
                return ServerResponse.createByErrorMessage("当前用户非系统管理员");
            }
        }

        return response;
    }
}
