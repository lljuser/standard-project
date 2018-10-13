package com.heyi.webapp.nettyserver.controller;

import com.heyi.webapp.nettyserver.model.User;
import com.heyi.webapp.nettyserver.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private MyService myService;

    @GetMapping("/{id}")
    public User getuser(@PathVariable Integer id){
        User user=new User();
        user.setId(id);
        user.setName("llj");
        user.setAge(34);
        user.setAddress("shanghai");
        return user;
    }

    @GetMapping("/getusername")
    public String getUserName(){
        return myService.getName();
    }


    @PostMapping("/getuser")
    public User getuser(@RequestBody User user){
        return user;
    }
}
