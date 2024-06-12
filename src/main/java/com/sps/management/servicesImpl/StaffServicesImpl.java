package com.sps.management.servicesImpl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sps.management.constants.Actions;
import com.sps.management.constants.Result;
import com.sps.management.constants.Status;
import com.sps.management.dtos.NewStaffDTO;
import com.sps.management.dtos.QualificationDTO;
import com.sps.management.dtos.ResponseStaffDTO;
import com.sps.management.dtos.StaffAreaDTO;
import com.sps.management.models.IDCard;
import com.sps.management.models.Qualification;
import com.sps.management.models.Staff;
import com.sps.management.models.StaffArea;
import com.sps.management.repositories.QualificationRepository;
import com.sps.management.repositories.StaffAreaRepository;
import com.sps.management.repositories.StaffRepository;
import com.sps.management.services.StaffServices;
import com.sps.management.utils.EmpIdGenerator;
import com.sps.management.utils.ImageToLocalStorage;

@Service
public class StaffServicesImpl implements StaffServices {

	@Autowired
	private StaffRepository staffRepo;
	@Autowired
	private QualificationRepository qualiRepo;
	@Autowired
	private StaffAreaRepository areaRepo;
	@Autowired
	private ImageToLocalStorage fileService;
	@Autowired
	private EmpIdGenerator idGenerator;

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

	@Override
	public List<ResponseStaffDTO> allStaffs() {
		List<Staff> staff = staffRepo.findAll();
		List<ResponseStaffDTO> found = new ArrayList<>();
		for (Staff s : staff) {
			ResponseStaffDTO rsd = new ResponseStaffDTO();
			found.add(mapForGet(rsd, s));
		}
		return found;
	}

	@Override
	public ResponseStaffDTO staffByID(Long staffId) {
		Staff staff = staffRepo.findById(staffId).get();
		if (staff != null) {
			ResponseStaffDTO rsd = new ResponseStaffDTO();
			return mapForGet(rsd, staff);
		}
		return null;
	}

	@Override
	public List<ResponseStaffDTO> staffByVerifiedStatus() {
		List<Staff> staff = staffRepo.findStaffByVerifiedStatus();
		List<ResponseStaffDTO> found = new ArrayList<>();
		for (Staff s : staff) {
			ResponseStaffDTO rsd = new ResponseStaffDTO();
			rsd = mapForGet(rsd, s);
			found.add(rsd);
			rsd = null;
		}
		return found;
	}

	@Override
	public List<ResponseStaffDTO> staffVerifiedStatus() {
		List<Staff> staff = staffRepo.findStaffVerifiedStatus();
		List<ResponseStaffDTO> found = new ArrayList<>();
		for (Staff s : staff) {
			ResponseStaffDTO rsd = new ResponseStaffDTO();
			rsd = mapForGet(rsd, s);
			found.add(rsd);
			rsd = null;
		}
		return found;
	}

	@Override
	public String generateOfferLetter(Long staffId, Long userId) {
		Staff s = staffRepo.findById(staffId).get();
		if (s.getVerified().equals("VERIFIED") && s.getActive().equals("ACTIVE")) {
			s.setOfferGenBy(userId.toString());
			s.setIsOfferGenrated(Status.TRUE);
			long timeInMillis = System.currentTimeMillis();
			Date now = new Date(timeInMillis);
			s.setOfferGenDate(now.toString());
			staffRepo.save(s);
			return Result.SUCCESS.toString();
		}
		return Result.INVALID_ACTION.toString();
	}

	@Override
	public String approveCandidate(Long staffId, Long userId) {
		Staff s = staffRepo.findById(staffId).get();
		if (s.getActive().equals("ACTIVE")) {
			s.setApprovBy(userId);
			s.setVerified(Status.VERIFIED);
			staffRepo.save(s);
			return Result.SUCCESS.toString();
		}
		return Result.INVALID_ACTION.toString();
	}

