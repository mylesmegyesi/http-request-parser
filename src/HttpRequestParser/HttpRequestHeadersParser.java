package HttpRequestParser;

import HttpRequestParser.Exceptions.ParseException;
import HttpRequestParser.Utility.InputStreamReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestHeadersParser {

    public InputStreamReader inputStreamReader;

    public HttpRequestHeadersParser(InputStreamReader inputStreamReader) {
        this.inputStreamReader = inputStreamReader;
    }

    public Map<String, String> parse(InputStream inputStream) throws ParseException, IOException {
        Map<String, String> requestHeaders = new HashMap<String, String>();
        String nextLine;
        while (!(nextLine = this.inputStreamReader.getNextLine(inputStream)).equals("")) { // headers are terminated by an empty line
            String[] header = this.parseHeader(nextLine);
            requestHeaders.put(header[0], header[1]);
        }
        return requestHeaders;
    }

    private String[] parseHeader(String headerLine) throws ParseException {
        int colonIndex = headerLine.indexOf(':');
        if (colonIndex == -1 || colonIndex == 0 || colonIndex == headerLine.length() - 1) {
            throw new ParseException(String.format("Improperly formatted header: %s", headerLine));
        }
        return new String[]{headerLine.substring(0, colonIndex), headerLine.substring(colonIndex + 1, headerLine.length()).trim()};
    }
}
