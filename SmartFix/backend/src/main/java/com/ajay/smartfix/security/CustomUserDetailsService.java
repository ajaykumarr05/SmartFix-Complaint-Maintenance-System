package com.ajay.smartfix.security;
import com.ajay.smartfix.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository users;
    public CustomUserDetailsService(UserRepository users){this.users=users;}
    @Override public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var u=users.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(u.getEmail(),u.getPassword(),createAuthorityList("ROLE_"+u.getRole().name()));
    }
}
