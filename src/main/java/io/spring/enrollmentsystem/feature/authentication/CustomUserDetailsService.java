package io.spring.enrollmentsystem.feature.authentication;

import io.spring.enrollmentsystem.feature.user.User;
import io.spring.enrollmentsystem.feature.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor @Slf4j
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(format("User with username - %s, not found", username)));
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Optional<Object> getCurrentPrincipal() {
        Authentication authentication = getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        return Optional.ofNullable(authentication.getPrincipal());
    }

    @Transactional(readOnly = true)
    public Optional<User> getCurrentUser() {
        Optional<Object> principal = this.getCurrentPrincipal();

        if (principal.isPresent()) {
            if (principal.get() instanceof User) {
                return Optional.of((User) principal.get());
            } else if(principal.get() instanceof UUID) {
                return userRepository.findWithAuthoritiesById((UUID) principal.get());
            }
        }

        return Optional.empty();
    }

    public Optional<UUID> getCurrentUserId() {
        Optional<Object> principal = this.getCurrentPrincipal();

        if (principal.isPresent()) {
            if(principal.get() instanceof UUID) {
                return Optional.of((UUID) principal.get());
            } else if (principal.get() instanceof User) {
                return Optional.of(((User) principal.get()).getId());
            }
        }

        return Optional.empty();
    }

    public Set<String> getCurrentUserAuthorities() {
        Collection<? extends GrantedAuthority> authorities = this.getAuthentication().getAuthorities();

        return authorities
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }
}
