package Encriptacion;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

/**
 * DesCbcDemo
 * - Muestra cifrado DES/CBC/PKCS5Padding y su descifrado.
 * - Usa claves/IVs fijos para que el ejemplo sea determinista y reproducible.
 *
 * AVISO: DES tiene clave efectiva de 56 bits y hoy se considera inseguro.
 *       Esto se hace solo con propósitos pedagógicos.
 */
public class DesCbcDemo {
    // Clave DES: 8 bytes (64 bits) - en DES 8 bits son de paridad, clave efectiva 56 bits.
    // Aquí la ponemos fija (NO HACER ESTO EN PRODUCCIÓN).
    private static final byte[] DES_KEY = hexToBytes("0123456789ABCDEF");
    // IV para CBC: 8 bytes
    private static final byte[] IV = hexToBytes("A1B2C3D4E5F60708");

    public static void main(String[] args) throws Exception {
        String plaintext = "Eliseo despues de este año te hacen santo";
        System.out.println("Original: " + plaintext);

        // 1. Crear key spec y IV spec
        SecretKeySpec keySpec = new SecretKeySpec(DES_KEY, "DES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV);

        // 2. Inicializar Cipher para cifrar
        Cipher encryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        // 3. Cifrar y mostrar Base64
        byte[] cipherBytes = encryptCipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        String cipherB64 = Base64.getEncoder().encodeToString(cipherBytes);
        System.out.println("Cifrado (Base64) generado: " + cipherB64);

        // --- NUEVO: permitir al usuario pegar un mensaje cifrado (Base64) para descifrar ---
        Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8.name());
        System.out.println("\nPega aquí un mensaje cifrado en Base64 para descifrar (o deja vacío para usar el generado arriba) y pulsa ENTER:");
        String inputB64 = sc.nextLine().trim();

        // Si el usuario deja vacío, usamos el cifrado generado por el programa
        if (inputB64.isEmpty()) {
            inputB64 = cipherB64;
            System.out.println("Usando el texto cifrado generado por el programa.");
        } else {
            System.out.println("Usando el Base64 introducido por el usuario.");
        }
        // -------------------------------------------------------------------------------

        // 4. Inicializar Cipher para descifrar (mismo key y IV)
        Cipher decryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        // 5. Intentar descifrar y mostrar (con manejo de errores)
        try {
            byte[] decoded = Base64.getDecoder().decode(inputB64);
            byte[] recovered = decryptCipher.doFinal(decoded);
            String recoveredText = new String(recovered, StandardCharsets.UTF_8);
            System.out.println("Descifrado: " + recoveredText);
        } catch (IllegalArgumentException iae) {
            // lanza IllegalArgumentException si Base64 inválido
            System.err.println("Error: la entrada no es un Base64 válido: " + iae.getMessage());
        } catch (javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException be) {
            // por ejemplo: clave/IV incorrectos, datos corruptos o padding inválido
            System.err.println("Error durante el descifrado (posible clave/IV incorrectos o datos corruptos): " + be.getMessage());
        } catch (Exception e) {
            // cualquier otra excepción
            System.err.println("Error inesperado: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        } finally {
            sc.close();
        }
    }

    // Helper: convierte hex string (p.e. "A1B2...") a bytes
    private static byte[] hexToBytes(String s) {
        int len = s.length();
        byte[] out = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            out[i / 2] = (byte) Integer.parseInt(s.substring(i, i + 2), 16);
        }
        return out;
    }
}