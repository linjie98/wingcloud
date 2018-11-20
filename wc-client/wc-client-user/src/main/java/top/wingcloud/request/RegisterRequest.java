package top.wingcloud.request;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 注册请求的参数
 */
public class RegisterRequest {

    @NotEmpty
    private String user_name;

    @NotEmpty
    private String user_password;

    @NotEmpty
    private String user_email;

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

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}