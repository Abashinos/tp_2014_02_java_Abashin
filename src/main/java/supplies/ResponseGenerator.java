package supplies;

import javax.servlet.http.HttpServletResponse;

import static resources.ResponseData.*;

public class ResponseGenerator {

    public static void setSuccessData(HttpServletResponse response) {
        response.setContentType(CONTENT_TYPE);
        response.setStatus(SUCCESS_STATUS);
    }

    public static void setRedirectData(HttpServletResponse response) {
        response.setContentType(CONTENT_TYPE);
        response.setStatus(REDIRECT_STATUS);
    }
}
