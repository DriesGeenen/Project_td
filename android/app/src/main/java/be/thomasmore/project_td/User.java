package be.thomasmore.project_td;

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
}
