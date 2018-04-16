package com.synerzip.demo.service;

import com.synerzip.demo.exception.UserNotFoundException;
import com.synerzip.demo.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<>();

    private static int userCount = 3;

    static {
        users.add(new User(1, "Vivek", new Date()));
        users.add(new User(2, "Vinay", new Date()));
        users.add(new User(3, "Vibhav", new Date()));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if(user.getId() == null){
            user.setId(++userCount);
        }
        users.add(user);
        return user;
    }

    public User findOne(int id) {
        for (User user : users) {
            if(user.getId() == id) {
                return user;
            }
        }
        throw new UserNotFoundException("id - "+id);
    }

    public User deleteById(int id) {
        Iterator<User> userIterator = users.iterator();
        while (userIterator.hasNext()) {
            User user = userIterator.next();
            if(user.getId() == id) {
                userIterator.remove();
                return user;
            }
        }
        throw new UserNotFoundException("id - "+id);
    }
}
