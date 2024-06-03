package Controladores;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class EncriptadorAES {

    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "YourSecretKey";

    public static String encriptarAES(String texto) {
        try {
            SecretKey secretKey = generarClave(SECRET_KEY);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(texto.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String desencriptarAES(String textoEncriptado) {
        try {
            SecretKey secretKey = generarClave(SECRET_KEY);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(textoEncriptado));
            return new String(decryptedBytes);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static SecretKey generarClave(String clave) throws Exception {
        byte[] claveBytes = clave.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        claveBytes = sha.digest(claveBytes);
        claveBytes = Arrays.copyOf(claveBytes, 16);
        return new SecretKeySpec(claveBytes, ALGORITHM);
    }
}
