package com.infy.ProductMS.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.ProductMS.dto.ProductDTO;
import com.infy.ProductMS.entity.Product;
import com.infy.ProductMS.exception.ProductMSException;
import com.infy.ProductMS.repository.ProductRepository;
import com.infy.ProductMS.validator.ProductValidator;

@Service(value = "productService")
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	private static int p;
	
	static {
		p=100;
	}
	
	@Override
	public String addProduct(ProductDTO productDTO) throws ProductMSException {
		
		ProductValidator.validateProduct(productDTO);
		
		Product product = productRepository.findByProductName(productDTO.getProductName());
		
		if(product != null)
			throw new ProductMSException("Service.PRODUCT_ALREADY_EXISTS");
		
		product = new Product();
		
		String id = "P"+p++;
		
		product.setProdId(id);
		product.setProductName(productDTO.getProductName());
		product.setPrice(productDTO.getPrice());
		product.setCategory(productDTO.getCategory());
		product.setDescription(productDTO.getDescription());
		product.setImage(productDTO.getImage());
		product.setSubCategory(productDTO.getSubCategory());
		product.setSellerId(productDTO.getSellerId());
		product.setProductRating(productDTO.getProductRating());
		product.setStock(productDTO.getStock());
		
		productRepository.save(product);
		
		return product.getProdId();
	}

	@Override
	public ProductDTO getProductByName(String name) throws ProductMSException {
		
		Product product = productRepository.findByProductName(name);
		
		if(product == null)
			throw new ProductMSException("Service.PRODUCT_DOES_NOT_EXISTS");
		
		ProductDTO productDTO = new ProductDTO();
		
		productDTO.setProdId(product.getProdId());
		productDTO.setCategory(product.getCategory());
		productDTO.setDescription(product.getDescription());
		productDTO.setImage(product.getImage());
		productDTO.setPrice(product.getPrice());
		productDTO.setProductName(product.getProductName());
		productDTO.setProductRating(product.getProductRating());
		productDTO.setSellerId(product.getSellerId());
		productDTO.setStock(product.getStock());
		productDTO.setSubCategory(product.getCategory());
		
		return productDTO;
	}
	
	@Override
	public ProductDTO getProductById(String id) throws ProductMSException {
		
		Product product = productRepository.findByProdId(id);
		
		if(product == null)
			throw new ProductMSException("Service.PRODUCT_DOES_NOT_EXISTS");
		
		ProductDTO productDTO = new ProductDTO();
		
		productDTO.setProdId(product.getProdId());
		productDTO.setCategory(product.getCategory());
		productDTO.setDescription(product.getDescription());
		productDTO.setImage(product.getImage());
		productDTO.setPrice(product.getPrice());
		productDTO.setProductName(product.getProductName());
		productDTO.setProductRating(product.getProductRating());
		productDTO.setSellerId(product.getSellerId());
		productDTO.setStock(product.getStock());
		productDTO.setSubCategory(product.getCategory());
		
		return productDTO;
	}

	@Override
	public String deleteProduct(String id) throws ProductMSException {
		
		Product product = productRepository.findByProdId(id);
		
		if(product == null)
			throw new ProductMSException("Service.CANNOT_DELETE_PRODUCT");
		
		productRepository.delete(product);
		
		return "Product successfully deleted";
		
	}

	@Override
	public List<ProductDTO> getProductByCategory(String category) throws ProductMSException {
		
		List<Product> list = productRepository.findByCategory(category);
		
		if(list.isEmpty())
			throw new ProductMSException("Service.CATEGORY_ERROR");
		
		List<ProductDTO> li = new ArrayList<>();
		
		for(Product product : list)
		{
			ProductDTO productDTO = new ProductDTO();
			
			productDTO.setProdId(product.getProdId());
			productDTO.setCategory(product.getCategory());
			productDTO.setDescription(product.getDescription());
			productDTO.setImage(product.getImage());
			productDTO.setPrice(product.getPrice());
			productDTO.setProductName(product.getProductName());
			productDTO.setProductRating(product.getProductRating());
			productDTO.setSellerId(product.getSellerId());
			productDTO.setStock(product.getStock());
			productDTO.setSubCategory(product.getCategory());
			
			li.add(productDTO);
		}
		
		return li;
	}

	@Override
	public Boolean updateStockOfProd(String prodId, Integer quantity) throws ProductMSException {
		Optional<Product> optProduct = productRepository.findById(prodId);
		Product product = optProduct.orElseThrow(()-> new ProductMSException("Product does not exist"));
		if(product.getStock()>=quantity) {
			product.setStock(product.getStock()-quantity);
			return true;
		}
		return false;		
	}

	@Override
	public List<ProductDTO> viewAllProducts() throws ProductMSException {
		
		List<Product> list = productRepository.findAll();
		
		if(list.isEmpty())
			throw new ProductMSException("There are no products to be shown");
		
		List<ProductDTO> li = new ArrayList<>();
		
		list.forEach(l -> {
			ProductDTO prod = new ProductDTO();
			prod.setCategory(l.getCategory());
			prod.setDescription(l.getDescription());
			prod.setImage(l.getImage());
			prod.setPrice(l.getPrice());
			prod.setProdId(l.getProdId());
			prod.setProductName(l.getProductName());
			prod.setProductRating(l.getProductRating());
			prod.setSellerId(l.getSellerId());
			prod.setStock(l.getStock());
			prod.setSubCategory(l.getSubCategory());
			
			li.add(prod);
		});
		
		return li;
	}


}
