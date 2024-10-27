package com.example.evera_backend.repository;

import com.example.evera_backend.entity.Cart;
import com.example.evera_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Integer> {

    List<Cart> findByUser(User user);
}
