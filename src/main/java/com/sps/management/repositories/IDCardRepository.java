package com.sps.management.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sps.management.models.IDCard;

public interface IDCardRepository extends JpaRepository<IDCard, Long> {
	@Query(value = "SELECT * FROM idcard WHERE status ='ACTIVE'", nativeQuery = true)
	List<IDCard> findAllActiveCard();
	
	@Query(value = "SELECT * FROM idcard WHERE emp_no =:emp_no", nativeQuery = true)
	IDCard findByEmpNo(@Param("emp_no") String emp_no);
	
	@Query(value = "SELECT * FROM idcard WHERE status ='INACTIVE'", nativeQuery = true)
	List<IDCard> findDeactiveCards();
}
