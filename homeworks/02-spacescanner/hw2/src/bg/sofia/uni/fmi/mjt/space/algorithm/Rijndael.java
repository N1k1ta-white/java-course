package bg.sofia.uni.fmi.mjt.space.algorithm;

import bg.sofia.uni.fmi.mjt.space.exception.CipherException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public record Rijndael(SecretKey key) implements SymmetricBlockCipher {
    private static final String ALGORITHM = "AES";
    private static final int BUFFER_SIZE = 8192;

    @Override
    public void encrypt(InputStream inputStream, OutputStream outputStream) throws CipherException {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] info = new byte[BUFFER_SIZE];
            int bytesRead;
            try (var output = new CipherOutputStream(outputStream, cipher)) {
                while ((bytesRead = inputStream.read(info)) != -1) {
                    output.write(info, 0, bytesRead);
                }
            }
        } catch (IOException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new CipherException(CipherException.CipherOperation.Encrypt, e);
        }
    }

    @Override
    public void decrypt(InputStream inputStream, OutputStream outputStream) throws CipherException {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] info = new byte[BUFFER_SIZE];
            int bytesRead;
            try (var output = new CipherOutputStream(outputStream, cipher)) {
                while ((bytesRead = inputStream.read(info)) != -1) {
                    output.write(info, 0, bytesRead);
                }
            }
        } catch (IOException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new CipherException(CipherException.CipherOperation.Decrypt , e);
        }
    }
}
