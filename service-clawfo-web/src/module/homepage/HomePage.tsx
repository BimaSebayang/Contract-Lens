import {styles} from "../../component/homepage/homepage.style";
import {useSearchParams} from "react-router-dom";
import {generateTokenChatApi} from "../../integration/module/auth0a.api";
import {removeStorage, setStorage, STORAGE_KEY} from "../../utils/props/async-storage";
import {useEffect, useMemo, useState} from "react";
import HeaderNavigation from "../../component/header-navigation/header-navigation";
import {generateTicketId} from "../../utils/props/header-props";
import {
    ClawfoPriceListDocument,
    getDetailPriceList,
    ServiceTemplateProps
} from "../../integration/module/pricelist0a.api";
import BannerCommander from "../../banner/BannerCommander";


export default function HomePage() {

    const [searchParams] =
        useSearchParams();

    const laundryCode =
        searchParams.get("laundryCode");

    const [clawfoPriceList, setClawfoPriceList] =
        useState<ClawfoPriceListDocument>({
            laundryCode: "",
            services: [],
            banners: [],
        });

    const [clawfoPromotions, setClawfoPromotions] =
        useState<ServiceTemplateProps[]>([]);

    const [clawfoPopularMenus, setClawfoPopularMenus] =
        useState<ServiceTemplateProps[]>([]);



    useEffect(() => {

        initialize();

    }, []);

    const activeBanners = useMemo(
        () =>
            (clawfoPriceList?.banners ?? [])
                .filter(
                    (banner) =>
                        banner.templateId != null
                )
                .map((banner) => ({
                    bannerId:banner.bannerId??"",
                    id: banner.templateId ?? 0,
                    title: banner.judul ?? "",
                    description: banner.deskripsi ?? "",
                    image: banner.gambarProduk ?? "",
                    enabled: true,
                    logo: banner.logo ?? "",
                })),
        [
            clawfoPriceList?.banners
        ],
    );

    const initialize = async () => {
         await initializeHomePage();
         await initializePriceList();
    };

    const initializePriceList = async () => {

        try {

            const ticketId =
                generateTicketId();

            const response =
                await getDetailPriceList(
                    ticketId
                );


            if (
                response.code ===
                "price.list.found"
            ) {

                const priceList =
                    response.data;


                /**
                 * SET PRICE LIST
                 */
                setClawfoPriceList(
                    priceList
                );


                /**
                 * ALL SERVICES
                 *
                 * Jangan filter service
                 * berdasarkan field apapun.
                 *
                 * Semua service tetap dipertahankan
                 * walaupun field masih kosong.
                 */
                const services =
                    priceList?.services ?? [];


                /**
                 * PROMOTIONS
                 *
                 * Semua service tetap ditampilkan.
                 *
                 * Field promotionService
                 * boleh kosong.
                 */

                const promotionMenus = [...services].filter(
                    service => service.promotionService === true
                );

                setClawfoPromotions(
                    promotionMenus
                );


                /**
                 * POPULAR SERVICES
                 *
                 * Semua service tetap ditampilkan.
                 *
                 * Kalau orders ada,
                 * service dengan orders lebih besar
                 * akan berada di atas.
                 *
                 * Kalau orders kosong / null / 0,
                 * service tetap tampil.
                 */
                const popularMenus =
                    [...services].sort(
                        (a, b) =>
                            Number(
                                b.orders ?? 0
                            ) -
                            Number(
                                a.orders ?? 0
                            )
                    );


                setClawfoPopularMenus(
                    popularMenus
                );

            } else {

                console.log(
                    "GET PRICE LIST FAILED:",
                    response.message.id
                );

            }

        } catch (error) {

            console.error(
                "FAILED GET PRICE LIST:",
                error
            );

        }

    };



    const initializeHomePage =
        async (): Promise<boolean> => {

            try {

                if (!laundryCode) {
                    return false;
                }

                const response =
                    await generateTokenChatApi({
                        codeLaundry:
                        laundryCode,

                        username:
                            "Bima",

                        email:
                            "bima@clawfo.com",

                        handphone:
                            "081234567890",
                    });


                if (
                    response.code ===
                    "auth.login.success"
                ) {

                    setStorage(
                        STORAGE_KEY.CHAT_ACCESS_TOKEN,
                        response.data.accessToken
                    );

                    setStorage(
                        STORAGE_KEY.CHAT_REFRESH_TOKEN,
                        response.data.refreshToken
                    );

                    return true;
                }


                /* =============================================
                   CLEAR TOKEN
                   ============================================= */

                removeStorage(
                    STORAGE_KEY.CHAT_ACCESS_TOKEN
                );

                removeStorage(
                    STORAGE_KEY.CHAT_REFRESH_TOKEN
                );

                return false;

            } catch (error) {

                console.error(
                    "Failed to initialize chat:",
                    error
                );

                return false;
            }
        };


    return (
        <div style={styles.container}>
            <div style={styles.contentContainer}>
                <HeaderNavigation
                    title="LISt HArga"
                    subtitle="Kelola menu dan harga untuk CLAwfo."
                    />

                <div>
                    <div style={styles.content}>
                        <BannerCommander
                            banners={activeBanners}
                        />
                    </div>
                </div>
            </div>
        </div>
    );
}
