package com.wsl.pojo;

import java.io.Serializable;

/**
 *
 * @ClassName: Result
 * @Description: 响应结果模型
 */
public class Result implements Serializable {

    /**
     * serialVersionUID: 序列化ID
     */
    private static final long serialVersionUID = -6017430993588164012L;
    /**
     * 结果码
     */
    private String status = FAIL;
    /**
     * 消息
     */
    private String message = FAIL_MESSAGE;
    /**
     * 数据
     */
    private Object data = DATA_NULL;
    /**
     * 成功返回码
     */
    public final static String SUCCESS = "1";
    /**
     * 失败返回码
     */
    public final static String FAIL = "0";
    /**
     * 成功返回消息
     */
    public final static String SUCCESS_MESSAGE = "成功";
    /**
     * 失败返回消息
     */
    public final static String FAIL_MESSAGE = "失败";
    /**
     * 数据空
     */
    public final static String DATA_NULL = "";



    public Result() {
        super();
    }
    public Result(String status, String message, Object data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

}
