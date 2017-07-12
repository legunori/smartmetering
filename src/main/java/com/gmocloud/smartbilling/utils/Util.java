package com.gmocloud.smartbilling.utils;

import java.util.*;

/**
 * Created by Yoichi Masumoto@Valsys, inc. on 2016/10/08.
 */
public class Util {
    public static String[] wordsToParams(String words) {
        HashSet<String> set = new LinkedHashSet<>();
        if(words!=null) {
            //大文字スペースを小文字1文字へ変換しTrim
            String str = words.replaceAll("　", " ").trim();
            if(str.length() > 0) {
                String[] splitStr = str.split(" +", 8);//配列に切り分ける Max8

                set.addAll(Arrays.asList(splitStr)); //重複文字を削除

            }
        }
        return set.toArray(new String[set.size()]);
    }
//<span class='ward-highlight text-nowrap'>
    //
    public static final String startTag = "<span class='ward-highlight text-nowrap'>";
    public static final String endTag = "</span>";
    public static String insertTagWithWords(String src, String[] words) {
        if(src==null||src.trim().isEmpty()) return src;

        StringBuilder sb = new StringBuilder(src);

        for(String s: words) {
            if(s==null||s.isEmpty()||s.equals("%")) continue;
            int index = 0;
            String word = s.replaceAll("%", " ").trim();
            while(true) {
                index = sb.indexOf(word, index);
                if(index<0) break;
                int wordLength = word.length();
                sb.insert(index, "\b").insert(index + wordLength + 1, "\f");
                index = index + wordLength + 2;
            }
        }

        int pos = 0;
        while(true) {
            pos = sb.indexOf("\b", pos);
            if (pos < 0) break;
            sb.replace(pos, pos + 1, startTag);
            pos += startTag.length();
        }
        pos = 0;
        while(true) {
            pos = sb.indexOf("\f", pos);
            if (pos < 0) break;
            sb.replace(pos, pos + 1, endTag);
            pos += endTag.length();
        }

        return sb.toString();
    }
}
