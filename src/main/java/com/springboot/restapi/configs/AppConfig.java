package com.springboot.restapi.configs;

import com.springboot.restapi.accounts.Account;
import com.springboot.restapi.accounts.AccountRepository;
import com.springboot.restapi.accounts.AccountRole;
import com.springboot.restapi.accounts.AccountService;
import com.springboot.restapi.common.AppProperties;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {

            @Autowired
            AccountService accountService;

            @Autowired
            AppProperties appProperties;

            @Override
            public void run(ApplicationArguments args) throws Exception {

                Set<AccountRole> hsAdmin = new HashSet();
                hsAdmin.add(AccountRole.ADMIN);
                Account admin = Account.builder()
                        .email(appProperties.getAdminUsername())
                        .password(appProperties.getAdminPassword())
                        .roles(hsAdmin)
                        .build();

                accountService.saveAccount(admin);

                Set<AccountRole> hsUser = new HashSet();
                hsUser.add(AccountRole.USER);
                Account user = Account.builder()
                        .email(appProperties.getUserUsername())
                        .password(appProperties.getUserPassword())
                        .roles(hsUser)
                        .build();

                accountService.saveAccount(user);
            }
        };
    }
}
