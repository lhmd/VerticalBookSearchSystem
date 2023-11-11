package com.bookwise.sembackend.controller;

import com.bookwise.sembackend.db_model.User;
import com.bookwise.sembackend.model.Book;
import com.bookwise.sembackend.model.Login;
import com.bookwise.sembackend.model.Register;
import com.bookwise.sembackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.logging.Logger;


@RestController
@RequestMapping("/auth")
public class LoginSystemController {
    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = Logger.getLogger(LoginSystemController.class.getName());

    @PostMapping("/login")
    public Login login(@RequestBody Login.LoginParams params) {
        try {
            logger.info("POST: /login");
            logger.info(params.toString());
            // TODO: Implement data validation
            User userFound = userRepository.findUserByUsername(params.username);
            if (userFound != null) {
                if (userFound.getPassword().equals(params.password)) {
                    return new Login(true, userFound, new ArrayList<Book>());
                }
            }
            return new Login(false, null, null);
        } catch (Exception e) {
            return new Login(false, null, null);
        }
    }

    @PostMapping("/register")
    public Register register(@RequestBody Register.RegisterParams params) {
        try {
            logger.info("POST: /register");
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
            return new Register(true, newUser);
        } catch (Exception e) {
            return new Register(false, e.getMessage(), null);
        }

    }
}

