package com.sps.management.models;

import java.sql.Date;

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
public class Qualification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long qid;
	private String qualiFication;
	private String yop;
	private String univ;
	private Double mmarks;
	private Double omartks;
	private Double percent;
	 @ManyToOne
	  @JoinColumn(name = "staff_id")
	  private Staff staff;
	
}
