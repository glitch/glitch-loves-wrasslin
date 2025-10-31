package dev.glitch.wrasslin.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminUserDetailsService implements UserDetailsService {

    @Value(value = "${admin.password}")
    private String adminPass;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // For the password you'll need to store the bcrypt encoded version and inject
        // it into the adminPass var, preferably using something like docker-secret
        if ("admin".equals(username)) {
            return User.builder().username("admin").password(adminPass).roles("ADMIN").build();
        } else {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }
    }

}
