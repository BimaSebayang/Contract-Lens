
import type { CSSProperties } from 'react';

import {
    normalize,
} from '../../utils/responsive';

// ============================================================
// COLORS
// ============================================================

const COLORS = {
    greenSoft: '#F0FDF4',
    white: '#FFFFFF',
    label: '#DCEAFF',
    description: '#EDF4FF',
    blue: '#1268F3',
    overlay: 'rgba(7, 21, 47, 0.42)',
};

// ============================================================
// STYLES
// ============================================================

export const styles: Record<string, CSSProperties> = {
    // ========================================================
    // CONTAINER
    // ========================================================

    container: {
        position: 'relative',

        display: 'flex',
        flexDirection: 'row',
        alignItems: 'stretch',

        width: '100%',
        height: normalize(170),

        padding: 0,
        border: 'none',
        borderRadius: normalize(20),

        overflow: 'hidden',

        backgroundColor: COLORS.greenSoft,

        cursor: 'pointer',
        textAlign: 'left',
        fontFamily: "'Outfit', sans-serif",
        boxSizing: 'border-box',
    },

    // ========================================================
    // BACKGROUND IMAGE
    // ========================================================

    image: {
        position: 'absolute',
        inset: 0,

        width: '100%',
        height: '100%',

        objectFit: 'cover',
    },

    // ========================================================
    // DARK OVERLAY
    // ========================================================

    overlay: {
        position: 'absolute',
        inset: 0,

        backgroundColor: COLORS.overlay,
    },

    // ========================================================
    // CONTENT
    // ========================================================

    content: {
        position: 'relative',
        zIndex: 1,

        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'flex-start',

        flex: 1,
        minWidth: 0,

        paddingLeft: normalize(20),
        paddingRight: normalize(20),
        paddingTop: normalize(18),
        paddingBottom: normalize(18),

        boxSizing: 'border-box',
    },

    // ========================================================
    // LABEL
    // ========================================================

    label: {
        marginBottom: normalize(6),

        fontFamily: "'Outfit', sans-serif",
        fontWeight: 600,

        fontSize: normalize(10),
        letterSpacing: normalize(1),

        color: COLORS.label,
    },

    // ========================================================
    // TITLE
    // ========================================================

    title: {
        maxWidth: '72%',

        margin: 0,

        fontFamily: "'Outfit', sans-serif",
        fontWeight: 700,

        fontSize: normalize(24),
        lineHeight: `${normalize(29)}px`,

        color: COLORS.white,

        display: '-webkit-box',
        WebkitLineClamp: 2,
        WebkitBoxOrient: 'vertical',
        overflow: 'hidden',
    },

    // ========================================================
    // DESCRIPTION
    // ========================================================

    description: {
        maxWidth: '68%',

        marginTop: normalize(6),
        marginBottom: 0,

        fontFamily: "'Outfit', sans-serif",
        fontWeight: 500,

        fontSize: normalize(12),
        lineHeight: `${normalize(17)}px`,

        color: COLORS.description,

        display: '-webkit-box',
        WebkitLineClamp: 2,
        WebkitBoxOrient: 'vertical',
        overflow: 'hidden',
    },
};
