package com.sales.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sales.online.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	@Query(value="select u from User u where email=?1 and password=?2")
	User validateUser(String email, String password);
}
