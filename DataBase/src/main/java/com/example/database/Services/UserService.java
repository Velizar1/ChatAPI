package com.example.database.Services;

import com.example.database.Models.Service.UserServiceModel;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public interface UserService {

    boolean registerUser(UserServiceModel userServiceModel);

    UserServiceModel getUser(UserServiceModel userServiceModel);

    UserServiceModel getUser(String username);

    List<UserServiceModel> getAllUsers();

    List<UserServiceModel> getAllOrderedByName();

    List<UserServiceModel> getTopNByDate(int n, OffsetDateTime from, OffsetDateTime to);

    UserServiceModel getUserByPass(String username, String pass);

    boolean checkUserExist(String username);

    boolean changeUserStatus(UserServiceModel userServiceModel, boolean status);
}