	@Override
	public String fileUpload(String empNo, String fileOf, MultipartFile file) {
		Staff staff = staffRepo.findByEmpId(empNo);
		String fileName = "";

		switch (fileOf) {
		case "BANK":
			if (staff.getBankDoc() != null) {
				fileService.deleteFile(staff.getBankDoc());
			}
			fileName = fileService.saveImage(file, staff.getName() + "BANK");
			staff.setBankDoc(fileName);
			break;

		case "AADHAR_BACK":
			if (staff.getAddharBackDoc() != null) {
				fileService.deleteFile(staff.getAddharBackDoc());
			}
			fileName = fileService.saveImage(file, staff.getName() + "AADHAR_BACK");
			staff.setAddharBackDoc(fileName);
			break;

		case "AADHAR_FRONT":
			if (staff.getAddharFrontDoc() != null) {
				fileService.deleteFile(staff.getAddharFrontDoc());
			}
			fileName = fileService.saveImage(file, staff.getName() + "AADHAR_FRONT");
			staff.setAddharFrontDoc(fileName);
			break;

		case "PAN_BACK":
			if (staff.getPanBackDoc() != null) {
				fileService.deleteFile(staff.getPanBackDoc());
			}
			fileName = fileService.saveImage(file, staff.getName() + "PAN_BACK");
			staff.setPanBackDoc(fileName);
			break;

		case "PAN_FRONT":
			if (staff.getPanFrontDoc() != null) {
				fileService.deleteFile(staff.getPanFrontDoc());
			}
			fileName = fileService.saveImage(file, staff.getName() + "PAN_FRONT");
			staff.setPanFrontDoc(fileName);
			break;

		case "CHARACTER":
			if (staff.getCharacterDoc() != null) {
				fileService.deleteFile(staff.getCharacterDoc());
			}
			fileName = fileService.saveImage(file, staff.getName() + "CHARACTER");
			staff.setCharacterDoc(fileName);
			break;

		case "USER_IMG":
			if (staff.getStaffImg() != null) {
				fileService.deleteFile(staff.getStaffImg());
			}
			fileName = fileService.saveImage(file, staff.getName());
			staff.setStaffImg(fileName);
			break;

		default:
			return Result.WENT_WRONG.toString();
		}

		staffRepo.save(staff);
		return fileName;
	}

	// supportive methods

	private String handleNewStaff(NewStaffDTO newStaff) {
		if (staffRepo.findByEmailOrContactNo(newStaff.getEmail(), newStaff.getContactNo()) != null) {
			return Result.ALLREADY_EXISTS.toString();
		}

		Staff staff = createOrUpdateStaff(new Staff(), newStaff, "NEW");
		if (staff.getQuali() != null) {
		    saveOrUpdateQualifications(staff, newStaff.getQuali());
		} 
		List<StaffAreaDTO> dto = new ArrayList<>();
		dto.add(newStaff.getArea());
		saveOrUpdateStaffArea(staff, dto);
		return saveStaff(staff);
	}

	private String handleEditStaff(Long staffId, NewStaffDTO newStaff) {
		Optional<Staff> optionalStaff = staffRepo.findById(staffId);
		if (!optionalStaff.isPresent()) {
			return Result.NOT_FOUND.toString();
		}

		Staff existingStaff = optionalStaff.get();
		existingStaff = createOrUpdateStaff(existingStaff, newStaff, "EDIT");
		saveOrUpdateQualifications(existingStaff, newStaff.getQuali());

		return saveStaff(existingStaff);
	}

