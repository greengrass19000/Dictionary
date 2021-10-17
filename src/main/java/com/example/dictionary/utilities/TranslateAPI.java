package com.example.dictionary.utilities;

import okhttp3.*;

import java.io.IOException;
import java.util.Objects;

import org.apache.commons.lang.StringEscapeUtils;

public class TranslateAPI {
    private static final OkHttpClient client = new OkHttpClient();

    public static void main(String[] args) throws IOException {
        String text = "Hello world!";
        System.out.println(request(text));
    }

    public static String request(String text) throws IOException {
        String source = "en";
        String target = "vi";
        String url = "https://translate.astian.org/translate";

        String content = String.format(
                """
                { "q": "%s", "source": "%s", "target": "%s", "format": "text" }
                """,text, source, target);

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, content);

        Request request = new Request.Builder().url(url).post(body)
                .addHeader("Content-Type", "application/json").build();

        Response response = client.newCall(request).execute();
        String responseString = Objects.requireNonNull(response.body()).string();
        String cutString = responseString.substring(responseString.indexOf(":")+2, responseString.indexOf("}")-1);
        return StringEscapeUtils.unescapeJava(cutString);
    }
}