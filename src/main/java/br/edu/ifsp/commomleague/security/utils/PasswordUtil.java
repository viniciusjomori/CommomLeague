package br.edu.ifsp.commomleague.security.utils;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil extends BCryptPasswordEncoder {

    @Value("${security.password-regexes}")
    public String passwordRegexes;

    @Override
    public String encode(CharSequence rawPassword) {
        return super.encode(rawPassword);
    }
    
    public boolean matches(String rawPassword, UserDetails user) {
        return super.matches(rawPassword, user.getPassword());
    }

    public boolean isStrongPassword(CharSequence rawPassword) {
        return Pattern.matches(passwordRegexes, rawPassword);
    }
}
