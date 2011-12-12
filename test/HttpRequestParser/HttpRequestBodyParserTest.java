package HttpRequestParser;

import HttpRequestParser.ContentTypeParsers.Mocks.ContentTypeParserMock;
import HttpRequestParser.Exceptions.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestBodyParserTest {

    HttpRequestBodyParser parser;

    @Before
    public void setUp() throws Exception {
        this.parser = new HttpRequestBodyParser(new ArrayList<ContentTypeParser>());
    }

    @After
    public void tearDown() throws Exception {
        this.parser = null;
    }

    @Test
    public void callsTheFirstParserThatCanParse() throws Exception {
        ContentTypeParserMock contentTypeParserMock1 = new ContentTypeParserMock(false);
        ContentTypeParserMock contentTypeParserMock2 = new ContentTypeParserMock(true);
        this.parser.contentTypeParsers.add(contentTypeParserMock1);
        this.parser.contentTypeParsers.add(contentTypeParserMock2);
        this.parser.parse(null, null, 0);
        assertEquals(1, contentTypeParserMock2.parseCalledCount);
    }

    @Test(expected = ParseException.class)
    public void throwsWhenNoParsers() throws Exception {
        this.parser.parse(null, null, 0);
    }

    @Test(expected = ParseException.class)
    public void throwsWhenNoValid() throws Exception {
        ContentTypeParserMock contentTypeParserMock1 = new ContentTypeParserMock(false);
        ContentTypeParserMock contentTypeParserMock2 = new ContentTypeParserMock(false);
        this.parser.contentTypeParsers.add(contentTypeParserMock1);
        this.parser.contentTypeParsers.add(contentTypeParserMock2);
        this.parser.parse(null, null, 0);
    }

}
