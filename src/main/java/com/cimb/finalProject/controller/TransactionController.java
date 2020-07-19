package com.cimb.finalProject.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.cimb.finalProject.dao.TransactionRepo;
import com.cimb.finalProject.entity.Transactions;
import com.cimb.finalProject.service.TransactionService;
import com.cimb.finalProject.util.EmailUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/transactions")
@CrossOrigin
public class TransactionController {
	
	private String uploadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\";
	
	@Autowired
	private TransactionRepo transactionRepo;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private EmailUtil emailUtil;
		
	@GetMapping
	public Iterable<Transactions> getTransactions() {	
		return transactionService.getTransactions();
	}
	
	@GetMapping("/{id}")
	public Transactions getTransactionsById(@PathVariable int id) {	
		Transactions findTransactions = transactionRepo.findById(id).get();

        if (findTransactions == null)
            throw new RuntimeException("Transactions Not Found");

        return findTransactions;
	}
	
	@PostMapping("/{doctorsId}/{usersId}")
	public Transactions postTransactions(@RequestBody Transactions transactions, @PathVariable int doctorsId, @PathVariable int usersId) {
		return transactionService.postTransactions(transactions, doctorsId, usersId);
	}
	
	@GetMapping("/status")
	public Iterable<Transactions> getTransactionsByStatus(@RequestParam String status){
		return transactionService.getTransactionsByStatus(status);
	}
	
	@GetMapping("/thisUser")
	public Iterable<Transactions> getTransactionsByUser(@RequestParam int usersId) {
		return transactionService.getTransactionsByUser(usersId);
	}
	
