package com.example.session_cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping("/login")
    public String login(HttpServletResponse response, @RequestParam String username, @RequestParam String password) {
        if ("admin".equals(username) && "password".equals(password)) {
            String token = "12345";
            Cookie cookie = new Cookie("JWT", token);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(3600);
            cookie.setPath("/");
            response.addCookie(cookie);

            return "Login successful, token stored in cookie: " + token;
        }
        return "Invalid username or password";
    }

    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT".equals(cookie.getName())) {
                    String token = cookie.getValue();

                    if ("12345".equals(token)) {
                        System.out.println("hihiihi" + cookie.getValue());
                        return "Access granted, token is valid!";
                    } else {
                        return "Unauthorized, invalid token!";
                    }
                }
            }
        }

        return "Unauthorized, missing token!";
    }



}
