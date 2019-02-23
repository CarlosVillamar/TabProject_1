package com.example.carlos.tabproject1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class FirebasePathVerify {


    FirebasePathVerify() {
    }

    public static String pathCheck(String s) {
        Log.d(TAG, "pathCheck: s before loop" + s);
        String newS = null;
        //TODO:find more test cases for this method;
        char[] charArr = new char[s.length()];
        for (int j = 0; j < s.length(); j++) {
            charArr[j] = s.charAt(j);
            Log.d(TAG, "pathCheck: string length " + s.length() + " charArr " + charArr[j]);
        }

        if (s == null) {
            s = "fix me";
            Log.d(TAG, "pathCheck: " + s);
            return s;
        } else {

//            Log.d(TAG, "pathCheck: charArr " + charArr);
            for (char c : charArr) {
                Log.d(TAG, "pathCheck: C " + c);

                if (c == '.' || c == '$' || c == '#' || c == '[' || c == ']') {
                    newS = s.replace(c, ' ');
//                    newS = newS.concat(" (update task name)");

                    return newS;
                }
            }

            return s;
        }
    }
    public static String usernameVerify(String un){
        //TODO: Come back to this during the firebase user authentication phase
        return un;
    }

    public static String passwordVeirfy(String pw){

        return pw;
    }
}
