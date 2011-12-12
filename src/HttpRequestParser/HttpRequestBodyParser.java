package HttpRequestParser;

import HttpRequestParser.ContentTypeParsers.UrlEncodedFormParser;
import HttpRequestParser.Exceptions.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestBodyParser {

    public List<ContentTypeParser> contentTypeParsers;

    public HttpRequestBodyParser() {
        this.contentTypeParsers = new ArrayList<ContentTypeParser>();
        this.contentTypeParsers.add(new UrlEncodedFormParser());
    }

    public HttpRequestBodyParser(List<ContentTypeParser> contentTypeParsers) {
        this.contentTypeParsers = contentTypeParsers;
    }

    public Map<String, Object> parse(InputStream inputStream, String contentType, int contentLength) throws ParseException, IOException {
        for (ContentTypeParser parser : this.contentTypeParsers) {
            if (parser.canParseContentType(contentType)) {
                return parser.parse(inputStream, contentLength);
            }
        }
        throw new ParseException("Could not find an appropriate parser for the given content type.");
    }
}
