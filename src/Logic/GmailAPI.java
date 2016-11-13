package Logic;

import java.util.Random;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * Created by BradWilliams on 11/2/16.
 * Implements Logic.EmailHandler
 */
public abstract class GmailAPI implements EmailHandler {
    static String generatePassword(){
        Random random = new Random();
        String characters = new String("abcdefghijklmnopqrstuvwxyz0123456789");
        int length = 8;

        char[] text = new char[length];

        do {
            for (int i = 0; i < length; i++) {
                text[i] = characters.charAt(random.nextInt(characters.length()));
            }
        } while (!(new String(text).matches("^(?=.*[a-z])(?=.*[0-9])[a-z0-9]+$")));

        return new String(text);
    }

    public static String sendMessage(String email){

        String generatedPassword = generatePassword();
        final String username = "bradwilliamsvi@gmail.com";
        final String password = "drums666";
        final String recipient = email;

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(username));

            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipient));

            // This is the subject
            message.setSubject("Integer Calculator Password Reset");

            //This is the message
            message.setContent("" +
                    "<h2>Here is your new password </h2>" +
                    "<br/>" +
                    generatedPassword
                    , "text/html");

            //Send message
            Transport.send(message);

            System.out.println("Password recovery email sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return generatedPassword;
    }




//    public static void main(String args []){
//
//        for(int i = 0; i < 20; ++i){
//            String password = generatePassword();
//            System.out.print(password + ": ");
//
//            System.out.println(Validation.isValidPassword(password));
//        }
//    }
}
