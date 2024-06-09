package com.sps.management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sps.management.models.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long>{

	public Staff findByEmailOrContactNo(String email, String contactNo);
}
