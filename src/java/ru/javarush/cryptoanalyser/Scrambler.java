package ru.javarush.cryptoanalyser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Scrambler {
    boolean isDecrypt;
    boolean withKey;
    boolean isBruteforce;
    boolean isAnalysis;
    boolean isAnalysisWithAddFile;
    int key;

    String strPathIn;
    String strPathInAddFile;
    String strPathOut;

    public Scrambler(Main instance) {
        this.isDecrypt = instance.isDecrypt;
        this.withKey = instance.withKey;
        this.isBruteforce = instance.isBruteforce;
        this.isAnalysis = instance.isAnalysis;
        this.isAnalysisWithAddFile = instance.isAnalysisWithAddFile;
        this.key = instance.key;
        this.strPathIn = instance.strPathIn;
        this.strPathInAddFile = instance.strPathInAddFile;
        this.strPathOut = instance.strPathOut;
    }

    public void process() {

        Path pathIn = Paths.get(strPathIn);
        Path pathInAddFile;
        if (isAnalysisWithAddFile) {
            pathInAddFile = Paths.get(strPathInAddFile);
        } else {
            pathInAddFile = pathIn;
        }
        Path pathOut = Paths.get(strPathOut);

        try (BufferedReader reader = Files.newBufferedReader(pathIn, StandardCharsets.UTF_8);
             BufferedWriter buffWriter = Files.newBufferedWriter(pathOut)) {
            String currentLine = "";
            while (reader.ready()) {
                currentLine = reader.readLine();
                if (!isDecrypt) {
                    currentLine = Encryptor.encodeText(currentLine, key);
                } else {
                    if (isBruteforce) {
                        key = Decryptor.getKeyFromBruteforce(pathIn);
                        isBruteforce = false;
                        withKey = true;
                    }

                    if (isAnalysis) {
                        key = Decryptor.getKeyFromAnalyse(pathIn, isAnalysisWithAddFile, pathInAddFile);
                        isAnalysis = false;
                        withKey = true;
                    }

                    if (withKey) {
                        currentLine = Decryptor.decodeTextWithKey(currentLine, key);
                    }
                }
                buffWriter.write(currentLine);
                if (reader.ready()) {
                    buffWriter.newLine();
                }
            }
        } catch (
                IOException ex) {
            ex.printStackTrace();
        }
    }
}

