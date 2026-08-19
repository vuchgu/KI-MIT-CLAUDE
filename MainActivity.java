package de.niclas.werkstatt;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainActivity extends Activity {

    private WebView web;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        web = new WebView(this);
        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setDatabaseEnabled(true);
        web.setWebViewClient(new WebViewClient());

        setContentView(web);

        // Basis-URL sorgt fuer eine echte Herkunft, damit localStorage funktioniert
        web.loadDataWithBaseURL(
                "https://werkstatt.local/",
                readAsset("app.html"),
                "text/html",
                "utf-8",
                null);
    }

    @Override
    public void onBackPressed() {
        if (web != null && web.canGoBack()) {
            web.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private String readAsset(String name) {
        StringBuilder sb = new StringBuilder();
        try {
            InputStream in = getAssets().open(name);
            BufferedReader r = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line).append('\n');
            }
            r.close();
        } catch (Exception e) {
            return "<html><body style=\"background:#101319;color:#E2E7ED;"
                 + "font-family:sans-serif;padding:24px\">"
                 + "app.html fehlt im Ordner app/src/main/assets/</body></html>";
        }
        return sb.toString();
    }
}
