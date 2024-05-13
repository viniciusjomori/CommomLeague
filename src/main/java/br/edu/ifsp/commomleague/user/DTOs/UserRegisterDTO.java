package br.edu.ifsp.commomleague.user.DTOs;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserRegisterDTO(
    @NotBlank(message = "nickname is null")
    String nickname,

    @NotBlank
    String password,

    @Email
    @NotBlank
    String email,

    @CPF
    @NotBlank
    String cpf,

    @NotBlank
    @Pattern(regexp = "^[0-9]{11,}$")
    String phone,

    @NotNull
    LocalDate birthday
) {

    public String cpf() {
        return cpf
            .replace("-", "")
            .replace(".", "");
    }
    
}
