package com.sales.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sales.online.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {}
