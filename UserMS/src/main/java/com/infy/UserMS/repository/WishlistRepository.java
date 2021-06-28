package com.infy.UserMS.repository;

import org.springframework.data.repository.CrudRepository;

import com.infy.UserMS.entity.Wishlist;
import com.infy.UserMS.utility.CustomPK;


public interface WishlistRepository extends CrudRepository<Wishlist, CustomPK> {

}