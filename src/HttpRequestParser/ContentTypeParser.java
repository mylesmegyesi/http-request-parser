package HttpRequestParser;

import HttpRequestParser.Exceptions.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Author: Myles Megyesi
 */
public interface ContentTypeParser {

    public boolean canParseContentType(String contentType);

    public Map<String, Object> parse(InputStream inputStream) throws ParseException, IOException;
}
