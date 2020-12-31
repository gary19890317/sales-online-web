package com.sales.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sales.online.model.Ranking;

public interface RankingRepository extends JpaRepository<Ranking, Long> {
	
	@Query("SELECT coalesce(AVG(stars),0) FROM Ranking where item.id=:itemId") 
	int getAvgRanking(long itemId);
}
