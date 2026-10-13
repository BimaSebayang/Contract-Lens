
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
    navy: '#07152F',
    navyBorder: '#12345C',
    white: '#FFFFFF',
};

// ============================================================
// STYLES
// ============================================================

export const styles: Record<string, CSSProperties> = {
    // ========================================================
    // HEADER CONTAINER
    // ========================================================

    container: {
        width: '100%',
        boxSizing: 'border-box',

        backgroundColor: COLORS.navy,

        borderBottomLeftRadius: radius(28),
        borderBottomRightRadius: radius(28),

        borderWidth: 1,
        borderStyle: 'solid',
        borderColor: COLORS.navyBorder,

        boxShadow: '0 4px 10px rgba(0, 0, 0, 0.25)',

        marginBottom: spacing(18),

        overflow: 'hidden',
    },

    // ========================================================
    // CONTENT
    // ========================================================

    content: {
        display: 'flex',
        flexDirection: 'row',
        alignItems: 'center',

        paddingLeft: normalize(20),
        paddingRight: normalize(20),

        paddingTop: normalize(16),
        paddingBottom: normalize(16),

        minWidth: 0,
    },

    // ========================================================
    // TITLE
    // ========================================================

    title: {
        margin: 0,

        fontFamily: "'Fira Code', monospace",
        fontWeight: 500,

        fontSize: fontScale(20),
        lineHeight: `${fontScale(26)}px`,

        color: COLORS.white,

        flexShrink: 0,
    },

    // ========================================================
    // SUBTITLE
    // ========================================================

    subtitle: {
        flex: 1,
        minWidth: 0,

        marginTop: 0,
        marginBottom: 0,
        marginLeft: normalize(14),

        fontFamily: "'Outfit', sans-serif",
        fontWeight: 500,

        fontSize: fontScale(10),
        lineHeight: `${fontScale(14)}px`,

        color: COLORS.white,
        opacity: 0.9,

        textAlign: 'right',
    },
};
