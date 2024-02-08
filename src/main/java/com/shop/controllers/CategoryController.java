package com.shop.controllers;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shop.CategoryService;
import com.shop.models.Category;
import com.shop.repo.CategoryRepository;


@Controller
@RequestMapping("/admin/category")
public class CategoryController {
	final String path=System.getProperty("user.dir")+"/catuploads/";
	
	@Autowired
	private CategoryRepository repo;
	
	
	@RequestMapping("/list")
	public String list(Model model,HttpSession session) {
		model.addAttribute("cats",repo.findAll());
		boolean read=false;
		if(session.getAttribute("cat")!=null) {
			model.addAttribute("cat",(Category)session.getAttribute("cat"));
			model.addAttribute("btntext","Update");
			session.setAttribute("cat",null);
			read=true;
		}else {
			model.addAttribute("cat",new Category());
			model.addAttribute("btntext","Save");
		}
		String msg="";
		if(session.getAttribute("msg")!=null) {
			msg=session.getAttribute("msg").toString();
			session.setAttribute("msg",null);
		}
		model.addAttribute("msg",msg);
		model.addAttribute("read",read);
		
		return "/admin/category";
	}
	
	@RequestMapping("/save")
	public String save(@ModelAttribute("cat") Category cat, String btntext,HttpSession session,@RequestParam MultipartFile file) {
		String msg="";
		try {
		if(btntext.equals("Save")) {
			String image=file.getOriginalFilename();
			InputStream is=file.getInputStream();	
			Files.copy(is, Paths.get(path+image), StandardCopyOption.REPLACE_EXISTING);
			System.out.println(Paths.get(path+image));
			cat.setCategoryimage(image);
			repo.save(cat);			
			msg="Category is Saved....";
		}else {
			if(!file.isEmpty())
			{
			String image=file.getOriginalFilename();
			InputStream is=file.getInputStream();
			Files.copy(is, Paths.get(path+image), StandardCopyOption.REPLACE_EXISTING);
			cat.setCategoryimage(image);
			repo.save(cat);
			}
			else
			{
				Optional<Category> st=repo.findById(cat.getCategoryid());
				cat.setCategoryimage(st.get().getCategoryimage());
				repo.save(cat);
			}
			
			repo.save(cat);
			msg=" Category is Updated";
		}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		session.setAttribute("msg" , msg);
		
		return "redirect:/admin/category/list";
	}
	
	@RequestMapping("/reset")
	public String reset() {
		
		
		return "redirect:/admin/category/list";
		
	}
	
	@RequestMapping("/del/{id}")
	public String del(@PathVariable("id") int id,HttpSession session) {
		
		repo.deleteById(id);
		session.setAttribute("msg", "Category is deleted....");
		
		return "redirect:/admin/category/list";
		
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, HttpSession session) {
		
		Optional<Category> s=repo.findById(id);
		Category cat=s.get();
		session.setAttribute("cat", cat);
		
		
		return "redirect:/admin/category/list";
	
    }
	@RequestMapping("/catuploads/{fname}")	
	@ResponseBody
	public String catphoto (@PathVariable("fname") String fname,HttpServletResponse response) throws Exception
	{
		byte b[]=Files.readAllBytes(Paths.get(path+fname));
		System.out.println(path+fname);
		response.setContentLength(b.length);
		response.setContentType("image/jpeg");
		ServletOutputStream os=response.getOutputStream();
		os.write(b);
		os.flush();
		return null;
	}
	
}
