package com.infy.UserMS.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.UserMS.dto.BuyerDTO;
import com.infy.UserMS.dto.CartDTO;
import com.infy.UserMS.dto.SellerDTO;
import com.infy.UserMS.entity.Buyer;
import com.infy.UserMS.entity.Cart;
import com.infy.UserMS.entity.Seller;
import com.infy.UserMS.entity.Wishlist;
import com.infy.UserMS.exception.UserMSException;
import com.infy.UserMS.repository.BuyerRepository;
import com.infy.UserMS.repository.CartRepository;
import com.infy.UserMS.repository.SellerRepository;
import com.infy.UserMS.repository.WishlistRepository;
import com.infy.UserMS.utility.CustomPK;
import com.infy.UserMS.validator.UserValidator;

@Service(value = "userService")
@Transactional
public class UserServiceImpl implements UserService {
	
	
	private static int b;
	private static int s;
	
	static {
		b=100;
		s=100;
	}
	
	@Autowired
	private BuyerRepository buyerRepository;
	
	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private WishlistRepository wishlistRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Override
	public String buyerRegistration(BuyerDTO buyerDTO) throws UserMSException {
		// TODO Auto-generated method stub
		
		UserValidator.validateBuyer(buyerDTO);
		
		Buyer buy = buyerRepository.findByPhoneNumber(buyerDTO.getPhoneNumber());
		
		if(buy != null)
			throw new UserMSException("Buyer already present");
		
		String id = "B" + b++;
		
		buy = new Buyer();
		
		buy.setBuyerId(id);
		buy.setEmail(buyerDTO.getEmail());
		buy.setName(buyerDTO.getName());
		buy.setPhoneNumber(buyerDTO.getPhoneNumber());
		buy.setPassword(buyerDTO.getPassword());
		buy.setIsActive("False");
		buy.setIsPrivileged("False");
		buy.setRewardPoints("0");
		
		buyerRepository.save(buy);
		
		return buy.getBuyerId();
	}

	@Override
	public String sellerRegistration(SellerDTO sellerDTO) throws UserMSException {
		// TODO Auto-generated method stub
		
		UserValidator.validateSeller(sellerDTO);
		
		Seller seller = sellerRepository.findByPhoneNumber(sellerDTO.getPhoneNumber());
		
		if(seller != null)
			throw new UserMSException("Seller Already present");
		
		String id = "S" + s++;
		
		seller = new Seller();
		
		seller.setEmail(sellerDTO.getEmail());
		seller.setSellerId(id);
		seller.setName(sellerDTO.getName());
		seller.setPassword(sellerDTO.getPassword());
		seller.setIsActive("False");
		seller.setPhoneNumber(sellerDTO.getPhoneNumber());
		
		sellerRepository.save(seller);
		
		return seller.getSellerId();
	}

	@Override
	public String buyerLogin(String email, String password) throws UserMSException {
		// TODO Auto-generated method stub
	
		if(!UserValidator.validateEmail(email))
			throw new UserMSException("Enter valid email id");
		
		Buyer buyer = buyerRepository.findByEmail(email);
		
//		System.out.println(buyer);
		
		if(buyer == null)
			throw new UserMSException("Wrong credentials");
		
		if(!buyer.getPassword().equals(password))
			throw new UserMSException("Wrong credentials");
		
		buyer.setIsActive("True");
			
		buyerRepository.save(buyer);
		
		return "Login Success";
	}

	@Override
	public String sellerLogin(String email, String password) throws UserMSException {

		if(!UserValidator.validateEmail(email))
			throw new UserMSException("Enter valid email id");
		
		Seller seller = sellerRepository.findByEmail(email);
		
//		System.out.println(seller);
		
		if(seller == null)
			throw new UserMSException("Wrong credentials");
		
		if(!seller.getPassword().equals(password))
			throw new UserMSException("Wrong credentials");
		
		seller.setIsActive("True");
			
		sellerRepository.save(seller);
		
		return "Login Success";
	}

	@Override
	public String deleteBuyer(String id){
		
		Buyer buyer = buyerRepository.findByBuyerId(id);
		
		buyerRepository.delete(buyer);
		
		return "Account successfully deleted";
	}

	@Override
	public String deleteSeller(String id) {
		
		Seller seller = sellerRepository.findBySellerId(id);
		
		sellerRepository.delete(seller);
		
		return "Account successfully deleted";
	}

	@Override
	public String wishlistService(String prodId, String buyerId) {
		
		CustomPK cust = new CustomPK(prodId,buyerId);
	
		Wishlist w = new Wishlist();
		
		w.setCustomId(cust);
		
		wishlistRepository.save(w);
		
		return "Added Successfully to Wishlist";
	}
	
	@Override
	public String cartService(String prodId, String buyerId, Integer quantity) {
		
		CustomPK cust = new CustomPK(prodId,buyerId);
	
		Cart cart = new Cart();
		
		cart.setCustomPK(cust);
		
		cart.setQuantity(quantity);
		
		cartRepository.save(cart);
		
		return "Added Successfully to Cart";
	}

	@Override
	public List<CartDTO> getCartProducts(String id) throws UserMSException {
		
		List<Cart> list = cartRepository.findByCustomPKBuyerId(id);
		
		if(list.isEmpty())
			throw new UserMSException("No Items In Cart");
		
		List<CartDTO> li = new ArrayList<>();
		
		for(Cart cart : list)
		{
			CartDTO cartDTO = new CartDTO();
			
			cartDTO.setBuyerId(cart.getCustomPK().getBuyerId());
			cartDTO.setProdId(cart.getCustomPK().getProdId());
			cartDTO.setQuantity(cart.getQuantity());
			
			li.add(cartDTO);
		}
		
		return li;
	}

	@Override
	public String removeFromCart(String buyerId, String prodId) throws UserMSException {
		// TODO Auto-generated method stub
		
		Cart cart = cartRepository.findByCustomPKBuyerIdAndCustomPKProdId(buyerId, prodId);
		
		if(cart==null)
			throw new UserMSException("No Such Item In Cart");
		
		cartRepository.deleteByCustomPKBuyerIdAndCustomPKProdId(buyerId, prodId);
		
		return "The product was deleted successfully";
	}

	@Override
	public String updateRewardPoint(String buyerId, Integer rewPoints) throws UserMSException {
		// TODO Auto-generated method stub
		
		Buyer buyer = buyerRepository.findByBuyerId(buyerId);
		
		if(buyer==null)
			throw new UserMSException("Buyer not found");
		
		buyer.setRewardPoints(rewPoints.toString());
		
		buyerRepository.save(buyer);
		
		return "Reward Points Updated for buyer Id : "+ buyer.getBuyerId();
	}

}