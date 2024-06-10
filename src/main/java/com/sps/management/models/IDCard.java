package com.sps.management.models;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import com.sps.management.constants.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class IDCard {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long idNo;
	 private String empNo;
	 private String name;
	 private String post;
	 private Date dob;
	 private String fname;
	 private String mobNo;
	 private String validUpto;
	 private String address;
	 private LocalDate generationDate;
	 private Long staffId;
	 private Long areaId;
	 @Enumerated(EnumType.STRING)
	 private Status status;
	 private Long generatedBy;
	 private Timestamp stamp;
	 
	 
}
