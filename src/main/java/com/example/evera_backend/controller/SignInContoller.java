package com.example.evera_backend.controller;

import com.example.evera_backend.dto.Cart_DTO;
import com.example.evera_backend.dto.Response_DTO;
import com.example.evera_backend.dto.User_DTO;
import com.example.evera_backend.dto.Wish_DTO;
import com.example.evera_backend.entity.Cart;
import com.example.evera_backend.entity.User;
import com.example.evera_backend.entity.Wishlist;
import com.example.evera_backend.repository.CartRepository;
import com.example.evera_backend.repository.UserRepository;
import com.example.evera_backend.repository.WishListRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/SignIn")


public class SignInContoller {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final WishListRepository wishListRepository;

    @Autowired
    public SignInContoller(UserRepository userRepository, CartRepository cartRepository, WishListRepository wishListRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.wishListRepository = wishListRepository;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String signIn(@RequestBody User_DTO user_DTO, HttpServletRequest request) {
        Response_DTO response_DTO = new Response_DTO();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        if (user_DTO.getEmail().isEmpty()) {
            response_DTO.setContent("Please enter Email");
        } else if (user_DTO.getPassword().isEmpty()) {
            response_DTO.setContent("Please enter Password");
        } else {
            Optional<User> userOptional = userRepository.findByEmailAndPassword(user_DTO.getEmail(), user_DTO.getPassword());

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                if (!user.getVerification().equals("Verified")) {
                    request.getSession().setAttribute("email", user_DTO.getEmail());
                    response_DTO.setContent("Unverified");
                } else {
                    user_DTO.setFisrt_name(user.getFisrt_name());
                    user_DTO.setLast_name(user.getLast_name());
                    user_DTO.setPassword(null);
                    request.getSession().setAttribute("user", user_DTO);

                    // Transfer session cart to db cart
                    if (request.getSession().getAttribute("sessionCart") != null) {
                        ArrayList<Cart_DTO> sessionCart = (ArrayList<Cart_DTO>) request.getSession().getAttribute("sessionCart");
                        List<Cart> dbCart = cartRepository.findByUser(user);

                        if (dbCart.isEmpty()) {
                            for (Cart_DTO cart_DTO : sessionCart) {
                                Cart cart = new Cart();
                                cart.setProduct(cart_DTO.getProduct());
                                cart.setQty(cart_DTO.getQty());
                                cart.setUser(user);
                                cartRepository.save(cart);
                            }
                        } else {
                            for (Cart_DTO cart_DTO : sessionCart) {
                                boolean isFound = false;
                                for (Cart cart : dbCart) {
                                    if (cart_DTO.getProduct().getId() == cart.getProduct().getId()) {
                                        isFound = true;
                                        if (cart_DTO.getQty() + cart.getQty() <= cart.getProduct().getQty()) {
                                            cart.setQty(cart_DTO.getQty() + cart.getQty());
                                        } else {
                                            cart.setQty(cart.getProduct().getQty());
                                        }
                                        cartRepository.save(cart);
                                    }
                                }
                                if (!isFound) {
                                    Cart cart = new Cart();
                                    cart.setProduct(cart_DTO.getProduct());
                                    cart.setQty(cart_DTO.getQty());
                                    cart.setUser(user);
                                    cartRepository.save(cart);
                                }
                            }
                        }
                        request.getSession().removeAttribute("sessionCart");
                    }

                    // Transfer wishlist data to db
                    ArrayList<Wish_DTO> sessionWish = (ArrayList<Wish_DTO>) request.getSession().getAttribute("sessionWish");

                    if (sessionWish != null) {  // Check if sessionWish is not null before proceeding
                        List<Wishlist> dbWishlist = wishListRepository.findByUser(user);

                        if (dbWishlist.isEmpty()) {
                            for (Wish_DTO wish_DTO : sessionWish) {
                                Wishlist wishlist = new Wishlist();
                                wishlist.setProduct(wish_DTO.getProduct());
                                wishlist.setUser(user);
                                wishListRepository.save(wishlist);
                            }
                        }
                        request.getSession().removeAttribute("sessionWish");
                    }

                    response_DTO.setSuccess(true);
                    response_DTO.setContent("Sign In successful");
                }
            } else {
                response_DTO.setContent("Invalid details");
            }
        }
        return gson.toJson(response_DTO);
    }

}
