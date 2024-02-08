package com.shop.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.repo.CartRepository;
import com.shop.repo.CartViewRepository;
import com.shop.repo.CategoryRepository;
import com.shop.repo.ItemRepository;
import com.shop.repo.OrdersRepository;

@Controller
@RequestMapping("/user")
public class CustomerController {
	
	@Autowired
	CategoryRepository categoryRepo;
	
	@Autowired
	ItemRepository itemrepo;
	
	@Autowired
	CartRepository cartrepo;
	
	@Autowired
	CartViewRepository cartviewrepo;
	
	@Autowired
	OrdersRepository orderrepo;

	@RequestMapping("/")
	public String custHome(Model model) {
		
		model.addAttribute("cats",categoryRepo.findAll());
		return "customer/customer"; 
	}

	@RequestMapping("/itemcat/{id}")
	public String itemcat(@PathVariable("id") int id, Model model ) {
		model.addAttribute("items",itemrepo.findAll());
		
		return "itemcat";
	}
	
	
//	@RequestMapping("/")
//	public String home() {
//		return "customer/customer";
//	}
	
	@RequestMapping("/orderlist")
	public String orderList(Model model, HttpSession session,HttpServletRequest request)
	{
		String user=session.getAttribute("username")+"";
		model.addAttribute("ords",orderrepo.findByUsername(user));
		
		return "customer/orderlist";
	}
	
}
