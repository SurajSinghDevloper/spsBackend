package com.sps.management.dtos;

import java.sql.Date;
import java.util.List;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

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
	private List<QualificationDTO> quali;
	private StaffAreaDTO area;
	private String aadharNo;
	private String panCard;
	@Nullable
	private MultipartFile bankDoc;
	@Nullable
	private MultipartFile characterDoc;
	private String exEmp;
	private String idCopy;
	private String declaration;
	private Date filledDate;
	private String place;
	private Long filledBy;
}
