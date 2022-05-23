package nextstep.jwp.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ETagService {

    private static final Logger logger = LoggerFactory.getLogger(ETagService.class);

    public String getETag(String path) {
        return getHash(path);
    }

    private String getHash(String path) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            MessageDigest messageDigest = getMessageDigest(path);
            createHash(messageDigest, stringBuffer);
        } catch (NoSuchAlgorithmException | IOException e) {
            logger.info(e.getMessage());
        }
        return stringBuffer.toString();
    }

    private MessageDigest getMessageDigest(String path)
        throws NoSuchAlgorithmException, IOException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        FileInputStream fileInputStream = new FileInputStream(path);
        byte[] dataBytes = new byte[1024];
        Integer nRead = 0;
        while ((nRead = fileInputStream.read(dataBytes)) != -1) {
            messageDigest.update(dataBytes, 0, nRead);
        }
        return messageDigest;
    }

    private void createHash(MessageDigest messageDigest, StringBuffer stringBuffer) {
        byte[] mdBytes = messageDigest.digest();
        for (Integer i = 0; i < mdBytes.length; i++) {
            stringBuffer.append(Integer.toString((mdBytes[i] & 0xff) + 0x100, 16)).substring(1);
        }
    }


}



