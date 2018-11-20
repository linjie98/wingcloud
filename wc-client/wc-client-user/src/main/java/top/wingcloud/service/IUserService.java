package top.wingcloud.service;

import top.wingcloud.common.result.SingleResult;
import top.wingcloud.domain.User;
import top.wingcloud.request.LoginRequest;
import top.wingcloud.request.RegisterRequest;
import top.wingcloud.response.TokenResponse;

import javax.servlet.http.HttpServletRequest;

public interface IUserService {
    /**
     * 登录接口
     * @param loginRequest
     * @param req
     * @return
     */
    public SingleResult<TokenResponse> login(LoginRequest loginRequest,HttpServletRequest req);

    /**
     * 注册接口
     * @param registerRequest
     * @return
     */
    public SingleResult<TokenResponse> register(RegisterRequest registerRequest);

    /**
     * 获取用户信息接口
     * @param req
     * @return
     */
    public User getusermsg(HttpServletRequest req);

    /**
     * 注销登录接口
     * @param req
     * @return
     */
    public String logout(HttpServletRequest req);
}
