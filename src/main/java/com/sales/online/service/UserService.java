package com.sales.online.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sales.online.exception.UserNotFoundException;
import com.sales.online.model.User;
import com.sales.online.repository.UserRepository;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User findById(int id) {
    return userRepository.findById(id).map(user -> user).orElseThrow(UserNotFoundException::new);
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public void deleteById(int id) {
    userRepository
        .findById(id)
        .map(
            userToDelete -> {
              userRepository.delete(userToDelete);
              return userToDelete;
            })
        .orElseThrow(UserNotFoundException::new);
  }

  public void save(User user) {
    userRepository.save(user);
  }
}
