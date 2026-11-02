package cetus.util;

import org.owasp.encoder.Encode;

public final class XSSUtil {

    public static String clean(String content) {
        // baseUri
        return Encode.forHtmlContent(content);
    }
}
