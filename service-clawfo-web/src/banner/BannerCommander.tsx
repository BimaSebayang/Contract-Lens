
import { useEffect, useRef, useState } from 'react';


import {styles} from "./style/banner-commander.style";
import BannerGeneral from "./BannerGeneral";
import BannerGreen from "./BannerGreen";
import BannerOrange from "./BannerOrange";
import BannerPromo from "./BannerPromo";
import {log} from "../utils/props/async-storage";

// ============================================================
// PROPS
// ============================================================

export type Banner = {
    bannerId?: string;
    id: number;
    title: string;
    description: string;
    image: string;
    enabled: boolean;
    logo: string;
};

type BannerCommanderProps = {
    banners: Banner[];
};

// ============================================================
// BANNER TEMPLATE
// ============================================================

function getBannerComponent(id: number) {
    switch (id) {
        case 2:
            return BannerGreen;
        case 3:
            return BannerOrange;
        case 4:
            return BannerPromo;
        case 1:
        default:
            return BannerGeneral;
    }
}

// ============================================================
// COMPONENT
// ============================================================

export default function BannerCommander({
                                            banners
                                        }: BannerCommanderProps) {
    const [activeIndex, setActiveIndex] = useState(0);

    const scrollRef = useRef<HTMLDivElement>(null);

    // ========================================================
    // SCROLL TO BANNER
    // ========================================================

    const scrollToBanner = (index: number) => {
        const container = scrollRef.current;

        if (!container) return;

        const item = container.children[index] as
            | HTMLElement
            | undefined;

        if (!item) return;

        container.scrollTo({
            left: item.offsetLeft,
            behavior: 'smooth',
        });
    };

    // ========================================================
    // RESET ACTIVE INDEX
    // ========================================================

    useEffect(() => {
        if (banners.length <= 1) {
            setActiveIndex(0);
            return;
        }

        setActiveIndex((current) =>
            Math.min(current, banners.length - 1)
        );
    }, [banners.length]);

    // ========================================================
    // AUTO CAROUSEL
    // ========================================================

    useEffect(() => {
        if (banners.length <= 1) return;

        const interval = window.setInterval(() => {
            setActiveIndex((current) => {
                const next =
                    current >= banners.length - 1
                        ? 0
                        : current + 1;

                requestAnimationFrame(() => {
                    scrollToBanner(next);
                });

                return next;
            });
        }, 5000);

        return () => window.clearInterval(interval);
    }, [banners.length]);

    // ========================================================
    // HANDLE SCROLL
    // ========================================================

    const handleScroll = () => {
        const container = scrollRef.current;

        if (!container) return;

        const index = Math.round(
            container.scrollLeft / container.clientWidth
        );

        if (
            index >= 0 &&
            index < banners.length
        ) {
            setActiveIndex(index);
        }
    };

    // ========================================================
    // DOT PRESS
    // ========================================================

    const handleDotPress = (index: number) => {
        setActiveIndex(index);
        scrollToBanner(index);
    };

    // ========================================================
    // RENDER
    // ========================================================

    return (
        <section style={styles.bannerSection}>
            {banners.length > 0 ? (
                <>
                    <div
                        ref={scrollRef}
                        style={styles.carousel}
                        onScroll={handleScroll}
                        aria-label="Banner promosi"
                    >
                        {banners.map((banner) => {
                            const BannerComponent =
                                getBannerComponent(banner.id);

                            return (
                                <article
                                    key={
                                        banner.bannerId ??
                                        banner.id
                                    }
                                    style={styles.bannerItem}
                                >
                                    <BannerComponent
                                        title={banner.title}
                                        description={banner.description}
                                        image={banner.image}
                                        logo={banner.logo}
                                        onPress={()=>log("CLICK")}
                                    />

                                </article>
                            );
                        })}
                    </div>

                    {banners.length > 1 && (
                        <div style={styles.carouselDots}>
                            {banners.map((banner, index) => (
                                <button
                                    key={
                                        banner.bannerId ??
                                        banner.id
                                    }
                                    type="button"
                                    onClick={() =>
                                        handleDotPress(index)
                                    }
                                    style={styles.dotButton}
                                    aria-label={`Banner ${index + 1}`}
                                >
                                    <span
                                        style={{
                                            ...styles.dot,
                                            ...(index === activeIndex
                                                ? styles.dotActive
                                                : {}),
                                        }}
                                    />
                                </button>
                            ))}
                        </div>
                    )}
                </>
            ) : (
                <div style={styles.emptyBannerCard}>
                    <div style={styles.emptyBannerIcon}>
                        <span>▧</span>

                        <span style={styles.plusBadge}>
                            +
                        </span>
                    </div>

                    <h3 style={styles.emptyBannerTitle}>
                        Belum ada banner promosi
                    </h3>

                    <p style={styles.emptyBannerDescription}>
                        Tambahkan banner untuk menampilkan
                        menu unggulanmu di beranda.
                    </p>
                </div>
            )}


        </section>
    );
}
