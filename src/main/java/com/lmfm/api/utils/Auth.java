package com.lmfm.api.utils;

import com.lmfm.api.bd.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;
import com.lmfm.api.model.Usuario;

import java.sql.*;

public class Auth {

    /**
     * Hashea un String con algoritmo BCrypt.
     * @param password String para hashear.
     * @return password hasheada.
     */
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * @param password String plano.
     * @param hashed String previamente hasheado.
     * @return true si las contraseña coinciden, sino false.
     */
    public static Boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }

    /**
     * Autentifica al usuario y retorna una instancia del mismo si los datos son correctos.
     * @param legajo n° de legajo del usuario.
     * @param password contraseña del usuario.
     * @return Instancia de Usuario o null si los datos no son correctos.
     */
    public static Usuario login(int legajo, String password) {
        String getUsusarioSQL = "SELECT * FROM usuarios WHERE legajo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(getUsusarioSQL);
             Statement stmt = conn.createStatement()) {

            // Uso la DB
            stmt.execute("USE hpc_db");

            // Obtengo al usuario en la DB
            pstmt.setInt(1, legajo);
            ResultSet resultUsuario = pstmt.executeQuery();

            // Si el usuario existe
            if (resultUsuario.next()) {
                String dbPassword = resultUsuario.getString("contraseña");

                // Si la contraseña es la correcta
                if (checkPassword(password, dbPassword)) {
                    int id = resultUsuario.getInt("id");
                    String nombre = resultUsuario.getString("nombre");
                    String apellido = resultUsuario.getString("apellido");

                    return new Usuario(id, nombre, apellido, legajo, dbPassword);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
