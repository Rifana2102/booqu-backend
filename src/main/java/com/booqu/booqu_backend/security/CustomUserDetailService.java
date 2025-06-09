package com.booqu.booqu_backend.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.booqu.booqu_backend.entity.MasterRoleEntity;
import com.booqu.booqu_backend.entity.UserEntity;
import com.booqu.booqu_backend.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        // Wrap the single role into a list
        return new User(
            user.getUsername(),
            user.getPassword(),
            mapRolesToAuthorities(Collections.singletonList(user.getRole()))
        );
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(Collection<MasterRoleEntity> roles) {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getCode())) // use .getCode(), not .getName()
        .collect(Collectors.toList());
}

}

