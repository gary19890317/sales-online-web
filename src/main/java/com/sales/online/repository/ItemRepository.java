package com.sales.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sales.online.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {}
