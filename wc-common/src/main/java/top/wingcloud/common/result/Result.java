package top.wingcloud.common.result;

import com.alibaba.fastjson.JSON;

public class Result {

    private Code code;

    private String message;

    public int getCode() {
        return code.getStatus();
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public String getMessage() {
        return message != null?message:code.getMessage();
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