	@PutMapping("/uploadBukti/{id}")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("transactionsData") String transactionsString, @PathVariable int id ) throws JsonMappingException, JsonProcessingException {

        Date date = new Date();

        Transactions transactions = transactionRepo.findById(id).get();

        transactions = new ObjectMapper().readValue(transactionsString, Transactions.class);

        String fileExtension = file.getContentType().split("/")[1];
        String newFileName = "BUKTI-TF-" + date.getTime() + "." + fileExtension;

        String fileName = StringUtils.cleanPath(newFileName);

        Path path = Paths.get(StringUtils.cleanPath(uploadPath) + fileName);

        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/transactions/download/")
                .path(fileName).toUriString();

        transactions.setBuktiTransfer(fileDownloadUri);
        transactionRepo.save(transactions);        
        
        return fileDownloadUri;
    }


    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Object> downloadFile(@PathVariable String fileName) {
        Path path = Paths.get(uploadPath, fileName);

        Resource resource = null;

        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream")).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + resource.getFilename() + "\"").body(resource);
    }
    
    @PutMapping("/reject/{transactionsId}") 
	public Transactions rejectTransactions(@RequestBody Transactions transactions, @PathVariable int transactionsId) {
		Transactions findTransaction = transactionRepo.findById(transactionsId).get();
		
		transactions.setId(findTransaction.getId());
		transactions.setTransactionDate(findTransaction.getTransactionDate());
		transactions.setCompletionDate(findTransaction.getCompletionDate());
		transactions.setDoctors(findTransaction.getDoctors());
		transactions.setGrandTotalPrice(findTransaction.getGrandTotalPrice());
		transactions.setTransactionDetails(findTransaction.getTransactionDetails());
		transactions.setBuktiTransfer(null);
		transactions.setVaccineDate(findTransaction.getVaccineDate());
		transactions.setUsers(findTransaction.getUsers());
		
		//data buat kirim email
		String getEmail = findTransaction.getUsers().getEmail();
	    String getTransactionFullname = findTransaction.getUsers().getFullName();
	    String getTransactionDate = findTransaction.getTransactionDate();
	    String getVaccineDate = findTransaction.getVaccineDate();
	    int getTransactionTotalPrice = findTransaction.getGrandTotalPrice();
		
		String message = "<h1>Your Transaction Is Rejected</h1>\n ";
	    message +=
	        	"Hello " + getTransactionFullname + " Sorry your transaction is rejected, please reupload your payment slip"+
	        	" <table>\r\n" + 
	        	"  <tr>\r\n" + 
	        	"    <td>Transactions Id</td>\r\n" + 
	        	"    <td>:	"+transactionsId+"</td> \r\n" + 
	        	"  </tr>\r\n" + 
	        		
				"  <tr>\r\n" + 
				"    <td>Transaction Date</td>\r\n" + 
				"    <td>:	"+getTransactionDate +"</td>\r\n" + 
				"  </tr>\r\n" + 

				"  <tr>\r\n" + 
				"    <td>VaccineDate</td>\r\n" + 
				"    <td>:	"+getVaccineDate +"</td>\r\n" + 
				"  </tr>\r\n" + 

				"  <tr>\r\n" + 
				"    <td>Total Price</td>\r\n" + 
				"    <td>:	"+getTransactionTotalPrice +"</td>\r\n" + 
				"  </tr>\r\n" + 
	        	"</table> \n\n";
	        		
	        
	    emailUtil.sendEmail(getEmail, "Reject Transaction", message);
		
		return transactionRepo.save(transactions);
		
	}
    
    @PutMapping("/accept/{transactionsId}") 
   	public Transactions acceptTransactions(@RequestBody Transactions transactions, @PathVariable int transactionsId) {
   		Transactions findTransaction = transactionRepo.findById(transactionsId).get();
   		
   		transactions.setId(findTransaction.getId());
   		transactions.setTransactionDate(findTransaction.getTransactionDate());
   		transactions.setDoctors(findTransaction.getDoctors());
   		transactions.setGrandTotalPrice(findTransaction.getGrandTotalPrice());
   		transactions.setTransactionDetails(findTransaction.getTransactionDetails());
   		transactions.setBuktiTransfer(findTransaction.getBuktiTransfer());
		transactions.setVaccineDate(findTransaction.getVaccineDate());
   		transactions.setUsers(findTransaction.getUsers());
   		transactions.setRejectionReason(null);
   		
//   		findTransaction.getTransactionDetails().forEach(val ->{
//			val.getVaccines().setSold(val.getVaccines().getSold() + val.getQuantity());
//			vaccineRepo.save(val.getVaccines());		
//		});
   		
   		//data buat kirim email
   		String getEmail = findTransaction.getUsers().getEmail();
   		String getTransactionFullname = findTransaction.getUsers().getFullName();
   		String getTransactionDate = findTransaction.getTransactionDate();
   		String getVaccineDate = findTransaction.getVaccineDate();
   		int getTransactionTotalPrice = findTransaction.getGrandTotalPrice();
   			
   		String message = "<h1>Transaction Success</h1>\n ";
 	    message +=
 	        	"Hello " + getTransactionFullname + " congratulations your payment has been accepted, this is your transaction details :\n\n"+
 	        	" <table>\r\n" + 
 	        	"  <tr>\r\n" + 
 	        	"    <td>Transactions Id</td>\r\n" + 
 	        	"    <td>:	"+transactionsId+"</td> \r\n" + 
 	        	"  </tr>\r\n" + 
 	        		
 				"  <tr>\r\n" + 
 				"    <td>Transaction Date</td>\r\n" + 
 				"    <td>:	"+getTransactionDate +"</td>\r\n" + 
 				"  </tr>\r\n" + 

 				"  <tr>\r\n" + 
 				"    <td>VaccineDate</td>\r\n" + 
 				"    <td>:	"+getVaccineDate +"</td>\r\n" + 
 				"  </tr>\r\n" + 

 				"  <tr>\r\n" + 
 				"    <td>Total Price</td>\r\n" + 
 				"    <td>:	"+getTransactionTotalPrice +"</td>\r\n" + 
 				"  </tr>\r\n" + 
 	        	"</table> \n\n";
 	        		
 	    emailUtil.sendEmail(getEmail, "Transaction Success", message);
   		
   		return transactionRepo.save(transactions);
   		
   	}
    
    @GetMapping("/count/all")
	public int countTransactions(@RequestParam String status) {		
		return transactionRepo.countTransactions(status);
	}

}
