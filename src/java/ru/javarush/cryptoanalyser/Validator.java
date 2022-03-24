package ru.javarush.cryptoanalyser;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int number = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isCorrectInPath(String PathIn) {
        boolean isExists = false;
        boolean isValidFormat = false;

        if (PathIn == null) {
            return false;
        }

        File file = new File(PathIn);

        if (file.isFile())
            isExists = true;

        String format = PathIn.substring(PathIn.length()-4);
        if(".txt".equals(format)){
            isValidFormat = true;
        }

        return isExists && isValidFormat;
    }

    public static boolean isCorrectOutPath(String PathOut) {
        boolean notInSecure = false;
        boolean isValidFormat = false;

        if (PathOut == null) {
            return false;
        }

        String format = PathOut.substring(PathOut.length()-4);
        if(".txt".equals(format)){
            isValidFormat = true;
        }

        String strPattern = "(\\\\Windows)|(\\\\Program Files)";

        Pattern pattern = Pattern.compile(strPattern);
        Matcher matcher = pattern.matcher(PathOut);

        if(!matcher.find()) {
            notInSecure = true;
        }

        return notInSecure && isValidFormat;
    }
    }
