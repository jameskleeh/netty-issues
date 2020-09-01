package test.plaintext;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/plaintext")
public class Test {

    private static final String TEXT = "Hello, World!";

    @Get(value = "/", produces = MediaType.TEXT_PLAIN)
    String getPlainText() {
        return TEXT;
    }
}

