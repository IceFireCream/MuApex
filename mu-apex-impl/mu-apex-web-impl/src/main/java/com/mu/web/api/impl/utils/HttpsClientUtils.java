package com.mu.web.api.impl.utils;

import com.mu.web.api.impl.manager.MuX509TrustManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * 特供apex查询用的工具类
 * 请求头默认加上token
 * API来源：www.apexlegendsapi.com
 */
@Component
@RefreshScope
public class HttpsClientUtils {

    @Value("${apex.key}")
    private String key;
    @Value("${apex.tokenName}")
    private String tokenName;

    public String httpsGet(String apexUrl){

        try {
            URL url = new URL(apexUrl);
            System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
            //TLS
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null,
                    new TrustManager[]{new MuX509TrustManager()},
                    null);
            SSLSocketFactory factory = context.getSocketFactory();

            //得到访问对象
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            //设置请求参数
            connection.setDoOutput(false);
            connection.setDoInput(true);
            //设置请求方式
            connection.setRequestMethod("GET");
            //是否缓存
            connection.setUseCaches(false);
            //时候自动执行http重定向
            connection.setInstanceFollowRedirects(true);
            //超时
            connection.setConnectTimeout(10000);
            //设置请求头
            connection.setRequestProperty(tokenName, key);
            connection.setRequestProperty("User-Agent","Mozilla/5.0");

            //
            connection.setHostnameVerifier((s, sslSession) -> true);

            connection.setSSLSocketFactory(factory);

            connection.connect();

            int code = connection.getResponseCode();

            StringBuilder msg = new StringBuilder();
            if (code == 200){
                //正常响应
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = null;
                while ((line = reader.readLine()) != null){
                    msg.append(line);
                }
                reader.close();
                inputStreamReader.close();
                inputStream.close();
            }
            connection.disconnect();

            return msg.toString();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
