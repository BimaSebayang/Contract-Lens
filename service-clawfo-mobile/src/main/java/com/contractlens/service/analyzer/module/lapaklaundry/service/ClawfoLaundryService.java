package com.contractlens.service.analyzer.module.lapaklaundry.service;

import com.contractlens.common.clawfo.request.ClawfoLapakLaundryRequest;
import com.contractlens.common.clawfo.request.ClawfoLapakLaundryResponse;
import com.contractlens.service.analyzer.db.mongo.dao.ClawfoLaundryDocument;
import com.contractlens.service.analyzer.db.mongo.service.LaundryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClawfoLaundryService {

    private final LaundryService laundryService;
    private String url = "https://{nama_laundry}.clawfo.id/encodeId={encodeId}";

    public void createLaundry(
            String deviceId,
            String ticketId,
            String longitude,
            String latitude,
            String location,
            @Valid ClawfoLapakLaundryRequest request
    ) {

        log.info(
                "Creating laundry lapak | deviceId={}, ticketId={}, longitude={}, latitude={}, location={}, request={}",
                deviceId,
                ticketId,
                longitude,
                latitude,
                location,
                request
        );

        log.info("Creating laundry lapak | with request = {}",request);

        laundryService.createLaundry(
                deviceId,
                ticketId,
                longitude,
                latitude,
                location,
                request
        );

        log.info(
                "Laundry lapak created successfully | deviceId={}, ticketId={}",
                deviceId,
                ticketId
        );
    }

    public ClawfoLapakLaundryResponse getOwnLaundry(
            String deviceId,
            String ticketId,
            String longitude,
            String latitude,
            String location
    ) {

        log.info(
                "Getting own laundry lapak | deviceId={}, ticketId={}, longitude={}, latitude={}, location={}",
                deviceId,
                ticketId,
                longitude,
                latitude,
                location
        );

        ClawfoLaundryDocument laundry =
                laundryService.getOwnLaundry();

        log.info(
                "Own laundry lapak retrieved successfully | laundryCode={} with laundry is={}",
                laundry.getLaundryCode(),
                laundry
        );

        return mapToResponse(laundry);
    }

    private ClawfoLapakLaundryResponse mapToResponse(
            ClawfoLaundryDocument document
    ) {

        ClawfoLapakLaundryResponse response =
                new ClawfoLapakLaundryResponse();

        String namaLaundry = document.getNamaLaundry()
                .replaceAll("[^a-zA-Z0-9]", "")
                .toLowerCase();

        String url = this.url
                .replace("{nama_laundry}", namaLaundry)
                .replace("{encodeId}", document.getLaundryCode());
        response.setUrl(url);
        response.setLaundryCode(document.getLaundryCode());
        response.setNamaLaundry(document.getNamaLaundry());
        response.setDeskripsiLaundry(document.getDeskripsiLaundry());
        response.setFotoToko(document.getFotoToko());

        if (document.getCurrentLocation() != null) {
            response.setCurrentLocation(
                    ClawfoLapakLaundryRequest.CurrentLocationProps.builder()
                            .address(
                                    document.getCurrentLocation().getAddress()
                            )
                            .longitude(
                                    document.getCurrentLocation().getLongitude()
                            )
                            .latitude(
                                    document.getCurrentLocation().getLatitude()
                            )
                            .build()
            );
        }

        if (document.getPaymentMethod() != null) {
            response.setPaymentMethod(
                    ClawfoLapakLaundryRequest.PaymentMethodProps.builder()
                            .isCash(
                                    document.getPaymentMethod().isCash()
                            )
                            .isQris(
                                    document.getPaymentMethod().isQris()
                            )
                            .fotoQris(
                                    document.getPaymentMethod().getFotoQris()
                            )
                            .isBankTransfer(
                                    document.getPaymentMethod().isBankTransfer()
                            )
                            .bankCode(
                                    document.getPaymentMethod().getBankCode()
                            )
                            .rekno(
                                    document.getPaymentMethod().getRekno()
                            )
                            .rekOwner(
                                    document.getPaymentMethod().getRekOwner()
                            )
                            .build()
            );
        }

        if (document.getDeliveryService() != null) {
            response.setDeliveryService(
                    ClawfoLapakLaundryRequest.DeliveryServiceProps.builder()
                            .isPickup(
                                    document.getDeliveryService().isPickup()
                            )
                            .isPickupFree(
                                    document.getDeliveryService().isPickupFree()
                            )
                            .pickupPayment(
                                    document.getDeliveryService().getPickupPayment()
                            )
                            .isDelivery(
                                    document.getDeliveryService().isDelivery()
                            )
                            .isDeliveryFree(
                                    document.getDeliveryService().isDeliveryFree()
                            )
                            .deliveryPayment(
                                    document.getDeliveryService().getDeliveryPayment()
                            )
                            .build()
            );
        }

        if (document.getOperationalHour() != null) {
            response.setOperationalHour(
                    ClawfoLapakLaundryRequest.OperationalHourProps.builder()
                            .seninStartDay(
                                    document.getOperationalHour().getSeninStartDay()
                            )
                            .seninEndDay(
                                    document.getOperationalHour().getSeninEndDay()
                            )
                            .selasaStartDay(
                                    document.getOperationalHour().getSelasaStartDay()
                            )
                            .selasaEndDay(
                                    document.getOperationalHour().getSelasaEndDay()
                            )
                            .rabuStartDay(
                                    document.getOperationalHour().getRabuStartDay()
                            )
                            .rabuEndDay(
                                    document.getOperationalHour().getRabuEndDay()
                            )
                            .kamisStartDay(
                                    document.getOperationalHour().getKamisStartDay()
                            )
                            .kamisEndDay(
                                    document.getOperationalHour().getKamisEndDay()
                            )
                            .jumatStartDay(
                                    document.getOperationalHour().getJumatStartDay()
                            )
                            .jumatEndDay(
                                    document.getOperationalHour().getJumatEndDay()
                            )
                            .sabtuStartDay(
                                    document.getOperationalHour().getSabtuStartDay()
                            )
                            .sabtuEndDay(
                                    document.getOperationalHour().getSabtuEndDay()
                            )
                            .mingguStartDay(
                                    document.getOperationalHour().getMingguStartDay()
                            )
                            .mingguEndDay(
                                    document.getOperationalHour().getMingguEndDay()
                            )
                            .build()
            );
        }

        return response;
    }
}
