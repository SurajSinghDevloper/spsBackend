package com.sps.management.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.sps.management.constants.Status;
import com.sps.management.models.IDCard;
import com.sps.management.repositories.IDCardRepository;

import java.time.LocalDate;
import java.util.List;
import java.time.format.DateTimeFormatter;

@Component
public class IDCardStatusUpdater {
	 @Autowired
	    private IDCardRepository idCardRepo;

	    @Scheduled(cron = "0 0 0 * * ?") // This cron expression runs the task daily at midnight
	    public void updateIDCardStatus() {
	        LocalDate currentDate = LocalDate.now();
	        List<IDCard> idCards = idCardRepo.findAll();

	        for (IDCard idCard : idCards) {
	        	 String dateStr = idCard.getValidUpto();
	        	String pattern = "yyyy-MM-dd";
	        	LocalDate localDate = convertStringToLocalDate(dateStr, pattern);
	            if (localDate.isBefore(currentDate) && idCard.getStatus() == Status.ACTIVE) {
	                idCard.setStatus(Status.INACTIVE);
	                idCardRepo.save(idCard);
	            }
	        }
	    }
	    
	    
	    public static LocalDate convertStringToLocalDate(String dateStr, String pattern) {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
	        return LocalDate.parse(dateStr, formatter);
	    }
}
