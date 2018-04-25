package com.synerzip.demo.controller;

import com.synerzip.demo.exception.UserNotFoundException;
import com.synerzip.demo.model.User;
import com.synerzip.demo.repository.UserJpaRepository;
import com.synerzip.demo.service.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa/users")
public class UserJpaController {

    @Autowired
    UserJpaRepository userJpaRepository;

    @RequestMapping()
    public List<User> getAllUser() {
        return userJpaRepository.findAll();
    }

    @PostMapping()
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user) {
        User savedUser = userJpaRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @RequestMapping("/{id}")
    public Resource<User> getUser(@PathVariable int id) {
        Optional<User> user = userJpaRepository.findById(id);
        if(!user.isPresent())
            throw new UserNotFoundException("id - > "+id);
        Resource<User> resource = new Resource<User>(user.get());
        ControllerLinkBuilder linkBuilder = linkTo(methodOn(this.getClass()).getAllUser());
        resource.add(linkBuilder.withRel("all-users"));
        return resource;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userJpaRepository.deleteById(id);
    }
}
