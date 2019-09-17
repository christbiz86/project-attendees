package com.attendee.attendee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.service.CloudService;
import com.attendee.attendee.service.JsonService;

@CrossOrigin(origins= "*")
@Controller
@RestController
public class FileUploadController {

    @Autowired
    private JsonService json;
    
    @Autowired
    private CloudService cloudService;
    
    @GetMapping(
    		  value = "/image/{filename}",
    		  produces = MediaType.IMAGE_JPEG_VALUE
    		)
    public @ResponseBody ResponseEntity<?> serveFile(@PathVariable String filename) {
    	try {
			return ResponseEntity.ok().body(cloudService.load(filename));
		} catch (Exception e) {
			MessageResponse mg = new MessageResponse("Failed insert" );
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
		}
    }
    
    @PostMapping("/uploading/{object}")
    public ResponseEntity<?> handleFileUploading(@PathVariable String object, @RequestParam("file") MultipartFile file) throws Exception{
    	try {
    		cloudService.save(file,object);
        
    		MessageResponse mg = new MessageResponse("Success!");
    		return ResponseEntity.ok(mg);
    		
    	}catch (Exception e) {
    		System.out.println(e);
			MessageResponse mg = new MessageResponse("Failed insert" );
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
        }
    }

    @PostMapping("/convert")
    public ResponseEntity<?> handleFileUpload() throws Exception{
    	try {
    		json.save();
    		MessageResponse mg = new MessageResponse("Insert success image with name !");
    		return ResponseEntity.ok(mg);
    		
    	}catch (Exception e) {
    		System.out.println(e);
			MessageResponse mg = new MessageResponse("Failed insert" );
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
        }
    }
}
