package ru.javarush.cryptoanalyser;

import java.util.Arrays;

import static ru.javarush.cryptoanalyser.CezarInfo.*;

public class Encryptor{

    public static String encodeText(String message, int key) {

        StringBuilder encText = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char currentChar = message.charAt(i);
            int indexOfCurrChar = findInAlphabet(currentChar);
            boolean inAlphabet = indexOfCurrChar >= 0;

            if (inAlphabet) {
                int indexOfNewChar = (indexOfCurrChar + key) % getAlphabetSize();

                if (Character.isUpperCase(currentChar)) {
                    encText.append(Character.toUpperCase(getAlphabet()[indexOfNewChar]));
                } else {
                    encText.append(getAlphabet()[indexOfNewChar]);
                }

            } else {
                encText.append(currentChar);
            }
        }

        return encText.toString();
    }

    private static int findInAlphabet(char ch) {
        char lowerCaseChar = Character.toLowerCase(ch);
        return Arrays.binarySearch(getAlphabet(), lowerCaseChar);
    }
}

