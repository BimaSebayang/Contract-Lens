import {styles} from "./style/banner-orange.style";

type BannerOrangeProps = {
    title: string;
    description: string;
    image?: string | null;
    onPress?: () => void;
    logo?: string | null;
};

export default function BannerOrange({
                                         title,
                                         description,
                                         image,
                                         onPress,
                                         logo,
                                     }: BannerOrangeProps) {
    return (
        <button
            type="button"
            style={styles.container}
            onClick={onPress}
            aria-label={title}
        >
            {/* Background Decoration */}
            <div style={styles.circleLarge} />
            <div style={styles.circleSmall} />

            {/* Image */}
            {image && (
                <div style={styles.imageWrapper}>
                    <img
                        src={image}
                        alt=""
                        style={styles.image}
                    />

                    <div style={styles.imageOverlay} />

                    <div style={styles.imageBadge}>
                        <div style={styles.imageBadgeDot} />
                    </div>
                </div>
            )}

            {/* Content */}
            <div style={styles.content}>
                <div style={styles.badge}>
                    <div style={styles.badgeDot} />

                    <span style={styles.badgeText}>
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