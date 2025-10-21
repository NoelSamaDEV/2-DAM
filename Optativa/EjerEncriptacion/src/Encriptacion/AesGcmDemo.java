package Encriptacion;
import javax.crypto.Cipher;
import javax.crypto.AEADBadTagException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

/**
 * AesGcmDemo
 * - Usa AES/GCM/NoPadding (AEAD).
 * - GCMParameterSpec necesita la longitud de tag en bits (usamos 128).
 *
 * Aviso: para producción, genera clave y nonce con SecureRandom. Aquí se fijan
 * para que los alumnos obtengan resultados reproducibles.
 */
public class AesGcmDemo {
    // AES-128 -> 16 bytes clave
    private static final byte[] AES_KEY = hexToBytes("00112233445566778899AABBCCDDEEFF");
    // GCM nonce: 12 bytes recomendado
    private static final byte[] GCM_NONCE = hexToBytes("AABBCCDDEEFF001122334455");

    public static void main(String[] args) throws Exception {
        String plaintext = "Esto es un mensaje cifrado";
        System.out.println("Original: " + plaintext);

        SecretKeySpec keySpec = new SecretKeySpec(AES_KEY, "AES");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, GCM_NONCE); // tag length 128 bits

        // Cifrar
        Cipher enc = Cipher.getInstance("AES/GCM/NoPadding");
        enc.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);
        byte[] cipherBytes = enc.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        String cipherB64 = Base64.getEncoder().encodeToString(cipherBytes);
        System.out.println("Cifrado (Base64) generado: " + cipherB64);

        // --- NUEVO: permitir al usuario pegar un mensaje cifrado (Base64) para descifrar ---
        Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8.name());
        System.out.println("\nPega aquí un mensaje cifrado en Base64 para descifrar (o deja vacío para usar el generado arriba) y pulsa ENTER:");
        String inputB64 = sc.nextLine().trim();

        if (inputB64.isEmpty()) {
            inputB64 = cipherB64;
            System.out.println("Usando el texto cifrado generado por el programa.");
        } else {
            System.out.println("Usando el Base64 introducido por el usuario.");
        }
        // -------------------------------------------------------------------------------

        // Descifrar (validará la etiqueta automática)
        Cipher dec = Cipher.getInstance("AES/GCM/NoPadding");
        dec.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec);

        try {
            byte[] decoded = Base64.getDecoder().decode(inputB64);
            byte[] plainRecovered = dec.doFinal(decoded);
            System.out.println("Descifrado: " + new String(plainRecovered, StandardCharsets.UTF_8));
        } catch (IllegalArgumentException iae) {
            // Base64 inválido
            System.err.println("Error: la entrada no es un Base64 válido: " + iae.getMessage());
        } catch (AEADBadTagException at) {
            // etiqueta inválida -> datos corruptos o clave/nonce distintos
            System.err.println("Error: fallo de autenticación AEAD (etiqueta inválida). Posible clave/nonce incorrectos o datos modificados.");
        } catch (javax.crypto.IllegalBlockSizeException | javax.crypto.BadPaddingException be) {
            // por defecto no debería ocurrir en GCM si AEAD funciona, pero lo cubrimos
            System.err.println("Error durante el descifrado: " + be.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        } finally {
            sc.close();
        }
    }

    private static byte[] hexToBytes(String s) {
        int len = s.length();
        byte[] out = new byte[len / 2];
        for (int i = 0; i < len; i += 2) out[i / 2] = (byte) Integer.parseInt(s.substring(i, i + 2), 16);
        return out;
    }
}