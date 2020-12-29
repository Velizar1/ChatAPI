package com.example.database.Controller;

import com.example.database.Models.Binding.UserBindingModel;
import com.example.database.Models.Respond.UserRespondModel;
import com.example.database.Models.Service.UserServiceModel;
import com.example.database.Services.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController("/user")
public class UserController {

    private final UserServiceImpl userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(ModelMapper modelMapper, UserServiceImpl userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/user/all")
    @ResponseBody
    ResponseEntity<List<UserBindingModel>> all() {
        try {
            return new ResponseEntity<>(userService.getAllUsers().stream().map(x ->
                    modelMapper.map(x, UserBindingModel.class)).collect(Collectors.toList()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/user/current/{username}")
    @ResponseBody
    ResponseEntity<UserBindingModel> findUserByUsername(@PathVariable String username) {

        try {
            return new ResponseEntity<>(modelMapper.map(userService.getUser(username), UserBindingModel.class),
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/user/current/{username}/{pass}")
    @ResponseBody
    ResponseEntity<UserRespondModel> findUserByUsernameAndPass(@PathVariable String username, @PathVariable String pass) {
        try {
            UserRespondModel userRespondModel = new UserRespondModel();
            userRespondModel.setUsername(username);

            if (!userService.checkUserExist(username)) {
                userRespondModel.setExists(false);
                userRespondModel.setAuthenticated(false);
                userRespondModel.setInformation("User doesnt exists!");
                return new ResponseEntity<>(userRespondModel, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            } else {
                userRespondModel.setExists(true);
                if (userService.getUserByPass(username, pass) == null) {
                    userRespondModel.setInformation("Username/pass didnt match!");
                    userRespondModel.setAuthenticated(false);
                    return new ResponseEntity<>(userRespondModel, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
                } else {
                    userRespondModel.setInformation("User found!");
                    userRespondModel.setAuthenticated(true);
                    return new ResponseEntity<>(userRespondModel, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/user/top/{n}/{from}/{to}")
    @ResponseBody
    ResponseEntity<List<UserBindingModel>> findTopNUsersByTime(@PathVariable int n, @PathVariable String from,
                                                               @PathVariable String to) {

        to = to.replace(" ", "T") + "Z";
        from = from.replace(" ", "T") + "Z";
        try {
            return new ResponseEntity<>(userService
                    .getTopNByDate(n, OffsetDateTime.parse(from), OffsetDateTime.parse(to))
                    .stream()
                    .map(x ->
                    modelMapper.map(x, UserBindingModel.class)).collect(Collectors.toList()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/user/all/ordered/by/username")
    @ResponseBody
    ResponseEntity<List<UserBindingModel>> findAllByOrderByUsername() {
        try {
            return new ResponseEntity<>(userService.getAllOrderedByName().stream().map(x ->
                    modelMapper.map(x, UserBindingModel.class)).collect(Collectors.toList()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PostMapping("/user/add")
    ResponseEntity<UserRespondModel> addUser(@RequestBody UserBindingModel member) {
        UserRespondModel userRespondModel = new UserRespondModel();
        userRespondModel.setUsername(member.getUsername());
        UserServiceModel user = modelMapper.map(member, UserServiceModel.class);
        try {
            userService.registerUser(user);
            userRespondModel.setExists(true);
            userRespondModel.setInformation("User registered!");
            userRespondModel.setAuthenticated(true);
            return new ResponseEntity<>(userRespondModel, HttpStatus.OK);
        } catch (Exception e) {
            userRespondModel.setExists(true);
            userRespondModel.setInformation("User exists!");
            userRespondModel.setAuthenticated(false);
            e.printStackTrace();
            return new ResponseEntity<>(userRespondModel, HttpStatus.BAD_REQUEST);
        }
    }
}
