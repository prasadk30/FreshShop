package com.shop.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shop.models.CartView;
@Repository
public interface CartViewRepository extends JpaRepository<CartView, Integer>{
	List<CartView> findAllByUsername(String username);
	
}
