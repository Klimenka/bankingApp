package nl.inholland.model;

import nl.inholland.repository.LoginRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetails extends Login implements UserDetails {

    public CustomUserDetails(final Login login) {
        super(login);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*List<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(super.getUser().getUserType().toString()));
        return roles.stream().collect(Collectors.toList());*/

        return super.getUser().getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return super.getUserName();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

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
}
