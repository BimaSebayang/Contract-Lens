package com.contractlens.service.analyzer.db.mongo.service;

import com.contractlens.common.clawfo.request.ClawfoLapakLaundryRequest;
import com.contractlens.service.analyzer.db.mongo.dao.ClawfoLaundryDocument;

public interface LaundryService {

    void createLaundry(
            String deviceId,
            String ticketId,
            String longitude,
            String latitude,
            String location,
            ClawfoLapakLaundryRequest request
    );

    ClawfoLaundryDocument getOwnLaundry();

}
