package com.sps.management.servicesImpl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sps.management.constants.Actions;
import com.sps.management.constants.Result;
import com.sps.management.dtos.NewStaffDTO;
import com.sps.management.dtos.QualificationDTO;
import com.sps.management.dtos.ResponseStaffDTO;
import com.sps.management.models.Qualification;
import com.sps.management.models.Staff;
import com.sps.management.repositories.QualificationRepository;
import com.sps.management.repositories.StaffRepository;
import com.sps.management.services.StaffServices;
import com.sps.management.utils.ImageToLocalStorage;

@Service
public class StaffServicesImpl implements StaffServices {

	@Autowired
	private StaffRepository staffRepo;
	@Autowired
	private QualificationRepository qualiRepo;
	@Autowired
	private ImageToLocalStorage fileService;

	@Override
	public String saveStaffDetails(Actions action, Long staffId, NewStaffDTO newStaff) {
		switch (action.toString()) {
		case "CREATE_NEW":
			return handleNewStaff(newStaff);
		case "EDIT_OLD":
			return handleEditStaff(staffId, newStaff);
		default:
			return Result.INVALID_ACTION.toString();
		}
	}

	private String handleNewStaff(NewStaffDTO newStaff) {
		if (staffRepo.findByEmailOrContactNo(newStaff.getEmail(), newStaff.getContactNo()) != null) {
			return Result.ALLREADY_EXISTS.toString();
		}

		Staff staff = createOrUpdateStaff(new Staff(), newStaff);
		saveOrUpdateQualifications(staff, newStaff.getQuali());

		return saveStaff(staff);
	}

	private String handleEditStaff(Long staffId, NewStaffDTO newStaff) {
		Optional<Staff> optionalStaff = staffRepo.findById(staffId);
		if (!optionalStaff.isPresent()) {
			return Result.NOT_FOUND.toString();
		}

		Staff existingStaff = optionalStaff.get();
		existingStaff = createOrUpdateStaff(existingStaff, newStaff);
		saveOrUpdateQualifications(existingStaff, newStaff.getQuali());

		return saveStaff(existingStaff);
	}

	private Staff createOrUpdateStaff(Staff staff, NewStaffDTO newStaff) {
		staff.setPostOf(newStaff.getPostOf());
		staff.setName(newStaff.getName());
		staff.setFname(newStaff.getFname());
		staff.setDob(newStaff.getDob());
		staff.setAge(newStaff.getAge());
		staff.setGender(newStaff.getGender());
		staff.setMaritalStatus(newStaff.getMaritalStatus());
		staff.setContactNo(newStaff.getContactNo());
		staff.setPaddress(newStaff.getPaddress());
		staff.setCaddress(newStaff.getCaddress());
		staff.setEmail(newStaff.getEmail());
		staff.setStaffImg(fileService.saveImage(newStaff.getStaffImg(), newStaff.getName()));
		staff.setAadharNo(newStaff.getAadharNo());
		staff.setPanCard(newStaff.getPanCard());
		staff.setBankDoc(fileService.saveImage(newStaff.getBankDoc(), newStaff.getName() + "BANK"));
		staff.setExEmp(newStaff.getExEmp());
		staff.setIdCopy(newStaff.getIdCopy());
		staff.setDeclaration(newStaff.getDeclaration());
		long timeInMillis = System.currentTimeMillis();
		Date now = new Date(timeInMillis);
		staff.setFilledDate(now);
		staff.setPlace(newStaff.getPlace());
		staff.setFilledBy(newStaff.getFilledBy());
		staff.setStamp(new Timestamp(timeInMillis));

		return staff;
	}

	private void saveOrUpdateQualifications(Staff staff, List<QualificationDTO> qualifications) {
		if (staff.getStaffId() != null) {
			List<Qualification> qu = qualiRepo.findByStaff(staff.getStaffId());
			if (!qu.isEmpty()) {
				qualiRepo.deleteAll(qu);
			}
		}
		if (staff.getStaffId() == null) {
			staff = staffRepo.save(staff);
		}
		for (QualificationDTO quali : qualifications) {
			Qualification q = new Qualification();
			q.setQualiFication(quali.getQualiFication());
			q.setYop(quali.getYop());
			q.setUniv(quali.getUniv());
			q.setMmarks(quali.getMmarks());
			q.setOmartks(quali.getOmartks());
			q.setPercent(quali.getPercent());
			q.setStaff(staff);
			qualiRepo.save(q);
		}
	}

	private String saveStaff(Staff staff) {
		Staff savedStaff = staffRepo.save(staff);
		return (savedStaff != null) ? Result.SUCCESS.toString() : Result.WENT_WRONG.toString();
	}
	
	@Override
	public List<ResponseStaffDTO> allStaffs() {
		List<Staff> staff= staffRepo.findAll();
		List<ResponseStaffDTO> found =new ArrayList<>();
		for(Staff s:staff) {
			ResponseStaffDTO rsd = new ResponseStaffDTO();
			rsd=mapForGet(rsd,s);
			found.add(rsd);
			rsd=null;
		}
		return found;
	}

	public ResponseStaffDTO mapForGet(ResponseStaffDTO rsd, Staff s) {
		rsd.setPostOf(s.getPostOf());
		rsd.setName(s.getName());
		rsd.setFname(s.getFname());
		rsd.setDob(s.getDob());
		rsd.setAge(s.getAge());
		rsd.setGender(s.getGender());
		rsd.setMaritalStatus(s.getMaritalStatus());
		rsd.setContactNo(s.getContactNo());
		rsd.setPaddress(s.getPaddress());
		rsd.setCaddress(s.getCaddress());
		rsd.setEmail(s.getEmail());
		rsd.setStaffImg(s.getStaffImg());
		rsd.setAadharNo(s.getAadharNo());
		rsd.setPanCard(s.getPanCard());
		rsd.setBankDoc(s.getBankDoc());
		rsd.setExEmp(s.getExEmp());
		rsd.setIdCopy(s.getIdCopy());
		rsd.setDeclaration(s.getDeclaration());
		rsd.setFilledDate(s.getFilledDate());
		rsd.setPlace(s.getPlace());
		rsd.setFilledBy(s.getFilledBy());
		rsd.setStamp(s.getStamp());
		return rsd;
	}
}
