package com.sps.management.models;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.sps.management.constants.Status;

import jakarta.persistence.Entity;
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
public class StaffAssests {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String leaderName;
	private Date dateOfIssue;
	private String areaOfStaff;
	private String modelNo;
	private String deviceSlNo;
	private String reciverName;
	private String empNo;
	private Long staffId;
	private String ram;
	private String hardDisk;
	private String remarks;
	private String issuer;
}
