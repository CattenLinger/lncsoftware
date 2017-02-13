package cn.lncsa.common;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by catten on 16/2/9.
 */
public class StringTools {
    public static List<String> splitTags(String tags){
        return (tags == null || tags.equals("")) ? null : Arrays.asList(tags.split(";"));
    }

    public static String listTags(List<String> tags){
        StringBuilder result = new StringBuilder();
        if(tags == null) return "";
        for(String s : tags) result.append(s).append(";");
        return result.toString();
    }

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public static String convertDate(Date date){
        return simpleDateFormat.format(date);
    }

}
