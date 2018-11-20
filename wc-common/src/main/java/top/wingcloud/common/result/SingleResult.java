package top.wingcloud.common.result;

/**
 * @param <T>
 */
public class SingleResult<T> extends Result {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 请求成功调用
     *
     * @param data
     * @return
     */
    public static <T> SingleResult<T> buildSuccess(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setCode(Code.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 请求失败调用
     *
     * @param message
     * @return
     */
    public static <T> SingleResult<T> buildSuccess(Code code, String message) {
        SingleResult result = new SingleResult();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 身份验证失败
     *
     * @param data
     * @return
     */
    public static <T> SingleResult<T> buildAuthentication(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setCode(Code.NO_PERMISSION);
        result.setData(data);
        return result;
    }

    /**
     * 请求成功调用
     *
     * @return
     */
    public static <T> SingleResult<T> buildSuccessWithoutData() {
        return buildSuccess(null);
    }

    /**
     * 请求成功调用
     *
     * @param message
     * @return
     */
    public static <T> SingleResult<T> buildSuccessWithMessage(String message) {
        SingleResult result = new SingleResult();
        result.setCode(Code.SUCCESS);
        result.setMessage(message);
        return result;
    }

    /**
     * 请求失败调用
     *
     * @param message
     * @return
     */
    public static <T> SingleResult<T> buildFailure(Code code, String message) {
        SingleResult result = new SingleResult();
        result.setCode(code);
        result.setMessage(message);
        result.setData("");
        return result;
    }

    /**
     * 请求失败调用
     * 使用默认的消息字符串
     *
     * @return
     */
    public static <T> SingleResult<T> buildFailure() {
        SingleResult result = new SingleResult();
        result.setCode(Code.ERROR);
        return result;
    }

}
