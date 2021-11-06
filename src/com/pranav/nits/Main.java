package com.pranav.nits;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);

    private static void crc() {
        System.out.print("Simulating CRC(CRC8) error detection and correction\nEnter Dataword->");
        String[] dataWordTokenized=getDataWord(0);
        System.out.println(Arrays.toString(dataWordTokenized) +"\nEnter number of Hops the messsage will travel");
        int hops=getHops();

    }
    private static void hammingCode() {
        System.out.print("Simulating 7-Bit Hamming Code error detection and correction\nEnter Dataword->");
        String[] dataWordTokenized =getDataWord(1);
        System.out.println(Arrays.toString(dataWordTokenized) +"\nEnter number of Hops the messsage will travel");
        int hops=getHops();
    }
    private static String[] getDataWord(int c){
        String temp=input.next();
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
        }
        else if(c == 1)
        {
            int len;
            int start = 0;
            if (temp.length() % 4 != 0) {
                len = (temp.length() / 4) + 1;
            } else {
                len = (temp.length() / 4);
            }
            String[] ret = new String[len];
            StringBuilder j = new StringBuilder();
            for (int i = 0; i < (len * 4); i++) {
                if (i < temp.length()) {
                    j.append(temp.charAt(i));
                } else {
                    j.append(0);
                }


                if (((i + 1) % 4) == 0) {
                    ret[start] = j.toString();
                    j = new StringBuilder();
                    start++;
                }
            }
            return ret;
        }
        return new String[0];
    }
    private static int getHops(){
        int temp=input.nextInt();
        return temp;
    }

    public static void main(String[] args) {

        System.out.println("CRC-8 and 7-Bit Hamming Code Simulating");
        int ch;
        System.out.println("Choose the type of Technique you want to test\n\t1:CRC-8\n\t2:7-Bit Hamming Code");
        ch = input.nextInt();
        switch (ch) {
            case 1: {
                crc();
                break;
            }
            case 2: {
                hammingCode();
                break;
            }
        }
    }
}
