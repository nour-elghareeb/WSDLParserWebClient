package ne.utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nour
 */
public class MiscUtils {
    public static String getAfterNthMatch(String value, int nth, String splitter){
      
        String[] list = value.split(splitter);
        String newVal = "";
        for (int i=nth;i<list.length;i++ ){
            newVal += list[i]+"/";
        }
        return newVal;
    }
    public static String getBeforeNthMatch(String value, int nth, String splitter){
        
        String[] list = value.split(splitter);
        if (nth >= list.length) return value;
        String[] newList = new String[nth+1];
        for (int i=0;i<nth+1;i++ ){
            newList[i] = list[i];
        }
        return String.join(splitter, newList);
    }
}
