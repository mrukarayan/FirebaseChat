package co.syntax.firebasedemo.model;

/**
 * Created by rukarayan on 24-Jan-17.
 */

public class Message {
    public String senderId;
    public String receipentId;
    public String message;
    public String time;

    public Message() {

    }

    public Message(String senderId, String receipentId, String message, String time) {
        this.senderId = senderId;
        this.receipentId = receipentId;
        this.message = message;
        this.time = time;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceipentId() {
        return receipentId;
    }

    public void setReceipentId(String receipentId) {
        this.receipentId = receipentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
