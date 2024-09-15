package com.minizin.travel.v1.user.domain.exception;

import com.minizin.travel.v1.user.domain.enums.UserErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomUserException extends RuntimeException {
    private final UserErrorCode userErrorCode;
}
