package com.terwer.qrcode.left;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.zxing.Result;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.terwer.qrcode.R;
import com.terwer.qrcode.model.Book;
import com.terwer.qrcode.util.AppConstants;
import com.terwer.qrcode.util.HttpUtil;
import com.terwer.qrcode.util.Util;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.zip.GZIPInputStream;

/**
 * Created by Tangyouwei on 2014/5/22.
 */
public class LineReadActivity extends FragmentActivity {

    private ImageView img_book_cover;

    private TextView txt_book_title;
    private TextView txt_author_name;
    private TextView txt_publisher;
    private TextView txt_book_content;
    private TextView txt_pages;
private  TextView txt_price;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.line_read);

        //扫描信息展示
        String decode_result = "";
        String contents = "";
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            decode_result = bundle.getString("Contents");
            //Util.showAlert(LineReadActivity.this, decode_result);
        }
        Gson gson = new Gson();
        Result result=gson.fromJson(decode_result,Result.class);
        //Util.showAlert(LineReadActivity.this,result.getText());
        ////////////////////////////////////////////////////////////


        //获取图书信息
//        try {
//            String url="http://api.douban.com/book/subject/isbn/"+contents;
//            Log.i("Msg",url);
//            Util.showAlert(LineReadActivity.this,url);
//            String data= HttpUtil.get(url);
//            Util.showAlert(LineReadActivity.this,data);
//
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //检测网络
        if (!Util.isNetworkConnected(LineReadActivity.this)){
            Util.showAlert(LineReadActivity.this,"当前网络不可用，请检查您的网络连接！");
            return;
        }

        Book book = null;
        try {
            String url = "http://api.douban.com/book/subject/isbn/" + result.getText().trim();
            //Log.i("Msg",url);
            //Util.showAlert(LineReadActivity.this,url);
            String data = getHttpContent(url);
            //Util.showAlert(LineReadActivity.this, data);
            //String author_name=StringUtils.substringBetween(data.trim(),"<name>","</name>");
            //Util.showAlert(LineReadActivity.this,author_name);
            book = getBookInfo(data);
            //Util.showAlert(LineReadActivity.this, book.getAuthor_name() + book.getTitle()+book.getPrice()+book.getPublisher()+book.getPages()+book.getPubdate()+book.getBook_cover());

        } catch (Exception e) {
            Util.showAlert(LineReadActivity.this, "错误！" + e.getMessage());
            e.printStackTrace();
        }

        //设置信息

        //初始化图片加载
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .showImageForEmptyUri(R.drawable.blue_bkg)
                .showStubImage(R.drawable.blue_bkg)
                .showImageOnFail(R.drawable.blue_bkg)
                .build();


        // Create global configuration and initialize ImageLoader with this configuration
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        //ImageLoader.getInstance().init(config);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);


        img_book_cover = (ImageView) findViewById(R.id.img_book_cover);
        //String imageUri = "http://img5.douban.com/spic/s9097939.jpg";
        String imageUri = book.getBook_cover();
        // Load image, decode it to Bitmap and display Bitmap in ImageView (or any other view
        //  which implements ImageAware interface)
        imageLoader.displayImage(imageUri, img_book_cover);

        txt_book_title = (TextView) findViewById(R.id.txt_book_title);
        txt_book_title.setText(book.getTitle());

        txt_author_name = (TextView) findViewById(R.id.txt_author_name);
        txt_author_name.setText(book.getAuthor_name());

        txt_publisher = (TextView) findViewById(R.id.txt_publisher);
        txt_publisher.setText(book.getPublisher());

        txt_pages = (TextView) findViewById(R.id.txt_pages);
        txt_pages.setText("共"+book.getPages()+"页");

        txt_book_content = (TextView) findViewById(R.id.txt_book_content);
        txt_book_content.setText(book.getContent());

        txt_price=(TextView)findViewById(R.id.txt_price);
        txt_price.setText(book.getPrice());

    }


    /**
     * 自定义Http请求
     *
     * @param url
     * @return
     */
    public static String getHttpContent(String url) {
        StringBuffer sbuff = new StringBuffer();
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(
                    url)
                    .openConnection();
            conn.setConnectTimeout(3000);
            conn.setRequestMethod("GET");
            if (conn != null && conn.getResponseCode() != 200) {
                return null;
            }
//得到读取的内容(流)
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
// 为输出创建BufferedReader
            BufferedReader buffer = new BufferedReader(in);
            String inputLine = null;
//使用循环来读取获得的数据
            while (((inputLine = buffer.readLine()) != null)) {
                sbuff.append(inputLine);
            }
//关闭InputStreamReader
            in.close();
//关闭http连接
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sbuff.toString();
    }


    /**
     * 根据返回内容获取有用的内容信息
     *
     * @param content
     * @return
     */
    private String getContents(String content) {
        return StringUtils.substringsBetween(content, "Contents:", "Raw")[0].trim();
    }


    /**
     * 获取图书信息
     *
     * @param info
     * @return
     */
    private Book getBookInfo(String info) {
        Book book = new Book();
        info = info.trim();
        book.setAuthor_name(StringUtils.substringBetween(info, "<name>", "</name>"));
        book.setTitle(StringUtils.substringBetween(info, "<title>", "</title>"));
        book.setPrice(StringUtils.substringBetween(info, "price\">", "</db"));
        book.setPublisher(StringUtils.substringBetween(info, "publisher\">", "</db"));
        book.setPubdate(StringUtils.substringBetween(info, "pubdate\">", "</db"));
        book.setPages(StringUtils.substringBetween(info, "pages\">", "</db"));
        book.setBook_cover(StringUtils.substringBetween(info, "rel=\"alternate\"/>", "rel=\"image\"/>").replace("<link href=\"", "").replace("\"", "").trim());
        book.setContent(StringUtils.substringBetween(info, "<summary>", "</summary>"));
        return book;
    }

}
