package com.sps.management.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.sps.management.models.Users;


public interface UserRepository extends JpaRepository<Users, Long>  {
	Users findByEmail(String eamil);
}
