package com.synerzip.demo.controller;

import com.synerzip.demo.exception.UserNotFoundException;
import com.synerzip.demo.model.Post;
import com.synerzip.demo.model.User;
import com.synerzip.demo.repository.PostJpaRepository;
import com.synerzip.demo.repository.UserJpaRepository;
import com.synerzip.demo.service.UserDaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER =
            LoggerFactory.getLogger(UserJpaController.class);

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    PostJpaRepository postJpaRepository;

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
        LOGGER.info("New User is created : " + savedUser);
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

    @GetMapping("/{id}/posts")
    public List<Post> getUserPosts(@PathVariable int id) {
        Optional<User> user = userJpaRepository.findById(id);
        if(!user.isPresent()) {
            LOGGER.info("User not found : " + id);
            throw new UserNotFoundException("id - > "+id);
        }
        return user.get().getPosts();
    }

    @PostMapping("/{id}/posts")
    public ResponseEntity<Object> addUserPost(@PathVariable int id, @RequestBody Post post) {
        Optional<User> dbUser = userJpaRepository.findById(id);
        if(!dbUser.isPresent())
            throw new UserNotFoundException("id - > "+id);
        User user = dbUser.get();
        post.setUser(user);
        postJpaRepository.save(post);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
