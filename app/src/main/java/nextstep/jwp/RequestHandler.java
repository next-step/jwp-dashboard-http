package nextstep.jwp;

import java.io.BufferedOutputStream;
//import nextstep.jwp.controller.HttpController;
import nextstep.jwp.mapper.RequestMapper;
import nextstep.jwp.model.http.httprequest.HttpRequest;
import nextstep.jwp.model.http.httpresponse.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Objects;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connection) {
        this.connection = Objects.requireNonNull(connection);
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());
        try (final InputStream inputStream = connection.getInputStream(); final OutputStream outputStream = connection.getOutputStream()) {
            readAndWrite(inputStream, outputStream);
        } catch (IOException exception) {
            logger.error("Exception stream", exception);
        } finally {
            close();
        }
    }

    private void readAndWrite(InputStream inputStream, OutputStream outputStream) throws IOException {
        if (inputStream != null) {
            HttpRequest request = read(inputStream);
            write(outputStream, request);
        }
    }

    private HttpRequest read(InputStream inputStream) throws IOException {
        HttpRequest httpRequest = new HttpRequest(inputStream);
        logger.info("Http Request {}", httpRequest.toString());
        return httpRequest;
    }

    private void write(OutputStream outputStream, HttpRequest httpRequest) throws IOException {
        HttpResponse httpResponse = RequestMapper.of(httpRequest.getPath()).service(httpRequest);
        logger.info("Http Response {}", httpResponse.toString());
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        bufferedOutputStream.write(httpResponse.toBytes());
        bufferedOutputStream.flush();
    }

    private void close() {
        try {
            connection.close();
        } catch (IOException exception) {
            logger.error("Exception closing socket", exception);
        }
    }

}
