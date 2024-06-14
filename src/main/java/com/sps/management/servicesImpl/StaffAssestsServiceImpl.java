package com.sps.management.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sps.management.constants.Result;
import com.sps.management.models.StaffAssests;
import com.sps.management.repositories.StaffAssestsRepository;
import com.sps.management.services.StaffAssestsService;

@Service
public class StaffAssestsServiceImpl implements StaffAssestsService{

	@Autowired
	private StaffAssestsRepository assetRepo;

	
	@Override
	public String createAssetChalan(StaffAssests asstes) {
		StaffAssests savedAsset = assetRepo.save(asstes);
		return savedAsset==null?Result.WENT_WRONG.toString():Result.SUCCESS.toString();
	}
	
	@Override
	public List<StaffAssests> findByStaff(Long staffId){
		return assetRepo.findByStaff(staffId);
	}
	
	@Override
	public StaffAssests updateStaffAsset(Long id, StaffAssests newAssetDetails) {
		StaffAssests existingStaff = assetRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found with id " + id));
        existingStaff.setLeaderName(newAssetDetails.getLeaderName());
        existingStaff.setDateOfIssue(newAssetDetails.getDateOfIssue());
        existingStaff.setAreaOfStaff(newAssetDetails.getAreaOfStaff());
        existingStaff.setModelNo(newAssetDetails.getModelNo());
        existingStaff.setDeviceSlNo(newAssetDetails.getDeviceSlNo());
        existingStaff.setReciverName(newAssetDetails.getReciverName());
        existingStaff.setEmpNo(newAssetDetails.getEmpNo());
        existingStaff.setStaffId(newAssetDetails.getStaffId());
        existingStaff.setRam(newAssetDetails.getRam());
        existingStaff.setHardDisk(newAssetDetails.getHardDisk());
        existingStaff.setRemarks(newAssetDetails.getRemarks());
        existingStaff.setIssuer(newAssetDetails.getIssuer());
        return assetRepo.save(existingStaff);
    }
}
