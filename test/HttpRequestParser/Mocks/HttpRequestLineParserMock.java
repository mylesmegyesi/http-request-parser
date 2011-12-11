package HttpRequestParser.Mocks;

import HttpRequestParser.ContentTypeParsers.UrlEncodedFormParser;
import HttpRequestParser.HttpRequestLine;
import HttpRequestParser.HttpRequestLineParser;
import HttpRequestParser.Utility.InputStreamReader;

import java.io.InputStream;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestLineParserMock extends HttpRequestLineParser {

    public int parseCalledCount = 0;

    public HttpRequestLineParserMock() {
        super(new InputStreamReader(), new UrlEncodedFormParser());
    }

    @Override
    public HttpRequestLine parse(InputStream inputStream) {
        this.parseCalledCount++;
        return new HttpRequestLineMock();
    }
}
