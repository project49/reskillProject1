package com.infy.UserMS.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.infy.UserMS.entity.Cart;
import com.infy.UserMS.utility.CustomPK;

public interface CartRepository extends CrudRepository<Cart, CustomPK> {
	
	
	public List<Cart> findByCustomPKBuyerId(String id); 
	
	public void deleteByCustomPKBuyerIdAndCustomPKProdId(String buyId,String prodId);
	
	public Cart findByCustomPKBuyerIdAndCustomPKProdId(String buyId,String ProdId);

}

