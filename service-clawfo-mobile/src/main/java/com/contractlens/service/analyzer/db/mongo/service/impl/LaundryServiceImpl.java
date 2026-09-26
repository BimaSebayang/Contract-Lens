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

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LaundryServiceImpl implements LaundryService {

    private final ClawfoLaundryRepository clawfoLaundryRepository;
    private final ClawfoRegistrationRepository clawfoRegistrationRepository;
    private final ClawfoJwtService clawfoJwtService;

    @Override
    public void createLaundry(
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

            document.setDeviceId(deviceId);
            document.setNamaLaundry(request.getNamaLaundry());
            document.setDeskripsiLaundry(request.getDeskripsiLaundry());

            document.setCurrentLocation(
                    mapCurrentLocation(
                            request.getCurrentLocation()
                    )
            );

            document.setFotoToko(request.getFotoToko());

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

        } else {

            document = ClawfoLaundryDocument.builder()
                    .laundryCode(registration.getLaundryCode())
                    .email(email)
                    .deviceId(deviceId)
                    .namaLaundry(request.getNamaLaundry())
                    .deskripsiLaundry(request.getDeskripsiLaundry())
                    .currentLocation(
                            mapCurrentLocation(
                                    request.getCurrentLocation()
                            )
                    )
                    .fotoToko(request.getFotoToko())
                    .paymentMethod(
                            mapPaymentMethod(
                                    request.getPaymentMethod()
                            )
                    )
                    .deliveryService(
                            mapDeliveryService(
                                    request.getDeliveryService()
                            )
                    )
                    .operationalHour(
                            mapOperationalHour(
                                    request.getOperationalHour()
                            )
                    )
                    .build();
        }

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

        return clawfoLaundryRepository.findByLaundryCode(registration.getLaundryCode())
                .orElse(new ClawfoLaundryDocument());
    }

    private ClawfoLaundryDocument mapToDocument(
            ClawfoLapakLaundryRequest request
    ) {

        return ClawfoLaundryDocument.builder()
                .namaLaundry(request.getNamaLaundry())
                .deskripsiLaundry(request.getDeskripsiLaundry())

                .currentLocation(
                        request.getCurrentLocation() == null
                                ? null
                                : ClawfoLaundryDocument.CurrentLocationProps.builder()
                                .address(request.getCurrentLocation().getAddress())
                                .longitude(request.getCurrentLocation().getLongitude())
                                .latitude(request.getCurrentLocation().getLatitude())
                                .build()
                )

                .fotoToko(request.getFotoToko())

                .paymentMethod(
                        request.getPaymentMethod() == null
                                ? null
                                : ClawfoLaundryDocument.PaymentMethodProps.builder()
                                .isCash(request.getPaymentMethod().isCash())
                                .isQris(request.getPaymentMethod().isQris())
                                .fotoQris(request.getPaymentMethod().getFotoQris())
                                .isBankTransfer(request.getPaymentMethod().isBankTransfer())
                                .bankCode(request.getPaymentMethod().getBankCode())
                                .rekno(request.getPaymentMethod().getRekno())
                                .rekOwner(request.getPaymentMethod().getRekOwner())
                                .build()
                )

                .deliveryService(
                        request.getDeliveryService() == null
                                ? null
                                : ClawfoLaundryDocument.DeliveryServiceProps.builder()
                                .isPickup(request.getDeliveryService().isPickup())
                                .isPickupFree(request.getDeliveryService().isPickupFree())
                                .pickupPayment(request.getDeliveryService().getPickupPayment())
                                .isDelivery(request.getDeliveryService().isDelivery())
                                .isDeliveryFree(request.getDeliveryService().isDeliveryFree())
                                .deliveryPayment(request.getDeliveryService().getDeliveryPayment())
                                .build()
                )

                .operationalHour(
                        request.getOperationalHour() == null
                                ? null
                                : ClawfoLaundryDocument.OperationalHourProps.builder()
                                .seninStartDay(request.getOperationalHour().getSeninStartDay())
                                .seninEndDay(request.getOperationalHour().getSeninEndDay())
                                .selasaStartDay(request.getOperationalHour().getSelasaStartDay())
                                .selasaEndDay(request.getOperationalHour().getSelasaEndDay())
                                .rabuStartDay(request.getOperationalHour().getRabuStartDay())
                                .rabuEndDay(request.getOperationalHour().getRabuEndDay())
                                .kamisStartDay(request.getOperationalHour().getKamisStartDay())
                                .kamisEndDay(request.getOperationalHour().getKamisEndDay())
                                .jumatStartDay(request.getOperationalHour().getJumatStartDay())
                                .jumatEndDay(request.getOperationalHour().getJumatEndDay())
                                .sabtuStartDay(request.getOperationalHour().getSabtuStartDay())
                                .sabtuEndDay(request.getOperationalHour().getSabtuEndDay())
                                .mingguStartDay(request.getOperationalHour().getMingguStartDay())
                                .mingguEndDay(request.getOperationalHour().getMingguEndDay())
                                .build()
                )
                .build();
    }

    private ClawfoLaundryDocument.CurrentLocationProps mapCurrentLocation(
            ClawfoLapakLaundryRequest.CurrentLocationProps source
    ) {

        if (source == null) {
            return null;
        }

        return ClawfoLaundryDocument.CurrentLocationProps.builder()
                .address(source.getAddress())
                .longitude(source.getLongitude())
                .latitude(source.getLatitude())
                .build();
    }

    private ClawfoLaundryDocument.PaymentMethodProps mapPaymentMethod(
            ClawfoLapakLaundryRequest.PaymentMethodProps source
    ) {

        if (source == null) {
            return null;
        }

        return ClawfoLaundryDocument.PaymentMethodProps.builder()
                .isCash(source.isCash())
                .isQris(source.isQris())
                .fotoQris(source.getFotoQris())
                .isBankTransfer(source.isBankTransfer())
                .bankCode(source.getBankCode())
                .rekno(source.getRekno())
                .rekOwner(source.getRekOwner())
                .build();
    }

    private ClawfoLaundryDocument.DeliveryServiceProps mapDeliveryService(
            ClawfoLapakLaundryRequest.DeliveryServiceProps source
    ) {

        if (source == null) {
            return null;
        }

        return ClawfoLaundryDocument.DeliveryServiceProps.builder()
                .isPickup(source.isPickup())
                .isPickupFree(source.isPickupFree())
                .pickupPayment(source.getPickupPayment())
                .isDelivery(source.isDelivery())
                .isDeliveryFree(source.isDeliveryFree())
                .deliveryPayment(source.getDeliveryPayment())
                .build();
    }

    private ClawfoLaundryDocument.OperationalHourProps mapOperationalHour(
            ClawfoLapakLaundryRequest.OperationalHourProps source
    ) {

        if (source == null) {
            return null;
        }

        return ClawfoLaundryDocument.OperationalHourProps.builder()
                .seninStartDay(source.getSeninStartDay())
                .seninEndDay(source.getSeninEndDay())
                .selasaStartDay(source.getSelasaStartDay())
                .selasaEndDay(source.getSelasaEndDay())
                .rabuStartDay(source.getRabuStartDay())
                .rabuEndDay(source.getRabuEndDay())
                .kamisStartDay(source.getKamisStartDay())
                .kamisEndDay(source.getKamisEndDay())
                .jumatStartDay(source.getJumatStartDay())
                .jumatEndDay(source.getJumatEndDay())
                .sabtuStartDay(source.getSabtuStartDay())
                .sabtuEndDay(source.getSabtuEndDay())
                .mingguStartDay(source.getMingguStartDay())
                .mingguEndDay(source.getMingguEndDay())
                .build();
    }

}
