package com.shop.controllers;

import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.repo.CategoryRepository;
import com.shop.repo.ItemRepository;
import com.shop.repo.UserRepository;

@Controller
public class HomeController {
	
	final String path=System.getProperty("user.dir")+"/catuploads/";
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	
	@RequestMapping("/home")
	public String Home(Model model) {
		
		model.addAttribute("cats",categoryRepository.findAll());
		System.out.println(categoryRepository.findAll());
		
		return "home";
		
	}
		
	
	@RequestMapping("/login")
	public String login() {
			
		return "login";
	}
	
	@RequestMapping("/itemcat/{id}")
	public String itemcat(@PathVariable("id") int id, Model model ) {
		model.addAttribute("items",itemRepository.findAll());
		
		return "itemcat";
	}
	
	@RequestMapping("/catuploads/{fname}")	
	@ResponseBody
	public String photostud (@PathVariable("fname") String fname,HttpServletResponse response) throws Exception
	{
		byte b[]=Files.readAllBytes(Paths.get(path+fname));
		response.setContentLength(b.length);
		response.setContentType("image/jpg");
		ServletOutputStream os=response.getOutputStream();
		os.write(b);
		os.flush();
		return null;
	}
	@RequestMapping("/itemuploads/{fname}")	   
	public String itemstud (@PathVariable("fname") String fname,HttpServletResponse response) throws Exception
	{
		byte b[]=Files.readAllBytes(Paths.get(path+fname));
		response.setContentLength(b.length);
		response.setContentType("image/jpg");
		ServletOutputStream os=response.getOutputStream();
		os.write(b);
		os.flush();
		return null;
	}
	
	
}
