package Encriptacion;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

/**
 * DesCbcDemo2
 * - Igual que el anterior, pero con otra clave y otro IV.
 * - DES/CBC/PKCS5Padding (solo con fines pedagógicos).
 */
public class DesCbcDemo2 {
    // NUEVA clave DES (8 bytes = 16 hex chars): 0x13 0x34 0x57 0x79 0x9B 0xBC 0xDF 0xF1
    private static final byte[] DES_KEY = hexToBytes("133457799BBCDFF1");
    // NUEVO IV (8 bytes = 16 hex chars)
    private static final byte[] IV = hexToBytes("0102030405060708");

    public static void main(String[] args) throws Exception {
        String plaintext = "Hola Luis no se si esto funciona";
        System.out.println("Original: " + plaintext);

        // 1. KeySpec e IV
        SecretKeySpec keySpec = new SecretKeySpec(DES_KEY, "DES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV);

        // 2. Cifrar
        Cipher encryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        byte[] cipherBytes = encryptCipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        String cipherB64 = Base64.getEncoder().encodeToString(cipherBytes);
        System.out.println("Cifrado (Base64) generado: " + cipherB64);

        // 3. Permitir pegar Base64 para descifrar
        Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8.name());
        System.out.println("\nPega aquí un mensaje cifrado en Base64 para descifrar (o vacío para usar el generado):");
        String inputB64 = sc.nextLine().trim();

        if (inputB64.isEmpty()) {
            inputB64 = cipherB64;
            System.out.println("Usando el texto cifrado generado por el programa.");
        } else {
            System.out.println("Usando el Base64 introducido por el usuario.");
        }

        // 4. Descifrar
        Cipher decryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        try {
            byte[] decoded = Base64.getDecoder().decode(inputB64);
            byte[] recovered = decryptCipher.doFinal(decoded);
            String recoveredText = new String(recovered, StandardCharsets.UTF_8);
            System.out.println("Descifrado: " + recoveredText);
        } catch (IllegalArgumentException iae) {
            System.err.println("Error: la entrada no es un Base64 válido: " + iae.getMessage());
        } catch (javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException be) {
            System.err.println("Error durante el descifrado (posible clave/IV incorrectos o datos corruptos): " + be.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        } finally {
            sc.close();
        }
    }

    // Helper: convierte hex string (p.e. "A1B2...") a bytes
    private static byte[] hexToBytes(String s) {
        int len = s.length();
        if (len % 2 != 0) throw new IllegalArgumentException("Hex inválido: longitud impar.");
        byte[] out = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            out[i / 2] = (byte) Integer.parseInt(s.substring(i, i + 2), 16);
        }
        return out;
    }
}
