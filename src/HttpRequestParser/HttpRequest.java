package HttpRequestParser;

import java.util.Map;

/**
 * Author: Myles Megyesi
 */
public class HttpRequest {

    public String action;
    public String url;
    public String version;
    public Map<String, String> requestHeaders;
    public Map<String, Object> parameters;

    public HttpRequest(String action, String url, String version, Map<String, String> requestHeaders, Map<String, Object> parameters) {
        this.action = action;
        this.url = url;
        this.version = version;
        this.requestHeaders = requestHeaders;
        this.parameters = parameters;
    }
}
