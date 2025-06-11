package com.prk;

import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

public class SCryptSanityCheck {
    public static void main(String[] args) {
        SCryptPasswordEncoder encoder = new SCryptPasswordEncoder(16384, 8, 1, 64, 16);
        String hash = encoder.encode("testPassword");
        System.out.println("Hash: " + hash);
    }
}
