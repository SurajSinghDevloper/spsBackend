package com.sps.management.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sps.management.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
    
    @Query("select r from Role r where r.name =?1")
    Optional<Role> findRoleName(@Param("roleName") String roleName);
}