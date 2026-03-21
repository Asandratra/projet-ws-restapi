package mg.projet.restapi.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mg.projet.restapi.model.User;
import mg.projet.restapi.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username = email in your case
        return userRepository.findByMail(username)
                .map(user -> org.springframework.security.core.userdetails.User.withUsername(user.getMail())
                        .password(user.getMdp())           // must be BCrypt hash
                        .authorities(getAuthorities(user))
                        .accountExpired(false)
                        .accountLocked(false)
                        .credentialsExpired(false)
                        .disabled(!(user.getEtat()==1))
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        // Example – adapt to your roles
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
}