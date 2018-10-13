package com.heyi.core.common;

public class ApiResponse<D> {

    private ResponseStatus status;
    /**
     * Error message
     */
    private String errMsg;
    private D data;

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus(ResponseStatus.OK);
        response.setData(data);
        return response;
    }

    public static ApiResponse error(String errMsg) {
        ApiResponse response = new ApiResponse();
        response.setStatus(ResponseStatus.FAILED);
        response.setErrMsg(errMsg);
        return response;
    }

    public static ApiResponse notFound(String errMsg) {
        ApiResponse response = new ApiResponse();
        response.setStatus(ResponseStatus.NOTFOUND);
        response.setErrMsg(errMsg);
        return response;
    }


    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }
}