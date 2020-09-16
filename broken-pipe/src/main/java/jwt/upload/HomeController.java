package jwt.upload;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.StreamingFileUpload;
import io.micronaut.security.annotation.Secured;
import io.reactivex.Single;

@Controller("/")  // <2>
public class HomeController {

    @Post(consumes = MediaType.MULTIPART_FORM_DATA, produces = MediaType.TEXT_PLAIN)
    @Secured("ROLE_NON_EXISTING")
    public Single<HttpResponse<String>> uploadPublic(StreamingFileUpload file) {
        return null; // never reached
    }
}
