package HttpRequestParser;

import java.util.Map;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestLine {
    public String action;
    public String uri;
    public Map<String, Object> parameters;
    public String protocolVersion;

    public HttpRequestLine(String action, String url, String protocolVersion, Map<String, Object> parameters) {
        this.action = action;
        this.uri = url;
        this.protocolVersion = protocolVersion;
        this.parameters = parameters;
    }
}
