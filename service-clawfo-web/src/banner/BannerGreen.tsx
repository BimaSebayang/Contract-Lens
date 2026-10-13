import {styles} from "./style/banner-green.style";

type BannerGreenProps = {
    title: string;
    description: string;
    image?: string | null;
    onPress?: () => void;
    logo?: string | null;
};

export default function BannerGreen({
                                        title,
                                        description,
                                        image,
                                        onPress,
                                        logo,
                                    }: BannerGreenProps) {
    return (
        <button
            type="button"
            style={styles.container}
            onClick={onPress}
            aria-label={title}
        >
            {/* Decorative Circles */}
            <div style={styles.circleLarge} />
            <div style={styles.circleMedium} />
            <div style={styles.circleSmall} />
            <div style={styles.circleBottom} />
            <div style={styles.circleTiny} />

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

            {/* Image */}
            {image && (
                <div style={styles.imageWrapper}>
                    <img
                        src={image}
                        alt=""
                        style={styles.image}
                    />

                    <div style={styles.imageOverlay} />

                    {/* Image Decoration */}
                    <div style={styles.imageCircle} />
                </div>
            )}
        </button>
    );
}