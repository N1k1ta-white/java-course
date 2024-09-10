package bg.sofia.uni.fmi.mjt.space.algorithm;

import bg.sofia.uni.fmi.mjt.space.exception.CipherException;
import org.junit.jupiter.api.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RijndaelTest {
    private static final String ALGORITHM = "AES";
    private static final String TEST_TEXT = "Text for unit test for my homework for MJT course";
    private static final int KEY_SIZE = 128;

    public static SecretKey getKey() throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
        generator.init(KEY_SIZE);
        return generator.generateKey();
    }

    @Test
    void testRijndael() throws NoSuchAlgorithmException, CipherException {
        SecretKey key = getKey();
        Rijndael test = new Rijndael(key);
        InputStream input = new ByteArrayInputStream(TEST_TEXT.getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        test.encrypt(input, output);
        InputStream input2 = new ByteArrayInputStream(output.toByteArray());
        OutputStream output2 = new ByteArrayOutputStream();
        test.decrypt(input2, output2);
        assertEquals(TEST_TEXT, output2.toString());
    }

    @Test
    void testRijndaelThrow() {
        SecretKey test = new SecretKeySpec("test".getBytes(), ALGORITHM);
        Rijndael testObj = new Rijndael(test);
        InputStream inputStream = new ByteArrayInputStream("Test".getBytes());
        OutputStream outputStream = new ByteArrayOutputStream();
        assertThrows(CipherException.class, () -> testObj.encrypt(inputStream, outputStream));
        InputStream inputStream2 = null;
        assertThrows(CipherException.class, () -> testObj.encrypt(inputStream2, outputStream));
        OutputStream outputStream2 = null;
        assertThrows(CipherException.class, () -> testObj.encrypt(inputStream, outputStream2));
    }
}
