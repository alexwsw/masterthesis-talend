package secretService;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


public class PasswordDecryptor {
	
	public static String ENCRYPT_KEY = "Encrypt"; //$NON-NLS-1$

    private static String rawKey = "Talend-Key"; //$NON-NLS-1$

    private static SecretKey key = null;

    private static SecureRandom secureRandom = new SecureRandom();

    private static String CHARSET = "UTF-8";

    private static SecretKey getSecretKey() throws Exception {
        if (key == null) {

            byte rawKeyData[] = rawKey.getBytes(CHARSET);
            DESKeySpec dks = new DESKeySpec(rawKeyData);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES"); //$NON-NLS-1$
            key = keyFactory.generateSecret(dks);
        }
        return key;
    }
    
    public static String decryptPassword(String input) {
        if (input == null || input.length() == 0) {
            return input;
        }
        try {
        	byte[] dec = Hex.decodeHex(input.toCharArray());
            SecretKey key = getSecretKey();
            Cipher c = Cipher.getInstance("DES"); //$NON-NLS-1$
            c.init(Cipher.DECRYPT_MODE, key, secureRandom);
            byte[] clearByte = c.doFinal(dec);
            return new String(clearByte, CHARSET);
        } catch (Exception e) {
            //do nothing
        }
        return input;
    }
    
    public static String encryptPassword(String input) throws Exception {
        if (input == null) {
            return input;
        }

        SecretKey key = getSecretKey();
        Cipher c = Cipher.getInstance("DES"); //$NON-NLS-1$
        c.init(Cipher.ENCRYPT_MODE, key, secureRandom);
        byte[] cipherByte = c.doFinal(input.getBytes(CHARSET));
        String dec = Hex.encodeHexString(cipherByte);
        return dec;
    }

}
