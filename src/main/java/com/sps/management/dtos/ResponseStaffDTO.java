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
	private String staffImg;
	private List<QualificationDTO> quali;
	private StaffAreaDTO area;
	private String aadharNo;
	private String panCard;
	private String bankDoc;
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
	private Timestamp stamp;
}
