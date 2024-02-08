package com.shop.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.CartService;
import com.shop.models.Cart;
import com.shop.models.CartView;
import com.shop.models.Users;
import com.shop.repo.CartRepository;
import com.shop.repo.CartViewRepository;

@Controller
@RequestMapping("/user/cart")  //changed "user" as customer   original("/customer/cart")
public class CartController {
	
	@Autowired
	private CartRepository cartrepo;
	
	@Autowired
	CartService cartserv;
		
	@Autowired
	private CartViewRepository cartviewrepo;
	
	@RequestMapping("/user/cart")
	public String Home()
	{
		
		return "/customer/cart";
	}
	
	
	
	@RequestMapping("/list")  //"/list" changed as cart 23Feb23k
	public String list(Model model,HttpSession session,HttpServletRequest request, Principal principal) {
		String user=request.getUserPrincipal().getName();
		List<CartView> lt=cartviewrepo.findAllByUsername(user);
		
		model.addAttribute("carts",lt);
		//model.addAttribute("total", cartviewrepo.cartAmt(user));
		
		return "customer/cart";
	}
	
	@RequestMapping("/addtocart/{id}")
	public String cartid(@PathVariable("id") String id, HttpSession session,HttpServletRequest request,Principal principal) {
		String user=request.getUserPrincipal().getName();
		Cart cart=new Cart();
		cart.setItemId(Integer.parseInt(id));
		cart.setQty(1);
		cart.setUserName(user);
		cartrepo.save(cart);
		
		return "redirect:/user/cart/list";
		
	}
	@RequestMapping("/cartdel/{id}")
    public String cartDel(@PathVariable ("id") int id) {
    	cartviewrepo.deleteById(id);
    	return "redirect:/user/cart/list";
    }
	
	@RequestMapping("/update/{id}")
	public String cartUpdate(@PathVariable ("id") int id) {
		Cart obj=new Cart();
		obj.setQty(id);
		cartrepo.save(obj);
		
		return "redirect:/user/cart/list";
	}
	
}
