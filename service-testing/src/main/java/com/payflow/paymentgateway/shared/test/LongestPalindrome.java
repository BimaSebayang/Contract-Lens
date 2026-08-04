package com.payflow.paymentgateway.shared.test;


public class LongestPalindrome {

    public static void main(String[] args) {
        System.out.println(longestPalindrome("cabababada"));
    }

    public static String longestPalindrome(String s) {

        if (s == null || s.isEmpty()) {
            return "";
        }

        int start = 0;
        int end = 0;

        for (int i = 0; i < s.length(); i++) {

                int oddLength = expand(s, i, i);

            int evenLength = expand(s, i, i + 1);

            int maxLength = Math.max(oddLength, evenLength);

            if (maxLength > end - start + 1) {

                start = i - (maxLength - 1) / 2;
                end = i + maxLength / 2;
            }
        }

        return s.substring(start, end + 1);
    }

    private static int expand(String s, int left, int right) {

        while (left >= 0 &&
                right < s.length() &&
                s.charAt(left) == s.charAt(right)) {

            left--;
            right++;
        }

        return right - left - 1;
    }

}
