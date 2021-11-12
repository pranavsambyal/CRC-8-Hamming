package com.pranav.nits;

import java.math.BigInteger;
import java.util.Scanner;

public class test{

    public static void main(String[] args) {
        Scanner sn=new Scanner(System.in);
		/*System.out.println("Enter your data");
		String binaryData=sn.nextLine();*/
        String binaryData="101010010101001010101001010101001011100100010010111100000010001001";
        System.out.println("Choose the number of hops");
        System.out.println("1. one \n2. two");
        int ch=sn.nextInt();
        int i;
        for(i=0;i+16<=binaryData.length();i=i+16) {
            String sxtBit=binaryData.substring(i,i+16);
            System.out.println("\nThe sixteen bits are "+sxtBit);
            senderCode(sxtBit,ch,sn);
        }
        if(binaryData.length()%16!=0) {
            String bits=binaryData.substring(i,binaryData.length());
            for(int j=bits.length();j<16;j++) {
                bits="0"+bits;
            }
            System.out.println("\nThe sixteen bits are "+bits);
            senderCode(bits, ch, sn);
        }
    }

    private static void senderCode(String sxtBit, int ch, Scanner sn) {
        // TODO Auto-generated method stub
        String divisor="100000111";
        String sxtCopy=sxtBit;
        sxtBit+="00000000";
        String rem=binaryXorDivision(sxtBit, divisor);

        sxtCopy+=rem;
        System.out.println("The remainder is "+rem);
        System.out.println("The codeword is "+sxtCopy);
        System.out.println("Enter the probability of first flip");
        double flip1=sn.nextDouble();
        double randProb=0;
        int arr[]=new int[24];
        for(int i=0;i<24;i++) {
            arr[i]=(int)sxtCopy.charAt(i)-48;
        }
        for(int i=0;i<24;i++) {
            randProb=Math.random();
            if(randProb<=flip1) {
                if(arr[i]==1) {
                    arr[i]=0;
                }else {
                    arr[i]=1;
                }
            }
        }
        if(ch==2) {
            System.out.println("Enter the probability of second flip");
            double flip2=sn.nextDouble();
            for(int i=0;i<24;i++) {
                randProb=Math.random();
                if(randProb<=flip2) {
                    if(arr[i]==1) {
                        arr[i]=0;
                    }else {
                        arr[i]=1;
                    }
                }
            }
        }
        sxtBit="";
        System.out.print("The recieved codeword is ");
        for(int i=0;i<=23;i++) {
            System.out.print(arr[i]);
            sxtBit+=arr[i];
        }System.out.println();
        rem=binaryXorDivision(sxtBit, divisor);
        if(rem.equals("00000000")==false) {
            System.out.println("Discard");
        }else {
            System.out.println("No error");
            System.out.println("The recieced data is "+sxtBit);
        }


    }
    public static String binaryXorDivision(String dividend, String divisor) {
        String temp=dividend.substring(0,divisor.length()-1);
        for(int i=divisor.length()-1;i<dividend.length();i++) {
            temp=temp+dividend.charAt(i);
            if(temp.charAt(0)=='1') {
                temp=xorOp(temp,divisor);
            }else {
                temp=xorOp(temp, "000000000");
            }
            temp=temp.substring(1,temp.length());

        }

        return temp;
    }
    public static String xorOp(String str1,String str2) {
        String res="";
        for(int i=0;i<str1.length();i++) {
            if (str1.charAt(i)==str2.charAt(i)) {
                res+="0";
            }
            else {
                res+="1";
            }
        }
        return res;
    }

}