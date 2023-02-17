package com.example.backend.Controller;
import com.example.backend.Bean.User;
import com.example.backend.Service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Resource(name = "userService")
    private UserService userService;

    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        List<User> Users = userService.list();
        return Users;
    }
}