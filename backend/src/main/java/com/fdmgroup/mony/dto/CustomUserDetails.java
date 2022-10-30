package com.fdmgroup.mony.dto;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

/**
 * Customised UserDetails for authentication purpose.
 *
 * @author Jason Liu
 * @version 1.0
 */
@Data
public class CustomUserDetails implements UserDetails {

    private Long id;

    private String username;

    private String password;

    private String email;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static CustomUserDetails createCustomUserDetails(){
        return new CustomUserDetails();
    }
}
