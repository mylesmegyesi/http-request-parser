package HttpRequestParser;

import HttpRequestParser.ContentTypeParsers.UrlEncodedFormParser;
import HttpRequestParser.Exceptions.ParseException;
import HttpRequestParser.Utility.InputStreamReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestLineParser {

    public InputStreamReader inputStreamReader;
    public UrlEncodedFormParser urlEncodedFormParser;

    public HttpRequestLineParser() {
        this.inputStreamReader = new InputStreamReader();
        this.urlEncodedFormParser = new UrlEncodedFormParser();
    }

    public HttpRequestLineParser(InputStreamReader inputStreamReader, UrlEncodedFormParser urlEncodedFormParser) {
        this.inputStreamReader = inputStreamReader;
        this.urlEncodedFormParser = urlEncodedFormParser;
    }

    public HttpRequestLine parse(InputStream inputStream) throws IOException, ParseException {
        String requestLine = inputStreamReader.getNextLine(inputStream);
        String[] requestLineItems = this.splitStringOnWhitespace(requestLine);
        if (requestLineItems.length != 3) {
            throw new ParseException(String.format("Improperly formatted request line: %s", requestLine));
        }
        return this.parseRequestItems(requestLineItems);
    }

    private String[] splitStringOnWhitespace(String requestLine) {
        return requestLine.trim().split("\\s+");
    }

    private HttpRequestLine parseRequestItems(String[] requestLineItems) throws IOException, ParseException {
        String[] requestUriItems = parseRequestUri(requestLineItems[1]);
        return new HttpRequestLine(requestLineItems[0], requestUriItems[0], requestLineItems[2], this.urlEncodedFormParser.parse(new ByteArrayInputStream(requestUriItems[1].getBytes())));
    }

    private String[] parseRequestUri(String requestUri) {
        String[] requestUriItems = new String[]{requestUri, ""};
        int questionMarkIndex = requestUri.indexOf("?");
        if (questionMarkIndex != -1) {
            requestUriItems[0] = requestUri.substring(0, questionMarkIndex);
            requestUriItems[1] = requestUri.substring(questionMarkIndex + 1, requestUri.length());
        }
        return requestUriItems;
    }
}
