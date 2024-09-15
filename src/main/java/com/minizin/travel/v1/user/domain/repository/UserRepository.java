package com.minizin.travel.v1.user.domain.repository;

import com.minizin.travel.v1.user.domain.entity.UserEntity;
import com.minizin.travel.v1.user.domain.enums.LoginType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    Boolean existsByUsername(String username);

    Optional<UserEntity> findByEmailAndLoginType(String email, LoginType loginType);

}
