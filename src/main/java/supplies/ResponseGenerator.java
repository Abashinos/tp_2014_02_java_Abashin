package supplies;

import resources.ResponseData;
import resources.resource_system.ResourceFactory;

import javax.servlet.http.HttpServletResponse;

public class ResponseGenerator {

    private static ResponseData responseData = null;

    private static void instantiateResponseData() {
        if (responseData == null) {
            responseData = (ResponseData) ResourceFactory.getInstance().get("responseData");
        }
    }

    public static void setSuccessData(HttpServletResponse response) {
        instantiateResponseData();
        response.setContentType(responseData.CONTENT_TYPE);
        response.setStatus(responseData.SUCCESS_STATUS);
    }

    public static void setRedirectData(HttpServletResponse response) {
        instantiateResponseData();
        response.setContentType(responseData.CONTENT_TYPE);
        response.setStatus(responseData.REDIRECT_STATUS);
    }
}
