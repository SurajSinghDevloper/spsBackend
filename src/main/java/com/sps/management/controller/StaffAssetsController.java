package com.sps.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sps.management.models.StaffAssests;
import com.sps.management.services.StaffAssestsService;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/staff-assets")
public class StaffAssetsController {
	@Autowired
	private StaffAssestsService staffAssetsService;

	@PostMapping
	public ResponseEntity<String> createAssetChalan(@RequestBody StaffAssests assets) {
		String result = staffAssetsService.createAssetChalan(assets);
		return new ResponseEntity<>(result,
				result.equals("SUCCESS") ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/staff/{staffId}")
	public ResponseEntity<List<StaffAssests>> findByStaff(@PathVariable Long staffId) {
		List<StaffAssests> assetsList = staffAssetsService.findByStaff(Long.parseLong(decoder(staffId.toString())));
		return assetsList.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<>(assetsList, HttpStatus.OK);
	}

	@PutMapping("/{assetId}")
	public ResponseEntity<StaffAssests> updateStaffAsset(@PathVariable Long assetId,
			@RequestBody StaffAssests newAssetDetails) {
		try {
			StaffAssests updatedAsset = staffAssetsService.updateStaffAsset(Long.parseLong(decoder(assetId.toString())), newAssetDetails);
			return new ResponseEntity<>(updatedAsset, HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	 public String decoder(String content) {
	    	byte[] decodedBytes = Base64.getDecoder().decode(content);
	    	return new String(decodedBytes, StandardCharsets.UTF_8);
	    }
}
