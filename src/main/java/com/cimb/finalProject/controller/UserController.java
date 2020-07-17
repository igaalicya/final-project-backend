package com.cimb.finalProject.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import com.cimb.finalProject.dao.UserRepo;
import com.cimb.finalProject.entity.Users;
import com.cimb.finalProject.util.EmailUtil;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
	
	@Autowired
	private UserRepo userRepo;
	
	private PasswordEncoder pwEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	private EmailUtil emailUtil;
	
	@GetMapping("/all")
	public Iterable<Users> findAllUsers() {
        return userRepo.findAll();
    }
	
	@GetMapping("/username")
    public Iterable<Users> getUsername(@RequestParam String username) {
        return userRepo.findUsername(username);
    }
	
	@GetMapping("/email")
    public Iterable<Users> getEmail(@RequestParam String email) {
        return userRepo.findEmail(email);
    }
	
//	@GetMapping("/id")
//    public Iterable<Users> getId(@RequestParam int id) {
//        return userRepo.findUsersById(id);
//    }
	
	@GetMapping("/id")
    public Users getId(@RequestParam int id) {
        return userRepo.findById(id).get();
    }
		
	@PostMapping
	public Users registerUser(@RequestBody Users user) {		
		Optional<Users> findUsername = userRepo.findByUsername(user.getUsername());
		Optional<Users> findEmail = userRepo.findByEmail(user.getEmail());
		
		if (findUsername.toString() != "Optional.empty") {
			throw new RuntimeException("Username exists, use another username!");
		} else if(findEmail.toString() != "Optional.empty") {
			throw new RuntimeException("Email exists, use another email!");
		} else {
			String encodedPassword = pwEncoder.encode(user.getPassword());
			String verifyToken = pwEncoder.encode(user.getUsername() + user.getEmail());

			user.setPassword(encodedPassword);
			user.setVerified(false);
			user.setVerifyToken(verifyToken);
			user.setRole("user");
			
			Users savedUser = userRepo.save(user);
			savedUser.setPassword(null);
			
			String linkToVerify = "http://localhost:8080/users/verify/" + user.getUsername() + "?token=" + verifyToken;
			
			String message = "<h1>Congrats " + user.getUsername()+ "! Your Account Has Been Registered</h1>\n";
			message += " <a href=\"" + linkToVerify + "\">Click here</a> to verify your account.";
			
			emailUtil.sendEmail(user.getEmail(), "Registrasi Akun", message);
			
			return savedUser;
		}
		
	}
		
	
	@GetMapping
	public Users LoginUser(@RequestParam String username, @RequestParam String password) {
		Optional<Users> findUsername = userRepo.findByUsername(username);
		Users findUser = userRepo.findByUsername(username).get();
		
		if(findUsername.toString() != "Optional.empty") {
			if(pwEncoder.matches(password, findUser.getPassword())) {
				findUser.setPassword(null);
				return findUser;
			} else{
				throw new RuntimeException("Password salah!");
			}
			
		} else{
			throw new RuntimeException("Username exist!");
		}
		
	}
	
	@GetMapping("/verify/{username}")
	public String verifyUser (@PathVariable String username, @RequestParam String token) {
		Users findUser = userRepo.findByUsername(username).get();
		
		String linkToHome = "http://localhost:3000";
		
		if (findUser.getVerifyToken().equals(token)) {
			findUser.setVerified(true);
		} else {
			throw new RuntimeException("Token is invalid");
		}
		
		userRepo.save(findUser);
		
		return "<center><h2>Succes! Your Account Has Been Verified\n <a href=\"" + linkToHome + "\">Click here</a> to go back to Vrome. \n</h2></center>";
	}
	
