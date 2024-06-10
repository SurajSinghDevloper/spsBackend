package com.sps.management.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sps.management.constants.Actions;
import com.sps.management.dtos.NewStaffDTO;
import com.sps.management.dtos.ResponseStaffDTO;
import com.sps.management.services.StaffServices;

@RestController
@RequestMapping("/api/v1/staff")
@CrossOrigin
public class StaffController {
	
	@Autowired
    private StaffServices staffService;

    @PostMapping("/new-staff")
    public String addStaff(@ModelAttribute NewStaffDTO newStaff) {
        return staffService.saveStaffDetails(Actions.CREATE_NEW, null, newStaff);
    }

    @PostMapping("/chnage-in-/{staffId}")
    public String editStaff(@PathVariable String staffId, @RequestBody NewStaffDTO newStaff) {
    	Long id = Long.parseLong(decoder(staffId));
        return staffService.saveStaffDetails(Actions.EDIT_OLD, id, newStaff);
    }
    
    @GetMapping("/staff-list/all")
    public ResponseEntity<?> getAllStaff() {
        List<ResponseStaffDTO> staffList = staffService.allStaffs();
        return ResponseEntity.ok(staffList);
    }
    
    @GetMapping("/staff-list/{staffId}")
    public ResponseEntity<?> getStaff(@PathVariable String staffId) {
    	Long id = Long.parseLong(decoder(staffId));
        ResponseStaffDTO staffList = staffService.staffByID(id);
        return ResponseEntity.ok(staffList);
    }
    
    
    public String decoder(String content) {
    	byte[] decodedBytes = Base64.getDecoder().decode(content);
    	return new String(decodedBytes, StandardCharsets.UTF_8);
    }
}
