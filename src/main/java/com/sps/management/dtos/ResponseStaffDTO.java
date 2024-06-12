package com.sps.management.dtos;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.sps.management.constants.Status;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class ResponseStaffDTO {
	private String postOf;
	private String staffId;
	private String name;
	private String fname;
	private String dob;
	private int age;
	private String gender;
	private String nationality;
	private String maritalStatus;
	private String contactNo;
	private String paddress;
	private String caddress;
	private String email;
	private String staffImg;
	private List<QualificationDTO> quali;
	private StaffAreaDTO area;
	private String aadharNo;
	private String panCard;
	private String bankDoc;
	private String isOfferGenrated;
	private String empNo;
	private String panFrontDoc;
	private String panBackDoc;
	private String addharFrontDoc;
	private String addharBackDoc;
	private String bloodGroup;
	private String isIdGenrated;
	private String accountNumber;
	private String bankName;
	private String branch;
	private String ifscCode;
	private String approvBy;
	private String exEmp;
	private String idCopy;
	private String declaration;
	private Date filledDate;
	private String place;
	private String active;
	private String verified;
	private String idStatus;
	private String characterDoc;
	private Long filledBy;
	private String offerGenDate;
	private String offerGenBy;
	private Timestamp stamp;
}
