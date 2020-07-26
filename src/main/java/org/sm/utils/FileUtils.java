package org.sm.utils;

import com.google.common.base.Joiner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


/*
Create a TXT file, enter the number of people, and generate a txt file.
 */
public class FileUtils {
//    static int manNum=6;
//    static int manNum;
//    static int womanNum=6;
//    static int womanNum;
    static String filePath="D:\\zuoye\\SM\\wmmatch.txt";//TXT file address.D:\zuoye\SM

//    public static int getManNum(int i){
//            int manNum = i;
//            return manNum;
//    }
//
//    public static int getWomanNuma(int i){
//        int womanNum = i;
//        return womanNum;
//    }

    public static void createTxt(int n){
        int manNum = n;
        int womanNum = n;
        try {
            File writename = new File(filePath);// Create a relative path. if not, create a new output. txt file
            if(!writename.exists()){
                writename.createNewFile(); // Create new file
            }

            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            StringBuilder sb=new StringBuilder("manNum="+manNum);
            sb.append("\r\n");
            sb.append("womanNum="+womanNum);
            sb.append("\r\n");
            List<Integer> manList=new ArrayList<Integer>();
            for (int i =1;i<=manNum;i++){
                manList.add(i);
            }
            List<Integer> womanList=new ArrayList<Integer>();
            for (int i =1;i<=womanNum;i++){
                womanList.add(i);
            }
            for (int i =0;i<manList.size();i++){
                Collections.shuffle(womanList);
                sb.append("man"+manList.get(i)+"="+ Joiner.on(",").join(womanList));
                sb.append("\r\n");
            }
            Collections.sort(womanList);//rearrange
            for (int i =0;i<womanList.size();i++){
                Collections.shuffle(manList);
                sb.append("woman"+womanList.get(i)+"="+Joiner.on(",").join(manList));
                sb.append("\r\n");
            }
            out.write(sb.toString()); // \r\n means line break
            out.flush(); // Push the contents of the buffer into the file
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the number of people：");
        int n = 0;
//        if (scan.hasNextInt()) {
//            // Determine whether the input is an integer
//            n = scan.nextInt();
//            // Receive integer
//            System.out.println("The number of men and women is：" + n);
//        } else {
//            System.out.println("Please enter an integer!");
//        }
//        System.out.println("The preference list for men and women will be randomly generated.");
        while(true) {
            try {
                n = scan.nextInt();			//If the input is not an integer, an InputMismatchException will be thrown.
                break;								//If it is an integer, exit the while loop.
            }catch(Exception e) {				//This Exception is caught with an Exception.
                System.out.println("What you entered is not an integer, please continue to enter an integer!");
                scan.next();
            }
        }
        System.out.println("The number of men and women is："+n);
        System.out.println("The preference list for men and women will be randomly generated.");

//        getManNum(n);
//        getWomanNuma(n);
        createTxt(n);
    }
}