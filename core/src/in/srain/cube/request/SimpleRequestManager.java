package in.srain.cube.request;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import android.os.Handler;
import android.os.Message;

/**
 * @author http://www.liaohuqiu.net
 */
public class SimpleRequestManager {

    private final static int REQUEST_SUCCESS = 0x01;

    private final static int REQUEST_FAILED = 0x02;

    public static <T> void sendRequest(final IRequest<T> request) {

        request.beforeRequest();
        final Handler handler = new Handler() {
            @SuppressWarnings("unchecked")
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case REQUEST_SUCCESS:
                        request.onRequestSuccess((T) msg.obj);
                        break;

                    default:
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                JsonData json = null;
                try {
                    URL url = new URL(request.getRequestData().getRequestUrl());
                    URLConnection urlConnection = url.openConnection();
                    InputStream ips = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader buf = new BufferedReader(new InputStreamReader(ips, "UTF-8"));

                    StringBuilder sb = new StringBuilder();
                    String s;
                    while (true) {
                        s = buf.readLine();
                        if (s == null || s.length() == 0)
                            break;
                        sb.append(s);

                    }
                    json = JsonData.create(sb.toString());
                    buf.close();
                    ips.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                T data = request.processOriginDataFromServer(json);
                if (null == data) {
                    Message msg = Message.obtain();
                    msg.what = REQUEST_FAILED;
                    handler.sendMessage(msg);
                } else {
                    Message msg = Message.obtain();
                    msg.what = REQUEST_SUCCESS;
                    msg.obj = data;
                    handler.sendMessage(msg);
                }
            }
        }, "SimpleRequestBase-Manager").start();
    }
}
