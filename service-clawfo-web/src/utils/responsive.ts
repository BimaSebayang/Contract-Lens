/**
 * =========================================================
 * CLAwfo Responsive Utility
 * =========================================================
 *
 * Base design width:
 * 390px
 *
 * Web version dari responsive utility
 * yang digunakan pada React Native CLAwfo.
 */


/**
 * Base design width.
 *
 * Kita menggunakan 390px sebagai ukuran
 * referensi desain.
 */
export const BASE_WIDTH = 390;


/**
 * Mendapatkan ukuran layar saat ini.
 */
export const getScreenWidth = (): number => {
    return window.innerWidth;
};


export const getScreenHeight = (): number => {
    return window.innerHeight;
};


/**
 * Menghasilkan faktor scaling berdasarkan
 * lebar layar.
 *
 * Contoh:
 *
 * 360px → sekitar 0.92
 * 390px → 1.00
 * 430px → sekitar 1.10
 *
 * Scaling dibatasi agar UI tidak menjadi
 * terlalu kecil atau terlalu besar pada
 * device ekstrem.
 */
export const getScale = (): number => {
    const width = getScreenWidth();

    return Math.min(
        Math.max(
            width / BASE_WIDTH,
            0.90,
        ),
        1.10,
    );
};


/**
 * Full scaling.
 *
 * Cocok untuk ukuran yang memang ingin
 * mengikuti ukuran layar.
 *
 * Contoh:
 *
 * scale(40)
 * scale(60)
 * scale(100)
 */
export const scale = (
    size: number,
): number => {
    return size * getScale();
};


/**
 * Moderate scaling.
 *
 * Lebih aman untuk sebagian besar UI
 * karena tidak melakukan scaling sebesar
 * scale().
 *
 * factor:
 *
 * 0   = tidak scaling
 * 0.5 = scaling sedang
 * 1   = full scaling
 */
export const moderateScale = (
    size: number,
    factor: number = 0.5,
): number => {
    const scaledSize = scale(size);

    return (
        size +
        (scaledSize - size) * factor
    );
};


/**
 * Scaling untuk font.
 *
 * Font menggunakan scaling yang lebih kecil
 * agar teks tidak tiba-tiba menjadi terlalu
 * besar pada viewport yang lebih lebar.
 */
export const fontScale = (
    size: number,
): number => {
    return moderateScale(
        size,
        0.35,
    );
};


/**
 * Membulatkan ukuran ke pixel terdekat.
 *
 * React Native menggunakan PixelRatio.
 *
 * Pada Web kita cukup menggunakan
 * Math.round().
 */
export const normalize = (
    size: number,
): number => {
    return Math.round(
        moderateScale(size),
    );
};


/**
 * Shortcut untuk spacing.
 *
 * Contoh:
 *
 * spacing(16)
 * spacing(24)
 */
export const spacing = (
    size: number,
): number => {
    return normalize(size);
};


/**
 * Shortcut untuk radius.
 *
 * Radius menggunakan moderate scaling
 * agar tidak terlalu berubah pada viewport
 * yang lebih besar atau lebih kecil.
 */
export const radius = (
    size: number,
): number => {
    return Math.round(
        moderateScale(
            size,
            0.4,
        ),
    );
};


/**
 * Ukuran icon.
 *
 * Icon menggunakan moderate scaling
 * supaya tetap konsisten antar viewport.
 */
export const iconSize = (
    size: number,
): number => {
    return Math.round(
        moderateScale(
            size,
            0.35,
        ),
    );
};