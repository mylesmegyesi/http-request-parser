package HttpRequestParser.ContentTypeParsers.Mocks;

import HttpRequestParser.ContentTypeParsers.UrlEncodedFormParser;
import HttpRequestParser.Utility.InputStreamReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Myles Megyesi
 */
public class UrlEncodedFormParserMock extends UrlEncodedFormParser {

    public int parseCalledCount = 0;
    public String parsePassed = "";

    @Override
    public Map<String, Object> parse(InputStream inputStream) {
        this.parseCalledCount++;
        try {
            this.parsePassed = new InputStreamReader().toString(inputStream);
        } catch (IOException e) {
        }
        return new HashMap<String, Object>();
    }
}
