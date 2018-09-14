package com.example.manup.group32_inclass13;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by manup on 4/24/2018.
 */

public class Message implements Serializable{

    String userId, text, userName,msg_key,toname;
    Boolean isRead;
    String date;

    public Message() {
    }

    public Message(String text, String userName, Boolean isRead, String date) {
        this.text = text;
        this.userName = userName;
        this.isRead = isRead;
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMsg_key() {
        return msg_key;
    }

    public void setMsg_key(String msg_key) {
        this.msg_key = msg_key;
    }

    public String getToname() {
        return toname;
    }

    public void setToname(String toname) {
        this.toname = toname;
    }

    @Override
    public String toString() {
        return "Message{" +
                "userId='" + userId + '\'' +
                ", text='" + text + '\'' +
                ", userName='" + userName + '\'' +
                ", msg_key='" + msg_key + '\'' +
                ", toname='" + toname + '\'' +
                ", isRead=" + isRead +
                ", date='" + date + '\'' +
                '}';
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("text", text);
        result.put("userName",userName);
        result.put("isRead", isRead);
        result.put("date", date);
        result.put("msg_key",msg_key);
        result.put("toname", toname);
        return result;

    }
}

