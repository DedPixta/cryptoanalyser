package ru.javarush.cryptoanalyser;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

import static ru.javarush.cryptoanalyser.CezarInfo.*;
import static ru.javarush.cryptoanalyser.CezarInfo.getAlphabetSize;

public class Decryptor {
    public static String decodeTextWithKey(String message, int key) {
        return Encryptor.encodeText(message, ((getAlphabetSize() - key) % getAlphabetSize()));
    }

    public static int getKeyFromBruteforce(Path inputFile) {
        int key;
        int[] counterSpaces = getFrequencyRateOfSpaces(inputFile);
        key = probableOffset(counterSpaces);

        return key;
    }

    public static int getKeyFromAnalyse(Path inputFile, boolean isAnalysisWithAddFile, Path inputAddFile) {
        double[][] frequencyRateOfLetters = getFrequencyRateOfLetters(inputFile);
        double[][] frequencyRateOfLettersStandard;
        double[] chiSquares = new double[getAlphabetSize()];
        int key;

        if (isAnalysisWithAddFile)
            frequencyRateOfLettersStandard = getFrequencyRateOfLetters(inputAddFile, isAnalysisWithAddFile);
        else {
            frequencyRateOfLettersStandard = getFrequencyRateDefault();
        }
        for (int i = 0; i < chiSquares.length; i++) {
            chiSquares[i] = getChiSquare(frequencyRateOfLettersStandard[0], frequencyRateOfLetters[i]);
        }
        key = probableOffset(chiSquares);
        return key;
    }

    private static double getChiSquare(double[] freqOfLettersStandard, double[] freqOfLetters) {
        double chiSquare = 0;
        int testCounter = 0;

        for (int i = 0; i < freqOfLettersStandard.length; i++, testCounter++) {
            double currentFreq = freqOfLetters[i];
            double standardFreq = freqOfLettersStandard[i];

            if (standardFreq < 0.001) continue;

            chiSquare += Math.pow((currentFreq - standardFreq), 2) / standardFreq;
        }
        return chiSquare;
    }

    private static int[] getFrequencyRateOfSpaces(Path inputFile) {
        int sizeOfAlphabet = getAlphabetSize();
        int[] countLetters = new int[sizeOfAlphabet];

        try (BufferedReader reader = Files.newBufferedReader(inputFile, StandardCharsets.UTF_8)) {

            String currentLine = null;

            while ((currentLine = reader.readLine()) != null) {
                for (int i = 0; i < currentLine.length(); i++) {
                    for (int offset = 0; offset < sizeOfAlphabet; offset++) {
                        String decText = decodeTextWithKey(currentLine, offset);

                        char currentChar = decText.charAt(i);
                        if (' ' == currentChar) {
                            countLetters[offset] += 1;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return countLetters;
    }

    private static double[][] getFrequencyRateOfLetters(Path inputFile) {

        return getFrequencyRateOfLetters(inputFile, false);
    }

    private static double[][] getFrequencyRateOfLetters(Path inputFile, boolean isAnalysisWithAddFile) {
        int sizeOfAlphabet = getAlphabetSize();
        int[][] countLetters = new int[sizeOfAlphabet][sizeOfAlphabet];
        double[][] frequencyRateOfLetters = new double[sizeOfAlphabet][sizeOfAlphabet];
        int totalLetters = 0;

        try (BufferedReader reader = Files.newBufferedReader(inputFile, StandardCharsets.UTF_8)) {

            String currentLine = null;

            while ((currentLine = reader.readLine()) != null) {
                for (int i = 0; i < currentLine.length(); i++, totalLetters++) {


                    if (isAnalysisWithAddFile) {
                        char currentChar = currentLine.charAt(i);
                        int indexOfCurrChar = findInAlphabet(currentChar);
                        boolean inAlphabet = indexOfCurrChar >= 0;
                        if (inAlphabet) {
                            countLetters[0][indexOfCurrChar] += 1;
                        }
                    } else {
                        for (int offset = 0; offset < sizeOfAlphabet; offset++) {
                            String decText = decodeTextWithKey(currentLine, offset);

                            char currentChar = decText.charAt(i);
                            int indexOfCurrChar = findInAlphabet(currentChar);
                            boolean inAlphabet = indexOfCurrChar >= 0;
                            if (inAlphabet) {
                                countLetters[offset][indexOfCurrChar] += 1;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < frequencyRateOfLetters.length; i++) {
            for (int j = 0; j < frequencyRateOfLetters[i].length; j++) {
                frequencyRateOfLetters[i][j] = (double) countLetters[i][j] / totalLetters * 1000;
            }
        }
        return frequencyRateOfLetters;
    }

    private static int probableOffset(int[] counterSpaces) {
        int probableOffset = 0;
        for (int offset = 0; offset < counterSpaces.length; offset++) {
            if (counterSpaces[offset] > counterSpaces[probableOffset]) {
                probableOffset = offset;
            }
        }
        return probableOffset;
    }

    private static int probableOffset(double[] chiSquares) {
        int probableOffset = 0;
        for (int offset = 0; offset < chiSquares.length; offset++) {
            if (chiSquares[offset] < chiSquares[probableOffset]) {
                probableOffset = offset;
            }
        }

        return probableOffset;
    }

    private static int findInAlphabet(char ch) {
        char lowerCaseChar = Character.toLowerCase(ch);
        return Arrays.binarySearch(getAlphabet(), lowerCaseChar);
    }
}
