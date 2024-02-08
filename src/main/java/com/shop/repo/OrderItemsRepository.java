package com.shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.models.OrderItems;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer>{

}
