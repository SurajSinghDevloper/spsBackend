package com.sps.management.services;

import java.util.List;

import com.sps.management.models.StaffAssests;

public interface StaffAssestsService {
	public String createAssetChalan(StaffAssests asstes);
	
	public List<StaffAssests> findByStaff(Long staffId);
	
	public StaffAssests updateStaffAsset(Long id, StaffAssests newAssetDetails);
}
