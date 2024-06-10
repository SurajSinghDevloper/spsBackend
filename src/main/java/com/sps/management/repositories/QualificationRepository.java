package com.sps.management.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sps.management.models.Qualification;

public interface QualificationRepository extends JpaRepository<Qualification, Long>{

	@Query(value = "SELECT * FROM qualification WHERE staff_id =:staffId", nativeQuery = true)
	List<Qualification> findByStaff(@Param("staffId") Long staffId);

}
