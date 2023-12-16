package com.sabersinfin.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncriptadorSHA256 {
    public static String encriptarSHA256(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(texto.getBytes());
            StringBuilder hexString = new StringBuilder(2 * hash.length);

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Manejar la excepción, por ejemplo, lanzar una excepción personalizada
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
    }
}
