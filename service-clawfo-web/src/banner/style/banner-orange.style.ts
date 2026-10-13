import type { CSSProperties } from 'react';
import { normalize } from '../../utils/responsive';

export const styles: Record<string, CSSProperties> = {
    container: {
        width: '100%',
        height: normalize(170),
        padding: 0,
        border: 'none',
        borderRadius: normalize(20),
        overflow: 'hidden',
        display: 'flex',
        flexDirection: 'row',
        backgroundColor: '#FFF7ED',
        position: 'relative',
        cursor: 'pointer',
        textAlign: 'left',
        fontFamily: 'Outfit',
        boxSizing: 'border-box',
    },

    /* Background Decoration */
    circleLarge: {
        position: 'absolute',
        width: normalize(150),
        height: normalize(150),
        borderRadius: '50%',
        backgroundColor: '#FFEDD5',
        top: normalize(-75),
        right: normalize(-55),
        opacity: 0.85,
        pointerEvents: 'none',
    },

    circleSmall: {
        position: 'absolute',
        width: normalize(42),
        height: normalize(42),
        borderRadius: '50%',
        backgroundColor: '#FED7AA',
        bottom: normalize(-12),
        right: normalize(30),
        opacity: 0.8,
        pointerEvents: 'none',
    },

    /* Image */
    imageWrapper: {
        width: '43%',
        height: '100%',
        position: 'relative',
        overflow: 'hidden',
        flexShrink: 0,
    },

    image: {
        display: 'block',
        width: '100%',
        height: '100%',
        objectFit: 'cover',
    },

    imageOverlay: {
        position: 'absolute',
        top: 0,
        right: 0,
        width: normalize(42),
        height: '100%',
        backgroundColor: '#FFF7ED',
        opacity: 0.9,
        pointerEvents: 'none',
    },

    imageBadge: {
        position: 'absolute',
        width: normalize(28),
        height: normalize(28),
        borderRadius: '50%',
        right: normalize(14),
        bottom: normalize(14),
        backgroundColor: '#FFFFFF',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        opacity: 0.9,
        pointerEvents: 'none',
    },

    imageBadgeDot: {
        width: normalize(8),
        height: normalize(8),
        borderRadius: '50%',
        backgroundColor: '#F97316',
    },

    /* Content */
    content: {
        flex: 1,
        minWidth: 0,
        height: '100%',
        padding: `${normalize(18)}px ${normalize(16)}px`,
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'flex-start',
        position: 'relative',
        zIndex: 2,
        boxSizing: 'border-box',
    },

    badge: {
        alignSelf: 'flex-start',
        display: 'flex',
        flexDirection: 'row',
        alignItems: 'center',
        padding: `${normalize(5)}px ${normalize(8)}px`,
        borderRadius: normalize(7),
        backgroundColor: '#FFEDD5',
        marginBottom: normalize(9),
        maxWidth: '100%',
        boxSizing: 'border-box',
    },

    badgeDot: {
        flexShrink: 0,
        width: normalize(5),
        height: normalize(5),
        borderRadius: '50%',
        backgroundColor: '#EA580C',
        marginRight: normalize(5),
    },

    badgeText: {
        fontFamily: 'Outfit',
        fontWeight: 700,
        fontSize: normalize(8),
        letterSpacing: normalize(0.7),
        color: '#EA580C',
    },

    title: {
        maxWidth: '100%',
        margin: 0,
        fontFamily: 'Outfit',
        fontWeight: 700,
        fontSize: normalize(20),
        lineHeight: `${normalize(24)}px`,
        color: '#9A3412',
        display: '-webkit-box',
        WebkitBoxOrient: 'vertical',
        WebkitLineClamp: 2,
        overflow: 'hidden',
        overflowWrap: 'anywhere',
    },

    description: {
        maxWidth: '100%',
        marginTop: normalize(5),
        marginBottom: 0,
        fontFamily: 'Outfit',
        fontWeight: 500,
        fontSize: normalize(11),
        lineHeight: `${normalize(16)}px`,
        color: '#64748B',
        display: '-webkit-box',
        WebkitBoxOrient: 'vertical',
        WebkitLineClamp: 2,
        overflow: 'hidden',
        overflowWrap: 'anywhere',
    },
};