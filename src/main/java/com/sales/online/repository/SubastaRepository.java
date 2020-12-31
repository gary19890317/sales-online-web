package com.sales.online.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sales.online.model.Subasta;

public interface SubastaRepository extends JpaRepository<Subasta, Long> {

  @Query(value = "select s from Subasta s where s.item.id=:itemId order by s.offer desc")
  List<Subasta> findLatestPrice(long itemId);
  
  @Query(value = "select s from Subasta s where s.item.status='ACTIVO' and s.user.id=:userId")
  List<Subasta> findSubastaByUser(int userId);
}
