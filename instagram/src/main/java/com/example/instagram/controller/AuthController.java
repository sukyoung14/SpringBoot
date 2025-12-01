package com.example.instagram.controller;

import com.example.instagram.dto.SignUpRequest;
import com.example.instagram.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    public final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signUpRequest", new SignUpRequest());
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signup(
            @Valid @ModelAttribute SignUpRequest signUpRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "auth/signup";
        }

        if (userService.existsByUsername(signUpRequest.getUsername())){
            bindingResult.rejectValue("username", "duplicate", "중복된 아이디입니다.");
            return "auth/signup";
        }

        userService.register(signUpRequest);
        return "redirect:/auth/login";
    }
}
