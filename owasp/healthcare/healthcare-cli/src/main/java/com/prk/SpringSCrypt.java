package com.prk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

public class SpringSCrypt {
    private static final Logger LOG = LoggerFactory.getLogger(SpringSCrypt.class);

    public static void main(String[] args) {
        String password = "examplePassword";

        // SCrypt parameters: N=2^14, r=8, p=1, keyLength=64, saltLength=16
        SCryptPasswordEncoder encoder = new SCryptPasswordEncoder(16384, 8, 1, 64, 16);

        // Hash the password (automatically handles salt and encoding)
        String hash = encoder.encode(password);

        LOG.info("SCrypt hash for {}: {}", password, hash);

        // Optional: verify
        boolean matches = encoder.matches(password, hash);
        LOG.info("Password matches: {}", matches);
    }
}
