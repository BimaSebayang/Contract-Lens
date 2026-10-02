package com.contractlens.service.analyzer.module.lapaklaundry.service;

import com.contractlens.common.clawfo.request.ClawfoLapakLaundryRequest;
import com.contractlens.common.clawfo.request.ClawfoLapakLaundryResponse;
import com.contractlens.common.clawfo.response.ClawfoBankResponse;
import com.contractlens.service.analyzer.db.mongo.dao.ClawfoLaundryDocument;
import com.contractlens.service.analyzer.db.mongo.service.LaundryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClawfoLaundryService {

    private final LaundryService laundryService;

    @Value("${clawfo.laundry.url}")
    private String laundryUrl;

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

        laundryService.upsertLaundry(
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
            String ticketId
    ) {

        log.info(
                "Getting own laundry lapak | deviceId={}, ticketId={}",
                deviceId,
                ticketId
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

        String namaLaundry = document.getNamaLaundry();

        if (namaLaundry != null) {
            namaLaundry = namaLaundry
                    .replaceAll("[^a-zA-Z0-9]", "")
                    .toLowerCase();

            String url = laundryUrl
                    .replace("{nama_laundry}", namaLaundry)
                    .replace("{encodeId}", document.getLaundryCode());

            response.setUrl(url);
        } else {
            response.setUrl(null);
        }
        response.setLaundryCode(document.getLaundryCode());
        response.setNamaLaundry(document.getNamaLaundry());
        response.setDeskripsiLaundry(document.getDeskripsiLaundry());
        response.setFotoToko(document.getFotoToko());
        response.setPhoneNumber(document.getPhone());

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

    public List<ClawfoBankResponse> getAllBanks() {
        return List.of(
                ClawfoBankResponse.builder().key("002").value("Bank BRI").build(),
                ClawfoBankResponse.builder().key("008").value("Bank Mandiri").build(),
                ClawfoBankResponse.builder().key("009").value("Bank BNI").build(),
                ClawfoBankResponse.builder().key("011").value("Bank Danamon").build(),
                ClawfoBankResponse.builder().key("013").value("Bank Permata").build(),
                ClawfoBankResponse.builder().key("014").value("Bank BCA").build(),
                ClawfoBankResponse.builder().key("016").value("Bank Maybank Indonesia").build(),
                ClawfoBankResponse.builder().key("019").value("Bank Panin").build(),
                ClawfoBankResponse.builder().key("022").value("Bank CIMB Niaga").build(),
                ClawfoBankResponse.builder().key("023").value("Bank UOB Indonesia").build(),
                ClawfoBankResponse.builder().key("028").value("Bank OCBC NISP").build(),
                ClawfoBankResponse.builder().key("031").value("Citibank N.A.").build(),
                ClawfoBankResponse.builder().key("032").value("JP Morgan Chase Bank").build(),
                ClawfoBankResponse.builder().key("033").value("Bank of America").build(),
                ClawfoBankResponse.builder().key("036").value("Bank China Construction Bank Indonesia").build(),
                ClawfoBankResponse.builder().key("042").value("Bank HSBC Indonesia").build(),
                ClawfoBankResponse.builder().key("046").value("Bank DBS Indonesia").build(),
                ClawfoBankResponse.builder().key("050").value("Bank Standard Chartered").build(),
                ClawfoBankResponse.builder().key("054").value("Bank Capital Indonesia").build(),
                ClawfoBankResponse.builder().key("057").value("Bank BNP Paribas Indonesia").build(),
                ClawfoBankResponse.builder().key("111").value("Bank Muamalat Indonesia").build(),
                ClawfoBankResponse.builder().key("112").value("Bank Permata Syariah").build(),
                ClawfoBankResponse.builder().key("113").value("Bank Jabar Banten").build(),
                ClawfoBankResponse.builder().key("114").value("Bank DKI").build(),
                ClawfoBankResponse.builder().key("115").value("Bank BPD DIY").build(),
                ClawfoBankResponse.builder().key("116").value("Bank Jateng").build(),
                ClawfoBankResponse.builder().key("117").value("Bank Jatim").build(),
                ClawfoBankResponse.builder().key("118").value("Bank Banten").build(),
                ClawfoBankResponse.builder().key("119").value("Bank Jambi").build(),
                ClawfoBankResponse.builder().key("120").value("Bank Sumut").build(),
                ClawfoBankResponse.builder().key("121").value("Bank Nagari").build(),
                ClawfoBankResponse.builder().key("122").value("Bank Riau Kepri").build(),
                ClawfoBankResponse.builder().key("123").value("Bank Sumsel Babel").build(),
                ClawfoBankResponse.builder().key("124").value("Bank Lampung").build(),
                ClawfoBankResponse.builder().key("125").value("Bank Kalsel").build(),
                ClawfoBankResponse.builder().key("126").value("Bank Kalbar").build(),
                ClawfoBankResponse.builder().key("127").value("Bank Kaltimtara").build(),
                ClawfoBankResponse.builder().key("128").value("Bank Kalteng").build(),
                ClawfoBankResponse.builder().key("129").value("Bank Sulselbar").build(),
                ClawfoBankResponse.builder().key("130").value("Bank SulutGo").build(),
                ClawfoBankResponse.builder().key("131").value("Bank NTB Syariah").build(),
                ClawfoBankResponse.builder().key("132").value("Bank NTT").build(),
                ClawfoBankResponse.builder().key("133").value("Bank Maluku Malut").build(),
                ClawfoBankResponse.builder().key("134").value("Bank Papua").build(),
                ClawfoBankResponse.builder().key("135").value("Bank Bengkulu").build(),
                ClawfoBankResponse.builder().key("137").value("Bank BCA Digital").build(),
                ClawfoBankResponse.builder().key("146").value("Bank of India Indonesia").build(),
                ClawfoBankResponse.builder().key("147").value("Bank Mestika Dharma").build(),
                ClawfoBankResponse.builder().key("152").value("Bank Shinhan Indonesia").build(),
                ClawfoBankResponse.builder().key("153").value("Bank Artha Graha Internasional").build(),
                ClawfoBankResponse.builder().key("157").value("Bank Maspion Indonesia").build(),
                ClawfoBankResponse.builder().key("161").value("Bank Ganesha").build(),
                ClawfoBankResponse.builder().key("167").value("Bank QNB Indonesia").build(),
                ClawfoBankResponse.builder().key("200").value("Bank BTN").build(),
                ClawfoBankResponse.builder().key("213").value("Bank Jago").build(),
                ClawfoBankResponse.builder().key("441").value("Bank KB Bukopin").build(),
                ClawfoBankResponse.builder().key("451").value("Bank Syariah Indonesia").build(),
                ClawfoBankResponse.builder().key("484").value("Bank KEB Hana Indonesia").build(),
                ClawfoBankResponse.builder().key("494").value("Bank Raya Indonesia").build(),
                ClawfoBankResponse.builder().key("501").value("Bank Digital BCA").build(),
                ClawfoBankResponse.builder().key("503").value("Bank Nationalnobu").build(),
                ClawfoBankResponse.builder().key("506").value("Bank Neo Commerce").build(),
                ClawfoBankResponse.builder().key("513").value("Bank Ina Perdana").build(),
                ClawfoBankResponse.builder().key("535").value("SeaBank Indonesia").build(),
                ClawfoBankResponse.builder().key("542").value("Bank Jago").build(),
                ClawfoBankResponse.builder().key("553").value("Bank Mayapada").build(),
                ClawfoBankResponse.builder().key("562").value("Bank Fama International").build(),
                ClawfoBankResponse.builder().key("564").value("Bank Mandiri Taspen").build(),
                ClawfoBankResponse.builder().key("567").value("Bank Aladin Syariah").build(),
                ClawfoBankResponse.builder().key("688").value("Bank BTPN Syariah").build()
        );
    }
}