//	@PutMapping("/{usersId}/address")
//	public Users addAddressToUsers(@RequestBody UserAddress userAddress, @PathVariable int usersId) {
//		Users findUsers = userRepo.findById(usersId).get();
//		
//		if (findUsers == null)
//			throw new RuntimeException("Employee not found");
//		
//		findUsers.setUserAddress(userAddress);
//		
//		return userRepo.save(findUsers); 
//	}
	
	
	@PostMapping("/forgetpassword")
	public Users forgetPassword(@RequestBody Users user){
		Users findUser = userRepo.findByEmail(user.getEmail()).get();
		String message = "<h1>Reset Password!</h1>\n ";
		message +="Click this <a href=\"http://localhost:3000/resetPassword/"+findUser.getId()+"/"+findUser.getVerifyToken()+"\">link</a> to reset your password";
		emailUtil.sendEmail(user.getEmail(), "Reset Password", message);
	        return findUser;
	}
		
	@GetMapping("/reset/{userId}/{token}")
	public Users getUserReset(@PathVariable int userId, @PathVariable String token){

		Users findUser = userRepo.findById(userId).get();
		findUser = userRepo.findByVerifyToken(token).get();
		
	    return findUser;
	    }
		
	@PutMapping("/resetPassword")
	public Users resetPassword(@RequestBody Users user){
//		Users findUser = userRepo.findById(user.getId()).get();
		Optional<Users> findUsers = userRepo.findById(user.getId());

		if (findUsers.toString() == "Optional.empty")
			throw new RuntimeException("Movies with id " + user.getId() + " does not exist");

		String encodedPassword = pwEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepo.save(user);
		return user;
	}
	
	
	// edit profile user
	@PutMapping("/editProfileUser/{id}")
	public Users editProfie (@RequestBody Users user, @PathVariable int id) {
		Users findUser = userRepo.findById(id).get();
		
		if (findUser==null) {
			throw new RuntimeException("Not Found");
		}
		user.setId(id);
		user.setPassword(findUser.getPassword());
		user.setVerifyToken(findUser.getVerifyToken());
		user.setVerified(findUser.isVerified());
		user.setCarts(findUser.getCarts());
		user.setTransactions(findUser.getTransactions());
		user.setWishlists(findUser.getWishlists());
		user.setRole(findUser.getRole());
		
		return userRepo.save(user);
		
	}
	
	// edit profile user admin
	@PutMapping("/editProfileAdmin/{id}")
	public Users editProfieAdmin (@RequestBody Users user, @PathVariable int id) {
		Users findUser = userRepo.findById(id).get();
		
		if (findUser==null) {
			throw new RuntimeException("Not Found");
		}
		user.setId(id);
		user.setPassword(findUser.getPassword());
		user.setVerifyToken(findUser.getVerifyToken());
		user.setVerified(findUser.isVerified());
		user.setCarts(findUser.getCarts());
		user.setTransactions(findUser.getTransactions());
		user.setWishlists(findUser.getWishlists());
		
		return userRepo.save(user);
		
	}
	
	//check encoded pass
	@GetMapping("/checkOldPassword/{usersId}")
	public Users checkOldPassword (@RequestParam String password, @PathVariable int usersId) {
		Users findUser = userRepo.findById(usersId).get();
		if (pwEncoder.matches(password, findUser.getPassword())) {
			findUser.setPassword(null);
			return findUser;
		}  else {
			throw new RuntimeException("password salah");
		}
	}
	
	@PutMapping("/changePassword/{usersId}")
	public Users changePassword (@RequestBody Users users, @PathVariable int usersId) {
		Users findUser = userRepo.findById(usersId).get();
		
		if (findUser==null) {
			throw new RuntimeException("Not Found");
		}
		users.setId(usersId);
		
		String encodePassword = pwEncoder.encode(users.getPassword());
		users.setPassword(encodePassword);

		users.setVerifyToken(findUser.getVerifyToken());
		users.setVerified(findUser.isVerified());

		users.setCarts(findUser.getCarts());
		users.setTransactions(findUser.getTransactions());
		users.setWishlists(findUser.getWishlists());

		return userRepo.save(users);

	}
	
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable int id) {
		Optional<Users> findUsers = userRepo.findById(id);
		
		if (findUsers.toString()== null) {
			throw new RuntimeException ("not found ");
		}
		
		userRepo.deleteById(id);
	}


}