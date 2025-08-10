package com.hana.flower.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.hana.flower.model.Product;
import com.hana.flower.model.User;
import com.hana.flower.model.WishList;
import com.hana.flower.repository.WishListItemRepository;
import com.hana.flower.repository.WishListRepository;
import com.hana.flower.wrapper.ServiceResponse;

public class WishListService {

	@Autowired
	private WishListRepository wishListRepository;

	/*
	 * @Autowired private ProductService productService;
	 */

	@Autowired
	private UserService userService;

	@Autowired
	private WishListItemRepository wishListItemRepository;

	public WishList createWishList(User user) {
		WishList wishList = WishList.builder().user(user).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now())
				.build();

		wishListRepository.save(wishList);
		return wishList;
	}

	public WishList getWishList(long userId) {
		ServiceResponse<User> user = userService.getUserById(userId);

		return user.getData().getWishList();

	}

	public WishList deleteWishList(Long userId) {

		WishList wishList = getWishList(userId);
		wishListRepository.delete(wishList);
		return wishList;
	}

	public List<Product> getWishListItems(Long userId) {
		WishList wishList = getWishList(userId);

		return wishListItemRepository.getAllWishListItems(wishList.getId()).stream().map(item -> item.getProduct())
				.collect(Collectors.toList());

	}
	
	public Product getWishListItem(long wishListid,long productId)
	{
		
		return wishListItemRepository.getWishListItem(wishListid, productId);
		
	}

}
