package com.sps.management.services;

import java.util.List;

import com.sps.management.constants.Actions;
import com.sps.management.dtos.NewStaffDTO;
import com.sps.management.dtos.ResponseStaffDTO;
import com.sps.management.models.IDCard;

public interface StaffServices {
	public String saveStaffDetails(Actions action, Long staffId, NewStaffDTO newStaff);
	public List<ResponseStaffDTO> allStaffs() ;
	public ResponseStaffDTO staffByID(Long staffId);
	public List<ResponseStaffDTO> staffByVerifiedStatus();
	public List<ResponseStaffDTO> staffVerifiedStatus();
	public String generateOfferLetter(Long staffId, Long userId);
	public String approveCandidate(Long staffId, Long userId);
	
}
