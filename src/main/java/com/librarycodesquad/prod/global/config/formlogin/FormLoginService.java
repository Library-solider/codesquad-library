package com.librarycodesquad.prod.global.config.formlogin;

import com.librarycodesquad.admin.domain.administrator.Administrator;
import com.librarycodesquad.admin.domain.administrator.AdministratorRepository;
import com.librarycodesquad.prod.global.error.exception.domain.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class FormLoginService implements UserDetailsService {

    private final AdministratorRepository administratorRepository;

    @Override
    public UserDetails loadUserByUsername(String adminName) throws UsernameNotFoundException {
        Administrator administrator = administratorRepository.findByAdminName(adminName)
                                                             .orElseThrow(AccountNotFoundException::new);
        return new User(administrator.getAdminName(), administrator.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority(administrator.getLibraryRole().getKey())));
    }
}
