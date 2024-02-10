package com.example.schoolorganizer.security;

import jakarta.servlet.http.HttpSession;

public class UserLoggedInValidator {
    public static boolean hasUserLoggedIn(HttpSession httpSession) {
        if (httpSession.getAttribute("user") == null) {
            return false;
        }

        return true;
    }
}