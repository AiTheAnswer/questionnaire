package com.allen.questionnaire.resp;

public class Resp {
    //状态码
    private Integer statusCode;
    //错误原因
    private String reason;

    private Object object;

    public Resp() {
    }

    public Resp(Integer statusCode, String reason, Object object) {
        this.statusCode = statusCode;
        this.reason = reason;
        this.object = object;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
