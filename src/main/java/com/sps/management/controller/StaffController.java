package com.sps.management.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sps.management.constants.Actions;
import com.sps.management.constants.Result;
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
    
    @GetMapping("/{staffId}")
    public ResponseEntity<?> getStaff(@PathVariable String staffId) {
    	Long id = Long.parseLong(decoder(staffId));
        ResponseStaffDTO staffList = staffService.staffByID(id);
        return ResponseEntity.ok(staffList);
    }
    
    @GetMapping("/unverified/staff-list")
    public ResponseEntity<?> getStaffByUnVerifiedStatus() {
        List<ResponseStaffDTO> staffList = staffService.staffByVerifiedStatus();
        return ResponseEntity.ok(staffList);
    }
    
    @GetMapping("/verified/staff-list")
    public ResponseEntity<?> getStaffByVerifiedStatus() {
        List<ResponseStaffDTO> staffList = staffService.staffVerifiedStatus();
        return ResponseEntity.ok(staffList);
    }
    
    @PostMapping("/generateOfferLetter/{staffId}/{userId}")
    public ResponseEntity<String> generateOfferLetter(@PathVariable String staffId, @PathVariable String userId) {
        String result = staffService.generateOfferLetter(Long.parseLong(decoder(staffId)), Long.parseLong(decoder(userId)));
        if (Result.SUCCESS.toString().equals(result)) {
            return ResponseEntity.ok(result);
        } else if (Result.INVALID_ACTION.toString().equals(result)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        }
    }
    
    @PostMapping("/approve-candidate/{staffId}/{userId}")
    public ResponseEntity<String> approveCandidate(@PathVariable String staffId, @PathVariable String userId) {
        String result = staffService.approveCandidate(Long.parseLong(decoder(staffId)), Long.parseLong(decoder(userId)));
        if (Result.SUCCESS.toString().equals(result)) {
            return ResponseEntity.ok(result);
        } else if (Result.INVALID_ACTION.toString().equals(result)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        }
    }
    
    
    @PostMapping("/{staffId}/upload")
    public ResponseEntity<String> uploadFile(
            @PathVariable Long staffId,
            @RequestParam("fileOf") String fileOf,
            @RequestParam("file") MultipartFile file) {
        
        try {
            String fileName = staffService.fileUpload(staffId, fileOf, file);
            if ("N/A".equals(fileName)) {
                return new ResponseEntity<>("File upload failed: Document already exists or invalid type.", HttpStatus.BAD_REQUEST);
            } else if (Result.WENT_WRONG.toString().equals(fileName)) {
                return new ResponseEntity<>("File upload failed: Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                return new ResponseEntity<>(fileName, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("File upload failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    public String decoder(String content) {
    	byte[] decodedBytes = Base64.getDecoder().decode(content);
    	return new String(decodedBytes, StandardCharsets.UTF_8);
    }
}
