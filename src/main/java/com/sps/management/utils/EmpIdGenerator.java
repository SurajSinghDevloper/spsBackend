package com.sps.management.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sps.management.models.Staff;
import com.sps.management.repositories.StaffRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class EmpIdGenerator {

    @Autowired
    private StaffRepository staffRepo;

    public String generateEmpCode() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Format the date to get YYMM
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yy");
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");
        String year = currentDate.format(yearFormatter);
        String month = currentDate.format(monthFormatter);

        // Generate a random 5-digit number
        Random random = new Random();
        int randomNumber = 10000 + random.nextInt(90000); 

        // Concatenate to form the EmpCode
        String empCode = String.format("SPS-ER/%s%s%05d", year, month, randomNumber);

        return empCode;
    }

    public String getEmpId() {
        String empId;
        Staff staff;

        // Loop until a unique EmpCode is found
        do {
            empId = generateEmpCode();
            staff = staffRepo.findByEmpId(empId);
        } while (staff != null);

        return empId;
    }
}
