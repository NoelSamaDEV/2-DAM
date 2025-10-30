package Encriptacion;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.util.Base64;

/**
 * BlowfishPbkdf2Console
 *
 * Versión del ejemplo Blowfish + PBKDF2 que pide el texto plano por consola.
 *
 * NOTAS IMPORTANTES (docencia):
 * - SALT e IV están fijos para que la salida sea reproducible en clase.
 *   EN PRODUCCIÓN: generar salt e IV aleatorios con SecureRandom para cada operación.
 * - PBKDF2WithHmacSHA256 se usa para derivar una clave desde una contraseña humana.
 * - Blowfish usa tamaño de bloque de 8 bytes -> por eso IV de 8 bytes y PKCS5 padding.
 * - Para almacenar contraseñas de usuario usa hashing (Argon2/bcrypt/PBKDF2 para verificación),
 *   no cifrado reversible, salvo que explicitamente necesites recuperar el texto.
 */
public class BlowfishPbkdf2Console {
    // Contraseña de ejemplo (solo para la práctica).
    // En un ejercicio real, pedirías la contraseña por consola o usarías un KMS.
    private static final String PASSWORD = "MiContraseñaFuerte123!";

    // Salt fijo (solo para reproducibilidad). En producción -> SecureRandom, 16 bytes mínimo.
    private static final byte[] SALT = hexToBytes("DEADBEEF00112233");

    // IV fijo de 8 bytes (Blowfish tiene bloque de 8 bytes). En producción -> aleatorio por mensaje.
    private static final byte[] IV = hexToBytes("0102030405060708");

    public static void main(String[] args) {
        try {
            // 1) Leer el plaintext desde consola (Console o fallback a BufferedReader)
            String plaintext = readLineFromConsole("Introduce el mensaje a cifrar: ");
            if (plaintext == null) {
                System.err.println("No se ha leído ningún texto. Saliendo.");
                return;
            }
            System.out.println("Texto a cifrar: " + plaintext);

            // 2) Derivar clave con PBKDF2WithHmacSHA256
            //    - Iteraciones: 10000 (ejemplo educativo). En producción evaluar el coste y usar más.
            //    - Key length: 128 bits (Blowfish acepta 32..448 bits, aquí usamos 128 para compatibilidad)
            char[] passwordChars = PASSWORD.toCharArray();
            PBEKeySpec pbeKeySpec = new PBEKeySpec(passwordChars, SALT, 10000, 128);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] keyBytes = skf.generateSecret(pbeKeySpec).getEncoded();
            // Limpieza del PBEKeySpec y del array de contraseña para reducir tiempo expuesto en memoria
            pbeKeySpec.clearPassword();
            java.util.Arrays.fill(passwordChars, '\0');

            // 3) Crear SecretKeySpec para Blowfish con los bytes derivados
            SecretKeySpec blowfishKey = new SecretKeySpec(keyBytes, "Blowfish");

            // 4) Preparar IV (8 bytes) para CBC
            IvParameterSpec ivSpec = new IvParameterSpec(IV);

            // 5) Cifrar con Blowfish/CBC/PKCS5Padding
            Cipher enc = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
            enc.init(Cipher.ENCRYPT_MODE, blowfishKey, ivSpec);
            byte[] cipherBytes = enc.doFinal(plaintext.getBytes("UTF-8"));
            String cipherB64 = Base64.getEncoder().encodeToString(cipherBytes);
            System.out.println("Cifrado (Base64): " + cipherB64);

            // 6) Descifrar para verificar el resultado (round-trip)
            Cipher dec = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
            dec.init(Cipher.DECRYPT_MODE, blowfishKey, ivSpec);
            byte[] recovered = dec.doFinal(Base64.getDecoder().decode(cipherB64));
            System.out.println("Descifrado: " + new String(recovered, "UTF-8"));

            // NOTA: aquí no se sobreescribe 'keyBytes' ni el contenido del SecretKeySpec porque
            // SecretKeySpec mantiene referencia a los bytes que contienen la clave. Si necesitas
            // limpiar memoria, usa técnicas avanzadas o librerías que soporten clave en memoria nativa.
        } catch (javax.crypto.BadPaddingException e) {
            // Este error puede indicar que la clave/IV no son correctos o que el padding es inválido
            System.err.println("Error de descifrado: datos corruptos o clave/IV incorrectos.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lectura robusta de línea desde consola: usa System.console si está disponible.
    private static String readLineFromConsole(String prompt) {
        Console console = System.console();
        if (console != null) {
            return console.readLine(prompt);
        } else {
            // Fallback (p. ej. IDEs que no exponen Console)
            System.out.print(prompt);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                return br.readLine();
            } catch (Exception e) {
                return null;
            }
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

