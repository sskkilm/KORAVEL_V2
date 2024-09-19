package com.minizin.travel.v2;

import com.minizin.travel.v2.domain.user.entity.UserEntity;
import com.minizin.travel.v2.domain.user.enums.Authority;
import com.minizin.travel.v2.domain.user.enums.LoginType;
import com.minizin.travel.v2.domain.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TestUser {

    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        userRepository.save(UserEntity.builder()
                .username("username")
                .email("email")
                .nickname("nickname")
                .role(Authority.ROLE_USER)
                .loginType(LoginType.LOCAL)
                .build());
    }
}
