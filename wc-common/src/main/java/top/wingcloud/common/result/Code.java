package top.wingcloud.common.result;

/**
 * 返回请求结果类
 */
public enum Code {

    /**
     * 请求失败
     */
    ERROR(0,"请求失败"),
    /**
     * 请求成功
     */
    SUCCESS(1,"请求成功"),
    /**
     * 请重新登录
     */
    NO_PERMISSION(403,"请重新登录");

    private int status;

    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    Code(int status,String message){
        this.status = status;
        this.message = message;
    }

}
