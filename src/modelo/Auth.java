package modelo;

import org.mindrot.jbcrypt.BCrypt;

public class Auth {

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static Boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }

    public 
}
