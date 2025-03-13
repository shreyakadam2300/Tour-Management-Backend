package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Employee;
import com.example.demo.entities.Login;
import com.example.demo.entities.LoginCheck;
import com.example.demo.entities.PassBasedEncryption;
import com.example.demo.entities.SaltValue;
import com.example.demo.entities.Tourist;
import com.example.demo.services.EmployeeService;
import com.example.demo.services.LoginService;
import com.example.demo.services.TouristRegService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class LoginController 
{
	@Autowired
	LoginService loginservice;
	
	@Autowired
	TouristRegService tservice;
	
	@Autowired
	JavaMailSender sender;
	
	@Autowired
	SaltValue saltValue;
	
	@Autowired
	EmployeeService eservice;
	
	//----------------------------------------- checking login ----------------------------------------
	
	@PostMapping("/chkLogin")
	public Login chkLogin(@RequestBody LoginCheck lcheck)
	{

		String encrypted = PassBasedEncryption.generateSecurePassword(lcheck.getPwd(), saltValue.getSalt());
		System.out.println("in login controller == encr :"+encrypted);;

		return loginservice.getLogin(lcheck.getUid(), encrypted);
		
	}
	
	
	// ---------------------------------------- forget password method ----------------------------
	
	@GetMapping("/forgotpassword")
	public Login forgotPassword(@RequestParam("userid") String uid)
	{
		int update=0;
		String newpwd=uid+"123";
		String encrypted = PassBasedEncryption.generateSecurePassword(newpwd, saltValue.getSalt());
		
		Login l = loginservice.getLoginforforgetpwd(uid);
		if(l!=null)
		{
		int roleid=l.getRole_id().getRole_id();
		System.out.println("role id : "+roleid);
		String mail = "";
		
		if(roleid==1)
		{
			
			Tourist t = tservice.getTouristforforgetpwd(uid);
			 mail = t.getT_email();
			
		}
		else if(roleid==3)
		{
			Employee e = eservice.getEmployeeforforgetpwd(uid);
			 mail = e.getE_email();
			 
		}
		
		 update=loginservice.UpdatePasswordiInLogin(encrypted,uid);
			SimpleMailMessage mailmsg = new SimpleMailMessage();
			mailmsg.setFrom("takeatour28@gmail.com");
			mailmsg.setTo(mail);
			mailmsg.setSubject("New Password");
			mailmsg.setText("Your new password: "+newpwd);
			sender.send(mailmsg);
		}
		System.out.println(".........."+l);
			return l;
					
	}
	

}