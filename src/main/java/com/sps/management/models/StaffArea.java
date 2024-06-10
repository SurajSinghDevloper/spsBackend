package com.sps.management.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StaffArea {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long areaId;
	private String area;
	private String circle;
	private String division;
	private String subDivision;
	 @ManyToOne
	 @JoinColumn(name = "staff_id")
	private Staff staff;
}
