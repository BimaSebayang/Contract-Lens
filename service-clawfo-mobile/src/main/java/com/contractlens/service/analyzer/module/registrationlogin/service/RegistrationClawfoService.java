package com.contractlens.service.analyzer.module.registrationlogin.service;

import com.contractlens.common.clawfo.request.ClawfoMappingRegistRequest;
import com.contractlens.common.enums.WordingClawfo;
import com.contractlens.service.analyzer.db.mongo.dao.ClawfoRegistrationDocument;
import com.contractlens.service.analyzer.db.mongo.service.RegistrationService;
import com.contractlens.service.analyzer.db.redis.service.RegistrationOtpService;
import com.contractlens.service.analyzer.infrastructure.ClawfoEmailService;
import com.contractlens.service.analyzer.infrastructure.ClawfoException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class RegistrationClawfoService {

    private final RegistrationService registrationService;
    private final RegistrationOtpService registrationOtpService;
    private final ClawfoEmailService clawfoEmailService;

    public void createOtp(String deviceId,String ticketId, String email) throws MessagingException, UnsupportedEncodingException {

        String otp = String.format(
                "%06d",
                ThreadLocalRandom.current().nextInt(0, 1_000_000)
        );

        clawfoEmailService.sendRegistrationOtp(email,otp);

        registrationOtpService.save(
                deviceId+"."+ticketId,
                otp,
                300
        );
    }

    public void validateOtp(String deviceId, String ticketId, String emailOtp){
        Optional<String> otp = registrationOtpService.findOtpByTicketId(deviceId+"."+ticketId);

        if (otp.isEmpty()) {
            throw new ClawfoException(
                    WordingClawfo.OTP_NOT_FOUND
            );
        }

        if (!otp.get().equals(emailOtp)) {
            throw new ClawfoException(
                    WordingClawfo.OTP_INVALID
            );
        }
    }

    public void register(String deviceId, String ticketId, @Valid ClawfoMappingRegistRequest request) {

        if(!request.isTnc()){
            throw new ClawfoException(WordingClawfo.TNC_NOT_ACCEPTED);
        }

        validateOtp(deviceId,ticketId,request.getEmailOtp());

        registrationOtpService.delete(deviceId+"."+ticketId);
        boolean existsEmail = registrationService.existsByEmail(request.getEmail());

        if(existsEmail){
            return;
        }


        ClawfoRegistrationDocument clawfoRegistrationDocument = new ClawfoRegistrationDocument();
        clawfoRegistrationDocument.toDocument(
                request,
                ticketId,
                deviceId
        );

        registrationService.upsert(clawfoRegistrationDocument);
    }


}
