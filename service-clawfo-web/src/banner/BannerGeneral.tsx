import {styles} from "./style/banner-general.style";


type BannerGeneralProps = {
    title: string;
    description: string;
    image?: string | null;
    logo?: string | null;
    onPress?: () => void;
};

export default function BannerGeneral({
                                          title,
                                          description,
                                          image,
                                          onPress,
                                          logo,
                                      }: BannerGeneralProps) {
    return (
        <button
            type="button"
            style={styles.container}
            onClick={onPress}
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

            {/* Content */}
            <div style={styles.content}>
                <span style={styles.label}>
                    {logo}
                </span>

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
