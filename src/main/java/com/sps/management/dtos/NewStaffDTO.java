package com.sps.management.dtos;


import java.util.List;
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
	private String dob;
	private String age;
	private String gender;
	private String nationality;
	private String maritalStatus;
	private String contactNo;
	private String paddress;
	private String caddress;
	private String email;
	private String staffImgName;
	private List<QualificationDTO> quali;
	private StaffAreaDTO area;
	private String aadharNo;
	private String panCard;
	private String bankDocName;
	private String panFrontDocName;
	private String panBackDocName;
	private String addharFrontDocName;
	private String addharBackDocName;
	private String bloodGroup;
	private String accountNumber;
	private String bankName;
	private String branch;
	private String ifscCode;
	private String approvBy;
	private String characterDocName;
	private String exEmp;
	private String idCopy;
	private String declaration;
	private String filledDate;
	private String place;
	private String filledBy;
	private String tempEmp;
	private Status isOfferGenrated;
	private Status isIdGenrated;
	private Status verified;
	private Status idStatus;
}
