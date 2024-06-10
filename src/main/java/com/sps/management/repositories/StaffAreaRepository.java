package com.sps.management.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.sps.management.models.StaffArea;

public interface StaffAreaRepository extends JpaRepository<StaffArea, Long>{
	@Query(value = "SELECT * FROM staff_area WHERE staff_id = :staffId", nativeQuery = true)
    List<StaffArea> findByStaff(@Param("staffId") Long staffId);
}
