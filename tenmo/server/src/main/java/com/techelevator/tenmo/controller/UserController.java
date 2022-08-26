package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@PreAuthorize("isAuthenticated()")
@RestController
public class UserController {

    private UserDao userDao;

    public UserController(JdbcUserDao dao) {
        this.userDao = dao;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        List<User> userList = userDao.findAll();
        return userList;
    }

    @RequestMapping(path = "/users/{username}", method = RequestMethod.GET)//TODO path?
    public int getIdByUsername(@PathVariable String username) {
        int userId = userDao.findIdByUsername(username);
        return userId;
    }
}
