package org.sm.utils;

import org.springframework.util.StringUtils;

import java.io.*;
import java.util.*;

public class EgsApplication {
    //    Woman woman1=new Woman(1);
//    Woman woman2=new Woman(2);
//    Woman woman3=new Woman(3);
//    Woman woman4=new Woman(4);
//    Man man1=new Man(1);
//    Man man2=new Man(2);
//    Man man3=new Man(3);
//    Man man4=new Man(4);
//    Woman[] allwoman=new Woman[]{woman1,woman2,woman3,woman4};
    List<Man> allman=null;
    List<Woman>  allwoman=null;
    static String filePath="D:\\zuoye\\SM\\wmmatch.txt";//File address.
    List<String> txtList=null;
    //Initialize the preference list
    public void ReadFile(){
        //Obtain the male list and female list from the TXT file.
        txtList=new ArrayList<String>();
        File file = new File(filePath);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));//Construct a BufferedReader class to read the file
            String s = null;
            while ((s = br.readLine()) != null) {//Read one line at a time using the readLine method
                txtList.add(s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Man> manList=null;
        List<Woman> womanList=null;
        if(allman==null){ //If the list is empty, add a man or woman to the list.
            manList=new ArrayList<Man>();
            womanList=new ArrayList<Woman>();
            int manSize=Integer.valueOf(StringUtils.trimAllWhitespace(txtList.get(0).replace("manNum=","")));//Remove the space to get the size of the number of men.
            int womanSize=Integer.valueOf(StringUtils.trimAllWhitespace(txtList.get(1).replace("womanNum=","")));
            for(int i=1;i<=manSize;i++){
                manList.add(new Man(i));
            }
            for(int i=1;i<=womanSize;i++){
                womanList.add(new Woman(i));
            }
        }else{
            manList=allman;
            womanList=allwoman;
        }

        for(Man man:manList){////Female preference list
            Woman[] preWoman=new Woman[womanList.size()];
            String preStr="man"+man.getCode()+"=";
            for(String txtStr:txtList){
                if(txtStr.startsWith(preStr)){////Check whether the string starts with the specified prefix（preStr）.
                    preStr=txtStr.replace(preStr,"");////Replace the prefix.
                    break;
                }
            }
            String[] preWomanCode=preStr.split(",");////Use "," to split.
            for(int i=0;i<preWomanCode.length;i++){
                preWoman[i]=getWomanInList(womanList,preWomanCode[i]);
            }
            man.setPreferWoman(preWoman);
        }
        for(Woman woman:womanList){
            Man[] preMan=new Man[manList.size()];
            String preStr="woman"+woman.getCode()+"=";
            for(String txtStr:txtList){
                if(txtStr.startsWith(preStr)){
                    preStr=txtStr.replace(preStr,"");
                    break;
                }
            }
            String[] preManCode=preStr.split(",");
            for(int i=0;i<preManCode.length;i++){
                preMan[i]=getManInList(manList,preManCode[i]);
            }
            woman.setPreferMan(preMan);
        }
        allman= manList;
        allwoman=womanList;
    }
    public Man getManInList(List<Man> manList,String manCode){//Get the male code from the list.
        for(Man man:manList){
            if(man.getCode()==Integer.valueOf(manCode)){//If the codes are equal, the male is returned.
                return man;
            }
        }
        return null;
    }
    public Woman getWomanInList(List<Woman> womanList,String womanCode){
        for(Woman man:womanList){
            if(man.getCode()==Integer.valueOf(womanCode)){//If the codes are equal, the female is returned.
                return man;
            }
        }
        return null;
    }
    //Check free men
    public Man findFreedomMan(){
        for(Man man:allman){
            if(man.isFreedom()){
                return man;
            }
        }
        return null;
    }

    //Match
    public void match(Man man,Woman woman){
//        System.out.println(man.getName()+"=========================="+woman.getName());
        man.setPartner(woman);
        man.setFreedom(false);
        woman.setPartner(man);
        woman.setFreedom(false);
    }

    //Search for partners
    public void searchPartner(Man man){
        Woman firstWoman=man.getPreferWoman()[0];
        System.out.println(man.getName()+"=========================="+firstWoman.getName());
        if(!firstWoman.isFreedom()){
            //The most favorite woman is not free, first break up, everyone is single
            Man oldMan=firstWoman.getPartner();
            oldMan.setFreedom(true);
            oldMan.setPartner(null);
            firstWoman.setFreedom(true);
            firstWoman.setPartner(null);
        }
        //The favorite woman is free.
        match(man,firstWoman);//got engaged
        Man[] preferMen=firstWoman.getPreferMan();
        Man[] newPreferMen=new Man[preferMen.length];
        boolean isBack=false;
        for(int i=0;i<preferMen.length;i++){
            if(preferMen[i]==null){
                continue;
            }
            if(isBack){
                Woman[] tempWomen=preferMen[i].getPreferWoman();
                System.out.println(man.getName()+" Deal with the remaining men."+preferMen[i].getName());
                List<Woman> newPreWomen=new ArrayList<Woman>();
                for(int j=0;j<tempWomen.length;j++){
                    //Delete firstWoman
                    if(!firstWoman.equals(tempWomen[j])){
                        newPreWomen.add(tempWomen[j]);
                    }
                }
                Woman[] resultWomen=new Woman[newPreWomen.size()];
                preferMen[i].setPreferWoman(newPreWomen.toArray(resultWomen));
            }else{
                //Used to delete the man behind m
                newPreferMen[i]=preferMen[i];
            }
            if(preferMen[i].equals(man)){
                //The men behind need to deal with
                isBack=true;
            }
        }
        firstWoman.setPreferMan(newPreferMen);
    }



    public static void main(String[] args) {
        EgsApplication egsApplication=new EgsApplication();
        egsApplication.ReadFile();
        while (true){
            //Find single men
            Man freeMan=egsApplication.findFreedomMan();
            if(freeMan!=null){
                //Single male found
                egsApplication.searchPartner(freeMan);
            }else{
                System.out.println("END=====================All men have partners");
                break;
            }
        }
        egsApplication.ReadFile();
        CheckUtil.hasBlockMatch(egsApplication.allman);
        for(Man man:egsApplication.allman){
            System.out.println(man.getName()+"===========Marry=========="+man.getPartner().getName());
        }
    }
}