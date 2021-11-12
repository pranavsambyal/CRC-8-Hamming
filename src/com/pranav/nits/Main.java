package com.pranav.nits;

import java.util.*;

public class Main {
    static Scanner input = new Scanner(System.in);

    private static void crc() {
        System.out.print("Simulating CRC(CRC8) error detection and correction\nChoose the Following Options:\n1:Enter Dataword\n2:Initlize with random Dataword of 16-Bits\n->");
        int ch= input.nextInt();
        ArrayList<String> dataWord = new ArrayList<String>();;
        String[] dataWordTokenized=new String[]{};
        switch (ch) {
            case 1: {
                dataWordTokenized = getDataWord(0);
                break;
            }
            case 2: {
                dataWordTokenized = randomDataWord();
            }
        }
        System.out.println(Arrays.toString(dataWordTokenized) +"\nEnter number of Hops the message will travel[ Currently only 2 hops are implemented ]");
        int hops=getHops();
        String CRC8Divisor="100000111";
        System.out.println("Using Std CRC-8 divisor x8+x2+x+1");
        String[] codeWord=dataWordTokenized;
        for (int i = 0; i <codeWord.length ; i++) {
            System.out.print("Remainder:\t");
            String x=codeWord[i];
            String temp=x;
            x+="00000000";
            System.out.println(binaryXorDivision(x,CRC8Divisor));
            temp+=binaryXorDivision(x,CRC8Divisor);
            System.out.print("CodeWord: ");
            dataWord.add(temp);
            System.out.println(temp);
            System.out.println("Enter Prob for Bit flip");
            double prob=input.nextDouble();
            temp=bitFlip(temp,prob);
            System.out.println("Received CodeWord: "+temp);
            String tempReceiver=binaryXorDivision(temp,CRC8Divisor);
            if(Objects.equals(tempReceiver, "00000000"))
            {
                System.out.println("No Error In transmission");
            }else{
                System.out.println("Error Detected send again");
            }
        }
    }
    private static void hammingCode() {
        System.out.print("Simulating 7-Bit Hamming Code error detection and correction\nEnter Dataword->");
        String[] dataWordTokenized =getDataWord(1);
        System.out.println(Arrays.toString(dataWordTokenized) +"\nEnter number of Hops the message will travel");
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

    public static String bitFlip(String str, double prob) {
        char[] crs = new char[str.length()];
        str.getChars(0,str.length(),crs,0);
        Random rand = new Random(System.currentTimeMillis());
        double mul=rand.nextFloat();
        System.out.println("Given Prob=>"+prob+"\nRandom Prob=>"+mul);
        prob=prob*mul;
        System.out.println("Final Prob=> Given Prob X Random Prob =>"+prob);
        System.out.println("");
        if(prob<0.3&&prob>0.2){
            for (int i = 0; i <crs.length ; i++) {
                if(crs[i]=='1')
                {
                    crs[i]='0';
                    break;
                }

            }
        }
        else if(prob>0.3&&prob<0.5)
        {
            int flag=0;
            for (int i = 0; i <crs.length ; i++) {
                if(crs[i]=='1')
                {
                    crs[i]='0';
                    flag++;
                    if(flag==2){
                    break;
                    }
                }
            }
        }
        else if(prob<0.6&&prob>0.5)
        {
            int flag=0;
            for (int i = 0; i <crs.length ; i++) {
                if(crs[i]=='1')
                {
                    crs[i]='0';
                    flag++;
                    if(flag==3){
                        break;
                    }
                }
                else if(crs[i]=='0')
                {
                    crs[i]='1';
                    flag++;
                    if(flag==3){
                        break;
                    }
                }
            }
        }
        else if(prob<0.8&&prob>0.6)
        {
            int flag=0;
            for (int i = 0; i <crs.length ; i++) {
                if(crs[i]=='1')
                {
                    crs[i]='0';
                    flag++;
                    if(flag==4){
                        break;
                    }
                }
                else if(crs[i]=='0')
                {
                    crs[i]='1';
                    flag++;
                    if(flag==4){
                        break;
                    }
                }
            }
        }
        else if(prob<1&&prob>0.8)
        {
            int flag=0;
            for (int i = 0; i <crs.length ; i++) {
                if(crs[i]=='1')
                {
                    crs[i]='0';
                    flag++;
                    if(flag==6){
                        break;
                    }
                }
                else if(crs[i]=='0')
                {
                    crs[i]='1';
                    flag++;
                    if(flag==6){
                        break;
                    }
                }
            }
        }
        StringBuilder ret = new StringBuilder();
        for (char c:crs) {
            ret.append(c);
        }
        return ret.toString();
    }
    private static String[] randomDataWord(){
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder str= new StringBuilder();
        String[] ret;
        for(int i=0;i<16;i++) {
            int w=rand.nextInt(2);
            if(w==0){
                str.append("0");
            }
            else if(w==1){
                str.append("1");
            }

        }
        ret= new String[]{str.toString()};
        return ret;

    }
    private static int getHops(){
        int temp=input.nextInt();
        return temp;
    }
    public static String binaryXorDivision(String dividend, String divisor) {
        String temp=dividend.substring(0,divisor.length()-1);
        for(int i=divisor.length()-1;i<dividend.length();i++) {
            temp=temp+dividend.charAt(i);
            if(temp.charAt(0)=='1') {
                temp=xor(temp,divisor);
            }else {
                temp=xor(temp, "000000000");
            }
            temp=temp.substring(1);
        }
        return temp;
    }
    public static String xor(String str1,String str2) {
        StringBuilder res= new StringBuilder();
        for(int i=0;i<str1.length();i++) {
            if (str1.charAt(i)==str2.charAt(i)) {
                res.append("0");
            }
            else {
                res.append("1");
            }
        }
        return res.toString();
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
