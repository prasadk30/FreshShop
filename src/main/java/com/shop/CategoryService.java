package com.shop;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.models.Category;
import com.shop.repo.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository catrepos;
	
	public List<Category> list()
	{
		return catrepos.findAll();
	}
	public Optional<Category>get(String catid)
	{
		return catrepos.findById(Integer.parseInt(catid));
	}
	public void save(Category category)
	{
		catrepos.save(category);
	}
	public void update(Category category)
	{
		catrepos.save(category);
	}
	public void delete(String id)
	{
		catrepos.deleteById(Integer.parseInt(id));
	}
}
