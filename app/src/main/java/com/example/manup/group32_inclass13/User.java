package com.example.manup.group32_inclass13;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by manup on 4/23/2018.
 */

public class User implements Serializable{

    String userID, user_email, userKey, userName;

    public User() {
    }



    public User(String userName, String userID, String userKey) {

       this.userName = userName;
        this.userID = userID;
        this.userKey = userKey;

    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +

                ", userKey='" + userKey + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("userName", userName);
        result.put("userID", userID);
        result.put("userKey", userKey);
        return result;
    }

}
