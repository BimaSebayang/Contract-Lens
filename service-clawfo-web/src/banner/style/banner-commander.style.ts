
import type { CSSProperties } from 'react';

import {
    normalize,
    spacing,
    radius,
    fontScale,
} from '../../utils/responsive';

// ============================================================
// COLORS
// ============================================================

const COLORS = {
    blue: '#1268F3',
    navy: '#07152F',
    white: '#FFFFFF',
};

// ============================================================
// STYLES
// ============================================================

export const styles: Record<string, CSSProperties> = {
    // ========================================================
    // BANNER
    // ========================================================

    bannerSection: {
        width: '100%',
        marginTop: spacing(14),
        boxSizing: 'border-box',
    },

    carousel: {
        display: 'flex',
        flexDirection: 'row',
        width: '100%',
        overflowX: 'auto',
        overflowY: 'hidden',
        scrollSnapType: 'x mandatory',
        scrollbarWidth: 'none',
    },

    bannerItem: {
        position: 'relative',
        flex: '0 0 100%',
        minWidth: 0,
        boxSizing: 'border-box',
        scrollSnapAlign: 'start',
    },

    // ========================================================
    // MANAGEMENT
    // ========================================================

    bannerManagement: {
        position: 'absolute',
        right: normalize(8),
        top: normalize(8),
        display: 'flex',
        flexDirection: 'row',
        alignItems: 'center',
        gap: spacing(4),
        zIndex: 2,
    },

    bannerManagementButton: {
        width: normalize(30),
        height: normalize(30),
        padding: 0,
        border: 'none',
        borderRadius: radius(9),
        backgroundColor: COLORS.white,
        color: COLORS.blue,
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        cursor: 'pointer',
        fontSize: fontScale(17),
    },

    // ========================================================
    // DOTS
    // ========================================================

    carouselDots: {
        display: 'flex',
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',
        gap: spacing(5),
        marginTop: spacing(10),
    },

    dotButton: {
        padding: normalize(3),
        border: 'none',
        backgroundColor: 'transparent',
        cursor: 'pointer',
    },

    dot: {
        display: 'block',
        width: normalize(8),
        height: normalize(7),
        borderRadius: normalize(5),
        backgroundColor: '#DDE9F8',
        transition: 'width 0.2s ease',
    },

    dotActive: {
        width: normalize(19),
        backgroundColor: COLORS.blue,
    },

    // ========================================================
    // EMPTY STATE
    // ========================================================

    emptyBannerCard: {
        width: '100%',
        minHeight: normalize(218),
        boxSizing: 'border-box',
        borderRadius: radius(18),
        border: '1px solid #CFE0F7',
        backgroundColor: '#F4F8FE',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        paddingLeft: normalize(16),
        paddingRight: normalize(16),
    },

    emptyBannerIcon: {
        position: 'relative',
        width: normalize(42),
        height: normalize(42),
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        color: '#77A8EF',
        fontSize: fontScale(35),
    },

    plusBadge: {
        position: 'absolute',
        right: normalize(-8),
        bottom: normalize(-2),
        width: normalize(30),
        height: normalize(30),
        borderRadius: normalize(15),
        backgroundColor: COLORS.blue,
        color: COLORS.white,
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        fontSize: fontScale(20),
    },

    emptyBannerTitle: {
        marginTop: spacing(12),
        marginBottom: 0,
        fontFamily: "'Outfit', sans-serif",
        fontWeight: 700,
        fontSize: fontScale(17),
        color: COLORS.navy,
        textAlign: 'center',
    },

    emptyBannerDescription: {
        marginTop: spacing(5),
        marginBottom: 0,
        fontFamily: "'Outfit', sans-serif",
        fontWeight: 500,
        fontSize: fontScale(11),
        lineHeight: `${fontScale(17)}px`,
        color: '#30456B',
        textAlign: 'center',
        maxWidth: '85%',
    },

    // ========================================================
    // ADD BANNER
    // ========================================================

    addBannerButton: {
        alignSelf: 'center',
        minWidth: normalize(174),
        minHeight: normalize(46),
        boxSizing: 'border-box',
        paddingLeft: normalize(18),
        paddingRight: normalize(18),
        border: 'none',
        borderRadius: radius(16),
        backgroundColor: COLORS.blue,
        color: COLORS.white,
        display: 'flex',
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',
        gap: spacing(7),
        marginTop: spacing(9),
        fontFamily: "'Outfit', sans-serif",
        fontWeight: 700,
        fontSize: fontScale(12),
        cursor: 'pointer',
    },
};
