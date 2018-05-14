import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * This file is intended to be used on a IDE for testing purposes.
 * ClassLoader.getSystemResource won't work in a JAR
 */
public class Main {

    public static void main(String[] args) throws Exception {

        Path path_key_private = Paths.get("/home/dev3", "myrsakey_pcks8");
        Path path_key_public = Paths.get("/home/dev3", "key.public.pem");


        String privateKeyContent = new String(Files.readAllBytes(path_key_private));
        String publicKeyContent = new String(Files.readAllBytes(path_key_public));

        privateKeyContent = privateKeyContent.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
        publicKeyContent = publicKeyContent.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");;

        KeyFactory kf = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
        PrivateKey privKey = kf.generatePrivate(keySpecPKCS8);

        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));
       // RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);
        PublicKey pubKey = kf.generatePublic(pubKeySpec);


        //Firmar documentos
        String documento = "esto es una prueba";

        byte[] firma = Firmador.firmarArchivo(privKey, documento.getBytes());

        //Verificar firma
        String documento2 = "esto es una prueba2";
        boolean respuesta = Verificador.validarFirma(documento2.getBytes(), firma, pubKey);

        System.out.println("Clave Privada:");
        System.out.println(privKey);
        System.out.println("Clave Publica:");
        System.out.println(pubKey);
        System.out.print("Firma Digital: ");
        System.out.println(firma.toString());
        System.out.println("Verificar firma: ");
        System.out.println(respuesta);
    }
}
