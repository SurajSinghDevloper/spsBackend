package com.sps.management.dtos;

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
public class QualificationDTO {
	private String qualiFication;
	private String yop;
	private String univ;
	private Double mmarks;
	private Double omartks;
	private Double percent;
}
