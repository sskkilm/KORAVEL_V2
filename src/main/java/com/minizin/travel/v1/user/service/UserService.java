package com.minizin.travel.v1.user.service;

import com.minizin.travel.v1.user.domain.dto.*;
import com.minizin.travel.v1.user.domain.entity.UserEntity;
import com.minizin.travel.v1.user.domain.enums.LoginType;
import com.minizin.travel.v1.user.domain.enums.UserErrorCode;
import com.minizin.travel.v1.user.domain.exception.CustomUserException;
import com.minizin.travel.v1.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MailService mailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto.Response getUserInfo(PrincipalDetails principalDetails) {
        UserEntity userEntity = userRepository.findByUsername(principalDetails.getUsername())
                .orElseThrow(() -> new CustomUserException(UserErrorCode.USER_NOT_FOUND));

        return UserDto.Response.fromUserEntity(userEntity);
    }

    public FindIdDto.Response findId(FindIdDto.Request request) {
        UserEntity userEntity = userRepository.findByEmailAndLoginType(request.getEmail(), LoginType.LOCAL)
                .orElseThrow(() -> new CustomUserException(UserErrorCode.EMAIL_UN_REGISTERED));

        return FindIdDto.Response.builder().username(userEntity.getUsername()).build();
    }

    @Transactional
    public void findPassword(FindPasswordDto.Request request) {
        UserEntity userEntity = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CustomUserException(UserErrorCode.USER_NOT_FOUND));
        if (!Objects.equals(userEntity.getEmail(), request.getEmail())) {
            throw new CustomUserException(UserErrorCode.USER_EMAIL_UN_MATCHED);
        }

        String password = mailService.sendTemporaryPassword(request);
        userEntity.setPassword(bCryptPasswordEncoder.encode(password));
    }

    @Transactional
    public UpdatePasswordDto.Response updatePassword(
            UpdatePasswordDto.Request request, PrincipalDetails principalDetails) {
        UserEntity userEntity = userRepository.findByUsername(principalDetails.getUsername())
                .orElseThrow(() -> new CustomUserException(UserErrorCode.USER_NOT_FOUND));
        if (!bCryptPasswordEncoder.matches(request.getOriginalPassword(), userEntity.getPassword())) {
            throw new CustomUserException(UserErrorCode.PASSWORD_UN_MATCHED);
        }

        userEntity.setPassword(bCryptPasswordEncoder.encode(request.getChangePassword()));

        return UpdatePasswordDto.Response.fromUserEntity(userEntity);
    }

    @Transactional
    public UpdateEmailDto.Response updateEmail(
            UpdateEmailDto.Request request, PrincipalDetails principalDetails
    ) {
        UserEntity userEntity = userRepository.findByUsername(principalDetails.getUsername())
                .orElseThrow(() -> new CustomUserException(UserErrorCode.USER_NOT_FOUND));

        userEntity.setEmail(request.getEmail());

        return UpdateEmailDto.Response.fromUserEntity(userEntity);
    }

    @Transactional
    public UpdateNicknameDto.Response updateNickname(
            UpdateNicknameDto.Request request, PrincipalDetails principalDetails
    ) {
        UserEntity userEntity = userRepository.findByUsername(principalDetails.getUsername())
                .orElseThrow(() -> new CustomUserException(UserErrorCode.USER_NOT_FOUND));

        userEntity.setNickname(request.getNickname());

        return UpdateNicknameDto.Response.fromUserEntity(userEntity);
    }

    public DeleteUserDto.Response deleteUser(PrincipalDetails principalDetails) {
        UserEntity userEntity = userRepository.findByUsername(principalDetails.getUsername())
                .orElseThrow(() -> new CustomUserException(UserErrorCode.USER_NOT_FOUND));

        userRepository.delete(userEntity);

        return DeleteUserDto.Response.fromUserEntity(userEntity);
    }
}
