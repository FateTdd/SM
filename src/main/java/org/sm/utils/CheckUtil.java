package org.sm.utils;

import java.util.List;

public class CheckUtil {
    //Whether to include blocking pairs
    public static boolean hasBlockMatch(List<Man> men){
        for(Man man:men){
            Woman currentWomen=man.getPartner();//Current match woman
            Woman[] prefWomen=man.getPreferWoman();//Female list
            for(Woman tempWoman:prefWomen){
                if(tempWoman.equals(currentWomen)){
                    break;
                }
                Man[]  prefMen=tempWoman.getPreferMan();
                Man tempPrefMan=tempWoman.getPartner();//Prefer a woman's existing partner
                for (Man tempMan:prefMen){
                    if(tempMan.equals(tempPrefMan)){
                        break;
                    }
                    if(tempMan.equals(man)){
                        //Blocking pairs found.
                        System.out.println("Block detection completed. There are blocking pairs："+man.getName()+""+tempWoman.getName());
                        return true;
                    }
                }
            }
        }
        System.out.println("Block detection completed, no block pairs.");
        return false;
    }
}