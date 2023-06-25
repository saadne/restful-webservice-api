package com.example.restfulwebservices.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {

    private static List<User>  users = new ArrayList<>();

    private static int userCount = 0;

    static {
        users.add(new User(++userCount,"Saadne", LocalDate.now().minusYears(25)));
        users.add(new User(++userCount,"Deyah", LocalDate.now().minusYears(26)));
        users.add(new User(++userCount,"Mohamed", LocalDate.now().minusYears(24)));
    }

    public List<User> finAll(){
        return users;
    }
    public User findOne(int id){
        Predicate<? super User> predicate = user -> user.getId() == id ;
        return users.stream().filter(predicate).findFirst().orElse(null);
    }
    public User save(User user){
        user.setId(++userCount);
        users.add(user);
        return user;

    }



    public void deleteById(int id) {
        Predicate<? super User> predicate = user -> user.getId() == id ;
        users.removeIf(predicate);
    }
}
