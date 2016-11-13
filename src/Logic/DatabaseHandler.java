package Logic;

/**
 * Created by BradWilliams on 10/17/16.
 * Interface to be implemented in Java JDBC API class
 */
public interface DatabaseHandler {
    static boolean loginAuth(String username, String password){return true;}

    static void addUser(String username, String email, String password){}

    static String[] getAccountInfo(String username){return new String[0];}

    static void changePassword(String username, String password){}

    static void changeEmail(String username, String email){}

}
