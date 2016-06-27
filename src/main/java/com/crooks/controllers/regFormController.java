/*
 * Copyright (c) 2016.
 */

package com.crooks.controllers;

import com.crooks.entities.User;
import com.crooks.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by johncrooks on 6/27/16.
 */
@RestController
public class regFormController {
    @Autowired
    UserRepository userRepo;

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public Iterable<User> getUsers(){
        return userRepo.findAll();
    }

    @RequestMapping(path="/user", method = RequestMethod.POST)
    public void addUser(@RequestBody User user){                //This @RequestBody is specifically for parsing Json Data
        userRepo.save(user);

    }
    @RequestMapping(path="/user", method = RequestMethod.PUT)
    public void editUser(@RequestBody User user){
        userRepo.save(user);
    }

    @RequestMapping(path="/user/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") int id){
        userRepo.delete(id);
    }
    @RequestMapping(path="/user/{id}", method = RequestMethod.GET)  //if a front-ender asked you to return a specific user
    public User getUser(@PathVariable("id") int id){
        return userRepo.findOne(id);
    }







}  // End controller Class

