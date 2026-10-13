import apiClient, {ClawfoMappingResponse} from "../client";
import {getStorage, log, STORAGE_KEY} from "../../utils/props/async-storage";
import {getDeviceId} from "../../utils/props/header-props";


export interface  ClawfoPriceListDocument{
    laundryCode: string;
    services:ServiceTemplateProps[];
    banners:ClawfoBannerDocument[];
}

export interface MenuImageProps{
    fotoId?:string|null|undefined;
    sequence:number;
    imageUrl:string;
}

export interface ClawfoBannerDocument {
    bannerId?:string;
    templateId?: number;
    logo?: string;
    judul?: string;
    deskripsi?: string;
    gambarProduk?: string;
    enabled?:boolean;
}

export interface ServiceTemplateProps {
    serviceId?:string|null|undefined;
    fotos?:MenuImageProps[]|null|undefined;
    mainFotoUrl:string;
    namaLayanan?:string;
    views:string;
    orders:string;
    description?:string|undefined;
    categoryId?:string|null|undefined;
    estimationMax?:number|null|undefined;
    estimationMin?:number|null|undefined;
    unit?:string|null|undefined;
    price:number;
    isMenuUnggulan?:boolean|null|undefined;
    showService?:boolean|null|undefined;
    promotionService?:boolean|null|undefined;
    status?:string|null|undefined;
    statusType?:string|null|undefined;
}

export const getDetailPriceList = async (
    ticketId: string
): Promise<
    ClawfoMappingResponse<ClawfoPriceListDocument>
> => {

    log("START: get CLAwfo price list detail");

    const token =  getStorage(
        STORAGE_KEY.CHAT_ACCESS_TOKEN,
    );

    log(
        "ACCESS TOKEN:",
        token
            ? `${token.substring(0, 20)}...`
            : null
    );

    const deviceId = getDeviceId();

    log("DEVICE ID:", deviceId);
    log("TICKET ID:", ticketId);

    const response = await apiClient.get<
        ClawfoMappingResponse<ClawfoPriceListDocument>
    >(
        `/V.1.0.0/web/price-list/detail`,
        {
            headers: {
                Authorization: `Bearer ${token}`,
                "X-Device-Id": deviceId,
                "X-Ticket-Id": ticketId,
                "Content-Type": "application/json",
            },
        }
    );

    log("HTTP STATUS:", response.status);
    log("CODE:", response.data.code);
    log("MESSAGE EN:", response.data.message.en);
    log("MESSAGE ID:", response.data.message.id);

    log(
        "PRICE LIST DATA:",
        response.data.data
    );

    log(
        "SERVICES:",
        response.data.data?.services?.length
    );

    log(
        "BANNERS:",
        response.data.data?.banners?.length
    );

    log("FINISH: get CLAwfo price list detail");

    return response.data;
};