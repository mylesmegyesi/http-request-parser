package HttpRequestParser;

import HttpRequestParser.Exceptions.ParseException;
import HttpRequestParser.Utility.InputStreamReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestHeadersParserTest {

    HttpRequestHeadersParser parser;
    InputStreamReader inputStreamReader;

    @Before
    public void setUp() throws Exception {
        this.inputStreamReader = new InputStreamReader();
        this.parser = new HttpRequestHeadersParser(this.inputStreamReader);
    }

    @After
    public void tearDown() throws Exception {
        this.inputStreamReader = null;
        this.parser = null;
    }

    @Test
    public void parsesAHeader() throws Exception {
        Map<String, String> requestHeaders = this.parser.parse(this.stringToStream("Some: header"));
        assertTrue(requestHeaders.containsKey("Some"));
        assertEquals("header", requestHeaders.get("Some"));
    }

    @Test(expected = ParseException.class)
    public void throwsAnExceptionIfTheColonIsAtTheBeginningOfTheHeader() throws Exception {
        this.parser.parse(this.stringToStream(": header"));
    }

    @Test(expected = ParseException.class)
    public void throwsAnExceptionIfTheColonIsAtTheEndOfTheHeader() throws Exception {
        this.parser.parse(this.stringToStream("header:"));
    }

    @Test(expected = ParseException.class)
    public void throwsAnExceptionIfTheColonDoesNotExist() throws Exception {
        this.parser.parse(this.stringToStream("header"));
    }

    @Test
    public void parsesTwoHeaders() throws Exception {
        Map<String, String> requestHeaders = this.parser.parse(this.stringToStream("Some: header\nAnother:header2\n\n"));
        assertEquals(2, requestHeaders.size());
        assertEquals("header", requestHeaders.get("Some"));
        assertEquals("header2", requestHeaders.get("Another"));
    }

    @Test
    public void stopsParsingAtAnEmptyLine() throws Exception {
        Map<String, String> requestHeaders = this.parser.parse(this.stringToStream("Some: header\n\nAnother:header2\n\n"));
        assertEquals("Request header parser returns the wrong number of headers.", 1, requestHeaders.size());
        assertTrue("Request header parser parses the header name.", requestHeaders.containsKey("Some"));
        assertEquals("Request header parser parses the header name.", "header", requestHeaders.get("Some"));
    }

    private InputStream stringToStream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }
}
