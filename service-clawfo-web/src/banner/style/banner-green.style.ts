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
        alignItems: 'stretch',
        position: 'relative',
        backgroundColor: '#F0FDF4',
        cursor: 'pointer',
        textAlign: 'left',
        fontFamily: 'Outfit',
        boxSizing: 'border-box',
    },

    /* Decorative Circles */
    circleLarge: {
        position: 'absolute',
        width: normalize(170),
        height: normalize(170),
        borderRadius: '50%',
        backgroundColor: '#DCFCE7',
        top: normalize(-85),
        left: normalize(-75),
        opacity: 0.9,
        pointerEvents: 'none',
    },

    circleMedium: {
        position: 'absolute',
        width: normalize(72),
        height: normalize(72),
        borderRadius: '50%',
        backgroundColor: '#E2F9E9',
        top: normalize(42),
        left: normalize(105),
        opacity: 0.8,
        pointerEvents: 'none',
    },

    circleSmall: {
        position: 'absolute',
        width: normalize(38),
        height: normalize(38),
        borderRadius: '50%',
        backgroundColor: '#C9F2D5',
        top: normalize(18),
        right: normalize(105),
        opacity: 0.9,
        pointerEvents: 'none',
    },

    circleBottom: {
        position: 'absolute',
        width: normalize(54),
        height: normalize(54),
        borderRadius: '50%',
        backgroundColor: '#D4F5DE',
        bottom: normalize(-18),
        left: normalize(175),
        opacity: 0.8,
        pointerEvents: 'none',
    },

    circleTiny: {
        position: 'absolute',
        width: normalize(22),
        height: normalize(22),
        borderRadius: '50%',
        backgroundColor: '#BBEBC9',
        bottom: normalize(25),
        left: normalize(8),
        opacity: 0.9,
        pointerEvents: 'none',
    },

    /* Content */
    content: {
        height: '100%',
        minWidth: 0,
        flex: 1,
        padding: `${normalize(18)}px`,
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
        backgroundColor: '#DCFCE7',
        marginBottom: normalize(9),
        maxWidth: '100%',
        boxSizing: 'border-box',
    },

    badgeDot: {
        flexShrink: 0,
        width: normalize(5),
        height: normalize(5),
        borderRadius: '50%',
        backgroundColor: '#16A34A',
        marginRight: normalize(5),
    },

    badgeText: {
        fontFamily: 'Outfit',
        fontWeight: 700,
        fontSize: normalize(8),
        letterSpacing: normalize(0.7),
        color: '#16A34A',
    },

    title: {
        maxWidth: '100%',
        margin: 0,
        fontFamily: 'Outfit',
        fontWeight: 700,
        fontSize: normalize(21),
        lineHeight: `${normalize(25)}px`,
        color: '#14532D',
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

    /* Image */
    imageWrapper: {
        position: 'absolute',
        top: 0,
        right: 0,
        width: '48%',
        height: '100%',
        overflow: 'hidden',
        pointerEvents: 'none',
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
        left: 0,
        width: normalize(50),
        height: '100%',
        backgroundColor: '#F0FDF4',
        opacity: 0.9,
    },

    imageCircle: {
        position: 'absolute',
        width: normalize(145),
        height: normalize(145),
        borderRadius: '50%',
        backgroundColor: '#DCFCE7',
        left: normalize(-70),
        top: normalize(13),
        opacity: 0.25,
    },
};