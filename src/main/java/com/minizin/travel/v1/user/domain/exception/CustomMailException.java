package com.minizin.travel.v1.user.domain.exception;

import com.minizin.travel.v1.user.domain.enums.MailErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomMailException extends RuntimeException {
    private final MailErrorCode mailErrorCode;
}
