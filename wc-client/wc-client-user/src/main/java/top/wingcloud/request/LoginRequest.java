package top.wingcloud.request;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 登录请求的参数
 */
public class LoginRequest {

    @NotEmpty
    private String user_name;

    @NotEmpty
    private String user_password;

    private String token;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
