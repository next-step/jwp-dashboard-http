package nextstep.jwp.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    public static URL getURL(String filePath) {
        return Thread.currentThread().getContextClassLoader().getResource("static" + filePath);
    }

    public static byte[] getBodyFromURL(URL resource) {
        try {
            final Path path = new File(resource.getFile()).toPath();
            return Files.readAllBytes(path);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return new byte[0];
    }

    public static byte[] getBodyFromPath(String filePath) {
        return getBodyFromURL(getURL(filePath));
    }
}
