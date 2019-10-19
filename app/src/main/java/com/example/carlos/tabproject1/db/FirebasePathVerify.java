package com.example.carlos.tabproject1.db;

/**
 * This a class created to act as a home for helper functions to meet certain firebase requirements
 * This class will also house other helper functions as the scope of the application changes and
 * new requirements need to be met
 */

public class FirebasePathVerify {
    //TODO: refactor this class into firebase utility

    FirebasePathVerify() {
    }

    public static String pathCheck(String s) {
        /**While the current project scope sets the todoTask name as the pathname to retrieve object
         * nodes, we must check for certain characters or conditions in order to meet firebase path
         * requirements*/

        String regex = "#$[].";

        //TODO:find more test cases for this method;
        try {
            if (s != null)
                for (char c : regex.toCharArray())
                    if (s.contains(String.valueOf(c)))
                        s = s.replace(c, ' ');

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public static String usernameVerify(String un) {
        //TODO: Come back to this during the firebase user authentication phase
        return un;
    }

    public static String passwordVeirfy(String pw) {

        return pw;
    }
}
