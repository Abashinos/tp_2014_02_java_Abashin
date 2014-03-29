package supplies;

import javax.servlet.http.HttpServletResponse;

public class ResponseGenerator {

    private static final String CONTENT_TYPE = "text/html;charset=utf-8";
    private static final int SUCCESS_STATUS = HttpServletResponse.SC_OK;
    private static final int REDIRECT_STATUS = HttpServletResponse.SC_TEMPORARY_REDIRECT;

    public static void setSuccessData(HttpServletResponse response) {
        response.setContentType(CONTENT_TYPE);
        response.setStatus(SUCCESS_STATUS);
    }

    public static void setRedirectData(HttpServletResponse response) {
        response.setContentType(CONTENT_TYPE);
        response.setStatus(REDIRECT_STATUS);
    }
}
