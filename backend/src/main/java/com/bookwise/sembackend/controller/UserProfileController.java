package com.bookwise.sembackend.controller;

import com.bookwise.sembackend.db_model.User;
import com.bookwise.sembackend.model.ResultBox;
import com.bookwise.sembackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/profile")
public class UserProfileController {
    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = Logger.getLogger(UserProfileController.class.getName());

    public static class Update extends ResultBox {

        public Update(boolean success, String message, User user) {
            super(success, message);
            this.user = user;
        }


        public User user;

        public static class UpdateParams {
            public String username;
            public String oldUsername;
            public String email;
            public String phone;
            public User.GENDER gender;
            public String address;
            public String interest;
        }

        public static class UpdatePasswordParams {
            public String username;
            public String newPassword;
        }
    }

    @PostMapping("/update")
    public Update update(@RequestBody Update.UpdateParams params) {
        User userFound = userRepository.findUserByUsername(params.oldUsername);
        if (userFound != null) {
            if (params.username != null) userFound.setUsername(params.username);
            if (params.email != null) userFound.setEmail(params.email);
            if (params.gender != null) userFound.setGender(params.gender);
            if (params.phone != null) userFound.setPhone(params.phone);
            if (params.interest != null) userFound.setInterest(params.interest);
            if (params.address != null) userFound.setAddress(params.address);

            try {
                userRepository.save(userFound);
            } catch (Exception e) {
                return new Update(false, e.getMessage(), null);
            }

            return new Update(true, "用户信息更新成功", userFound);
        }
        return new Update(false, "用户不存在", null);
    }

    @PostMapping("/update-password")
    public Update updatePassword(@RequestBody Update.UpdatePasswordParams params) {
        User userFound = userRepository.findUserByUsername(params.username);
        if (userFound != null && params.newPassword != null) {
            userFound.setPassword(params.newPassword);

            try {
                userRepository.save(userFound);
            } catch (Exception e) {
                return new Update(false, e.getMessage(), null);
            }

            return new Update(true, "用户密码更新成功", userFound);
        }
        return new Update(false, "用户不存在或输入有误", null);
    }
}
