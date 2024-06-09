package com.sps.management.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageToLocalStorage {
	@Value("${user.image.upload.path}")
	private String fileUploadPath;

	public String saveImage(MultipartFile file,String name) {
		// Check if the file is empty
		if (file.isEmpty()) {
			return "Please select a file to upload";
		}

		try {
			// Get the filename and extension
			String fileName = name+"_"+LocalDate.now().toString()+"_"+file.getOriginalFilename();
//			String fileExtension = fileName.substring(fileName.lastIndexOf("."));

			// Define the path where you want to save the image
			String filePath = fileUploadPath + fileName;

			// Create the directory if it doesn't exist
			File directory = new File(fileUploadPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			// Save the file to the specified path
			FileOutputStream fos = new FileOutputStream(filePath);
			fos.write(file.getBytes());
			fos.close();

			return fileName;
		} catch (IOException e) {
			e.printStackTrace();
			return "Failed to upload file";
		}
	}

	

	public String deleteFile(String fileName) {
		// Specify the directory where you want to delete the file
		String uploadDir = fileUploadPath;

		// Create the file path for the file to be deleted
		String filePath = uploadDir + File.separator + fileName;
		Path path = Paths.get(filePath);

		try {
			// Delete the file
			Files.delete(path);
			return "File deleted successfully";
		} catch (IOException e) {
			// Handle file deletion exceptions
			return "Failed to delete file: " + e.getMessage();
		}
	}
}
