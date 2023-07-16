package com.nazarite.arithmetic;

public class KMP {
    public static int kmp(String text, String pattern) {
        int[] next = getNext(pattern);
        int i = 0, j = 0;
        while (i < text.length() && j < pattern.length()) {
            if (j == -1 || text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j == pattern.length()) {
            return i - j;
        } else {
            return -1;
        }
    }

    public static int[] getNext(String pattern) {
        int[] next = new int[pattern.length()];
        next[0] = -1;
        int i = 0, j = -1;
        while (i < pattern.length()-1) {
            if (j == -1 || pattern.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
                next[i] = j;
            } else {
                j = next[j];
            }
        }
        return next;
    }

    public static void main(String[] args) {
        String text = "BBC ABCDAB ABCDABCDABDE";
        String pattern = "ABCDABD";
        int index = kmp(text, pattern);
        if (index == -1) {
            System.out.println("Pattern not found in the text");
        } else {
            System.out.printf("Pattern found at index: %d", index);
        }
    }
}
