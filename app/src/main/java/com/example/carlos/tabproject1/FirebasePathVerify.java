package com.example.carlos.tabproject1;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Stack;

import static android.content.ContentValues.TAG;

public class FirebasePathVerify {
    int i;
    FirebasePathVerify(){}

    public  static String pathCheck(String s,Context context){
        Log.d(TAG, "pathCheck: s before loop" + s);

        String newS = null;
//        StringBuilder newString = new StringBuilder(s);
        char [] charArr = new char[s.length()];
        for(int j = 0; j<s.length();j++){
            charArr[j] = s.charAt(j);
            Log.d(TAG, "pathCheck: strong length " + s.length() +"charArr "+ charArr[j]);
        }

        if (s==null){
            s = "fix me";
            Log.d(TAG, "pathCheck: " + s);
            return s;
        }else if(!s.isEmpty()){

            Log.d(TAG, "pathCheck: charArr " + charArr);
            for(char c:charArr){
                Log.d(TAG, "pathCheck: C " + c);

                if(c == '.'||c=='$'||c == '#'||c=='['||c==']'){
                    newS = s.replace(c,'!');
                    newS = newS.concat(" (update task name)");
                    Log.d(TAG, "pathCheck: " + newS);
                }
            }
            Toast.makeText(context.getApplicationContext(),"Task name cannot contain . , # ,$ ,[ or ]",Toast.LENGTH_SHORT).show();
            return newS;
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
