package com.sps.management.dtos;

import java.sql.Date;
import java.util.List;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.sps.management.constants.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewStaffDTO {
	private String postOf;
	private String name;
	private String fname;
	private Date dob;
	private int age;
	private String gender;
	private String nationality;
	private String maritalStatus;
	private String contactNo;
	private String paddress;
	private String caddress;
	private String email;
	@Nullable
	private MultipartFile staffImg;
	private String staffImgName;
	private List<QualificationDTO> quali;
	private StaffAreaDTO area;
	private String aadharNo;
	private String panCard;
	@Nullable
	private MultipartFile bankDoc;
	private String bankDocName;
	@Nullable
	private MultipartFile panFrontDoc;
	private String panFrontDocName;
	@Nullable
	private MultipartFile panBackDoc;
	private String panBackDocName;
	@Nullable
	private MultipartFile addharFrontDoc;
	private String addharFrontDocName;
	@Nullable
	private MultipartFile addharBackDoc;
	private String addharBackDocName;
	private String bloodGroup;
	private String accountNumber;
	private String bankName;
	private String branch;
	private String ifscCode;
	private String approvBy;
	@Nullable
	private MultipartFile characterDoc;
	private String characterDocName;
	private String exEmp;
	private String idCopy;
	private String declaration;
	private Date filledDate;
	private String place;
	private Long filledBy;
	private Status isOfferGenrated;
	private Status isIdGenrated;
	private Status verified;
	private Status idStatus;
}
