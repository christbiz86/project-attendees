package com.attendee.attendee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.attendee.attendee.exception.MessageResponse;
import com.attendee.attendee.service.JsonService;
import com.attendee.attendee.storage.StorageFileNotFoundException;
import com.attendee.attendee.storage.StorageService;

@CrossOrigin(origins= "*")
@Controller
@RestController
public class FileUploadController {

    private final StorageService storageService;
    
    @Autowired
    private JsonService json;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/image/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) throws Exception{
    	try {
    		storageService.store(file,file.getOriginalFilename());
        
    		MessageResponse mg = new MessageResponse("Insert success image with name "+file.getOriginalFilename() + "!");
    		return ResponseEntity.ok(mg);
    		
    	}catch (Exception e) {
    		System.out.println(e);
			MessageResponse mg = new MessageResponse("Failed insert" );
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mg);
        }
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
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
