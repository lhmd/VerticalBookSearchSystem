package com.bookwise.sembackend.controller.v2;

import com.bookwise.sembackend.db_model.User;
import com.bookwise.sembackend.dev.ExceptionEnum;
import com.bookwise.sembackend.model.api.ResultBox;
import com.bookwise.sembackend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping("/v2/profile")
public class UserProfileControllerV2 {
    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = Logger.getLogger(UserProfileControllerV2.class.getName());

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
    public ResultBox update(@RequestBody Update.UpdateParams params) {
        User userFound = userRepository.findUserByUsername(params.oldUsername);
        User userExist = userRepository.findUserByUsername(params.username);

        if (userExist != null) return ResultBox.error(ExceptionEnum.USER_EXIST);

        if (userFound != null) {
            if (params.username != null) userFound.setUsername(params.username);
            if (params.email != null) userFound.setEmail(params.email);
            if (params.gender != null) userFound.setGender(params.gender);
            if (params.phone != null) userFound.setPhone(params.phone);
            if (params.interest != null) userFound.setInterest(params.interest);
            if (params.address != null) userFound.setAddress(params.address);

            userRepository.save(userFound);

            return ResultBox.success(userFound);
        }
        return ResultBox.error(ExceptionEnum.NOT_FOUND, "用户不存在");
    }

    @PostMapping("/update-password")
    public ResultBox updatePassword(@RequestBody Update.UpdatePasswordParams params) {
        User userFound = userRepository.findUserByUsername(params.username);
        if (userFound != null && params.newPassword != null) {
            userFound.setPassword(params.newPassword);

            userRepository.save(userFound);

            return ResultBox.success(userFound);
        }
        return ResultBox.error(ExceptionEnum.NOT_FOUND, "用户不存在或输入有误");
    }
}
