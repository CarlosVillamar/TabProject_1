package com.example.carlos.tabproject1;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**This a class created to act as a home for helper functions to meet certain firebase requirements
 *
 * This class will also house other helper functions as the scope of the application changes and
 * new requirements need to be met*/

public class FirebasePathVerify {


    FirebasePathVerify() {
    }

    public static String pathCheck(String s) {
        /**While the current project scope sets the taskList name as the pathname to retrieve object
         * nodes, we must check for certain characters or conditions in order to meet firebase path
         * requirements*/

        Log.d(TAG, "pathCheck: s before loop" + s);
        String newS = null;
        //TODO:find more test cases for this method;
        char[] charArr = new char[s.length()];
        for (int j = 0; j < s.length(); j++) {
            charArr[j] = s.charAt(j);
            Log.d(TAG, "pathCheck: string length " + s.length() + " charArr " + charArr[j]);
        }

        try {

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
                        return newS;
                    }
                }

            }
        }catch (Exception e){

        }
                return s;
    }
    public static String usernameVerify(String un){
        //TODO: Come back to this during the firebase user authentication phase
        return un;
    }

    public static String passwordVeirfy(String pw){

        return pw;
    }
}
