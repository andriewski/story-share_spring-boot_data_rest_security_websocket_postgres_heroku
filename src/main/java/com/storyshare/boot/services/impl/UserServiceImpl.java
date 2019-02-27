package com.storyshare.boot.services.impl;

import com.google.gson.JsonObject;
import com.storyshare.boot.pojos.User;
import com.storyshare.boot.repositories.UserRepository;
import com.storyshare.boot.services.ServiceException;
import com.storyshare.boot.services.UserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.cache.annotation.CacheResult;

@Service("userService")
@Transactional
@NoArgsConstructor
public class UserServiceImpl extends BaseService<User> implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @CacheResult(cacheName = "userAvatar")
    public String getUserAvatar(long userID) {
        try {
            return userRepository.getUserAvatar(userID);
        } catch (Exception e) {
            throw new ServiceException("Error getting user avatar by userID " + userID);
        }
    }

    @Override
    public String getUserStatus(long userID) {
        try {
            return userRepository.getUserStatus(userID);
        } catch (Exception e) {
            throw new ServiceException("Error getting user status by userID " + userID);
        }
    }

    @Override
    @CacheResult(cacheName = "userName")
    public String getUserName(long userID) {
        try {
            return userRepository.getUserName(userID);
        } catch (Exception e) {
            throw new ServiceException("Error getting user name by userID " + userID);
        }
    }

    @Override
    public JsonObject getUserRoleAndStatus(long userID) {
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("role", userRepository.getUserRole(userID));
            jsonObject.addProperty("status", userRepository.getUserStatus(userID));

            return jsonObject;
        } catch (Exception e) {
            throw new ServiceException("Error getting user role and status by userID " + userID);
        }
    }

    @Override
    public User banUser(User user) {
        try {
            user.setStatus("banned");
            return userRepository.saveAndFlush(user);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("Error banning user " + user);
        }
    }

    @Override
    public User unbanUser(User user) {
        try {
            user.setStatus("active");
            return userRepository.saveAndFlush(user);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("Error unbanning user " + user);
        }
    }

    @Override
    public User assignAdmin(long id) {
        try {
            User user = userRepository.getOne(id);
            user.setRole("admin");

            return userRepository.saveAndFlush(user);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Error assigning user as Admin by id " + id);
        }
    }

    @Override
    public User assignUser(long id) {
        try {
            User user = userRepository.getOne(id);
            user.setRole("user");

            return userRepository.saveAndFlush(user);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Error assigning user as User by id " + id);
        }
    }
}
