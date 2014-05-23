package com.terwer.qrcode.util;

/**
 * Created with IntelliJ IDEA.
 *
 * @version 1.0
 * @project terwer
 * @package com.xinvalue.util
 * @filename filename
 * @description 描述
 * @author: Terwer
 * @updatetime: 13-12-15 上午12:47
 * @site http://www.xinvalue.com
 * @mail cbgtyw@gmail.com
 * To change this template use File | Settings | File Templates.
 */
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class HttpUtil {

    /**
     * @param url url地址
     * @return 返回网页内容
     * @throws Exception
     */

    public static String get(String url) throws Exception {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        String resStr = "";

        for (String s = reader.readLine(); s != null; s = reader.readLine()) {

            resStr += s;

        }

        return resStr;

    }

    /**
     * @param url    网页地址
     * @param params 参数
     * @return 返回网页内容
     * @throws Exception
     */

    public static String post(String url, List < NameValuePair > params) throws Exception

    {

        String response = null;

        HttpClient httpclient = new DefaultHttpClient();

        // 创建HttpPost对象
        HttpPost httppost = new HttpPost(url);

        try

        {

            // 设置httpPost请求参数，设置字符集
            httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

            // 使用execute方法发送HTTP Post请求，并返回HttpResponse对象
            HttpResponse httpResponse = httpclient.execute(httppost);

            int statusCode = httpResponse.getStatusLine().getStatusCode();

            if (statusCode == HttpStatus.SC_OK)

            {

                // 获得返回结果
                response = EntityUtils.toString(httpResponse.getEntity());

            } else

            {

                response = "请求错误，返回码：" + statusCode;

            }

        } catch (Exception e)

        {

            e.getMessage();

        }

        return response;

    }

}