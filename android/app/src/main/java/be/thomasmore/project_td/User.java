package be.thomasmore.project_td;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
    private static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        User.token = token;
    }

    public static boolean hasToken(){
        return (token != null && !token.isEmpty());
    }

    public static void logOut(Context context){
        User.token = null;
        SharedPreferences settings = context.getSharedPreferences("user", 0);
        settings.edit().putString("token", null).apply();
    }
}
