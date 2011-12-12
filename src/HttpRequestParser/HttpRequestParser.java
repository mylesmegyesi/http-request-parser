package HttpRequestParser;

import HttpRequestParser.Exceptions.ParseException;
import HttpRequestParser.Utility.InputStreamReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestParser {

    public HttpRequestLineParser httpRequestLineParser;
    public HttpRequestHeadersParser httpRequestHeadersParser;
    public HttpRequestBodyParser httpRequestBodyParser;

    public HttpRequestParser() {
        this.httpRequestLineParser = new HttpRequestLineParser();
        InputStreamReader inputStreamReader = new InputStreamReader();
        this.httpRequestHeadersParser = new HttpRequestHeadersParser(inputStreamReader);
        this.httpRequestBodyParser = new HttpRequestBodyParser();
    }

    public HttpRequestParser(HttpRequestLineParser httpRequestLineParser, HttpRequestHeadersParser httpRequestHeadersParser, HttpRequestBodyParser httpRequestBodyParser) {
        this.httpRequestLineParser = httpRequestLineParser;
        this.httpRequestHeadersParser = httpRequestHeadersParser;
        this.httpRequestBodyParser = httpRequestBodyParser;
    }

    public HttpRequest parseRequestLineAndHeaders(InputStream inputStream) throws IOException, ParseException {
        HttpRequestLine requestLine = httpRequestLineParser.parse(inputStream);
        Map<String, String> requestHeaders = httpRequestHeadersParser.parse(inputStream);
        return new HttpRequest(requestLine.action, requestLine.uri, requestLine.protocolVersion, requestHeaders, requestLine.parameters);
    }

    public Map<String, Object> parseBody(InputStream inputStream, String contentType, int contentLength) throws IOException, ParseException {
        return this.httpRequestBodyParser.parse(inputStream, contentType, contentLength);
    }

}
