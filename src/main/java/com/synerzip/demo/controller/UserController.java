package com.synerzip.demo.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import com.synerzip.demo.model.User;
import com.synerzip.demo.service.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserDaoService userDaoService;

    @Autowired
    private MessageSource messageSource;

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
    public Resource<User> getUser(@PathVariable int id) {
        User user = userDaoService.findOne(id);
        Resource<User> resource = new Resource<User>(user);
        ControllerLinkBuilder linkBuilder = linkTo(methodOn(this.getClass()).getAllUser());
        resource.add(linkBuilder.withRel("all-users"));
        return resource;
    }

    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable int id) {
        return userDaoService.deleteById(id);
    }

    //1Locale. getting locale from header
    /*@GetMapping("/hello-world-internationalized")
    public String helloWorldInternationalized(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        return messageSource.getMessage("good.morning.message", null, locale);
    }*/

    //2Locale. getting locale from locale context
    @GetMapping("/hello-world-internationalized")
    public String helloWorldInternationalized() {
        return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
    }
}
