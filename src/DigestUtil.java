import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class DigestUtil {

    /**
     * @param file
     * @param algorithm 所请求算法的名称  for example: MD5, SHA1, SHA-256, SHA-384, SHA-512 etc.
     * @return
     */
    public static String getFileMD5(File file, String algorithm) {

        if (!file.isFile()) {
            return null;
        }

        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;

        try {
            digest = MessageDigest.getInstance(algorithm);
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }
}