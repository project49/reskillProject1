package com.infy.UserMS.service;

import java.util.List;

import com.infy.UserMS.dto.BuyerDTO;
import com.infy.UserMS.dto.CartDTO;
import com.infy.UserMS.dto.SellerDTO;
import com.infy.UserMS.exception.UserMSException;

public interface UserService {

	public String buyerRegistration(BuyerDTO buyerDTO) throws UserMSException;
	
	public String sellerRegistration(SellerDTO sellerDTO) throws UserMSException;
	
	public String buyerLogin(String email, String password) throws UserMSException;
	
	public String sellerLogin(String email, String password) throws UserMSException;
	
	public String deleteBuyer(String id);
	
	public String deleteSeller(String id);
	
	public String wishlistService(String prodId,String buyerId);
	
	public String cartService(String prodId, String buyerId, Integer quantity);
	
	public List<CartDTO> getCartProducts(String id) throws UserMSException;
	
	public String removeFromCart(String buyerId, String prodId) throws UserMSException;
	
	public String updateRewardPoint(String buyerId, Integer rewPoints) throws UserMSException;
	
}

