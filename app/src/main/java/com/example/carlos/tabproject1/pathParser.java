package com.example.carlos.tabproject1;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.Stack;

import static android.content.ContentValues.TAG;

public class pathParser {
    //    Context context;
    String s;
    int i;
    pathParser(){}

    public  static String pathCheck(String s){
//       String newS = null;
//        StringBuilder newString = new StringBuilder(s);

        if (!s.isEmpty()||s==null){
            s = "fix me";
            Log.d(TAG, "pathCheck: " + s);
            return s;
        }else{
            char [] charArr = new char[s.length()];

            for(int j = 0; j<s.length();j++){
                charArr[j] = s.charAt(j);
            }

            for(char c:charArr){
                if(c == '.'||c=='$'||c == '#'||c=='['||c==']'){
                    s.replace(c,'@');
                    Log.d(TAG, "pathCheck: " + s);
                }
            }

        }
//        s= newS;
//        if (s.contains(".")||s.contains("#")||s.contains("$")) {
//            for(int j = 0; j<s.length();j++) {
//                newString.deleteCharAt(j);
//                s = newString.toString();
//                Log.d(TAG, "pathCheck: " + s);
//                return s;
//            }
//        }
//
//        if(s.contains(".")){
//            s.replace(".","");
////            newString.deleteCharAt(i);
////            s = newString.toString();
//            return s;
//        }

//        switch(5) {
//            case 0:
//                if (s.contains(".")) {
//
//                    i = s.indexOf(".");
//                    newString.deleteCharAt(i);
//                    s = newString.toString();
//                    Log.d(TAG, "pathCheck: " + s);
//                    return s;
//                }
//            case 1:
//                if (s.contains("$")) {
//
//                    i = s.indexOf("$");
//                    newString.deleteCharAt(i);
//                   s = newString.toString();
//                }
//            case 2:
//                if (s.contains("#")) {
//
//                    i = s.indexOf("#");
//                    newString.deleteCharAt(i);
//                    s = newString.toString();
//                }
//            case 3:
//                if (s.contains("[")) {
//
//                    i = s.indexOf("[");
//                    newString.deleteCharAt(i);
//                    s = newString.toString();
//                }
//            case 4:
//                if (s.contains("]")) {
//
//                    i = s.indexOf("]");
//                    newString.deleteCharAt(i);
//                    s = newString.toString();
//                }
//                break;
//        }
//        Log.d(TAG, "pathCheck: " + s);
        return s;
    }
}
