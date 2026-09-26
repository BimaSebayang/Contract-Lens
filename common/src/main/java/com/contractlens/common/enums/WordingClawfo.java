package com.contractlens.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WordingClawfo {

    // =========================
    // Registration
    // =========================

    USER_REGISTERED(
            "auth.register.success",
            "Registration successful",
            "Registrasi berhasil"
    ),

    TNC_NOT_ACCEPTED(
            "auth.register.tnc.not.accepted",
            "Please accept the Terms and Conditions",
            "Silakan setujui Syarat dan Ketentuan"
    ),

    EMAIL_ALREADY_REGISTERED(
            "auth.register.email_exists",
            "Email is already registered",
            "Email sudah terdaftar"
    ),

    PHONE_ALREADY_REGISTERED(
            "auth.register.phone_exists",
            "Phone number is already registered",
            "Nomor telepon sudah terdaftar"
    ),

    USER_NOT_FOUND(
            "auth.user.not_found",
            "User not found",
            "Pengguna tidak ditemukan"
    ),

    DEVICE_ALREADY_REGISTERED(
            "auth.register.device_exists",
            "This device is already registered",
            "Perangkat ini sudah terdaftar"
    ),

    INVALID_OTP(
            "auth.register.invalid_otp",
            "Invalid verification code",
            "Kode verifikasi tidak valid"
    ),

    OTP_EXPIRED(
            "auth.register.otp_expired",
            "Verification code has expired",
            "Kode verifikasi sudah kedaluwarsa"
    ),

    LAPAK_LAUNDRY_CREATED(
            "laundry.lapak_created",
            "Laundry lapak has been created successfully",
            "Lapak laundry berhasil dibuat"
    ),

    LAPAK_LAUNDRY_FOUND(
            "laundry.lapak_found",
            "Laundry lapak found successfully",
            "Lapak laundry berhasil ditemukan"
    ),

    OTP_SEND_SUCCESS(
            "auth.register.otp.send.success",
            "Verification code has been sent successfully",
            "Kode verifikasi berhasil dikirim"
    ),

    LOGIN_SUCCESS(
            "auth.login.success",
            "Login successful",
            "Login berhasil"
    ),

    LOGIN_FAILED(
            "auth.login.failed",
            "Invalid email or password",
            "Email atau password salah"
    ),

    OTP_VALIDATION_SUCCESS(
            "auth.register.otp.validation.success",
            "Verification code verified successfully",
            "Kode verifikasi berhasil diverifikasi"
    ),

    INVALID_REGISTRATION_DATA(
            "auth.register.invalid_data",
            "Invalid registration data",
            "Data registrasi tidak valid"
    ),

    OTP_NOT_FOUND(
            "auth.register.otp.not.found",
            "OTP not found or has expired",
            "Kode OTP tidak ditemukan atau sudah kedaluwarsa"
    ),

    OTP_INVALID(
            "auth.register.otp.invalid",
            "Invalid OTP",
            "Kode OTP tidak valid"
    ),

    REGISTRATION_FAILED(
            "auth.register.failed",
            "Registration failed",
            "Registrasi gagal"
    );



    private final String key;
    private final String en;
    private final String id;
}
