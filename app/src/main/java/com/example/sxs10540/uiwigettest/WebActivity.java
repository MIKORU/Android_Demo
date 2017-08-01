package com.example.sxs10540.uiwigettest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sxs10540.uibean.App;
import com.example.sxs10540.uiinterface.HttpCallbackListener;
import com.example.sxs10540.uiutil.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WebActivity extends AppCompatActivity {

    private static final String TAG = "WebActivity";

    TextView responseText;
    String responseData;
    final String address = "172.16.27.13";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Button sendRequest = (Button) findViewById(R.id.send_request);
        responseText = (TextView) findViewById(R.id.response_view);
        WebView webView = (WebView) findViewById(R.id.web_view);

        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOkHttpRequest();
            }
        });

/**    页面调取百度页面
 webView.getSettings().setJavaScriptEnabled(true);
 webView.setWebViewClient(new WebViewClient());
 webView.loadUrl("https://www.baidu.com");
 **/
    }

    private void sendOkHttpRequest() {
        HttpUtil.sendOkHttpRequest(address, new okhttp3.Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(WebActivity.this, "oh no sorry", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseData = response.body().string();
                if (responseData != null) {
                    parseJSONWithGSON(responseData);
                }
            }
        });
    }

    private void sendHttpUrlRequest() {
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                parseJSONWithGSON(response);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(WebActivity.this, "oh no sorry", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sendRequsetWithOkConnection() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    //genymotion使用真机调试，无法用10.0.2.2，所以使用本地地址
                    Request request = new Request.Builder()
                            .url("your address")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithGSON(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    /***
     * Gson解析json：要与bean属性名称对应
     * @param jsonData
     */
    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        List<App> appList = gson.fromJson(jsonData,
                new TypeToken<List<App>>() {
                }.getType());
        for (App app : appList) {
            Log.d(TAG, "id is " + app.getId());
            Log.d(TAG, "name is " + app.getName());
            Log.d(TAG, "version is " + app.getVersion());
            showResponse(app);
        }
    }

    /**
     * JSONObject解析
     *
     * @param jsonData
     */
    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String version = jsonObject.getString("version");
                String name = jsonObject.getString("name");
                Log.d(TAG, id + " " + version + " " + name);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /***
     * XML解析
     * @param xmlData
     */
    private void parseXMLWithPull(String xmlData) {
        try {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();

            String id = "";
            String name = "";
            String version = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG: {
                        if ("id".equals(nodeName)) {
                            id = xmlPullParser.nextText();
                        } else if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                        } else if ("version".equals(nodeName)) {
                            version = xmlPullParser.nextText();
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG: {
                        if ("app".equals(nodeName)) {
                            Log.d(TAG, "id is " + id);
                            Log.d(TAG, "name is " + name);
                            Log.d(TAG, "version is " + version);
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 线程UI显示responseText
     *
     * @param app
     */
    private void showResponse(final App app) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText("name is" + app.getName() + "\n" +
                        "id is " + app.getId() + "\n" +
                        "version is " + app.getVersion() + "\n");
            }
        });
    }
}
