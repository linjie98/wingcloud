package top.wingcloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import top.wingcloud.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wingcloud.common.result.SingleResult;
import top.wingcloud.domain.User;
import top.wingcloud.request.LoginRequest;
import top.wingcloud.response.TokenResponse;
import top.wingcloud.service.IUserService;
import top.wingcloud.service.impl.UserServiceimpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 用户请求处理控制器
 */
@RestController
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 用户登录
     * @param request
     * @param result
     * @param req
     * @return
     */
    @RequestMapping("login")
    public SingleResult<TokenResponse> login(@Valid @RequestBody LoginRequest request, BindingResult result,HttpServletRequest req){
        //必须要调用validate方法才能实现输入参数的合法性校验
        System.out.println("-------------test-----------");
        validate(result);
        return userService.login(request,req);
    }

    /**
     * 获取用户信息
     * @param req
     * @return
     */
    @RequestMapping("getusermsg")
    public User GetUserMsg(HttpServletRequest req){
        return userService.getusermsg(req);
    }

    /**
     * 用户注销
     * @param req
     * @return
     */
    @RequestMapping("logout")
    public String logout(HttpServletRequest req){
        return userService.logout(req);
    }
}
