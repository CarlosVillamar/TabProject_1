package com.example.carlos.tabproject1;

import android.util.Log;

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

            Log.d(TAG, "pathCheck: charArr " + charArr);
            for (char c : charArr) {
                Log.d(TAG, "pathCheck: C " + c);

                if (c == '.' || c == '$' || c == '#' || c == '[' || c == ']') {
                    newS = s.replace(c, '!');
                    newS = newS.concat(" (update task name)");
                    Log.d(TAG, "pathCheck: " + newS);
                    return newS;
                }
            }

            return s;
        }
    }
}
