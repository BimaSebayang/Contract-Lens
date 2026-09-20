package com.contractlens.common.clawfo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClawfoMappingRegistRequest {

    @NotBlank
    @Size(max = 100)
    private String namaPemilik;

    @NotBlank
    @Size(max = 100)
    private String namaLapak;

    @NotBlank
    @Email
    @Size(max = 200)
    private String email;

    @NotBlank
    private String emailOtp;

    @NotBlank
    @Size(min = 6, max = 200)
    private String password;

    @NotBlank
    @Size(min = 6, max = 200)
    private String confirmPassword;

    private boolean tnc;
}