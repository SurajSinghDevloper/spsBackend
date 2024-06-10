package com.sps.management.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sps.management.models.IDCard;
import com.sps.management.services.IDCardServices;

@RestController
@RequestMapping("/api/v1/id")
@CrossOrigin
public class IDController {

	@Autowired 
	private IDCardServices idService;
	
	
	
	@PostMapping("/approveAndGenerateId")
    public ResponseEntity<String> approveAndGenerateId(@RequestParam Long staffId, @RequestParam Long userId) {
        String result = idService.approveAndGenerateId(staffId, userId);
        if (result.equals("SUCCESS")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(500).body(result);
        }
    }
	
	@GetMapping("/id-active")
    public List<IDCard> getAllActiveCards() {
        return idService.allActiveCards();
    }
	
	@GetMapping("/{empNo}")
    public ResponseEntity<IDCard> getIDCardByEmpNo(@PathVariable String empNo) {
		String no = decoder(empNo);
        IDCard idCard = idService.findByEmpNo(no);
        if (idCard != null) {
            return new ResponseEntity<>(idCard, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	public String decoder(String content) {
    	byte[] decodedBytes = Base64.getDecoder().decode(content);
    	return new String(decodedBytes, StandardCharsets.UTF_8);
    }
}
