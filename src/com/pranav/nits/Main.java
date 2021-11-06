package com.pranav.nits;

import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);

    private static void crc() {
        System.out.print("Simulating CRC(CRC8) error detection and correction\nEnter Dataword->");
        long[] dataWordTokenized=getDataWord(0);
        for (long x :dataWordTokenized) {
            System.out.print(x+" ");
        }
        System.out.println("\nEnter number of Hops the messsage will travel");
        int hops=getHops();

    }
    private static void hammingCode() {
        System.out.print("Simulating 7-Bit Hamming Code error detection and correction\nEnter Dataword->");
        long dataWord[]=getDataWord(1);
        System.out.println(dataWord+"\nEnter number of Hops the messsage will travel");
        int hops=getHops();
    }
    private static long[] getDataWord(int c){
        String temp=input.next();
        switch (c){
            case 0:
            {
                int len;
                int start=0;
                if(temp.length()%16!=0){
                    len=(temp.length()/16)+1;
                }
                else {
                    len = (temp.length() / 16);
                }
                long[] ret=new long[len];
                long j=0;
                for(int i=0;i<(len*16);i++) {
                    if(i < temp.length()){
                        j += temp.charAt(i)-48;
                    }
                    else {
                        j += 0;
                    }
                    j *= 10;
                    if (((i+1) % 16) == 0) {
                        ret[start] = j;
                        j = 0;
                        start++;
                    }
                }
                return ret;
            }
        }
        return new long[0];
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
