package com.example.evera_backend.controller;

import com.example.evera_backend.dto.Response_DTO;
import com.example.evera_backend.dto.User_DTO;
import com.example.evera_backend.entity.User;
import com.example.evera_backend.model.Validation;
import com.example.evera_backend.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/SignUp")
public class SignUpController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public  String saveuser(@RequestBody User_DTO user_DTO, HttpServletRequest request){
        Response_DTO response_DTO = new Response_DTO();

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        if (user_DTO.getFisrt_name().isEmpty()) {
            response_DTO.setContent("Please enter First Name");
        } else if (user_DTO.getLast_name().isEmpty()) {
            response_DTO.setContent("Please enter Last Name");
        } else if (user_DTO.getEmail().isEmpty()) {
            response_DTO.setContent("Please enter Email");
        } else if (!Validation.isEmailValid(user_DTO.getEmail())) {
            response_DTO.setContent("Please enter valid email");
        } else if (user_DTO.getPassword().isEmpty()) {
            response_DTO.setContent("Please enter Password");
        } else if (!Validation.isPasswordValid(user_DTO.getPassword())) {
            response_DTO.setContent("Password must include ate leat one uppercase letter, number,special charactor and be at least eight chracters long.");
        } else {

         Optional<User> userOptional = userRepository.findByEmail(user_DTO.getEmail());

            if (userOptional.isPresent()) {
                response_DTO.setContent("this email all ready exixt");
            } else {


                int code = (int) (Math.random() * 10000000);

                User user = new User();
                user.setEmail(user_DTO.getEmail());
                user.setFisrt_name(user_DTO.getFisrt_name());
                user.setLast_name(user_DTO.getLast_name());
                user.setPassword(user_DTO.getPassword());
                user.setVerification(String.valueOf(code));
                user.setSubscription(0);

                userRepository.save(user);
                request.getSession().setAttribute("email", user_DTO.getEmail());
                response_DTO.setSuccess(true);
                response_DTO.setContent("Registration success");
            }

        }

        return gson.toJson(response_DTO);

    }
}
