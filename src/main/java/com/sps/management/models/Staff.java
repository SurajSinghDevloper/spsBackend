package com.sps.management.models;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	private String aadharNo;
	private String panCard;
	private String bankDoc;
	private String exEmp;
	private String idCopy;
	private String declaration;
	private Date filledDate;
	private String place;
	private Long filledBy;
	private Timestamp stamp;
	
}
