package com.example.evera_backend.repository;

import com.example.evera_backend.entity.Cart;
import com.example.evera_backend.entity.User;
import com.example.evera_backend.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListRepository extends JpaRepository<Wishlist,Integer> {
    List<Wishlist> findByUser(User user);
}
