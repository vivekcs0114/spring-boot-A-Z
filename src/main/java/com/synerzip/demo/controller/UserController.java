package com.synerzip.demo.controller;

import com.synerzip.demo.model.User;
import com.synerzip.demo.service.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserDaoService userDaoService;

    @RequestMapping()
    public List<User> getAllUser() {
        return userDaoService.findAll();
    }

    @PostMapping()
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user) {
        User savedUser = userDaoService.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @RequestMapping("/{id}")
    public User getUser(@PathVariable int id) {
        return userDaoService.findOne(id);
    }

    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable int id) {
        return userDaoService.deleteById(id);
    }
}
