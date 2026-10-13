import { useState } from 'react';
import {styles} from "./style/banner-promo.style";

type BannerPromoProps = {
    title: string;
    description: string;
    image?: string | null;
    onPress?: () => void;
    logo?: string | null;
};

export default function BannerPromo({
                                        title,
                                        description,
                                        image,
                                        onPress,
                                        logo,
                                    }: BannerPromoProps) {
    const [pressed, setPressed] = useState(false);

    return (
        <button
            type="button"
            style={{
                ...styles.container,
                ...(pressed ? styles.pressed : {}),
            }}
            onClick={onPress}
            onMouseDown={() => setPressed(true)}
            onMouseUp={() => setPressed(false)}
            onMouseLeave={() => setPressed(false)}
            onTouchStart={() => setPressed(true)}
            onTouchEnd={() => setPressed(false)}
            aria-label={title}
        >
            {/* Background Image */}
            {image && (
                <img
                    src={image}
                    alt=""
                    style={styles.image}
                />
            )}

            {/* Dark Overlay */}
            <div style={styles.overlay} />

            {/* Bottom Depth */}
            <div style={styles.bottomOverlay} />

            {/* Decorative Accent */}
            <div style={styles.accent} />
            <div style={styles.accentSmall} />

            {/* Content */}
            <div style={styles.content}>
                <div style={styles.labelWrapper}>
                    <div style={styles.labelDot} />

                    <span style={styles.label}>
            {logo}
          </span>
                </div>

                <h2 style={styles.title}>
                    {title}
                </h2>

                <p style={styles.description}>
                    {description}
                </p>
            </div>
        </button>
    );
}