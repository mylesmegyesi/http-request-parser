package HttpRequestParser.Mocks;

import HttpRequestParser.HttpRequestBodyParser;

import java.io.InputStream;
import java.util.Map;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestBodyParserMock extends HttpRequestBodyParser {

    public int parseCalledCount = 0;
    public String contentTypePassed = "";

    @Override
    public Map<String, Object> parse(InputStream inputStream, String contentType) {
        this.parseCalledCount++;
        this.contentTypePassed = contentType;
        return null;
    }

}
