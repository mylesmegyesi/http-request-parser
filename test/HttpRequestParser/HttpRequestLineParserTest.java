package HttpRequestParser;

import HttpRequestParser.ContentTypeParsers.Mocks.UrlEncodedFormParserMock;
import HttpRequestParser.Exceptions.ParseException;
import HttpRequestParser.Utility.InputStreamReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static junit.framework.Assert.assertEquals;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestLineParserTest {

    HttpRequestLineParser parser;
    InputStreamReader inputStreamReader;
    UrlEncodedFormParserMock urlEncodedFormParserMock;

    @Before
    public void setUp() throws Exception {
        this.urlEncodedFormParserMock = new UrlEncodedFormParserMock();
        this.inputStreamReader = new InputStreamReader();
        this.parser = new HttpRequestLineParser(this.inputStreamReader, this.urlEncodedFormParserMock);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void parsesGoldPath() throws Exception {
        HttpRequestLine requestLine = this.parser.parse(stringToStream("first second third"));
        assertEquals("first", requestLine.action);
        assertEquals("second", requestLine.uri);
        assertEquals("third", requestLine.protocolVersion);
    }

    @Test(expected = ParseException.class)
    public void throwsWithOnlyTwoItemsInRequestLine() throws Exception {
        this.parser.parse(stringToStream("first second"));
    }

    @Test
    public void parsesWithLotsOfSpace() throws Exception {
        HttpRequestLine requestLine = this.parser.parse(stringToStream("  first   second   third   \r\n"));
        assertEquals("first", requestLine.action);
        assertEquals("second", requestLine.uri);
        assertEquals("third", requestLine.protocolVersion);
    }

    @Test
    public void callsQueryStringParser() throws Exception {
        this.parser.parse(stringToStream("first second third"));
        assertEquals(1, this.urlEncodedFormParserMock.parseCalledCount);
    }

    @Test
    public void passesTheQueryToTheQueryStringParser() throws Exception {
        this.parser.parse(stringToStream("first second?thequery third"));
        assertEquals("thequery", this.urlEncodedFormParserMock.parsePassed);
    }

    private InputStream stringToStream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }
}
