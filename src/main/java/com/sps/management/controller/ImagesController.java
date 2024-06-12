package com.sps.management.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/spsm")
public class ImagesController {

	
	@Value("${user.image.upload.path}")
    private String imageUploadPath;



	@GetMapping("/view/{imageName:.+}")
	public ResponseEntity<Resource> getImage(@PathVariable("imageName") String imageName) {
	    try {
	        // Construct the full path to the image file
	        Path imagePath = Paths.get(imageUploadPath).resolve(imageName);
	        Resource resource = new UrlResource(imagePath.toUri());
	        HttpHeaders headers = new HttpHeaders();

	        // Check if the image file exists
	        if (resource.exists() && resource.isReadable()) {
	            // Set appropriate headers for the image response
	            // Determine MediaType based on file extension
	            String fileExtension = StringUtils.getFilenameExtension(imageName);
	            if (fileExtension != null && fileExtension.equalsIgnoreCase("pdf")) {
	                headers.setContentType(MediaType.APPLICATION_PDF);
	            } else {
	                headers.setContentType(MediaType.IMAGE_JPEG); // Adjust as needed for image types
	            } // Adjust MediaType as needed
	            // Return the image as a ResponseEntity
	            return ResponseEntity.ok().headers(headers).body(resource);
	        } else {
	            // Return a 404 Not Found response if the image does not exist
	            return ResponseEntity.notFound().build();
	        }
	    } catch (IOException e) {
	        // Handle exceptions appropriately (e.g., log the error)
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
}
