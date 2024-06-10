package com.sps.management.dtos;

import com.sps.management.models.Staff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StaffAreaDTO {
	private String area;
	private String circle;
	private String division;
	private String subDivision;
}
