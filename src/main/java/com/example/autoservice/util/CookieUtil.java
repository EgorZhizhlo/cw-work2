package com.example.autoservice.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {
    public static void create(HttpServletResponse res, String name, String value, boolean httpOnly, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(httpOnly);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        res.addCookie(cookie);
    }
    public static String read(HttpServletRequest req, String name) {
        if (req.getCookies() == null) return null;
        for (Cookie c : req.getCookies()) {
            if (c.getName().equals(name)) {
                return c.getValue();
            }
        }
        return null;
    }
    public static void clear(HttpServletResponse res, String name) {
        create(res, name, "", true, 0);
    }
}
