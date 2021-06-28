package com.infy.ProductMS.service;

import java.util.List;

import com.infy.ProductMS.dto.ProductDTO;
import com.infy.ProductMS.exception.ProductMSException;

public interface ProductService {
	
	public String addProduct(ProductDTO productDTO) throws ProductMSException;
	
	public String deleteProduct(String id) throws ProductMSException;
	
	public ProductDTO getProductByName(String name) throws ProductMSException;
	
	public List<ProductDTO> getProductByCategory(String category) throws ProductMSException;
	
	public ProductDTO getProductById(String id) throws ProductMSException;

	public Boolean updateStockOfProd(String prodId, Integer quantity) throws ProductMSException;
	
	public List<ProductDTO> viewAllProducts() throws ProductMSException;

}