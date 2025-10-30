package Encriptacion;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.BadPaddingException;

/**
 * BlowfishPbkdf2Tool (versión corregida)
 *
 * - Modo ENCRYPT: pide mensaje por consola, genera salt (16 bytes) y IV (8 bytes),
 *   deriva clave con PBKDF2WithHmacSHA256 (10000 iteraciones, 128 bits),
 *   cifra con Blowfish/CBC/PKCS5Padding y muestra:
 *     - salt (hex), iv (hex), clave derivada (hex), ciphertext (Base64)
 *
 * - Modo DECRYPT: pide ciphertext (Base64) y salt+iv (hex). Puedes elegir:
 *     a) Derivar la clave desde una contraseña (recomendado)
 *     b) Introducir la clave directamente en hex (si la tienes)
 *
 * NOTAS DE SEGURIDAD EDUCATIVAS:
 * - Mostrar claves en consola es solo para aprendizaje. Nunca lo hagas en producción.
 * - En producción: genera salt e IV aleatorios (como aquí), envíalos junto al ciphertext,
 *   guarda salt/IV por registro y NO muestres claves.
 * - Para almacenamiento de contraseñas de login, usar hashing (Argon2/bcrypt/PBKDF2) y NO cifrado reversible.
 */
public class BlowfishPbkdf2Tool {
    private static final SecureRandom RANDOM = new SecureRandom();

    public static void main(String[] args) {
        try {
            String mode = chooseMode(args);
            if ("encrypt".equalsIgnoreCase(mode)) {
                runEncrypt();
            } else if ("decrypt".equalsIgnoreCase(mode)) {
                runDecrypt();
            } else {
                System.out.println("Modo desconocido. Elige 'encrypt' o 'decrypt'.");
            }
        } catch (BadPaddingException e) {
            System.err.println("Error: fallo en el descifrado (clave/IV/salt incorrectos o datos corruptos).");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Selecciona modo: usa args[0] si existe, si no pregunta al usuario
    private static String chooseMode(String[] args) throws Exception {
        if (args != null && args.length > 0 && args[0] != null && !args[0].trim().isEmpty()) {
            return args[0].trim();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Modo ('encrypt' o 'decrypt'): ");
        String line = br.readLine();
        return line != null ? line.trim() : "";
    }

    // MODO ENCRYPT
    private static void runEncrypt() throws Exception {
        String plaintext = readLine("Introduce el mensaje a cifrar: ");
        if (plaintext == null) {
            System.err.println("Texto vacío. Saliendo.");
            return;
        }

        // 1) Generar salt aleatorio (16 bytes) y IV (8 bytes para Blowfish)
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        byte[] iv = new byte[8];
        RANDOM.nextBytes(iv);

        // 2) Pedir contraseña para derivación (recomendada)
        char[] password = readPassword("Introduce la contraseña para derivar la clave (usa la misma al descifrar): ");

        // 3) Derivar clave: PBKDF2WithHmacSHA256, 10000 iter, 128 bits (Blowfish key in bytes)
        byte[] keyBytes = deriveKeyFromPassword(password, salt, 10000, 128);

        // Limpiar password en memoria
        if (password != null) { java.util.Arrays.fill(password, '\0'); }

        // 4) Mostrar (SOLO para la clase / pruebas) salt/IV/clave
        System.out.println("=== Valores (solo para pruebas) ===");
        System.out.println("Salt (hex): " + bytesToHex(salt));
        System.out.println("IV   (hex): " + bytesToHex(iv));
        System.out.println("Clave derivada (hex): " + bytesToHex(keyBytes));
        System.out.println("==================================");

        // 5) Cifrar con Blowfish/CBC/PKCS5Padding
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "Blowfish");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher enc = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
        enc.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] cipherBytes = enc.doFinal(plaintext.getBytes("UTF-8"));
        String cipherB64 = Base64.getEncoder().encodeToString(cipherBytes);

        System.out.println("Cifrado (Base64): " + cipherB64);

        // NOTA: no sobreescribimos keyBytes ni keySpec internamente (SecretKeySpec mantiene referencia).
        // En aplicaciones críticas, usar librerías que permitan manejo seguro de claves en memoria nativa.
    }

    // MODO DECRYPT
    private static void runDecrypt() throws Exception {
        String cipherB64 = readLine("Introduce el ciphertext (Base64): ");
        if (cipherB64 == null || cipherB64.trim().isEmpty()) {
            System.err.println("Ciphertext vacío. Saliendo.");
            return;
        }

        String saltHex = readLine("Introduce el salt (hex): ");
        String ivHex = readLine("Introduce el IV (hex): ");
        if (saltHex == null || ivHex == null) {
            System.err.println("Salt o IV vacíos. Saliendo.");
            return;
        }
        byte[] salt = hexToBytes(saltHex.trim());
        byte[] iv = hexToBytes(ivHex.trim());

        // Preguntar si se quiere derivar clave con contraseña o introducir clave en hex
        String choice = readLine("¿Derivar clave con contraseña? (s/n): ");
        byte[] keyBytes;
        if (choice != null && choice.trim().toLowerCase().startsWith("s")) {
            char[] password = readPassword("Introduce la contraseña usada en el cifrado: ");
            keyBytes = deriveKeyFromPassword(password, salt, 10000, 128);
            if (password != null) java.util.Arrays.fill(password, '\0');
            System.out.println("Clave derivada (hex): " + bytesToHex(keyBytes));
        } else {
            String keyHex = readLine("Introduce la clave en hex (clave derivada): ");
            keyBytes = hexToBytes(keyHex.trim());
            System.out.println("Clave proporcionada (hex): " + bytesToHex(keyBytes));
        }

        // Descifrar
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "Blowfish");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher dec = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
        dec.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] recovered = dec.doFinal(Base64.getDecoder().decode(cipherB64));
        System.out.println("Descifrado: " + new String(recovered, "UTF-8"));
    }

    // Deriva clave desde contraseña y salt usando PBKDF2WithHmacSHA256
    private static byte[] deriveKeyFromPassword(char[] password, byte[] salt, int iterations, int keyBits) throws Exception {
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password, salt, iterations, keyBits);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] key = skf.generateSecret(pbeKeySpec).getEncoded();
        pbeKeySpec.clearPassword();
        return key;
    }

    // Lectura simple de línea (compatible con IDEs)
    private static String readLine(String prompt) {
        Console console = System.console();
        if (console != null) {
            return console.readLine(prompt);
        } else {
            System.out.print(prompt);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                return br.readLine();
            } catch (Exception e) {
                return null;
            }
        }
    }

    // Lectura de password sin eco si es posible (fallback a línea si no)
    private static char[] readPassword(String prompt) {
        Console console = System.console();
        if (console != null) {
            return console.readPassword(prompt);
        } else {
            // Fallback: leer como línea (no está oculto)
            System.out.print(prompt);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                String line = br.readLine();
                return line != null ? line.toCharArray() : null;
            } catch (Exception e) {
                return null;
            }
        }
    }

    // Helpers hex/base64
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) sb.append(String.format("%02X", b));
        return sb.toString();
    }

    private static byte[] hexToBytes(String s) {
        s = s.replaceAll("\\s+", "");
        if (s.length() % 2 != 0) throw new IllegalArgumentException("Hex string length must be even");
        int len = s.length();
        byte[] out = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            out[i / 2] = (byte) Integer.parseInt(s.substring(i, i + 2), 16);
        }
        return out;
    }
}