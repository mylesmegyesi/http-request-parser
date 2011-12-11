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
        assertEquals(0, this.parser.parse(this.stringToStream("")).size());
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
        Map<String, Object> params = this.parser.parse(this.stringToStream("text=myText"));
        assertEquals(1, params.size());
        assertTrue(params.containsKey("text"));
        assertEquals("myText", params.get("text"));
    }

    @Test
    public void parsesTwoParameters() throws Exception {
        Map<String, Object> params = this.parser.parse(this.stringToStream("text=myText&textTwo=Hello+Gnter"));
        assertEquals(2, params.size());
        assertTrue(params.containsKey("textTwo"));
        assertEquals("Hello Gnter", params.get("textTwo"));
    }

    @Test
    public void returnsEmptyForNoValue() throws Exception {
        Map<String, Object> params = this.parser.parse(this.stringToStream("text="));
        assertEquals(1, params.size());
        assertTrue(params.containsKey("text"));
        assertEquals("", params.get("text"));
    }

    @Test(expected = ParseException.class)
    public void throwsOnBadRequest() throws Exception {
        this.parser.parse(this.stringToStream("=ad"));
    }

    private InputStream stringToStream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }
}
