package HttpRequestParser.ContentTypeParsers;

import HttpRequestParser.Exceptions.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import static junit.framework.Assert.*;

/**
 * Author: Myles Megyesi
 */
public class UrlEncodedFormParserTest {

    UrlEncodedFormParser parser;

    @Before
    public void setUp() throws Exception {
        this.parser = new UrlEncodedFormParser();
    }

    @After
    public void tearDown() throws Exception {
        this.parser = null;
    }

    @Test
    public void parsesZeroParameters() throws Exception {
        assertEquals(0, this.parser.parse(this.stringToStream(""), 0).size());
    }

    @Test
    public void returnsTrueForTheRightContentType() throws Exception {
        assertTrue(this.parser.canParseContentType("application/x-www-form-urlencoded"));
    }

    @Test
    public void returnsFalseForTheWrongContentType() throws Exception {
        assertFalse(this.parser.canParseContentType("application/multipart"));
    }

    @Test
    public void parsesOneParameters() throws Exception {
        String request = "text=myText";
        Map<String, Object> params = this.parser.parse(this.stringToStream("text=myText"), request.length());
        assertEquals(1, params.size());
        assertTrue(params.containsKey("text"));
        assertEquals("myText", params.get("text"));
    }

    @Test
    public void parsesTwoParameters() throws Exception {
        String request = "text=myText&textTwo=Hello+Gnter";
        Map<String, Object> params = this.parser.parse(this.stringToStream(request), request.length());
        assertEquals(2, params.size());
        assertTrue(params.containsKey("textTwo"));
        assertEquals("Hello Gnter", params.get("textTwo"));
    }

    @Test
    public void returnsEmptyForNoValue() throws Exception {
        String request = "text=";
        Map<String, Object> params = this.parser.parse(this.stringToStream("text="), request.length());
        assertEquals(1, params.size());
        assertTrue(params.containsKey("text"));
        assertEquals("", params.get("text"));
    }

    @Test(expected = ParseException.class)
    public void throwsOnBadRequest() throws Exception {
        this.parser.parse(this.stringToStream("=ad"), 3);
    }

    private InputStream stringToStream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }
}
