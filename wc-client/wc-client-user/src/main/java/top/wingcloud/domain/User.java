package top.wingcloud.domain;


import top.wingcloud.common.model.BaseModel;

/**
 * 用户实体类
 */
public class User extends BaseModel {
    //用户昵称
    private String user_name;
    //用户密码
    private String user_password;
    //用户邮箱
    private String user_email;
    //用户权限级别
    private Integer user_authority;
    //用户在线状态
    private Integer user_online_state;
    //用户是否永久注销状态
    private Integer user_loggedoff_state;

    public User(){}

    //getter or setter
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

    public Integer getUser_authority() {
        return user_authority;
    }

    public void setUser_authority(Integer user_authority) {
        this.user_authority = user_authority;
    }

    public Integer getUser_online_state() {
        return user_online_state;
    }

    public void setUser_online_state(Integer user_online_state) {
        this.user_online_state = user_online_state;
    }

    public Integer getUser_loggedoff_state() {
        return user_loggedoff_state;
    }

    public void setUser_loggedoff_state(Integer user_loggedoff_state) {
        this.user_loggedoff_state = user_loggedoff_state;
    }
}
