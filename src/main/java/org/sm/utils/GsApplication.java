package org.sm.utils;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GsApplication {
    List<Man> allman=null;
    List<Woman>  allwoman=null;
    static String filePath="D:\\zuoye\\SM\\wmmatch.txt";
    List<String> txtList=null;
    //Initialize the preference list
    public void init(){
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
        if(allman==null){
            manList=new ArrayList<Man>();
            womanList=new ArrayList<Woman>();
            int manSize=Integer.valueOf(StringUtils.trimAllWhitespace(txtList.get(0).replace("manNum=","")));
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

        for(Man man:manList){
            Woman[] preWoman=new Woman[womanList.size()];
            String preStr="man"+man.getCode()+"=";
            for(String txtStr:txtList){
                if(txtStr.startsWith(preStr)){
                    preStr=txtStr.replace(preStr,"");
                    break;
                }
            }
            String[] preWomanCode=preStr.split(",");
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
    public Man getManInList(List<Man> manList,String manCode){
        for(Man man:manList){
            if(man.getCode()==Integer.valueOf(manCode)){
                return man;
            }
        }
        return null;
    }
    public Woman getWomanInList(List<Woman> womanList,String womanCode){
        for(Woman man:womanList){
            if(man.getCode()==Integer.valueOf(womanCode)){
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
    //Search for partners
    public void searchPartner(Man man,List<Woman> womanTotal){
        for(Woman tempWoman:man.getPreferWoman()){
            if(tempWoman.isFreedom()){
                //Freedom.Can be together
                match(man,tempWoman);
                break;
            }else{
                //Not free.To compare
                Man currentMan=tempWoman.getPartner();
                int manOrder=0;
                int currentOrder=0;
                for(int i=0;i<tempWoman.getPreferMan().length;i++){
                    if(tempWoman.getPreferMan()[i].equals(man)){
                        manOrder=i;
                    }
                    if(tempWoman.getPreferMan()[i].equals(currentMan)){
                        currentOrder=i;
                    }
                }
                if(manOrder<currentOrder){
                    //Men and women are more compatible. Change
                    match(man,tempWoman);
                    break;
                }
            }
        }
    }

    //Match
    public void match(Man man,Woman woman){
        if(woman.isFreedom()){
            //Freedom.together
            man.setPartner(woman);
            man.setFreedom(false);
            woman.setPartner(man);
            woman.setFreedom(false);
        }else{
            //Not free.In a boyfriend
            man.setPartner(woman);
            man.setFreedom(false);
            Man preMan=woman.getPartner();
            preMan.setFreedom(true);
            preMan.setPartner(null);
            woman.setPartner(man);
        }

    }
    public static void main(String[] args) {
        GsApplication gsApplication=new GsApplication();
        gsApplication.init();
        while (true){
            Man freeMan=gsApplication.findFreedomMan();
            if(freeMan!=null){
                gsApplication.searchPartner(freeMan,gsApplication.allwoman);
            }else{
                System.out.println("END=====================All the men have partners");
                break;
            }
        }
        gsApplication.init();
        CheckUtil.hasBlockMatch(gsApplication.allman);
        for(Man man:gsApplication.allman){
            System.out.println(man.getName()+"===========Marry=========="+man.getPartner().getName());
        }
    }
}