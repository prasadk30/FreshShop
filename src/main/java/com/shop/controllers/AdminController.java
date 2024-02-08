package com.shop.controllers;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.models.OrderItems;
import com.shop.models.Orders;
import com.shop.repo.OrderItemsRepository;
import com.shop.repo.OrdersRepository;

@Controller
@RequestMapping("/admin")  
public class AdminController {
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	OrdersRepository orderrepo;
	
	@Autowired
	private OrderItemsRepository orderitems;
	
	@RequestMapping("/")
	public String admin() {
		
		return "admin/admin";
	}
	
	@RequestMapping("/orderlist")
	public String orderList(Model model,HttpSession session,HttpServletRequest request) 
	{
		//String name=request.getUserPrinciple().getName();
		List<Orders> ord=orderrepo.findAll();
		
		model.addAttribute("order", ord);
		return "admin/orderlist";
		
	}
	
	
    @RequestMapping("/showorder/{id}")
    public String showOrder(@PathVariable ("id") int id,Model model,HttpSession session) {
  	  
  	  Optional<OrderItems> oitems=orderitems.findById(id);
		
		Orders orders=orderrepo.getOne(id);
		List<OrderItems> lt=orderitems.findAll().stream().filter(ob->ob.getOrderid()==id).collect(Collectors.toList());
		
		model.addAttribute("ords", orders);	
		model.addAttribute("oit", lt);
  	  
  	  return"admin/showorder";
    }
    
    @RequestMapping("/orderlist2") 
	public String orderList2(Model model,HttpSession session,HttpServletRequest request) {		
		/*
		 * List<Orders> lt=orderrepo.list("dispatched");
		 * 
		 * model.addAttribute("ords", lt);
		 */
		
		return "admin/orderlist2";
	
	}
      
    
    
    @RequestMapping("/dispatch/{id}") 
	public String orderLi(@PathVariable ("id") int id ,Model model,HttpSession session,HttpServletRequest request) {		
  	 
			Orders ord=orderrepo.getOne(id);
			String user=request.getParameter("id");
			System.out.println(user);
			ord.setOrderstatus("Dispatched");
			this.orderrepo.save(ord);
			
			String sub;
			String msg;
			try {
				
				SimpleMailMessage mm=new SimpleMailMessage();
				mm.setTo(user);
				mm.setSubject(sub="Order Dispached");
				mm.setText(msg="\"Dear \"+user+\"Your Order is Dispatched From Our Head office You can recieve Your order within 2-3 working Days\"");
				
				mailSender.send(mm);
				
				}catch(Exception ex) {
					model.addAttribute("msg", ex.getMessage());
				}		
			
		
		
		
		return "admin/orderlist2";
	
	}
      
    @RequestMapping("/delorder/{id}")
	public String del(@PathVariable int id,HttpSession session) {		
		orderrepo.deleteById(id);
		session.setAttribute("msg", "Order is deleted...");
		 return "redirect:/admin/orderlist";
	}
    
    
		/*
		 * @RequestMapping("/dispatch") public String dispatchorder(HttpServletRequest
		 * request,HttpSession session,Model model) {
		 * 
		 * String user=request.getParameter("u"); String id=request.getParameter("id");
		 * orderService.updateOrderStatus("Order Dispatched",id);
		 * 
		 * 
		 * SimpleMailMessage msg=new SimpleMailMessage(); msg.setTo(user);
		 * msg.setSubject("Your Online Order Request"); msg.setText("Dear "
		 * +user+"Your Order is Dispatched From Our Head office You can recieve Your order within 2-3 working Days"
		 * ); mail.send(msg);
		 * model.addAttribute("msg","Your Order Dispatched Successfully");
		 * 
		 * return "redirect:/client/dispatchorder";
		 * 
		 */
	
	
	
}
