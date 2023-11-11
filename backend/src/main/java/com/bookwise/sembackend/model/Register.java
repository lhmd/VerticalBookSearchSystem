package com.bookwise.sembackend.model;

import com.bookwise.sembackend.db_model.User;

public class Register extends ResultBox {
    public User user;

    public Register(boolean success, User user) {
        super(success, "");
        this.user = user;
        this.setMessage(success ? "注册成功" : "注册失败");
    }

    public Register(boolean success, String message, User user) {
        super(success, message);
        this.user = user;
    }

    public static class RegisterParams {
        public String username;
        public String password;
        public String email;
        public String phone;
        public User.GENDER gender;
        public String address;
        public String interest;

        @Override
        public String toString() {
            return "RegisterParams{" +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", email='" + email + '\'' +
                    ", phone='" + phone + '\'' +
                    ", gender='" + gender + '\'' +
                    ", address='" + address + '\'' +
                    ", interest='" + interest + '\'' +
                    '}';
        }
    }
}
