package com.example.demo.controllers;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Address;
import com.example.demo.entities.Employee;
import com.example.demo.entities.EmployeeDummy;
import com.example.demo.entities.Login;
import com.example.demo.entities.PassBasedEncryption;
import com.example.demo.entities.Role;
import com.example.demo.entities.SaltValue;
import com.example.demo.entities.Tourist;
import com.example.demo.services.AddressService;
import com.example.demo.services.EmployeeService;
import com.example.demo.services.LoginService;
import com.example.demo.services.RoleService;



@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class EmployeeController {
	
	@Autowired
	EmployeeService empservice;
	
	@Autowired
	RoleService roleservice;

	@Autowired
	LoginService lservice;
	
	@Autowired
	AddressService aservice;
	
	@Autowired
	JavaMailSender sender ;
	
	@Autowired
	SaltValue saltValue;
	
	
	// ---------------------------employee registration method----------------------------------
	
	@PostMapping("/empReg")
	public Employee EmpRegister(@RequestBody EmployeeDummy empdummy)
	{
		
        Role remp = roleservice.getRole(3);
        
        System.out.println("salt value = "+saltValue.getSalt());
		
		String encrypted = PassBasedEncryption.generateSecurePassword(empdummy.getPwd(), saltValue.getSalt());
		
		System.out.println(encrypted);
		
		Login l = new Login(empdummy.getUid(),encrypted,1,remp);
		
		Login lsavedemp = lservice.save(l);
		
		Address addr = new Address(empdummy.getAddressline(),empdummy.getDistrict(),empdummy.getCity(),empdummy.getState(),empdummy.getCountry(),empdummy.getPostalcode());
	
		Address asavedemp = aservice.save(addr);
		
		
		Employee emp = new Employee(empdummy.getE_bdate(),empdummy.getE_hiredate(),empdummy.getE_fname()
				,empdummy.getE_mname(),empdummy.getE_lname(),empdummy.getE_designation(),empdummy.getE_adharno(),empdummy.getE_email()
				,empdummy.getE_contact(),empdummy.getE_gender()	, asavedemp, lsavedemp);
		
		
		Employee empsaved =empservice.save(emp);
		

		System.out.println(empsaved);

		return empsaved;
		
	}
	
	// -------------------------- upload img of employee profile ----------------------------------
	
	@PostMapping(value="/uploadimageemp/{empid}",consumes = "multipart/form-data")
	public boolean uploadImage(@PathVariable("empid") int eid ,@RequestBody MultipartFile file )
	{
		System.out.println(eid);
		System.out.println(file.getName());
       boolean flag = true;
		try
		{
			
			empservice.upload(eid, file.getBytes());
		}
		catch(Exception e)
		{
			flag= false;
		}

		return flag;
		
	} 

	
	// ------------------------------------  fetching emp info by  login id ----------------------------
	
	@GetMapping("/employeegetbyid")
	public Employee getEmployee(@RequestParam("eid") int id )
	{
		Employee emp = empservice.getEmployee(id);
		System.out.println(emp);
		
		return emp;
	}

}

