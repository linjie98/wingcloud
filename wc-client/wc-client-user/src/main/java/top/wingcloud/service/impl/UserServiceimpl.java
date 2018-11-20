package top.wingcloud.service.impl;

import top.wingcloud.common.redis.Redis;
import top.wingcloud.common.result.Code;
import top.wingcloud.common.result.SingleResult;
import top.wingcloud.common.service.BaseService;
import top.wingcloud.domain.User;
import top.wingcloud.mapper.UserMapper;
import top.wingcloud.request.LoginRequest;
import top.wingcloud.request.RegisterRequest;
import top.wingcloud.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.wingcloud.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class UserServiceimpl extends BaseService implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private Redis redis;
    /**
     * 用户登录接口
     * @param request
     * @return
     */
    public SingleResult<TokenResponse> login(LoginRequest request,HttpServletRequest req) {
        List<User> userList = userMapper.selectUser(request.getUser_name(), request.getUser_password());
        HttpSession session = req.getSession();
        //String username = (String)session.getAttribute("username");
        //System.out.println(username);
        System.out.println("走数据库了");
        if (null != userList && userList.size() > 0) {
            String token = getToken(request.getUser_name(), request.getUser_password());
            session.setAttribute("username", request.getUser_name() + System.currentTimeMillis());
            redis.set(request.getUser_name(),token);
            TokenResponse response = new TokenResponse();
            response.setToken(token);
            System.out.println(SingleResult.buildSuccess(response));
            return SingleResult.buildSuccess(response);
        } else {
            return SingleResult.buildFailure(Code.ERROR, "用户名或密码输入不正确！");
        }
    }

    /**
     * 用户注册接口
     * @param registerRequest
     * @return
     */
    @Override
    public SingleResult<TokenResponse> register(RegisterRequest registerRequest) {
        return null;
    }

    /**
     * 获取用户信息
     * @param req
     * @return
     */
    @Override
    public User getusermsg(HttpServletRequest req) {
        User user = new User();
        user.setUser_name((String) req.getSession().getAttribute("username"));
        return user;
    }

    /**
     * 用户注销
     * @param req
     * @return
     */
    @Override
    public String logout(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (session.getAttribute("username")!=null){
            session.removeAttribute("username");
            return "用户已退出";
        }
        return "用户已退出";
    }


}
