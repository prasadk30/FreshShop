package com.shop;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.icu.math.BigDecimal;
import com.shop.repo.CartRepository;

@Service
public class CartService {

	@Autowired
	private EntityManager em;
	
	@Autowired
	CartRepository dao;
	
	public double cartAmt(int user)
	{
		Query query=em.createNativeQuery("select sum(c.ItemPrice*c.Qty) from cartview c where c.UserName=?1");
		query.setParameter(1, user);
		Object sum=BigDecimal.valueOf(Double.parseDouble(query.getSingleResult().toString()));
		return (Double)sum;
		
	}
	
	
	
	
}
