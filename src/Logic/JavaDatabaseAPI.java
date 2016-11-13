package Logic; /**
 * Created by BradWilliams on 10/17/16.
 */

import java.sql.*;

public abstract class JavaDatabaseAPI implements DatabaseHandler{


    public static boolean loginAuth(String username, String password){
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ResultSet resultSet2 = null;

        try{
            // Get connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/calculator?autoReconnect=true&useSSL=false", "root","bdw52575");

            try {
                // Create a statement
                statement = conn.createStatement();

                try {
                    //Check if username exists
                    resultSet = statement.executeQuery("SELECT username FROM users WHERE username = '" + username + "'");

                    //if result set is empty (if the username was not found)
                    if (!resultSet.next()) {
                        return false;
                    }

                    //Check password
                    resultSet2 = statement.executeQuery("SELECT password FROM users WHERE username= '" + username + "'");

                    // while there is more stuff in result set
                    while(resultSet2.next()) {
                        if(resultSet2.getString("password").equals(password))
                            return true;
                        else
                            return false;
                    }

                }
                catch (SQLException e){}
            }
            finally{
                try{
                    if(statement != null)
                    statement.close();
                } catch(SQLException e){}
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try{
                if(conn != null)
                conn.close();
            } catch(SQLException e){}
        }



        return true;
    }

    public static void addUser(String username, String email, String password){
        Connection conn = null;
        Statement statement = null;

        try{
            // Get connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/calculator?autoReconnect=true&useSSL=false", "root","bdw52575");

            try {
                // Create a statement
                statement = conn.createStatement();
                int updateSuccess = statement.executeUpdate("INSERT INTO users (username,email,password) VALUES (" +
                        "'" + username + "'," +
                        "'" + email + "'," +
                        "'" + password + "')"
                );

                //executeStatement() return number of columns affected
                if (updateSuccess == 0)
                    System.out.println("INSERT " + username + ", " + email + ", " + password + " FAILED");
            }
            finally{
                try{
                    if(statement != null)
                    statement.close();
                } catch (SQLException e) {}
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(conn != null)
                conn.close();
            } catch (SQLException e) {e.printStackTrace();}
        }


    }

    public static String [] getAccountInfo(String username) { // change to get username and email
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String[] results = new String[2];

        try{
            // Get connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/calculator?autoReconnect=true&useSSL=false", "root","bdw52575");

            try {
                // Create a statement
                statement = conn.createStatement();

                try {
                    //Check username
                    resultSet = statement.executeQuery("SELECT password, email FROM users WHERE username = '" + username + "'");

                    //if result set is empty (if the username was not found)
                    while (resultSet.next()) {
                        results[0] = resultSet.getString("password");
                        results[1] = resultSet.getString("email");
                    }
                }
                catch (SQLException e){}
            }
            finally{
                try{
                    if(statement != null)
                        statement.close();
                } catch(SQLException e){}
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try{
                if(conn != null)
                    conn.close();
            } catch(SQLException e){}
        }
        return results;
    }

    public static void changePassword(String username, String password){
        Connection conn = null;
        Statement statement = null;

        try{
            // Get connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/calculator?autoReconnect=true&useSSL=false", "root","bdw52575");

            try {
                // Create a statement
                statement = conn.createStatement();
                int updateSuccess = statement.executeUpdate("UPDATE users " +
                        "SET password='" + password + "' " +
                        "WHERE username='" + username + "'"
                );

                //executeStatement() return number of columns affected
                if (updateSuccess == 0)
                    System.out.println("UPDATE SET " + username + ",  new password: " + password + " FAILED");
            }
            finally{
                try{
                    if(statement != null)
                        statement.close();
                } catch (SQLException e) {}
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(conn != null)
                    conn.close();
            } catch (SQLException e) {e.printStackTrace();}
        }
    }

    public static void changeEmail(String username, String email){
        Connection conn = null;
        Statement statement = null;

        try{
            // Get connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/calculator?autoReconnect=true&useSSL=false", "root","bdw52575");

            try {
                // Create a statement
                statement = conn.createStatement();
                int updateSuccess = statement.executeUpdate("UPDATE users " +
                        "SET email='" + email + "' " +
                        "WHERE username='" + username + "'"
                );

                //executeStatement() return number of columns affected
                if (updateSuccess == 0)
                    System.out.println("UPDATE SET " + username + ",  new email: " + email + " FAILED");
            }
            finally{
                try{
                    if(statement != null)
                        statement.close();
                } catch (SQLException e) {}
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(conn != null)
                    conn.close();
            } catch (SQLException e) {e.printStackTrace();}
        }
    }

    public static void main(String args[]){

        //Logic.JavaDatabaseAPI.addUser("laurenT","lt@gmail.net","amywinehouse");
        //System.out.println(Logic.JavaDatabaseAPI.loginAuth("laurenT","amywinehouse"));
        //Logic.JavaDatabaseAPI.changePassword("laurenT","davidbowie");
        //Logic.JavaDatabaseAPI.changeEmail("laurenT","kasshmir@hotmail.poop");
//        for(String stuff : Logic.JavaDatabaseAPI.getAccountInfo("laurenT"))
//            System.out.println(stuff);


    }

}
