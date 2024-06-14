package com.sps.management.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sps.management.models.StaffAssests;

public interface StaffAssestsRepository extends JpaRepository<StaffAssests, Long> {
	@Query(value = "SELECT * FROM staff_assests WHERE staff_id =:", nativeQuery = true)
	List<StaffAssests> findByStaff(@Param("staff_id") Long staff_id);
}
