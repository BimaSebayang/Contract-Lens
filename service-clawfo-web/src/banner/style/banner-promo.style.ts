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
        backgroundColor: '#0B2347',
        position: 'relative',
        display: 'block',
        cursor: 'pointer',
        textAlign: 'left',
        fontFamily: 'Outfit',
        boxSizing: 'border-box',
        transition: 'opacity 150ms ease, transform 150ms ease',
    },

    pressed: {
        opacity: 0.92,
        transform: 'scale(0.99)',
    },

    image: {
        position: 'absolute',
        inset: 0,
        display: 'block',
        width: '100%',
        height: '100%',
        objectFit: 'cover',
    },

    overlay: {
        position: 'absolute',
        inset: 0,
        backgroundColor: 'rgba(5, 18, 42, 0.46)',
        pointerEvents: 'none',
    },

    bottomOverlay: {
        position: 'absolute',
        left: 0,
        right: 0,
        bottom: 0,
        height: '65%',
        backgroundColor: 'rgba(4, 15, 35, 0.24)',
        pointerEvents: 'none',
    },

    /* Decorative Elements */
    accent: {
        position: 'absolute',
        width: normalize(150),
        height: normalize(150),
        borderRadius: '50%',
        right: normalize(-60),
        top: normalize(-70),
        backgroundColor: 'rgba(18, 104, 243, 0.20)',
        pointerEvents: 'none',
    },

    accentSmall: {
        position: 'absolute',
        width: normalize(75),
        height: normalize(75),
        borderRadius: '50%',
        right: normalize(25),
        bottom: normalize(-35),
        backgroundColor: 'rgba(255, 255, 255, 0.07)',
        pointerEvents: 'none',
    },

    /* Content */
    content: {
        position: 'relative',
        zIndex: 1,
        width: '100%',
        height: '100%',
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'flex-start',
        padding: `${normalize(18)}px ${normalize(20)}px`,
        boxSizing: 'border-box',
        pointerEvents: 'none',
    },

    labelWrapper: {
        display: 'flex',
        flexDirection: 'row',
        alignItems: 'center',
        marginBottom: normalize(7),
    },

    labelDot: {
        flexShrink: 0,
        width: normalize(5),
        height: normalize(5),
        borderRadius: '50%',
        marginRight: normalize(7),
        backgroundColor: '#5FA3FF',
    },

    label: {
        fontFamily: 'Outfit',
        fontWeight: 600,
        fontSize: normalize(10),
        letterSpacing: normalize(1),
        color: '#DCEAFF',
    },

    title: {
        maxWidth: '72%',
        margin: 0,
        fontFamily: 'Outfit',
        fontWeight: 700,
        fontSize: normalize(24),
        lineHeight: `${normalize(29)}px`,
        color: '#FFFFFF',
        display: '-webkit-box',
        WebkitBoxOrient: 'vertical',
        WebkitLineClamp: 2,
        overflow: 'hidden',
        overflowWrap: 'anywhere',
    },

    description: {
        maxWidth: '68%',
        marginTop: normalize(6),
        marginBottom: 0,
        fontFamily: 'Outfit',
        fontWeight: 500,
        fontSize: normalize(12),
        lineHeight: `${normalize(17)}px`,
        color: '#E4EEFF',
        display: '-webkit-box',
        WebkitBoxOrient: 'vertical',
        WebkitLineClamp: 2,
        overflow: 'hidden',
        overflowWrap: 'anywhere',
    },
};