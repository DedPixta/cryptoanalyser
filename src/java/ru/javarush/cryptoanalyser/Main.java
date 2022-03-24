package ru.javarush.cryptoanalyser;


import java.util.Scanner;

public class Main {
    boolean isDecrypt;
    boolean withKey;
    boolean isBruteforce;
    boolean isAnalysis;
    boolean isAnalysisWithAddFile;
    int key;

    String strPathIn;
    String strPathInAddFile;
    String strPathOut;

    public static void main(String[] args) {

        Main instance = new Main();
        instance.dialogue();
        Scrambler scrambler = new Scrambler(instance);
        scrambler.process();
    }


    private void dialogue() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Type number of action or exit to end:%n 1. encrypt %n 2. decrypt");
        String currentLine = scanner.nextLine();
        while (!"exit".equals(currentLine)) {
            if ("1".equals(currentLine) | "encrypt".equals(currentLine)) {
                System.out.println("Enter path for file which you want encrypt in txt format");
                currentLine = scanner.nextLine();

                while (!"exit".equals(currentLine)) {
                    if (Validator.isCorrectInPath(currentLine)) {
                        strPathIn = currentLine;
                        break;
                    } else {
                        System.out.println("File not found or wrong format");
                        currentLine = scanner.nextLine();
                    }
                }

                while (!"exit".equals(currentLine)) {
                    System.out.println("Enter path for decrypted file in txt format");
                    currentLine = scanner.nextLine();

                    if (Validator.isCorrectOutPath(currentLine)) {
                        strPathOut = currentLine;
                        break;
                    } else {
                        System.out.println("File in secure place or wrong format");
                        currentLine = scanner.nextLine();
                    }
                }

                while (!"exit".equals(currentLine)) {
                    System.out.println("Enter integer key for encrypt");
                    currentLine = scanner.nextLine();

                    if (Validator.isNumeric(currentLine)) {
                        key = Integer.parseInt(currentLine);
                        currentLine = "exit";
                        break;
                    } else {
                        System.out.println("Wrong key format");
                        currentLine = scanner.nextLine();
                    }
                }
            }

            if ("2".equals(currentLine) | "decrypt".equals(currentLine)) {
                isDecrypt = true;

                System.out.println("Enter path for encrypted file in txt format");
                currentLine = scanner.nextLine();

                while (!"exit".equals(currentLine)) {

                    if (Validator.isCorrectInPath(currentLine)) {
                        strPathIn = currentLine;
                        break;
                    } else {
                        System.out.println("File not found or wrong format");
                        currentLine = scanner.nextLine();
                    }
                }

                while (!"exit".equals(currentLine)) {
                    System.out.println("Enter path for decrypted file in txt format");
                    currentLine = scanner.nextLine();

                    if (Validator.isCorrectOutPath(currentLine)) {
                        strPathOut = currentLine;
                        break;
                    } else {
                        System.out.println("File in secure place or wrong format");
                    }
                }

                while (!"exit".equals(currentLine)) {
                    System.out.printf("Enter number decryption type or exit to end: " +
                            "%n1. With key" +
                            "%n2. With bruteforce" +
                            "%n3. With statistical analysis");
                    currentLine = scanner.nextLine();

                    if ("1".equals(currentLine)) {
                        withKey = true;

                        System.out.println("Enter integer key for decrypt");
                        while (!"exit".equals(currentLine)) {
                            currentLine = scanner.nextLine();

                            if (Validator.isNumeric(currentLine)) {
                                key = Integer.parseInt(currentLine);
                                currentLine = "exit";
                                break;
                            } else {
                                System.out.println("Wrong key format");
                                currentLine = scanner.nextLine();
                            }
                        }
                    } else if ("2".equals(currentLine)) {
                        isBruteforce = true;
                        currentLine = "exit";
                        break;

                    } else if ("3".equals(currentLine)) {
                        isAnalysis = true;

                        System.out.printf("Do you have an additional file with sample text from the same author?" +
                                "%n1. Yes" +
                                "%n2. No");
                        while (!"exit".equals(currentLine)) {
                            currentLine = scanner.nextLine();
                            if ("1".equals(currentLine)) {
                                while (!"exit".equals(currentLine)) {
                                    System.out.println("Enter path for sample text file in txt format");
                                    currentLine = scanner.nextLine();

                                    if (Validator.isCorrectInPath(currentLine)) {
                                        isAnalysisWithAddFile = true;
                                        strPathInAddFile = currentLine;
                                        currentLine = "exit";
                                        break;
                                    } else {
                                        System.out.println("File not found or wrong format");
                                        currentLine = scanner.nextLine();
                                    }
                                }
                            }else if ("2".equals(currentLine)){
                                currentLine = "exit";
                                break;
                            }
                            else {
                                System.out.println("Wrong answer");
                            }
                        }
                    } else {
                        System.out.println("Wrong answer");
                    }
                }
            }
        }
    }
}