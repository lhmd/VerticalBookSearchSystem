package com.bookwise.sembackend.model;

import com.bookwise.sembackend.db_model.User;
import com.bookwise.sembackend.elastic_search.ESBook;
import lombok.Getter;

import java.util.List;

@Getter
public class Login extends ResultBox {
    public static class LoginParams {
        public String username;
        public String password;

        @Override
        public String toString() {
            return "LoginParams{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }

    public Login(boolean success, User user, List<ESBook> book) {
        super(success, "");
        this.user = user;
        this.book = book;
        this.setMessage(success ? "登录成功" : "登录失败");
    }

    private User user;
    private List<ESBook> book;

    public void setUser(User user) {
        this.user = user;
    }

    public void setBook(List<ESBook> book) {
        this.book = book;
    }
}
