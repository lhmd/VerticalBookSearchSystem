package com.bookwise.sembackend.controller.v2;

import com.bookwise.sembackend.db_model.User;
import com.bookwise.sembackend.dev.ExceptionEnum;
import com.bookwise.sembackend.elastic_search.ESBook;
import com.bookwise.sembackend.model.api.Login;
import com.bookwise.sembackend.model.api.Register;
import com.bookwise.sembackend.model.api.ResultBox;
import com.bookwise.sembackend.repository.UserRepository;
import com.bookwise.sembackend.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping("/v2/auth")
public class LoginSystemControllerV2 {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookService bookService;

    @PostMapping("/login")
    public ResultBox login(@RequestBody Login.LoginParams params) {
        try {
            log.info("POST: /login");
            log.info(params.toString());
            // TODO: Implement data validation
            User userFound = userRepository.findUserByUsername(params.username);
            if (userFound != null) {
                if (userFound.getPassword().equals(params.password)) {
                    List<ESBook> recommendedBooks = bookService.recommendBooks(5, userFound.getInterest());
                    return ResultBox.success(userFound);
                }
            }
            return ResultBox.error(ExceptionEnum.NOT_FOUND, "此用户不存在");
        } catch (Exception e) {
            return ResultBox.error(ExceptionEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResultBox register(@RequestBody Register.RegisterParams params) {
        log.info("POST: /register");
        User user = userRepository.findUserByUsername(params.username);
        if (user != null) return ResultBox.error(ExceptionEnum.USER_EXIST);

        // TODO: Implement data validation
        User newUser = new User(
                0,
                params.username,
                params.password,
                params.email,
                params.phone,
                params.gender,
                params.address,
                params.interest
        );
        userRepository.save(newUser);
        return ResultBox.success(newUser);

    }
}


