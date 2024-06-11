package com.sps.management.services;

import java.util.List;

import com.sps.management.models.IDCard;

public interface IDCardServices {
	 public String approveAndGenerateId(Long staffId, Long userId);
	 
	 public String generateIdOnly(Long staffId, Long userId);
	 
	 public List<IDCard> allActiveCards();
	 
	 public IDCard findByEmpNo(String empNo);
}
