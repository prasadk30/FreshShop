package com.shop.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.models.Users;
import com.shop.repo.UserRepository;

@Controller
public class RegController {

	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	private UserRepository userRepo;
	
	@RequestMapping("/reg")
	public String Home(Model model)
	{
		model.addAttribute("user",new Users());
		return "reg";
	}
	
	@PostMapping("/reg")
	public String registration(@ModelAttribute("user")@Valid Users user,BindingResult result,Model model,HttpServletRequest request)
	{
		
		if(result.hasErrors()) {
			return "reg";
		}
		String to=request.getParameter("username");
		user.setEnabled("Y");
		user.setAuthority("ROLE_USER");
		userRepo.save(user);
		model.addAttribute("msg","registration completed");
		String msg;
		String sub;
	try {
		
		SimpleMailMessage mm=new SimpleMailMessage();
		mm.setTo(to);
		mm.setSubject(sub="Registration successfully Done");
		mm.setText(msg="Welcome to Fresh Shop Thank you for the registration...You will get most of fresh foods on this site.. ");
		
		mailSender.send(mm);
		model.addAttribute("msg","Mail Sent");
		
	} catch (Exception e) {
		model.addAttribute("msg",e.getMessage());
	}
		
		
		return "redirect:/login";
	}
	
}
