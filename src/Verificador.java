import java.security.PublicKey;
import java.security.Signature;

public class Verificador {

    /**
     * Metodo encargado de validar una firma
     *
     * @param documento - archivo del cual se quiere verificar su firma
     * @param firma     - firma digital del archivo
     * @param publicKey - clave publica del firmador
     * @return boolean - True si la firma es correcta (veridica), False en caso contrario
     * @throws Exception - lanza excepcion si no se termina la validacion correctamente
     */
    public static boolean validarFirma(byte[] documento, byte[] firma, PublicKey publicKey) throws Exception {

        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initVerify(publicKey);
        signature.update(documento);
        return signature.verify(firma);
    }
}
