package com.shop.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shop.models.Item;
import com.shop.repo.CategoryRepository;
import com.shop.repo.ItemRepository;

@Controller
@RequestMapping("/admin/item")
public class ItemController {
	final String path=System.getProperty("user.dir")+"/itemuploads/";
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ItemRepository itemRepository;
	
	@RequestMapping("/list")
	public String itemList(Model model,HttpSession session) {
		model.addAttribute("items",itemRepository.findAll());
		model.addAttribute("cats",categoryRepository.findAll());
		boolean read=false;
		if(session.getAttribute("itemcat")!=null) {
			model.addAttribute("itemcat",(Item)session.getAttribute("itemcat"));
			model.addAttribute("btntext","Update");
			session.setAttribute("itemcat",null);
			read=true;
		}else {
			model.addAttribute("itemcat",new Item());
			model.addAttribute("btntext","Save");
		}
		String msg="";
		if(session.getAttribute("msg")!=null) {
			msg=session.getAttribute("msg").toString();
			session.setAttribute("msg",null);
		}
		model.addAttribute("msg",msg);
		model.addAttribute("read",read);
		
		return "admin/item";
	}
	
	@RequestMapping("/save")
	public String save(@ModelAttribute("itemcat") Item itemcat,HttpSession session,String btntext, @RequestParam MultipartFile file) {
		
		try {
		if(btntext.equals("Save"))
		{
			String image=file.getOriginalFilename();
			
				InputStream is = file.getInputStream();
				
				Files.copy(is,Paths.get(path+file), StandardCopyOption.REPLACE_EXISTING);
				System.out.println(Paths.get(path+image));
				itemcat.setItemImage(image);
				itemRepository.save(itemcat);
							
		}} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
				
		return "redirect:/admin/item/list";
		
	}
	
	@RequestMapping("/reset")
	public String reset() {
		
		return "redirect:/admin/item/list";
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable ("id") int id,HttpSession session) {
		
		Optional<Item> s=itemRepository.findById(id);
		Item itemcat=s.get();
		session.setAttribute("itemcat",itemcat);
		
		return "redirect:/admin/item/list";
	}
	
	@RequestMapping("/del/{id}")
	public String delete (@PathVariable ("id") int id, HttpSession session) {
		
		itemRepository.deleteById(id);
		return "redirect:/admin/item/list";
		
	}
	
	@RequestMapping("/itemuploads/{fname}")
	public String photoitem(@PathVariable("fname") String fname,HttpServletResponse response) throws Exception{
		
		byte b[]=Files.readAllBytes(Paths.get(path+fname));
		response.setContentLength(b.length);
		response.setContentType("image/jpeg");
		ServletOutputStream os=response.getOutputStream();
		os.write(b);
		os.flush();
		return null;
		
	}
}
