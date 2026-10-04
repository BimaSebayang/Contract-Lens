import type { CSSProperties } from 'react';

import {
    normalize,
    spacing,
    fontScale,
} from '../../utils/responsive';


/* =========================================================
   COLORS
   ========================================================= */

const COLORS = {
    background: '#FFFFFF',
    white: '#FFFFFF',
    navy: '#07152F',
    secondary: '#405477',
    blue: '#168BFF',
    blueSoft: '#EAF4FF',
    border: '#E5EAF1',
    borderSoft: '#EEF2F7',
    gray: '#8290AA',
    grayLight: '#F5F7FA',
    green: '#18B86A',
};


/* =========================================================
   STYLES
   ========================================================= */

export const styles: Record<string, CSSProperties> = {

    /* =========================================================
       CONTAINER
       ========================================================= */

    container: {
        width: '100%',
        height: '100dvh',
        minHeight: '100dvh',

        display: 'flex',
        flexDirection: 'column',

        backgroundColor:
        COLORS.background,

        overflow: 'hidden',
    },


    /* =========================================================
       HEADER
       ========================================================= */

    header: {
        flexShrink: 0,

        backgroundColor:
        COLORS.white,

        paddingTop:
            spacing(12),

        paddingBottom:
            spacing(12),

        paddingLeft:
            spacing(14),

        paddingRight:
            spacing(14),

        borderBottom:
            `1px solid ${COLORS.border}`,

        boxShadow:
            '0 2px 6px rgba(7, 21, 47, 0.04)',
    },


    headerTop: {
        minHeight:
            normalize(54),

        display: 'flex',
        flexDirection: 'row',
        alignItems: 'center',
    },


    backButton: {
        flexShrink: 0,

        width:
            normalize(40),

        height:
            normalize(40),

        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',

        padding: 0,

        border: 'none',

        borderRadius:
            normalize(12),

        backgroundColor:
        COLORS.grayLight,

        cursor: 'pointer',
    },


    headerTitleContainer: {
        flex: 1,

        minWidth: 0,

        marginLeft:
            spacing(10),
    },


    headerTitle: {
        fontFamily:
            "'Outfit', sans-serif",

        fontWeight: 700,

        fontSize:
            fontScale(16),

        color:
        COLORS.navy,

        overflow: 'hidden',

        textOverflow:
            'ellipsis',

        whiteSpace:
            'nowrap',
    },


    headerSubtitle: {
        marginTop:
            spacing(2),

        fontFamily:
            "'Outfit', sans-serif",

        fontWeight: 400,

        fontSize:
            fontScale(11),

        color:
        COLORS.secondary,
    },


    onlineContainer: {
        flexShrink: 0,

        display: 'flex',
        flexDirection: 'row',
        alignItems: 'center',

        gap:
            spacing(5),

        marginLeft:
            spacing(8),
    },


    onlineDot: {
        width:
            normalize(7),

        height:
            normalize(7),

        borderRadius:
            normalize(4),

        backgroundColor:
        COLORS.green,
    },


    onlineText: {
        fontFamily:
            "'Outfit', sans-serif",

        fontWeight: 500,

        fontSize:
            fontScale(10),

        color:
        COLORS.green,
    },


    /* =========================================================
       CHAT
       ========================================================= */

    chatScroll: {
        flex: 1,

        minHeight: 0,

        overflowY: 'auto',

        overflowX: 'hidden',

        scrollbarWidth: 'thin',

        backgroundColor:
        COLORS.background,
    },


    chatContent: {
        width: '100%',

        boxSizing: 'border-box',

        paddingLeft:
            spacing(14),

        paddingRight:
            spacing(14),

        paddingTop:
            spacing(16),

        paddingBottom:
            spacing(20),
    },


    /* =========================================================
       DATE
       ========================================================= */

    dateContainer: {
        width: '100%',

        display: 'flex',
        flexDirection: 'row',
        alignItems: 'center',

        gap:
            spacing(9),

        marginBottom:
            spacing(20),
    },


    dateLine: {
        flex: 1,

        height: 1,

        backgroundColor:
        COLORS.border,
    },


    dateText: {
        flexShrink: 0,

        paddingLeft:
            spacing(10),

        paddingRight:
            spacing(10),

        paddingTop:
            spacing(5),

        paddingBottom:
            spacing(5),

        borderRadius:
            normalize(9),

        backgroundColor:
        COLORS.blueSoft,

        fontFamily:
            "'Outfit', sans-serif",

        fontWeight: 500,

        fontSize:
            fontScale(10),

        color:
        COLORS.blue,
    },


    /* =========================================================
       CHAT DETAIL
       ========================================================= */

    chatDetail: {
        alignSelf:
            'flex-start',

        display: 'flex',

        flexDirection:
            'row',

        alignItems:
            'center',

        gap:
            spacing(5),

        maxWidth:
            '100%',

        paddingLeft:
            spacing(9),

        paddingRight:
            spacing(9),

        paddingTop:
            spacing(5),

        paddingBottom:
            spacing(5),

        marginBottom:
            spacing(14),

        borderRadius:
            normalize(8),

        backgroundColor:
        COLORS.grayLight,
    },


    chatDetailText: {
        maxWidth:
            normalize(300),

        overflow: 'hidden',

        textOverflow:
            'ellipsis',

        whiteSpace:
            'nowrap',

        fontFamily:
            "'Outfit', sans-serif",

        fontWeight: 400,

        fontSize:
            fontScale(10),

        color:
        COLORS.gray,
    },


    /* =========================================================
       CLAWFO MESSAGE
       ========================================================= */

    clawfoMessageRow: {
        width: '100%',

        display: 'flex',

        flexDirection:
            'row',

        alignItems:
            'flex-start',

        marginBottom:
            spacing(14),
    },


    clawfoAvatar: {
        flexShrink: 0,

        width:
            normalize(36),

        height:
            normalize(36),

        display: 'flex',

        alignItems:
            'center',

        justifyContent:
            'center',

        borderRadius:
            normalize(11),

        backgroundColor:
        COLORS.blueSoft,

        marginRight:
            spacing(9),
    },


    clawfoMessageContainer: {
        minWidth: 0,

        width:
            'fit-content',

        maxWidth:
            'min(86%, 680px)',
    },


    messageSender: {
        marginBottom:
            spacing(4),

        fontFamily:
            "'Outfit', sans-serif",

        fontWeight: 600,

        fontSize:
            fontScale(11),

        color:
        COLORS.navy,
    },


    clawfoMessage: {
        paddingLeft:
            spacing(13),

        paddingRight:
            spacing(13),

        paddingTop:
            spacing(11),

        paddingBottom:
            spacing(11),

        borderRadius:
            normalize(14),

        borderTopLeftRadius:
            normalize(4),

        backgroundColor:
        COLORS.grayLight,

        border:
            `1px solid ${COLORS.border}`,
    },


    clawfoMessageText: {
        fontFamily:
            "'Outfit', sans-serif",

        fontWeight: 400,

        fontSize:
            fontScale(13),

        lineHeight:
            `${fontScale(20)}px`,

        color:
        COLORS.navy,

        whiteSpace:
            'pre-wrap',

        overflowWrap:
            'anywhere',
    },


    clawfoMessageTime: {
        marginTop:
            spacing(4),

        fontFamily:
            "'Outfit', sans-serif",

        fontWeight: 400,

        fontSize:
            fontScale(9),

        color:
        COLORS.gray,
    },


    /* =========================================================
       USER MESSAGE
       ========================================================= */

    userMessageRow: {
        width: '100%',

        display: 'flex',

        flexDirection:
            'row',

        justifyContent:
            'flex-end',

        marginBottom:
            spacing(14),
    },


    userMessageContainer: {
        minWidth: 0,

        width:
            'fit-content',

        maxWidth:
            'min(82%, 680px)',

        display: 'flex',

        flexDirection:
            'column',

        alignItems:
            'flex-end',
    },


    userMessage: {
        paddingLeft:
            spacing(14),

        paddingRight:
            spacing(14),

        paddingTop:
            spacing(11),

        paddingBottom:
            spacing(11),

        borderRadius:
            normalize(14),

        borderBottomRightRadius:
            normalize(4),

        backgroundColor:
        COLORS.blue,
    },


    userMessageText: {
        fontFamily:
            "'Outfit', sans-serif",

        fontWeight: 400,

        fontSize:
            fontScale(13),

        lineHeight:
            `${fontScale(20)}px`,

        color:
        COLORS.white,

        whiteSpace:
            'pre-wrap',

        overflowWrap:
            'anywhere',
    },


    userMessageTime: {
        marginTop:
            spacing(4),

        fontFamily:
            "'Outfit', sans-serif",

        fontWeight: 400,

        fontSize:
            fontScale(9),

        color:
        COLORS.gray,
    },


    /* =========================================================
       INPUT
       ========================================================= */

    inputWrapper: {
        flexShrink: 0,

        paddingLeft:
            spacing(14),

        paddingRight:
            spacing(14),

        paddingTop:
            spacing(8),

        paddingBottom:
            spacing(25),

        backgroundColor:
        COLORS.white,

        borderTop:
            `1px solid ${COLORS.border}`,

        boxSizing:
            'border-box',
    },


    inputContainer: {
        width: '100%',

        minHeight:
            normalize(52),

        display: 'flex',

        flexDirection:
            'row',

        alignItems:
            'center',

        paddingLeft:
            spacing(6),

        paddingRight:
            spacing(6),

        borderRadius:
            normalize(16),

        backgroundColor:
        COLORS.grayLight,

        border:
            `1px solid ${COLORS.border}`,

        boxSizing:
            'border-box',
    },


    attachButton: {
        flexShrink: 0,

        width:
            normalize(38),

        height:
            normalize(38),

        display: 'flex',

        alignItems:
            'center',

        justifyContent:
            'center',

        padding: 0,

        border: 'none',

        borderRadius:
            normalize(11),

        backgroundColor:
        COLORS.blueSoft,

        marginRight:
            spacing(5),

        cursor:
            'pointer',
    },


    input: {
        flex: 1,

        minWidth: 0,

        height:
            normalize(42),

        minHeight:
            normalize(42),

        maxHeight:
            normalize(100),

        padding: 0,

        paddingLeft:
            spacing(8),

        paddingRight:
            spacing(8),

        border: 'none',

        outline: 'none',

        resize:
            'none',

        backgroundColor:
            'transparent',

        fontFamily:
            "'Outfit', sans-serif",

        fontWeight: 400,

        fontSize:
            fontScale(13),

        lineHeight:
            `${fontScale(19)}px`,

        color:
        COLORS.navy,

        overflowY:
            'auto',

        boxSizing:
            'border-box',
    },


    sendButton: {
        flexShrink: 0,

        width:
            normalize(38),

        height:
            normalize(38),

        display: 'flex',

        alignItems:
            'center',

        justifyContent:
            'center',

        padding: 0,

        border: 'none',

        borderRadius:
            normalize(12),

        backgroundColor:
        COLORS.blue,

        marginLeft:
            spacing(5),

        cursor:
            'pointer',

        transition:
            'opacity 0.15s ease',
    },


    sendButtonDisabled: {
        opacity:
            0.35,

        cursor:
            'not-allowed',
    },
};