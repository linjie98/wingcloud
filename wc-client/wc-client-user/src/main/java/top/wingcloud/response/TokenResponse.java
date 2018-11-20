package top.wingcloud.response;

import top.wingcloud.common.model.response.BaseResponse;

/**
 * token
 */
public class TokenResponse extends BaseResponse{

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
