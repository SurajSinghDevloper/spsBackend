package com.sps.management.models;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.sps.management.constants.Status;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Staff {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long staffId;
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
	 @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
	private List<Qualification> quali;
	 @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
	private List<StaffArea> area;
	private String aadharNo;
	private String addharFrontDoc;
	private String addharBackDoc;
	private String panCard;
	private String panFrontDoc;
	private String panBackDoc;
	private String bankDoc;
	private String accountNumber;
	private String bankName;
	private String branch;
	private String ifscCode;
	private String exEmp;
	private String idCopy;
	private String declaration;
	private String bloodGroup;
	private Date filledDate;
	@Enumerated(EnumType.STRING)
	private Status active;
	@Enumerated(EnumType.STRING)
	private Status verified;
	@Enumerated(EnumType.STRING)
	private Status idStatus;
	private String characterDoc;
	@Enumerated(EnumType.STRING)
	private Status isOfferGenrated;
	@Enumerated(EnumType.STRING)
	private Status isIdGenrated;
	private String offerGenDate;
	private String offerGenBy;
	private Long approvBy;
	private String empNo;
	private String place;
	private Long filledBy;
	private Timestamp stamp;
	
}
