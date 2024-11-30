package com.example.semiautomatedlims.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        // Set cache control headers to prevent caching
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        // Get the URI of the current request
        String requestURI = request.getRequestURI().toUpperCase();

        // Skip session check for public pages like registration and login
        if (requestURI.contains("/CLIENT-register") || requestURI.contains("/client-login") || requestURI.contains("/STAFF-login")) {
            return true;  // Skip session check for these pages
        }

        if (session == null) {
            System.out.println("Session is null. Redirecting to login.");
            // Check if the request is for a staff page and redirect accordingly
            if (requestURI.contains("/STAFF") || requestURI.contains("/RELEASE") || requestURI.contains("/TESTING")
                    || requestURI.contains("/TR") || requestURI.contains("/REPORT") || requestURI.contains("/RESULTS")) {
                response.sendRedirect("https://limstest-latest.onrender.com" + "/STAFF-login");  // Fully qualified URL
            } else {
                response.sendRedirect("https://limstest-latest.onrender.com" + "/client-login");  // Fully qualified URL
            }
            return false; // Ends the method execution after redirection
        }

        // Log the attributes for debugging
        Object clientAttribute = session.getAttribute("loggedInClient");
        Object staffAttribute = session.getAttribute("testingSection");

        if (clientAttribute == null && staffAttribute == null) {
            System.out.println("No valid session attribute found. Redirecting to login.");
            // Check if the request is for a staff page and redirect accordingly
            if (requestURI.contains("/STAFF") || requestURI.contains("/RELEASE") || requestURI.contains("/TESTING")
                    || requestURI.contains("/TR") || requestURI.contains("/REPORT") || requestURI.contains("/RESULTS")) {
                response.sendRedirect("https://limstest-latest.onrender.com" + "/STAFF-login");  // Fully qualified URL
            } else {
                response.sendRedirect("https://limstest-latest.onrender.com" + "/client-login");  // Fully qualified URL
            }
            return false; // Ends the method execution after redirection
        }

        // Ensure secure cookies for HTTPS requests
        if (request.isSecure()) {
            response.setHeader("Set-Cookie", "JSESSIONID=" + session.getId() + "; Secure; HttpOnly; SameSite=Lax;");
        }

        // Ensure headers are set for valid session
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        return true; // Allow the request to proceed
    }
}