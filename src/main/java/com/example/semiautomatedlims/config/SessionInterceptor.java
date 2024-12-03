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
        // Log the request URI for debugging
        String requestURI = request.getRequestURI().toUpperCase();
        System.out.println("Request URI: " + requestURI);

        HttpSession session = request.getSession(false);

        // Set cache control headers to prevent caching
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        // Skip session check for public pages like login and registration
        if (requestURI.contains("/CLIENT-REGISTER") || requestURI.contains("/CLIENT-LOGIN") || requestURI.contains("/STAFF-LOGIN")) {
            System.out.println("Skipping session check for public page: " + requestURI);
            return true; // Allow the request to proceed
        }

        if (session == null) {
            System.out.println("Session is null. Redirecting to login.");
            // Check if the request is for a staff page and redirect accordingly
            if (requestURI.contains("/STAFF") || requestURI.contains("/RELEASE") || requestURI.contains("/TESTING")
                    || requestURI.contains("/TR") || requestURI.contains("/REPORT") || requestURI.contains("/RESULTS")) {
                response.sendRedirect(request.getContextPath() + "/STAFF-login");
            } else {
                response.sendRedirect(request.getContextPath() + "/client-login");
            }
            return false; // Ends the method execution after redirection
        }

        // Log the attributes for debugging
        Object clientAttribute = session.getAttribute("loggedInClient");
        Object staffAttribute = session.getAttribute("loggedInStaff");
        Object staffType = session.getAttribute("staffType");
        Object testingSection = session.getAttribute("testingSection");
        System.out.println("Client Attribute: " + clientAttribute);
        System.out.println("Staff Attribute: " + staffAttribute);
        System.out.println("Staff Type: " + staffType);
        System.out.println("Testing Section: " + testingSection);

        // Redirect if no valid session attributes are found
        if (clientAttribute == null && staffAttribute == null) {
            System.out.println("No valid session attribute found. Redirecting to login.");
            if (requestURI.contains("/STAFF") || requestURI.contains("/RELEASE") || requestURI.contains("/TESTING")
                    || requestURI.contains("/TR") || requestURI.contains("/REPORT") || requestURI.contains("/RESULTS")) {
                response.sendRedirect(request.getContextPath() + "/STAFF-login");
            } else {
                response.sendRedirect(request.getContextPath() + "/client-login");
            }
            return false; // Ends the method execution after redirection
        }

        // Add validation for staff session
        if (staffAttribute != null) {
            System.out.println("Validating staff session...");

            // Validate testing staff
            if ("testing".equals(staffType)) {
                if (testingSection == null || (!"chem".equals(testingSection) && !"molbio".equals(testingSection) && !"microbio".equals(testingSection))) {
                    System.out.println("Invalid session for testing staff. Redirecting to STAFF-login.");
                    response.sendRedirect(request.getContextPath() + "/STAFF-login");
                    return false;
                }
            }
            // Validate receiving/releasing staff
            else if ("receiving/releasing".equals(staffType)) {
                if (testingSection != null) {
                    System.out.println("Invalid session for receiving/releasing staff. Redirecting to STAFF-login.");
                    response.sendRedirect(request.getContextPath() + "/STAFF-login");
                    return false;
                }
            }
        }

        // Secure cookies for HTTPS requests
        if (request.isSecure()) {
            System.out.println("Secure request. Setting secure cookie.");
            response.setHeader("Set-Cookie", "JSESSIONID=" + session.getId() + "; Secure; HttpOnly; SameSite=Lax;");
        }

        return true; // Allow the request to proceed
    }
}
