import java.security.PrivateKey;
import java.security.Signature;

public class Firmador {
    /**
     * Metodo que firma un archivo. Para esto le computa un hash (SHA1), cuyo resultado es encriptado utilizando la clave privada
     *
     * @param clavePrivada - clave privada con la cual se encriptara el hash
     * @param data         - ruta donde se guardara la firma
     * @throws Exception - lanza Excepcion si no es posible terminar exitosamente la firma
     */
    public static byte[] firmarArchivo(PrivateKey clavePrivada, byte[] data) throws Exception {

        Signature dsa = Signature.getInstance("SHA1withRSA");
        dsa.initSign(clavePrivada);
        dsa.update(data);
        byte[] firma = dsa.sign();
        return firma;
    }
}
