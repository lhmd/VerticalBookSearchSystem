package com.bookwise.sembackend.model.api;

import com.alibaba.fastjson2.JSONObject;
import com.bookwise.sembackend.dev.BaseErrorInfoInterface;
import com.bookwise.sembackend.dev.ExceptionEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultBox {
    @Deprecated
    private boolean success;

    private String message;
    private String code;
    private Object result;

    public ResultBox(BaseErrorInfoInterface errorInfoInterface, Object result) {
        this.code = errorInfoInterface.getResultCode();
        this.message = errorInfoInterface.getResultMsg();
        this.result = result;
    }

    @Deprecated
    public ResultBox(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * 成功
     *
     * @return
     */
    public static ResultBox success() {
        return success(null);
    }

    /**
     * 成功
     *
     * @param data
     * @return
     */
    public static ResultBox success(Object data) {
        ResultBox rb = new ResultBox();
        rb.setSuccess(true);
        rb.setCode(ExceptionEnum.SUCCESS.getResultCode());
        rb.setMessage(ExceptionEnum.SUCCESS.getResultMsg());
        rb.setResult(data);
        return rb;
    }

    /**
     * 失败
     */
    public static ResultBox error(BaseErrorInfoInterface errorInfo) {
        ResultBox rb = new ResultBox();
        rb.setSuccess(false);
        rb.setCode(errorInfo.getResultCode());
        rb.setMessage(errorInfo.getResultMsg());
        rb.setResult(null);
        return rb;
    }

    public static ResultBox error(BaseErrorInfoInterface errorInfo, String message) {
        ResultBox rb = new ResultBox();
        rb.setSuccess(false);
        rb.setCode(errorInfo.getResultCode());
        rb.setMessage(message);
        rb.setResult(null);
        return rb;
    }

    /**
     * 失败
     */
    public static ResultBox error(String code, String message) {
        ResultBox rb = new ResultBox();
        rb.setSuccess(false);
        rb.setCode(code);
        rb.setMessage(message);
        rb.setResult(null);
        return rb;
    }

    /**
     * 失败
     */
    public static ResultBox error(String message) {
        ResultBox rb = new ResultBox();
        rb.setSuccess(false);
        rb.setCode("-1");
        rb.setMessage(message);
        rb.setResult(null);
        return rb;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
