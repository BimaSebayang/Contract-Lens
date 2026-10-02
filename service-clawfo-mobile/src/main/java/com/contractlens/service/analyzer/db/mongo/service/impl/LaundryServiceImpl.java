package com.contractlens.service.analyzer.db.mongo.service.impl;

import com.contractlens.common.clawfo.request.ClawfoLapakLaundryRequest;
import com.contractlens.common.enums.WordingClawfo;
import com.contractlens.service.analyzer.db.mongo.dao.ClawfoLaundryDocument;
import com.contractlens.service.analyzer.db.mongo.dao.ClawfoRegistrationDocument;
import com.contractlens.service.analyzer.db.mongo.repository.ClawfoLaundryRepository;
import com.contractlens.service.analyzer.db.mongo.repository.ClawfoRegistrationRepository;
import com.contractlens.service.analyzer.db.mongo.service.LaundryService;
import com.contractlens.service.analyzer.infrastructure.ClawfoException;
import com.contractlens.service.analyzer.infrastructure.ClawfoJwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LaundryServiceImpl implements LaundryService {

    private final ClawfoLaundryRepository clawfoLaundryRepository;
    private final ClawfoRegistrationRepository clawfoRegistrationRepository;
    private final ClawfoJwtService clawfoJwtService;


    @Override
    public void upsertLaundry(
            String deviceId,
            String ticketId,
            String longitude,
            String latitude,
            String location,
            ClawfoLapakLaundryRequest request
    ) {

        String email = clawfoJwtService
                .getCurrentUser()
                .getEmail();

        ClawfoRegistrationDocument registration =
                clawfoRegistrationRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new ClawfoException(
                                        WordingClawfo.USER_NOT_FOUND
                                )
                        );

        Optional<ClawfoLaundryDocument> existingLaundry =
                clawfoLaundryRepository.findByLaundryCode(
                        registration.getLaundryCode()
                );

        ClawfoLaundryDocument document;

        if (existingLaundry.isPresent()) {

            document = existingLaundry.get();

        } else {

            document = new ClawfoLaundryDocument();

            document.setLaundryCode(
                    registration.getLaundryCode()
            );

            document.setEmail(email);
            document.setCreatedBy(ticketId);
            document.setCreatedDate(LocalDateTime.now());
        }

        document.setUpdatedBy(ticketId);
        document.setUpdatedDate(LocalDateTime.now());

        document.setPhone(
                request.getPhoneNumber()
        );

        document.setDeviceId(
                deviceId
        );

        document.setNamaLaundry(
                request.getNamaLaundry()
        );

        document.setDeskripsiLaundry(
                request.getDeskripsiLaundry()
        );

        document.setCurrentLocation(
                mapCurrentLocation(
                        request.getCurrentLocation()
                )
        );

        document.setFotoToko(
                request.getFotoToko()
        );

        document.setPaymentMethod(
                mapPaymentMethod(
                        request.getPaymentMethod()
                )
        );

        document.setDeliveryService(
                mapDeliveryService(
                        request.getDeliveryService()
                )
        );

        document.setOperationalHour(
                mapOperationalHour(
                        request.getOperationalHour()
                )
        );

        clawfoLaundryRepository.save(document);
    }


    @Override
    public ClawfoLaundryDocument getOwnLaundry() {

        String email = clawfoJwtService
                .getCurrentUser()
                .getEmail();

        ClawfoRegistrationDocument registration =
                clawfoRegistrationRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new ClawfoException(
                                        WordingClawfo.USER_NOT_FOUND
                                )
                        );

        return clawfoLaundryRepository
                .findByLaundryCode(
                        registration.getLaundryCode()
                )
                .orElse(
                        new ClawfoLaundryDocument()
                );
    }


    private ClawfoLaundryDocument mapToDocument(
            ClawfoLapakLaundryRequest request
    ) {

        ClawfoLaundryDocument document =
                new ClawfoLaundryDocument();

        document.setNamaLaundry(
                request.getNamaLaundry()
        );

        document.setDeskripsiLaundry(
                request.getDeskripsiLaundry()
        );

        document.setCurrentLocation(
                mapCurrentLocation(
                        request.getCurrentLocation()
                )
        );

        document.setFotoToko(
                request.getFotoToko()
        );

        document.setPaymentMethod(
                mapPaymentMethod(
                        request.getPaymentMethod()
                )
        );

        document.setDeliveryService(
                mapDeliveryService(
                        request.getDeliveryService()
                )
        );

        document.setOperationalHour(
                mapOperationalHour(
                        request.getOperationalHour()
                )
        );

        return document;
    }


    private ClawfoLaundryDocument.CurrentLocationProps mapCurrentLocation(
            ClawfoLapakLaundryRequest.CurrentLocationProps source
    ) {

        if (source == null) {
            return null;
        }

        ClawfoLaundryDocument.CurrentLocationProps target =
                new ClawfoLaundryDocument.CurrentLocationProps();

        target.setAddress(
                source.getAddress()
        );

        target.setLongitude(
                source.getLongitude()
        );

        target.setLatitude(
                source.getLatitude()
        );

        return target;
    }


    private ClawfoLaundryDocument.PaymentMethodProps mapPaymentMethod(
            ClawfoLapakLaundryRequest.PaymentMethodProps source
    ) {

        if (source == null) {
            return null;
        }

        ClawfoLaundryDocument.PaymentMethodProps target =
                new ClawfoLaundryDocument.PaymentMethodProps();

        target.setCash(
                source.isCash()
        );

        target.setQris(
                source.isQris()
        );

        target.setFotoQris(
                source.getFotoQris()
        );

        target.setBankTransfer(
                source.isBankTransfer()
        );

        target.setBankCode(
                source.getBankCode()
        );

        target.setRekno(
                source.getRekno()
        );

        target.setRekOwner(
                source.getRekOwner()
        );

        return target;
    }


    private ClawfoLaundryDocument.DeliveryServiceProps mapDeliveryService(
            ClawfoLapakLaundryRequest.DeliveryServiceProps source
    ) {

        if (source == null) {
            return null;
        }

        ClawfoLaundryDocument.DeliveryServiceProps target =
                new ClawfoLaundryDocument.DeliveryServiceProps();

        target.setPickup(
                source.isPickup()
        );

        target.setPickupFree(
                source.isPickupFree()
        );

        target.setPickupPayment(
                source.getPickupPayment()
        );

        target.setDelivery(
                source.isDelivery()
        );

        target.setDeliveryFree(
                source.isDeliveryFree()
        );

        target.setDeliveryPayment(
                source.getDeliveryPayment()
        );

        return target;
    }


    private ClawfoLaundryDocument.OperationalHourProps mapOperationalHour(
            ClawfoLapakLaundryRequest.OperationalHourProps source
    ) {

        if (source == null) {
            return null;
        }

        ClawfoLaundryDocument.OperationalHourProps target =
                new ClawfoLaundryDocument.OperationalHourProps();

        target.setSeninStartDay(
                source.getSeninStartDay()
        );

        target.setSeninEndDay(
                source.getSeninEndDay()
        );

        target.setSelasaStartDay(
                source.getSelasaStartDay()
        );

        target.setSelasaEndDay(
                source.getSelasaEndDay()
        );

        target.setRabuStartDay(
                source.getRabuStartDay()
        );

        target.setRabuEndDay(
                source.getRabuEndDay()
        );

        target.setKamisStartDay(
                source.getKamisStartDay()
        );

        target.setKamisEndDay(
                source.getKamisEndDay()
        );

        target.setJumatStartDay(
                source.getJumatStartDay()
        );

        target.setJumatEndDay(
                source.getJumatEndDay()
        );

        target.setSabtuStartDay(
                source.getSabtuStartDay()
        );

        target.setSabtuEndDay(
                source.getSabtuEndDay()
        );

        target.setMingguStartDay(
                source.getMingguStartDay()
        );

        target.setMingguEndDay(
                source.getMingguEndDay()
        );

        return target;
    }
}