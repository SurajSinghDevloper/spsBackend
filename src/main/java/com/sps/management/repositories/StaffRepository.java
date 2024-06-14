package com.sps.management.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sps.management.models.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long>{

	public Staff findByEmailOrContactNo(String email, String contactNo);
	
	@Query(value = "SELECT * FROM staff WHERE verified ='UNVERIFIED'", nativeQuery = true)
    List<Staff> findStaffByVerifiedStatus();
	
	@Query(value = "SELECT * FROM staff WHERE contact_no =:contact_no", nativeQuery = true)
    Staff findStaffByContact(@Param("contact_no")String contact_no);
	
	@Query(value = "SELECT * FROM staff WHERE verified ='VERIFIED'", nativeQuery = true)
    List<Staff> findStaffVerifiedStatus();
	
	 @Query(value = "SELECT * FROM staff WHERE emp_no = :emp_no", nativeQuery = true)
	    Staff findByEmpId(@Param("emp_no") String emp_no);
	 
	 @Query(value = "SELECT * FROM staff ORDER BY staff_id DESC LIMIT 1", nativeQuery = true)
	 Staff getLatest();
}
