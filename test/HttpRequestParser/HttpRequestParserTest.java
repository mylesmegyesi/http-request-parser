package HttpRequestParser;

import HttpRequestParser.Mocks.HttpRequestBodyParserMock;
import HttpRequestParser.Mocks.HttpRequestHeaderParserMock;
import HttpRequestParser.Mocks.HttpRequestLineParserMock;
import HttpRequestParser.Utility.InputStreamReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestParserTest {
    HttpRequestParser requestParser;
    HttpRequestLineParserMock httpRequestLineParserMock;
    HttpRequestHeaderParserMock httpRequestHeaderParserMock;
    HttpRequestBodyParserMock httpRequestBodyParserMock;

    @Before
    public void setUp() throws Exception {
        this.httpRequestLineParserMock = new HttpRequestLineParserMock();
        this.httpRequestHeaderParserMock = new HttpRequestHeaderParserMock();
        this.httpRequestBodyParserMock = new HttpRequestBodyParserMock();
        this.requestParser = new HttpRequestParser(this.httpRequestLineParserMock, this.httpRequestHeaderParserMock, this.httpRequestBodyParserMock);
    }

    @After
    public void tearDown() throws Exception {
        this.httpRequestLineParserMock = null;
        this.httpRequestHeaderParserMock = null;
        this.requestParser = null;
    }

    @Test
    public void callsTheRequestLineParser() throws Exception {
        this.requestParser.parseRequestLineAndHeaders(null);
        assertEquals(1, this.httpRequestLineParserMock.parseCalledCount);
    }

    @Test
    public void callsTheRequestHeaderParser() throws Exception {
        this.requestParser.parseRequestLineAndHeaders(null);
        assertEquals(1, this.httpRequestHeaderParserMock.parseCalledCount);
    }

    @Test
    public void callsTheBodyParser() throws Exception {
        this.requestParser.parseBody(null, "");
        assertEquals(1, this.httpRequestBodyParserMock.parseCalledCount);
    }

    @Test
    public void ifGetReturnsQueryStringParams() throws Exception {
        this.requestParser.httpRequestLineParser = new HttpRequestLineParser();
        this.requestParser.httpRequestHeadersParser = new HttpRequestHeadersParser(new InputStreamReader());
        HttpRequest request = this.requestParser.parseRequestLineAndHeaders(this.stringToStream("GET /some/path?text=adsf HTTP/1.1"));
        assertEquals(1, request.parameters.size());
        assertTrue(request.parameters.containsKey("text"));
        assertEquals("adsf", request.parameters.get("text"));
    }

    @Test
    public void ifGetIgnoresBody() throws Exception {
        this.requestParser.httpRequestLineParser = new HttpRequestLineParser();
        this.requestParser.httpRequestHeadersParser = new HttpRequestHeadersParser(new InputStreamReader());
        HttpRequest request = this.requestParser.parseRequestLineAndHeaders(this.stringToStream("GET /some/path HTTP/1.1\nContent-Type: type\n\nbody"));
        assertEquals(0, request.parameters.size());
    }

    private InputStream stringToStream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

}
