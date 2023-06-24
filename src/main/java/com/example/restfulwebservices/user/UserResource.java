package com.example.restfulwebservices.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResource {

    private UserDaoService service;

    public UserResource(UserDaoService service){
        this.service = service;
    }


    @GetMapping("/users")
    @CrossOrigin
    public List<User> retrieveAllUsers(){
        return service.finAll();
    }
    @GetMapping("/users/{id}")
    public User retrieveOne(@PathVariable int id, CorsRegistry registry){
        User user = service.findOne(id);
        registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:8080");
        if(user == null){
            throw new UserNotFoundException("This Id: " + id + " not found");
        }
        return user;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        service.deleteById(id);
    }


    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = service.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();

    }
}
