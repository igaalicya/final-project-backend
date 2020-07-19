package com.cimb.finalProject.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cimb.finalProject.dao.DoctorRepo;
import com.cimb.finalProject.entity.Doctors;
import com.cimb.finalProject.service.DoctorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/doctors")
@CrossOrigin
public class DoctorController {
	private String uploadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\";
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private DoctorRepo doctorRepo;
	
	@GetMapping
	public Iterable<Doctors> getDoctors() {	
		return doctorService.getDoctors();
	}
	
	@GetMapping("/home")
	public Iterable<Doctors> getDoctorsForHome() {	
		return doctorService.getDoctorsForHome();
	}
	
	@GetMapping("/{doctorsId}")
	public Iterable<Doctors> getDoctorsById(@PathVariable int doctorsId) {	
		return doctorService.getDoctorsById(doctorsId);
	}
	
//	@PostMapping
//	public Doctors addDoctors(@RequestBody Doctors doctors) {
//		return doctorService.addDoctors(doctors);
//	}
	
	@DeleteMapping("/{id}")
	public void deleteDoctorById(@PathVariable int id) {
		
		doctorService.deleteDoctorById(id);
	}
	
	@PutMapping
	public Doctors editDoctors(@RequestBody Doctors doctors) {
		return doctorService.editDoctors(doctors);
	}
	
	@PostMapping
	public String addDoctors(@RequestParam("file") MultipartFile file, @RequestParam("doctorsData") String doctorsString) throws JsonMappingException, JsonProcessingException {

		Date date = new Date();
		
		Doctors doctors = new ObjectMapper().readValue(doctorsString, Doctors.class);
		
		String fileExtension = file.getContentType().split("/")[1];
		
		String newFileName = "DR-"+ doctors.getFullName() + date.getTime() + "." + fileExtension;

		String fileName = StringUtils.cleanPath(newFileName);
		
		Path path = Paths.get( StringUtils.cleanPath(uploadPath) + fileName);
		
		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/doctors/download/")
				.path(fileName).toUriString();
		
		doctors.setImage(fileDownloadUri);
		doctorRepo.save(doctors);
		
		return fileDownloadUri;
				
	}
	
	@GetMapping("/download/{fileName:.+}")
	public ResponseEntity<Object> downloadFile(@PathVariable String fileName){
		Path path = Paths.get(uploadPath + fileName);
		Resource resource = null;
		
		try {
			resource = new UrlResource(path.toUri());
		} catch(MalformedURLException e) {
			e.printStackTrace();
		}
		
		System.out.println("downloaded");
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@PutMapping("/edit/{id}")
	public String editDoctors(@RequestParam("file") Optional<MultipartFile> file, @RequestParam("doctorsData") String doctorsString, @PathVariable int id) throws JsonMappingException, JsonProcessingException {
		
		Doctors	findDoctors = doctorRepo.findById(id).get();
				
		findDoctors = new ObjectMapper().readValue(doctorsString, Doctors.class);
		
		Date date = new Date();
		
		String fileDownloadUri = findDoctors.getImage();
		
		if(file.toString() != "Optional.empty") {
			String fileExtension = file.get().getContentType().split("/")[1];
			String newFileName = "VAC-" + date.getTime() + "." + fileExtension;
			
			String fileName = StringUtils.cleanPath(newFileName);
			
			Path path = Paths.get(StringUtils.cleanPath(uploadPath) + fileName);
			
			try {
				Files.copy(file.get().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/vaccines/download/")
					.path(fileName).toUriString();
			
		}
		
		findDoctors.setImage(fileDownloadUri);
		
		doctorRepo.save(findDoctors);
		
		return fileDownloadUri;
	}
	
}
