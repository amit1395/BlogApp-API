package com.fitstam.constants;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncrypter {
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("a@1234"));
    }
}
