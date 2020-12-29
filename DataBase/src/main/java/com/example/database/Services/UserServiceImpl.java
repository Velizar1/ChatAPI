package com.example.database.Services;

import com.example.database.Entities.AllChat;
import com.example.database.Entities.User;
import com.example.database.Models.Service.UserServiceModel;
import com.example.database.Repositories.UserRepostitory;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepostitory userRepostitory;
    private final ModelMapper modelMapper;


    @Autowired
    public UserServiceImpl(UserRepostitory userRepostitory, ModelMapper modelMapper) {
        this.userRepostitory = userRepostitory;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean registerUser(UserServiceModel userServiceModel) {
        User user = modelMapper.map(userServiceModel, User.class);
        user.setPassword(DigestUtils.sha256Hex(user.getPassword()));

        try {
            this.userRepostitory.saveAndFlush(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public UserServiceModel getUser(UserServiceModel userServiceModel) {
        User user = modelMapper.map(userServiceModel, User.class);
        try {
            return modelMapper.map(this.userRepostitory.
                    findUserByUsernameAndPassword(user.getUsername(), user.getPassword()), UserServiceModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UserServiceModel getUser(String username) {
        try {
            return modelMapper.map(userRepostitory.findUserByUsername(username), UserServiceModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UserServiceModel getUserByPass(String username, String pass) {
        try {
            return modelMapper.map(userRepostitory
                    .findUserByUsernameAndPassword(username, pass), UserServiceModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean checkUserExist(String username) {
        return userRepostitory.existsByUsername(username);
    }


    @Override
    public List<UserServiceModel> getAllOrderedByName() {
        try {
            return this.userRepostitory.
                    findAllByOrderByUsername().stream().map(x -> modelMapper.map(x, UserServiceModel.class)).
                    collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<UserServiceModel> getAllUsers() {
        try {
            return this.userRepostitory.
                    findAllBy().stream().map(x -> modelMapper.map(x, UserServiceModel.class)).
                    collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<UserServiceModel> getTopNByDate(int n, OffsetDateTime from, OffsetDateTime to) {

        try {
            return this.userRepostitory.
                    findAllByCreatedAtBetween(from, to).stream().map(x -> modelMapper.map(x, UserServiceModel.class)).
                    collect(Collectors.toList()).subList(0, n);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean changeUserStatus(UserServiceModel userServiceModel, boolean status) {
        User user = modelMapper.map(userServiceModel, User.class);
        user.setDeleted(status);
        try {
            this.userRepostitory.updateUserStatus(status, user.getUsername());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
