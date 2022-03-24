package ru.javarush.cryptoanalyser;

import java.util.Arrays;

public class CezarInfo {
    private static final char[] ALPHABET;
    private static final int ALPHABET_SIZE;
    private static final double[][] FREQUENCY_RATE_DEFAULT;

    static {
        char[] tempArr = new char[]{
                ' ', '!', '\"', ',', '-', '.', ':', '?', '\\',
                'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'й', 'к',
                'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х',
                'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', 'ё'
        };
        Arrays.sort(tempArr);
        ALPHABET = tempArr;

        ALPHABET_SIZE = ALPHABET.length;

        FREQUENCY_RATE_DEFAULT = new double[][]{{
                175.0, 000.2, 000.1, 018.7, 002.4, 013.6,
                000.9, 001.4, 000.1, 080.1, 015.9, 045.4,
                017.0, 029.8, 084.5, 009.4, 016.5, 073.5,
                012.1, 034.9, 044.0, 032.1, 067.0, 109.7,
                028.1, 047.3, 054.7, 062.6, 026.2, 002.6,
                009.7, 004.8, 014.4, 007.3, 003.6, 000.4,
                019.0, 017.4, 003.2, 006.4, 020.1, 000.4}};
    }

    public static char[] getAlphabet() {
        return ALPHABET;
    }

    public static int getAlphabetSize() {
        return ALPHABET_SIZE;
    }

    public static double[][] getFrequencyRateDefault() {
        return FREQUENCY_RATE_DEFAULT;
    }

}
