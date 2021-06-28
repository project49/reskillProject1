package com.infy.OrderMS.service;

import java.util.List;

import com.infy.OrderMS.dto.CartDTO;
import com.infy.OrderMS.dto.OrderDTO;
import com.infy.OrderMS.dto.OrderPlacedDTO;
import com.infy.OrderMS.dto.ProductDTO;
import com.infy.OrderMS.exception.OrderMSException;



public interface OrderService {
	
	public List<OrderDTO> viewAllOrders() throws OrderMSException;

	public OrderPlacedDTO placeOrder(List<ProductDTO> productList, List<CartDTO> cartList, OrderDTO order) throws OrderMSException;

	public List<OrderDTO> viewOrdersByBuyer(String buyerId)throws OrderMSException;

	public OrderDTO viewOrder(String orderId) throws OrderMSException;

	public String reOrder(String buyerId, String orderId) throws OrderMSException;


}
