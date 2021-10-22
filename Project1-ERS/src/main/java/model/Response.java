package model;

/**
 * Response model class. Contains the properties, getters, setters and toString
 * @author      Alberto Figallo
 * @version     1.5
 * @since       2021-09-07
 * */
public class Response {
    private String message;
    private Boolean success;
    private Object data;

    public Response() {
    }

    public Response(String message, Boolean success, Object data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}
