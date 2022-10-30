package com.fdmgroup.mony.util;

import com.fdmgroup.mony.dto.AuthResponse;
import com.fdmgroup.mony.dto.CustomUserDetails;
import com.fdmgroup.mony.model.Bill;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * For testing purposes.
 */
@Component
public class ObjectFactory {
    public CustomUserDetails createCustomUserDetails(){
        return new CustomUserDetails();
    }

    public Bill createBill(){
        return new Bill();
    }

    public UsernamePasswordAuthenticationToken createAuthToken(String username, String password){
        return new UsernamePasswordAuthenticationToken(username, password);
    }

    public AuthResponse createAuthResponse(String token){
        return new AuthResponse(token);
    }
}