	private Staff createOrUpdateStaff(Staff staff, NewStaffDTO newStaff, String action) {
		staff.setPostOf(newStaff.getPostOf());
		staff.setName(newStaff.getName());
		staff.setFname(newStaff.getFname());
		staff.setDob(newStaff.getDob());
		staff.setAge(Integer.parseInt(newStaff.getAge()));
		staff.setGender(newStaff.getGender());
		staff.setMaritalStatus(newStaff.getMaritalStatus());
		staff.setContactNo(newStaff.getContactNo());
		staff.setPaddress(newStaff.getPaddress());
		staff.setCaddress(newStaff.getCaddress());
		staff.setEmail(newStaff.getEmail());
		staff.setAadharNo(newStaff.getAadharNo());
		staff.setPanCard(newStaff.getPanCard());
		staff.setExEmp(newStaff.getExEmp());
		staff.setIdCopy(newStaff.getIdCopy());
		staff.setDeclaration(newStaff.getDeclaration());
		long timeInMillis = System.currentTimeMillis();
		Date now = new Date(timeInMillis);
		staff.setFilledDate(now);
		staff.setPlace(newStaff.getPlace());
		staff.setFilledBy(Long.parseLong(newStaff.getFilledBy()));
		staff.setBloodGroup(newStaff.getBloodGroup());
		staff.setAccountNumber(newStaff.getAccountNumber());
		staff.setBankName(newStaff.getBankName());
		staff.setBranch(newStaff.getBranch());
		staff.setIfscCode(newStaff.getIfscCode());
		if (action.equals("EDIT")) {
			staff.setIsOfferGenrated(newStaff.getIsOfferGenrated());
			staff.setIsIdGenrated(newStaff.getIsIdGenrated());
			staff.setVerified(newStaff.getVerified());
			staff.setIdStatus(newStaff.getIdStatus());
		}
		if (!action.equals("EDIT")) {
			staff.setIsOfferGenrated(Status.FALSE);
			staff.setIsIdGenrated(Status.FALSE);
			staff.setVerified(Status.UNVERIFIED);
			staff.setIdStatus(Status.INACTIVE);
			staff.setEmpNo(idGenerator.getEmpId());

		}
		staff.setActive(Status.ACTIVE);
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

	private void saveOrUpdateStaffArea(Staff staff, List<StaffAreaDTO> staffAreaDTOs) {
		List<StaffArea> existingAreas = areaRepo.findByStaff(staff.getStaffId());
		if (!existingAreas.isEmpty()) {
			areaRepo.deleteAll(existingAreas);
		}

		for (StaffAreaDTO dto : staffAreaDTOs) {
			StaffArea staffArea = new StaffArea();
			staffArea.setArea(dto.getArea());
			staffArea.setCircle(dto.getCircle());
			staffArea.setDivision(dto.getDivision());
			staffArea.setSubDivision(dto.getSubDivision());
			staffArea.setStaff(staff);
			areaRepo.save(staffArea);
		}
	}

	private String saveStaff(Staff staff) {
		Staff savedStaff = staffRepo.save(staff);
		return (savedStaff != null) ? savedStaff.getEmpNo() : Result.WENT_WRONG.toString();
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
		List<Qualification> quali = qualiRepo.findByStaff(s.getStaffId());
		List<QualificationDTO> qq = new ArrayList<>();
		for (Qualification d : quali) {
			QualificationDTO qd = new QualificationDTO();
			qd.setQualiFication(d.getQualiFication());
			qd.setYop(d.getYop());
			qd.setUniv(d.getUniv());
			qd.setMmarks(d.getMmarks());
			qd.setOmartks(d.getOmartks());
			qd.setPercent(d.getPercent());
			qq.add(qd);
			qd = null;
		}
		List<StaffArea> a = areaRepo.findByStaff(s.getStaffId());
		List<StaffAreaDTO> sad = new ArrayList<>();
		for (StaffArea sa : a) {
			StaffAreaDTO sd = new StaffAreaDTO();
			sd.setArea(sa.getArea());
			sd.setCircle(sa.getCircle());
			sd.setDivision(sa.getDivision());
			sd.setSubDivision(sa.getSubDivision());
			sad.add(sd);
			sd = null;
		}
		rsd.setArea(sad.contains(sad)?sad.get(0):null);
		rsd.setQuali(qq);
		rsd.setActive(s.getActive().toString());
		rsd.setVerified(s.getVerified().toString());
		rsd.setIdStatus(s.getIdStatus().toString());
		rsd.setCharacterDoc(s.getCharacterDoc());
		rsd.setIsOfferGenrated(s.getIsOfferGenrated().toString());
		rsd.setEmpNo(s.getEmpNo());
		rsd.setPanFrontDoc(s.getPanFrontDoc());
		rsd.setPanBackDoc(s.getPanBackDoc());
		rsd.setAddharFrontDoc(s.getAddharFrontDoc());
		rsd.setAddharBackDoc(s.getAddharBackDoc());
		rsd.setBloodGroup(s.getBloodGroup());
		rsd.setIsIdGenrated(s.getIsIdGenrated().toString());
		rsd.setAccountNumber(s.getAccountNumber());
		rsd.setBankName(s.getBankName());
		rsd.setBranch(s.getBranch());
		rsd.setIfscCode(s.getIfscCode());
		rsd.setOfferGenBy(s.getOfferGenBy());
		rsd.setOfferGenDate(s.getOfferGenDate());
		rsd.setApprovBy(s.getApprovBy() != null ? s.getApprovBy().toString() : "N/A");

		return rsd;
	}

}
