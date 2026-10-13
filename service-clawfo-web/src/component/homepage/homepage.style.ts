
import type { CSSProperties } from 'react';

/* =========================================================
   COLORS
   ========================================================= */

const COLORS = {
    background: '#FFFFFF',
};

import {
    normalize,
    spacing,
    fontScale,
} from '../../utils/responsive';

/* =========================================================
   STYLES
   ========================================================= */

export const styles: Record<string, CSSProperties> = {
    container: {
        width: '100%',
        height: '100dvh',
        minHeight: '100dvh',

        display: 'flex',
        flexDirection: 'column',

        backgroundColor: COLORS.background,

        overflow: 'hidden',
    },

    contentContainer: {
        width: '100%',
        boxSizing: 'border-box',

        paddingBottom: normalize(18),
    },

    content: {
        paddingLeft: normalize(14),
        paddingRight: normalize(14),
        boxSizing: 'border-box',
    },
};
