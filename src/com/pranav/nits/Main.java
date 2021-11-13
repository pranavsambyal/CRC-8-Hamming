package com.pranav.nits;

import java.util.*;

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";

    static Scanner input = new Scanner(System.in);

    private static void crc() {
        System.out.print(ANSI_BLUE+"Simulating CRC(CRC8) error detection and correction\n"+ANSI_RESET+"Choose the Following Options:\n1:Enter Dataword\n2:Initlize with random Dataword of 16-Bits\n->");
        int ch = input.nextInt();
        String[] dataWordTokenized = new String[]{};
        switch (ch) {
            case 1 -> {
                dataWordTokenized = getDataWord(0);
            }
            case 2 -> {
                dataWordTokenized = randomDataWord();
            }
        }
        System.out.println(Arrays.toString(dataWordTokenized) + "\nEnter number of Hops the message will travel[ Currently only 2 hops are implemented ]");
        int hops = input.nextInt();
        String CRC8Divisor = "100000111";
        System.out.println("Using Std CRC-8 divisor x8+x2+x+1");
        String[] codeWord = dataWordTokenized;
        String[] codeWord2 = new String[codeWord.length];
        System.out.println("For Hop-1:-\n------------------------------");
        for (int i = 0; i < codeWord.length; i++) {
            System.out.print("Remainder:\t");
            String dataword = codeWord[i];
            String temp = dataword;
            dataword += "00000000";
            System.out.println(binaryXorDivision(dataword, CRC8Divisor));
            temp += binaryXorDivision(dataword, CRC8Divisor);
            System.out.print("CodeWord: ");
            System.out.println(temp);
            System.out.println("Enter Prob for Bit flip");
            double prob = input.nextDouble();
            temp = bitFlip(temp, prob);
            codeWord2[i] = temp.substring(0, temp.length() - 8);
            System.out.println(ANSI_CYAN+"Received CodeWord:    " + temp);
            String tempReceiver = binaryXorDivision(temp, CRC8Divisor);
            if (Objects.equals(tempReceiver, "00000000")) {
                System.out.println(ANSI_GREEN+"No Error In transmission\nWooHoo!"+ANSI_RESET);
            } else {
                System.out.println(ANSI_RED +"Error Detected!\nSend again, Discard!"+ANSI_RESET);
            }
        }
        if (hops == 2) {
            System.out.println("For Hop-2:-\n------------------------------");
            for (String s : codeWord2) {
                System.out.print("Remainder:\t");
                String x = s;
                String temp = x;
                x += "00000000";
                System.out.println(binaryXorDivision(x, CRC8Divisor));
                temp += binaryXorDivision(x, CRC8Divisor);
                System.out.print("CodeWord: ");
                System.out.println(temp);
                System.out.println("Enter Prob for Bit flip");
                double prob = input.nextDouble();
                temp = bitFlip(temp, prob);
                System.out.println(ANSI_CYAN+"Received CodeWord:    " + temp);
                String tempReceiver = binaryXorDivision(temp, CRC8Divisor);
                if (Objects.equals(tempReceiver, "00000000")) {
                    System.out.println(ANSI_GREEN+"No Error In transmission\nWooHoo!"+ANSI_RESET);
                } else {
                    System.out.println(ANSI_RED +"Error Detected!\nSend again, Discard!"+ANSI_RESET);
                }
            }
        }
    }

    private static void hammingCode() {
        System.out.print(ANSI_BLUE+"Simulating 7-Bit Hamming Code error detection and correction"+ANSI_RESET+"\nEnter Dataword->");
        String[] DataWords = getDataWord(1);
        for (String dataWord : DataWords) {
            System.out.println("\nDataword:"+dataWord);
            senderCode(dataWord);
        }

    }

    private static void senderCode(String dataword) {

        int[] codeword = new int[8];
        codeword[3] = (int) dataword.charAt(3) - 48;
        codeword[5] = (int) dataword.charAt(2) - 48;
        codeword[6] = (int) dataword.charAt(1) - 48;
        codeword[7] = (int) dataword.charAt(0) - 48;
        if ((codeword[3] + codeword[5] + codeword[7]) % 2 == 0) {
            codeword[1] = 0;
        } else {
            codeword[1] = 1;
        }
        if ((codeword[3] + codeword[6] + codeword[7]) % 2 == 0) {
            codeword[2] = 0;
        } else {
            codeword[2] = 1;
        }
        if ((codeword[5] + codeword[6] + codeword[7]) % 2 == 0) {
            codeword[4] = 0;
        } else {
            codeword[4] = 1;
        }

        System.out.print(ANSI_PURPLE+"Transmitted CodeWord: ");
        for (int i = 7; i >0; i--) {
            System.out.print(codeword[i]);
        }
        System.out.println(ANSI_RESET);
        System.out.println(ANSI_YELLOW+"Enter the probability of an error to occur"+ANSI_RESET);
        double p = input.nextFloat();

        double randProb = Math.random();

        int randBitPos = 0;
        while (randBitPos < 1 || randBitPos > 7) {
            randBitPos = (int) Math.ceil(Math.random() * 10);

        }

        if (randProb <= p) {
            if (codeword[randBitPos] == 1) {
                codeword[randBitPos] = 0;
            } else {
                codeword[randBitPos] = 1;
            }
        }
        System.out.print(ANSI_CYAN+"Received CodeWord:    ");
        for (int i = 7; i > 0; i--) {
            System.out.print(codeword[i]);
        }
        System.out.println(ANSI_RESET);

        String parityCode = "";
        if ((codeword[1] + codeword[3] + codeword[5] + codeword[7]) % 2 == 0) {
            parityCode += "0";
        } else {
            parityCode += "1";
        }
        if ((codeword[2] + codeword[3] + codeword[6] + codeword[7]) % 2 == 0) {
            parityCode = "0" + parityCode;
        } else {
            parityCode = "1" + parityCode;
        }
        if ((codeword[4] + codeword[5] + codeword[6] + codeword[7]) % 2 == 0) {
            parityCode = "0" + parityCode;
        } else {
            parityCode = "1" + parityCode;
        }

        int decimalValue = Integer.parseInt(parityCode, 2);
        if (decimalValue == 0) {
            System.out.println(ANSI_GREEN+"No error found"+ANSI_RESET);
        } else {
            System.out.println(ANSI_RED+"The error is found at position " + decimalValue+ANSI_RESET);
            if (codeword[decimalValue] == 1) {
                codeword[decimalValue] = 0;
            } else {
                codeword[decimalValue] = 1;
            }
            System.out.print(ANSI_BLUE+"The corrected codeword is ");
            for (int i = 7; i > 0; i--) {
                System.out.print(codeword[i]);
            }
            System.out.println(ANSI_RESET);
        }

    }


    private static String[] getDataWord(int c) {
        String temp = input.next();
        if (c == 0) {
            int len;
            int start = 0;
            if (temp.length() % 16 != 0) {
                len = (temp.length() / 16) + 1;
            } else {
                len = (temp.length() / 16);
            }
            String[] ret = new String[len];
            StringBuilder j = new StringBuilder();
            for (int i = 0; i < (len * 16); i++) {
                if (i < temp.length()) {
                    j.append(temp.charAt(i));
                } else {
                    j.append(0);
                }
                if (((i + 1) % 16) == 0) {
                    ret[start] = j.toString();
                    j = new StringBuilder();
                    start++;
                }
            }
            return ret;
        }  else if (c == 1) {
            int len = (int) Math.ceil(((double) temp.length()) / 4);
            String[] ret = new String[len];
            int j = 0;
            for (int i = 0; i + 4 <= temp.length(); i = i + 4) {
                ret[j] = temp.substring(i, i + 4);
                j++;
            }
            if (temp.length() % 4 != 0) {
                String bits = temp.substring(((len - 1) * 4));
                for (int i = bits.length(); i < 4; i++) {
                    bits =bits+"0";
                }
                ret[j] = bits;
            }
            return ret;
        }
        return new String[0];
    }

    public static String bitFlip(String str, double prob) {
        char[] crs = new char[str.length()];
        str.getChars(0, str.length(), crs, 0);
        Random rand = new Random(System.currentTimeMillis());
        double mul = rand.nextFloat();
        System.out.println(ANSI_YELLOW+"Given Prob=>" + prob + "\nRandom Prob=>" + mul);
        prob = prob * mul;
        System.out.println("Final Prob=> Given Prob X Random Prob =>" + prob+ANSI_RESET);
        if (prob < 0.3 && prob > 0.2) {
            for (int i = 0; i < crs.length; i++) {
                if (crs[i] == '1') {
                    crs[i] = '0';
                    break;
                }

            }
        } else if (prob > 0.3 && prob < 0.5) {
            int flag = 0;
            for (int i = 0; i < crs.length; i++) {
                if (crs[i] == '1') {
                    crs[i] = '0';
                    flag++;
                    if (flag == 2) {
                        break;
                    }
                }
            }
        } else if (prob < 0.6 && prob > 0.5) {
            int flag = 0;
            for (int i = 0; i < crs.length; i++) {
                if (crs[i] == '1') {
                    crs[i] = '0';
                    flag++;
                    if (flag == 3) {
                        break;
                    }
                } else if (crs[i] == '0') {
                    crs[i] = '1';
                    flag++;
                    if (flag == 3) {
                        break;
                    }
                }
            }
        } else if (prob < 0.8 && prob > 0.6) {
            int flag = 0;
            for (int i = 0; i < crs.length; i++) {
                if (crs[i] == '1') {
                    crs[i] = '0';
                    flag++;
                    if (flag == 4) {
                        break;
                    }
                } else if (crs[i] == '0') {
                    crs[i] = '1';
                    flag++;
                    if (flag == 4) {
                        break;
                    }
                }
            }
        } else if (prob < 1 && prob > 0.8) {
            int flag = 0;
            for (int i = 0; i < crs.length; i++) {
                if (crs[i] == '1') {
                    crs[i] = '0';
                    flag++;
                    if (flag == 6) {
                        break;
                    }
                } else if (crs[i] == '0') {
                    crs[i] = '1';
                    flag++;
                    if (flag == 6) {
                        break;
                    }
                }
            }
        }
        StringBuilder ret = new StringBuilder();
        for (char c : crs) {
            ret.append(c);
        }
        System.out.println(ANSI_PURPLE+"Transmitted CodeWord: " + str+ANSI_RESET);
        return ret.toString();
    }

    private static String[] randomDataWord() {
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder str = new StringBuilder();
        String[] ret;
        for (int i = 0; i < 16; i++) {
            int w = rand.nextInt(2);
            if (w == 0) {
                str.append("0");
            } else if (w == 1) {
                str.append("1");
            }

        }
        ret = new String[]{str.toString()};
        return ret;

    }

    public static String binaryXorDivision(String dividend, String divisor) {
        String temp = dividend.substring(0, divisor.length() - 1);
        for (int i = divisor.length() - 1; i < dividend.length(); i++) {
            temp = temp + dividend.charAt(i);
            if (temp.charAt(0) == '1') {
                temp = xor(temp, divisor);
            } else {
                temp = xor(temp, "000000000");
            }
            temp = temp.substring(1);
        }
        return temp;
    }

    public static String xor(String str1, String str2) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i) == str2.charAt(i)) {
                result.append("0");
            } else {
                result.append("1");
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {

        boolean loop=true;
        while(loop) {
            System.out.println("CRC-8 and 7-Bit Hamming Code Simulating");
            int ch;
            System.out.println("Choose the type of Technique you want to test\n\t1:CRC-8\n\t2:7-Bit Hamming Code");
            ch = input.nextInt();
            switch (ch) {
                case 1 -> {
                    crc();
                    loop=false;
                }
                case 2 -> {
                    hammingCode();
                    loop=false;
                }
                default -> {
                    System.out.println(ANSI_RED + "Choose correct option" + ANSI_RESET);
                }
            }
        }
        System.out.println(ANSI_CYAN+"Bye, Exiting "+"\uD83D\uDC4B"+ANSI_RESET);
    }
}
