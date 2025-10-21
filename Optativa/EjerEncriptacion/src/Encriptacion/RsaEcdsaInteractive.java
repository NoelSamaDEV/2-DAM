package Encriptacion;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.security.spec.ECGenParameterSpec;

/**
 * RsaEcdsaInteractive
 * - Interactivo por consola.
 * - Permite generar claves RSA/ECDSA o pegar claves en Base64 (X.509 / PKCS#8).
 * - Realiza cifrado RSA/OAEP (SHA-256) y firma ECDSA (P-256).
 */
public class RsaEcdsaInteractive {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in, "UTF-8")) {
            System.out.println("== RSA / ECDSA interactivo ==");
            System.out.println("Elija la operación:");
            System.out.println("  1 - RSA cifrar/descifrar (RSA/OAEP SHA-256)");
            System.out.println("  2 - ECDSA firmar/verificar (secp256r1 / SHA256withECDSA)");
            System.out.print("> ");
            String choice = scanner.nextLine().trim();

            if ("1".equals(choice)) {
                rsaFlow(scanner);
            } else if ("2".equals(choice)) {
                ecdsaFlow(scanner);
            } else {
                System.out.println("Opción no válida. Saliendo.");
            }
        } catch (Exception e) {
            System.err.println("Error fatal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ---------- RSA flow ----------
    private static void rsaFlow(Scanner scanner) throws Exception {
        System.out.println("\n--- RSA: cifrar/descifrar (OAEP SHA-256) ---");
        System.out.println("¿Deseas GENERAR claves nuevas o PEGAR claves existentes?");
        System.out.println("  G - Generar");
        System.out.println("  P - Pegar claves en Base64");
        System.out.print("> ");
        String opt = scanner.nextLine().trim().toUpperCase();

        PublicKey rsaPub = null;
        PrivateKey rsaPriv = null;

        if ("G".equals(opt)) {
            KeyPairGenerator rsaKpg = KeyPairGenerator.getInstance("RSA");
            rsaKpg.initialize(2048);
            KeyPair rsaKeyPair = rsaKpg.generateKeyPair();
            rsaPub = rsaKeyPair.getPublic();
            rsaPriv = rsaKeyPair.getPrivate();

            System.out.println("\nClaves generadas:");
            System.out.println("Publica (X.509) Base64:");
            System.out.println(encodeBase64(rsaPub.getEncoded()));
            System.out.println("\nPrivada (PKCS#8) Base64:");
            System.out.println(encodeBase64(rsaPriv.getEncoded()));
        } else if ("P".equals(opt)) {
            System.out.println("\nIntroduce la clave pública RSA en Base64 (X.509). Si no deseas proporcionarla deja en blanco y pulsa ENTER:");
            String pubB64 = readMultiline(scanner);
            if (pubB64 != null && !pubB64.isBlank()) {
                rsaPub = loadRSAPublicKeyFromBase64(pubB64.trim());
                System.out.println("Clave pública cargada.");
            }

            System.out.println("\nIntroduce la clave privada RSA en Base64 (PKCS#8). Si no deseas proporcionarla deja en blanco y pulsa ENTER:");
            String privB64 = readMultiline(scanner);
            if (privB64 != null && !privB64.isBlank()) {
                rsaPriv = loadRSAPrivateKeyFromBase64(privB64.trim());
                System.out.println("Clave privada cargada.");
            }
        } else {
            System.out.println("Opción no válida. Saliendo RSA.");
            return;
        }

        System.out.println("\n¿Qué quieres hacer?");
        System.out.println("  1 - Cifrar (necesita la clave pública)");
        System.out.println("  2 - Descifrar (necesita la clave privada)");
        System.out.print("> ");
        String action = scanner.nextLine().trim();

        if ("1".equals(action)) {
            if (rsaPub == null) {
                System.out.println("No hay clave pública disponible. Operación cancelada.");
                return;
            }
            System.out.println("\nIntroduce el texto a cifrar (línea única):");
            String plaintext = scanner.nextLine();
            String cipherB64 = rsaEncryptToBase64(rsaPub, plaintext);
            System.out.println("\nResultado (RSA cifrado, Base64):");
            System.out.println(cipherB64);
        } else if ("2".equals(action)) {
            if (rsaPriv == null) {
                System.out.println("No hay clave privada disponible. Operación cancelada.");
                return;
            }
            System.out.println("\nIntroduce el texto cifrado en Base64 (RSA/OAEP):");
            String cipherB64 = readMultiline(scanner);
            byte[] decrypted = rsaDecryptFromBase64(rsaPriv, cipherB64.trim());
            System.out.println("\nDescifrado (UTF-8):");
            System.out.println(new String(decrypted, StandardCharsets.UTF_8));
        } else {
            System.out.println("Acción no reconocida. Saliendo RSA.");
        }
    }

    // ---------- ECDSA flow ----------
    private static void ecdsaFlow(Scanner scanner) throws Exception {
        System.out.println("\n--- ECDSA: firmar/verificar (secp256r1) ---");
        System.out.println("¿Deseas GENERAR claves nuevas o PEGAR claves existentes?");
        System.out.println("  G - Generar");
        System.out.println("  P - Pegar claves en Base64");
        System.out.print("> ");
        String opt = scanner.nextLine().trim().toUpperCase();

        PublicKey ecPub = null;
        PrivateKey ecPriv = null;

        if ("G".equals(opt)) {
            KeyPairGenerator ecKpg = KeyPairGenerator.getInstance("EC");
            ecKpg.initialize(new ECGenParameterSpec("secp256r1"));
            KeyPair ecKeyPair = ecKpg.generateKeyPair();
            ecPriv = ecKeyPair.getPrivate();
            ecPub = ecKeyPair.getPublic();

            System.out.println("\nClaves generadas:");
            System.out.println("Publica (X.509) Base64:");
            System.out.println(encodeBase64(ecPub.getEncoded()));
            System.out.println("\nPrivada (PKCS#8) Base64:");
            System.out.println(encodeBase64(ecPriv.getEncoded()));
        } else if ("P".equals(opt)) {
            System.out.println("\nIntroduce la clave pública EC en Base64 (X.509). Si no deseas proporcionarla deja en blanco y pulsa ENTER:");
            String pubB64 = readMultiline(scanner);
            if (pubB64 != null && !pubB64.isBlank()) {
                ecPub = loadECPublicKeyFromBase64(pubB64.trim());
                System.out.println("Clave pública EC cargada.");
            }

            System.out.println("\nIntroduce la clave privada EC en Base64 (PKCS#8). Si no deseas proporcionarla deja en blanco y pulsa ENTER:");
            String privB64 = readMultiline(scanner);
            if (privB64 != null && !privB64.isBlank()) {
                ecPriv = loadECPrivateKeyFromBase64(privB64.trim());
                System.out.println("Clave privada EC cargada.");
            }
        } else {
            System.out.println("Opción no válida. Saliendo ECDSA.");
            return;
        }

        System.out.println("\n¿Qué quieres hacer?");
        System.out.println("  1 - Firmar (necesita la clave privada)");
        System.out.println("  2 - Verificar (necesita la clave pública)");
        System.out.print("> ");
        String action = scanner.nextLine().trim();

        if ("1".equals(action)) {
            if (ecPriv == null) {
                System.out.println("No hay clave privada disponible. Operación cancelada.");
                return;
            }
            System.out.println("\nIntroduce el texto a firmar (línea única):");
            String text = scanner.nextLine();
            byte[] sig = ecdsaSign(ecPriv, text.getBytes(StandardCharsets.UTF_8));
            System.out.println("\nFirma (Base64):");
            System.out.println(encodeBase64(sig));
        } else if ("2".equals(action)) {
            if (ecPub == null) {
                System.out.println("No hay clave pública disponible. Operación cancelada.");
                return;
            }
            System.out.println("\nIntroduce el texto original (línea única):");
            String text = scanner.nextLine();
            System.out.println("\nIntroduce la firma en Base64:");
            String sigB64 = readMultiline(scanner);
            boolean ok = ecdsaVerify(ecPub, text.getBytes(StandardCharsets.UTF_8), Base64.getDecoder().decode(sigB64.trim()));
            System.out.println("\nVerificación: " + ok);
        } else {
            System.out.println("Acción no reconocida. Saliendo ECDSA.");
        }
    }

    // ---------- RSA helpers ----------
    private static String rsaEncryptToBase64(PublicKey pub, String plaintext) throws Exception {
        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        // explicit OAEP parameters (optional, for clarity)
        OAEPParameterSpec oaepParams = new OAEPParameterSpec(
                "SHA-256",
                "MGF1",
                new MGF1ParameterSpec("SHA-256"),
                PSource.PSpecified.DEFAULT);
        rsaCipher.init(Cipher.ENCRYPT_MODE, pub, oaepParams);
        byte[] cipherBytes = rsaCipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(cipherBytes);
    }

    private static byte[] rsaDecryptFromBase64(PrivateKey priv, String cipherB64) throws Exception {
        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        OAEPParameterSpec oaepParams = new OAEPParameterSpec(
                "SHA-256",
                "MGF1",
                new MGF1ParameterSpec("SHA-256"),
                PSource.PSpecified.DEFAULT);
        rsaCipher.init(Cipher.DECRYPT_MODE, priv, oaepParams);
        byte[] cipherBytes = Base64.getDecoder().decode(cipherB64);
        return rsaCipher.doFinal(cipherBytes);
    }

    private static PublicKey loadRSAPublicKeyFromBase64(String b64) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(b64);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    private static PrivateKey loadRSAPrivateKeyFromBase64(String b64) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(b64);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    // ---------- EC / ECDSA helpers ----------
    private static byte[] ecdsaSign(PrivateKey priv, byte[] data) throws Exception {
        Signature signer = Signature.getInstance("SHA256withECDSA");
        signer.initSign(priv);
        signer.update(data);
        return signer.sign();
    }

    private static boolean ecdsaVerify(PublicKey pub, byte[] data, byte[] signature) throws Exception {
        Signature verifier = Signature.getInstance("SHA256withECDSA");
        verifier.initVerify(pub);
        verifier.update(data);
        return verifier.verify(signature);
    }

    private static PublicKey loadECPublicKeyFromBase64(String b64) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(b64);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("EC");
        return kf.generatePublic(spec);
    }

    private static PrivateKey loadECPrivateKeyFromBase64(String b64) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(b64);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("EC");
        return kf.generatePrivate(spec);
    }

    // ---------- Utilities ----------
    private static String encodeBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Lee varias líneas hasta que el usuario introduzca una línea vacía.
     * Si el texto cabe en una línea, puede pegarse y pulsar ENTER seguido de otra ENTER para terminar.
     * Devuelve null si el usuario solo pulsa ENTER inmediatamente.
     */
    private static String readMultiline(Scanner scanner) {
        System.out.println("(Pega el texto, luego pulsa ENTER y otra línea vacía para terminar.)");
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = scanner.nextLine();
            // si la primera línea es vacía devolvemos cadena vacía
            if (line == null) break;
            if (line.isEmpty()) {
                break;
            }
            if (sb.length() > 0) sb.append('\n');
            sb.append(line);
        }
        return sb.toString();
    }
}