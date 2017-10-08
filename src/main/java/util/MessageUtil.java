package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhao Zhe on 2017/10/8.
 */
public class MessageUtil {

    private static List<Integer> encodeList;
    public static String encodeMessage(String str){
       return toBinary(str);
    }
    public static String decodeMessage(String str, List<Integer> list){

        return BinstrToStr(str,list);
    }

    private static String toBinary(String str){
        char[] strChar=str.toCharArray();
        encodeList = new ArrayList<Integer>();
        String result="";
        for(int i=0;i<strChar.length;i++){
            String temp = Integer.toBinaryString(strChar[i]);
            result +=temp;
            encodeList.add(temp.length());
        }
        //System.out.println(result);
        return result;
    }
    private static int[] BinstrToIntArray(String binStr) {
        char[] temp=binStr.toCharArray();
        int[] result=new int[temp.length];
        for(int i=0;i<temp.length;i++) {
            result[i]=temp[i]-48;
        }
        return result;
    }
    private static char BinstrToChar(String binStr){
        int[] temp=BinstrToIntArray(binStr);
        int sum=0;
        for(int i=0; i<temp.length;i++){
            sum +=temp[temp.length-1-i]<<i;
        }
        return (char)sum;
    }
    private static String BinstrToStr(String binStr, List<Integer> encodeList){
        String[] tempStr = new String[encodeList.size()];
        StringBuffer sb = new StringBuffer(binStr);
        for (int i = 0; i < encodeList.size(); i++) {
            int len = encodeList.get(i);
            tempStr[i] = sb.substring(0,len);
            sb = sb.delete(0,len);
        }
        char[] tempChar=new char[tempStr.length];
        for(int i=0;i<tempStr.length;i++) {
            tempChar[i]=BinstrToChar(tempStr[i]);
        }
        //System.out.println(String.valueOf(tempChar));
        return String.valueOf(tempChar);
    }

    public static List<Integer> getEncodeList() {
        return encodeList;
    }
}
