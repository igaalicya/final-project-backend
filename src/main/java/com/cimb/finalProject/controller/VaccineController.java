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

import com.cimb.finalProject.dao.CategoriesRepo;
import com.cimb.finalProject.dao.VaccineRepo;
import com.cimb.finalProject.entity.Categories;
import com.cimb.finalProject.entity.Vaccines;
import com.cimb.finalProject.service.VaccineService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/vaccines")
@CrossOrigin
public class VaccineController {
	
	private String uploadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\";
	
	@Autowired
	private VaccineRepo vaccineRepo;
	
	@Autowired
	private CategoriesRepo categoriesRepo;
	
	@Autowired
	private VaccineService vaccineService;
	
	@GetMapping
	public Iterable<Vaccines> getVaccines() {	
		return vaccineService.getVaccines();
	}
	
	@GetMapping("/page/{offset}/{minPrice}/{maxPrice}")
	public Iterable<Vaccines> getVaccinesPerPage(@PathVariable int offset, @RequestParam String vaccineName, @PathVariable int minPrice, @PathVariable int maxPrice) {	
		if (maxPrice == 0) {
			maxPrice = 9999999;
		}
		return vaccineRepo.getVaccinesPerPage(offset, vaccineName, minPrice, maxPrice);
	}
	
	@GetMapping("/page/categories/{offset}/{minPrice}/{maxPrice}")
	public Iterable<Vaccines> getVaccinesCategories(@PathVariable int offset, @RequestParam String vaccineName, @PathVariable int minPrice, @PathVariable int maxPrice, @RequestParam String categoriesName) {	
		if (maxPrice == 0) {
			maxPrice = 9999999;
		}
		return vaccineRepo.getVaccinesCategories(offset, vaccineName, minPrice, maxPrice, categoriesName);
	}
	
	@GetMapping("/home")
	public Iterable<Vaccines> getVaccinesForHome() {	
		return vaccineService.getVaccinesForHome();
	}
	
	@GetMapping("/{vaccinesId}")
	public Iterable<Vaccines> getVaccinesById(@PathVariable int vaccinesId) {	
		return vaccineService.getVaccinesById(vaccinesId);
	}
		
	@PostMapping("/{categoriesId}")
	public String addVaccines(@RequestParam("file") MultipartFile file, @RequestParam("vaccinesData") String vaccinesString, @PathVariable int categoriesId) throws JsonMappingException, JsonProcessingException {

		Date date = new Date();
		
		Categories findCategories = categoriesRepo.findById(categoriesId).get();
		
		Vaccines vaccines = new ObjectMapper().readValue(vaccinesString, Vaccines.class);
		
		String fileExtension = file.getContentType().split("/")[1];
		
		String newFileName = "VAC-" + date.getTime() + "." + fileExtension;

		String fileName = StringUtils.cleanPath(newFileName);
		
		Path path = Paths.get( StringUtils.cleanPath(uploadPath) + fileName);
		
		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/vaccines/download/")
				.path(fileName).toUriString();
		
		vaccines.setImage(fileDownloadUri);
		vaccines.setCategories(findCategories);
		vaccineRepo.save(vaccines);
		
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
	
	@PutMapping("/edit/{id}/{categoriesId}")
	public String editVaccines(@RequestParam("file") Optional<MultipartFile> file, @RequestParam("vaccinesData") String vaccinesString, @PathVariable int id, @PathVariable int categoriesId) throws JsonMappingException, JsonProcessingException {
		Categories findCategories = categoriesRepo.findById(categoriesId).get();
		
		Vaccines findVaccines = vaccineRepo.findById(id).get();
				
		findVaccines = new ObjectMapper().readValue(vaccinesString, Vaccines.class);
		
		Date date = new Date();
		
		String fileDownloadUri = findVaccines.getImage();
		
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
		
		findVaccines.setImage(fileDownloadUri);
		findVaccines.setCategories(findCategories);
		
		vaccineRepo.save(findVaccines);
		
		return fileDownloadUri;
	}
	
	@DeleteMapping("/{id}")
	public void deleteVaccineById(@PathVariable int id) {
		
		vaccineService.deleteVaccineById(id);
	}
	
//	add Categories to vaccines
	@PostMapping("/{vaccinesId}/categories/{categoriesId}")
	public Vaccines addCategoriesToVaccines(@PathVariable int vaccinesId, @PathVariable int categoriesId) {
		return vaccineService.addCategoriesToVaccines(vaccinesId, categoriesId);
	}
	
	// add vaccine plus category
//	@PostMapping("/categories/{categoriesId}")
//	public Vaccines addVaccines(@RequestBody Vaccines vaccines, @PathVariable int categoriesId) {
//		return vaccineService.addVaccines(vaccines, categoriesId);
//	}
	
	@GetMapping("/count/all/{minPrice}/{maxPrice}")
	public int countVaccines(@RequestParam String vaccineName, @PathVariable int minPrice, @PathVariable int maxPrice) {	
		if (maxPrice == 0) {
			maxPrice = 9999999;
		}
		return vaccineRepo.countVaccines(vaccineName, minPrice, maxPrice);
	}
	
	@GetMapping("/count/categories/{minPrice}/{maxPrice}")
	public int countVaccinesCategories(@RequestParam String vaccineName, @PathVariable int minPrice, @PathVariable int maxPrice, @RequestParam String categoriesName) {	
		if (maxPrice == 0) {
			maxPrice = 9999999;
		}
		return vaccineRepo.countVaccinesCategories(vaccineName, minPrice, maxPrice, categoriesName);
		
	}
	
	// buat ngurangin stock pas CO
	@PutMapping("/checkout/{id}")
	public Vaccines checkoutVaccines (@RequestBody Vaccines vaccines, @PathVariable int id) {
		Vaccines findVaccines = vaccineRepo.findById(id).get();
		
		vaccines.setId(findVaccines.getId());
		vaccines.setCategories(findVaccines.getCategories());
		vaccines.setAgeOfDose(findVaccines.getAgeOfDose());
		vaccines.setBrand(findVaccines.getBrand());
		vaccines.setDescription(findVaccines.getDescription());
		vaccines.setPrice(findVaccines.getPrice());
		vaccines.setVaccineName(findVaccines.getVaccineName());
		vaccines.setImage(findVaccines.getImage());
		
		vaccines.setCarts(findVaccines.getCarts());
		vaccines.setTransactionDetails(findVaccines.getTransactionDetails());
		vaccines.setWishlists(findVaccines.getWishlists());
		
		return vaccineRepo.save(vaccines);
	}
	
	@GetMapping("/report")
	public Iterable<Vaccines> getVaccineReport(){
		return vaccineRepo.getTransactionReport();
	}
	
	@GetMapping("/report/categories/{minPrice}/{maxPrice}")
	public Iterable<Vaccines> getTransactionReport(@RequestParam String vaccineName, @PathVariable int minPrice, @PathVariable int maxPrice, @RequestParam String categoriesName){
		if (maxPrice == 0) {
			maxPrice = 9999999;
		}
		return vaccineRepo.getTransactionReportCategories(vaccineName, minPrice, maxPrice, categoriesName);
	}
	
	@GetMapping("/report/all/{minPrice}/{maxPrice}")
	public Iterable<Vaccines> getAllTransactionReport(@RequestParam String vaccineName, @PathVariable int minPrice, @PathVariable int maxPrice){
		if (maxPrice == 0) {
			maxPrice = 9999999;
		}
		return vaccineRepo.getTransactionReportAllCategories(vaccineName, minPrice, maxPrice);
	}
	
	
}
