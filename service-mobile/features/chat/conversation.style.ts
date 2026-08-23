import {
    StyleSheet,
} from 'react-native';

export const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#F8FAFC',
    },

    /* ================= HEADER ================= */

    header: {
        backgroundColor: '#3531C9',
        paddingTop: 40,
        paddingBottom: 15,
        borderBottomLeftRadius: 28,
        borderBottomRightRadius: 28,
    },

    headerContent: {
        flexDirection: 'row',
        alignItems: 'center',
        paddingHorizontal: 24,
    },

    backButton: {
        width: 40,
        height: 40,
        backgroundColor: '#FFFFFF',
        borderRadius: 16,
        justifyContent: 'center',
        alignItems: 'center',
        marginRight: 25,
    },

    headerAvatar: {
        width: 60,
        height: 60,
        borderRadius: 20,
        marginRight: 16,
    },

    profileContainer: {
        justifyContent: 'center',
    },

    nameContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        gap: 8,
    },

    profileName: {
        color: '#FFFFFF',
        fontSize: 18,
        fontWeight: '800',
    },

    verifiedBadge: {
        width: 22,
        height: 22,
        borderRadius: 11,
        backgroundColor: '#7C7CFF',
        justifyContent: 'center',
        alignItems: 'center',
    },

    profileSubtitle: {
        color: '#E0E7FF',
        fontSize: 11,
        marginTop: 4,
    },

    onlineContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        marginTop: 8,
        gap: 8,
    },

    onlineDot: {
        width: 8,
        height: 8,
        borderRadius: 10,
        backgroundColor: '#22C55E',
    },

    onlineText: {
        color: '#FFFFFF',
        fontSize: 9,
    },

    /* ================= CONTENT ================= */

    scrollView: {
        flex: 1,
    },

    scrollContent: {
        paddingHorizontal: 20,
        paddingTop: 20,
    },

    /* ================= DATE ================= */

    dateContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        gap: 12,
        marginTop: 8,
        marginBottom: 28,
    },

    dateLine: {
        flex: 1,
        height: 1,
        backgroundColor: '#E2E8F0',
    },

    dateText: {
        color: '#4338CA',
        backgroundColor: '#EEF2FF',
        paddingHorizontal: 18,
        paddingVertical: 9,
        borderRadius: 18,
        fontSize: 9,
        fontWeight: '600',
    },

    /* ================= INITIAL MESSAGE ================= */

    messageRow: {
        flexDirection: 'row',
        alignItems: 'flex-start',
    },

    messageAvatar: {
        width: 58,
        height: 58,
        borderRadius: 16,
        marginRight: 12,
    },

    messageContent: {
        flex: 1,
    },

    botMessage: {
        backgroundColor: '#FFFFFF',
        borderRadius: 24,
        borderTopLeftRadius: 6,
        padding: 24,
        shadowColor: '#64748B',
        shadowOpacity: 0.08,
        shadowRadius: 12,
        elevation: 3,
    },

    botMessageTitle: {
        color: '#17203A',
        fontWeight: '800',
        marginBottom: 18,
        fontSize: 14,
        lineHeight: 18,
    },

    botMessageText: {
        color: '#334155',
        fontSize: 12,
        lineHeight: 18,
    },

    divider: {
        height: 1,
        backgroundColor: '#E2E8F0',
        marginTop: 20,
        marginBottom: 12,
    },

    feedbackContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        gap: 20,
    },

    rightanswer: {
        marginTop: 5,
        color: '#64748B',
        fontSize: 8,
    },

    feedbackButton: {
        padding: 4,
    },

    messageTime: {
        marginTop: 12,
        color: '#64748B',
        fontSize: 10,
    },

    /* ================= USER MESSAGE ================= */

    userMessageRow: {
        alignItems: 'flex-end',
        marginTop: 20,
    },

    userMessage: {
        maxWidth: '80%',
        backgroundColor: '#4F46E5',
        paddingHorizontal: 20,
        paddingVertical: 14,
        borderRadius: 22,
        borderBottomRightRadius: 6,
    },

    userMessageText: {
        color: '#FFFFFF',
        fontSize: 12,
        lineHeight: 18,
    },

    /* ================= AI MESSAGE ================= */

    aiMessageRow: {
        flexDirection: 'row',
        alignItems: 'flex-start',
        marginTop: 20,
    },

    aiMessageAvatar: {
        width: 42,
        height: 42,
        borderRadius: 14,
        marginRight: 12,
    },

    aiMessage: {
        flex: 1,
        backgroundColor: '#FFFFFF',
        borderRadius: 20,
        borderTopLeftRadius: 6,
        paddingHorizontal: 18,
        paddingVertical: 14,
        shadowColor: '#64748B',
        shadowOpacity: 0.06,
        shadowRadius: 10,
        elevation: 2,
    },

    aiMessageText: {
        color: '#334155',
        fontSize: 13,
        lineHeight: 20,
    },

    /* ================= INPUT ================= */

    inputWrapper: {
        paddingTop: 12,
        paddingBottom: 60,
        paddingHorizontal: 10,
        backgroundColor: '#F8FAFC',
        flexShrink: 0,
    },

    inputContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        minHeight: 72,
        backgroundColor: '#FFFFFF',
        borderRadius: 36,
        paddingLeft: 8,
        paddingRight: 8,
        shadowColor: '#64748B',
        shadowOpacity: 0.12,
        shadowRadius: 18,
        elevation: 6,
    },

    attachButton: {
        width: 50,
        height: 50,
        borderRadius: 28,
        backgroundColor: '#EEF2FF',
        justifyContent: 'center',
        alignItems: 'center',
    },

    input: {
        flex: 1,
        fontSize: 15,
        lineHeight: 20,
        color: '#17203A',
        paddingHorizontal: 20,
        textAlignVertical: 'center',
        maxHeight: 100,
    },

    sendButton: {
        width: 50,
        height: 50,
        borderRadius: 25,
        backgroundColor: '#4338CA',
        justifyContent: 'center',
        alignItems: 'center',
    },

    sendButtonDisabled: {
        opacity: 0.5,
    },

    claraThinkingText: {
        fontSize: 13,
        fontWeight: '500',
        color: '#64748B',
        fontStyle: 'italic',
    },
});