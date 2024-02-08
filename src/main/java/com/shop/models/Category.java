package com.shop.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int categoryid;
	
	@NotEmpty
	private String categoryname;
	
	@NotEmpty
	private String categoryimage;

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Category(int categoryid, @NotEmpty String categoryname, @NotEmpty String categoryimage) {
		super();
		this.categoryid = categoryid;
		this.categoryname = categoryname;
		this.categoryimage = categoryimage;
	}

	public int getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public String getCategoryimage() {
		return categoryimage;
	}

	public void setCategoryimage(String categoryimage) {
		this.categoryimage = categoryimage;
	}
	
	
	
	
		
}
