package com.pranav.nits;

import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String s=input.next();
        int[] lst=new int[s.length()];
        for (int i = 0; i <s.length() ; i++) {
            lst[i]=s.charAt(i)-48;
        }
        for (int i = 0; i <s.length() ; i++) {
            System.out.print(lst[i]+" ");
        }
        System.out.println("help");
    }

}
