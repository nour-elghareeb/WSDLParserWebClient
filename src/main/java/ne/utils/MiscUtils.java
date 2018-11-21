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
    public static String cleanXML(String value){
        return value.replace("&#62;", ">").replace("&#60;","<");
    }
    public static String getVariableFromPath(String path){
        return splitPrefixes(path.substring(path.lastIndexOf(".") + 1))[1];
    }
    public static String[] splitPrefixes(String value) {

        String[] splitArray = new String[2];
        if (value == null)
            return splitArray;
        else if (!value.contains(":")) {
            splitArray[0] = null;
            splitArray[1] = value;
            return splitArray;
        }
        return value.split(":");
    }
}
