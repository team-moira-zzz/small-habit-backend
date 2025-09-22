package com.moira.smallhabitbackend.global.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class Encryptor {
    private final AesBytesEncryptor encryptor;

    public String encrypt(String value) {
        byte[] encryptedBytes = encryptor.encrypt(value.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String value) {
        byte[] decodedBytes = Base64.getDecoder().decode(value);
        return new String(encryptor.decrypt(decodedBytes), StandardCharsets.UTF_8);
    }
}
