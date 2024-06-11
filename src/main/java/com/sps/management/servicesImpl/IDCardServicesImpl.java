package com.sps.management.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sps.management.models.IDCard;
import com.sps.management.constants.Result;
import com.sps.management.constants.Status;
import com.sps.management.models.Staff;
import com.sps.management.models.StaffArea;
import com.sps.management.repositories.IDCardRepository;
import com.sps.management.repositories.StaffAreaRepository;
import com.sps.management.repositories.StaffRepository;
import com.sps.management.services.IDCardServices;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class IDCardServicesImpl implements IDCardServices{

	@Autowired
	private IDCardRepository idRepo;
	@Autowired
	private StaffRepository staffRepo;
	@Autowired
	private StaffAreaRepository areaRepo;
	
	
    @Override
    public String approveAndGenerateId(Long staffId, Long userId) {
        Optional<Staff> optionalStaff = staffRepo.findById(staffId);
        if (optionalStaff.isPresent()) {
            Staff staff = optionalStaff.get();
            staff.setIsIdGenrated(Status.TRUE);
            staff.setIdStatus(Status.ACTIVE);
            staff.setVerified(Status.VERIFIED);
            staff.setIsOfferGenrated(Status.TRUE);
            staff.setApprovBy(userId);
            Staff savedStaff = staffRepo.save(staff);

            if (savedStaff != null) {
                IDCard card = new IDCard();
                card.setEmpNo(savedStaff.getEmpNo());
                card.setName(savedStaff.getName());
                card.setPost(savedStaff.getPostOf());
                card.setDob(savedStaff.getDob());
                card.setFname(savedStaff.getFname());
                card.setMobNo(savedStaff.getContactNo());
                LocalDate currentDate = LocalDate.now();
                card.setGenerationDate(currentDate);
                card.setValidUpto(currentDate.plusDays(90).toString());
                card.setAddress(savedStaff.getPaddress());
                card.setStaffId(staffId);
                StaffArea area = areaRepo.findByStaff(staffId).get(0);
                card.setAreaId(area.getAreaId());
                card.setStatus(Status.ACTIVE);
                card.setGeneratedBy(userId);
                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                card.setStamp(currentTimestamp);

                idRepo.save(card);
                return Result.SUCCESS.toString();
            }
        }
        return Result.WENT_WRONG.toString();
    }
    
    @Override
    public String generateIdOnly(Long staffId, Long userId) {
    	  Optional<Staff> optionalStaff = staffRepo.findById(staffId);
    	  if (optionalStaff.isPresent()) {
              Staff staff = optionalStaff.get();
              if(staff.getActive().equals(Status.ACTIVE)&&staff.getVerified().equals(Status.VERIFIED)&&staff.getIsOfferGenrated().equals(Status.TRUE)) {
            	  IDCard card = new IDCard();
                  card.setEmpNo(staff.getEmpNo());
                  card.setName(staff.getName());
                  card.setPost(staff.getPostOf());
                  card.setDob(staff.getDob());
                  card.setFname(staff.getFname());
                  card.setMobNo(staff.getContactNo());
                  LocalDate currentDate = LocalDate.now();
                  card.setGenerationDate(currentDate);
                  card.setValidUpto(currentDate.plusDays(90).toString());
                  card.setAddress(staff.getPaddress());
                  card.setStaffId(staffId);
                  StaffArea area = areaRepo.findByStaff(staffId).get(0);
                  card.setAreaId(area.getAreaId());
                  card.setStatus(Status.ACTIVE);
                  card.setGeneratedBy(userId);
                  Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                  card.setStamp(currentTimestamp);

                  idRepo.save(card);
                  return Result.SUCCESS.toString();
              }
              return Result.INVALID_ACTION.toString();
    	  }
    	  return Result.NOT_FOUND.toString();
    }
	
    @Override
    public List<IDCard> allActiveCards(){
    	return idRepo.findAllActiveCard();
    }
    
    @Override
    public IDCard findByEmpNo(String empNo) {
    	return idRepo.findByEmpNo(empNo);
    }
	

	

}
