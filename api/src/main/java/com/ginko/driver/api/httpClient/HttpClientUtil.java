package com.ginko.driver.api.httpClient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.ginko.driver.common.tolls.TokenTools;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: tran
 * @Date: 2019/11/13
 * @Description:
 */
public class HttpClientUtil {
    /**
     * Http请求工具类,发送json返回json
     * <p>
     * 除了要导入json-lib-2.1.jar之外，还必须有其它几个依赖包：
     * commons-beanutils.jar
     * commons-httpclient.jar
     * commons-lang.jar
     * ezmorph.jar
     * morph-1.0.1.jar
     * 另外，commons-collections.jar也需要导入
     */
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 发送get请求
     *
     * @param url 路径
     * @return
     */
    public static JSONObject httpGet(String url) {

        //get请求返回结果
        JSONObject jsonResult = null;
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());
                /**把json字符串转换成json对象**/
                jsonResult = JSON.parseObject(strResult);
                try {
                    url = URLDecoder.decode(url, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                logger.error("get请求提交失败:" + url);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("get请求提交失败:" + url, e);
        }
        return jsonResult;
    }

    /**
     * httpPost
     *
     * @param url       路径
     * @param jsonParam 参数
     * @return
     */
    public static JSON httpPost(String url, String jsonParam,String token) {
        return httpPost(url, jsonParam,token, false);
    }

    /**
     * post请求
     *
     * @param url            url地址
     * @param jsonParam      参数
     * @param noNeedResponse 不需要返回结果
     * @return
     */
    public static JSON httpPost(String url, String jsonParam,String token, boolean noNeedResponse) {

        //post请求返回结果
        DefaultHttpClient httpClient = new DefaultHttpClient();
        JSON jsonResult = null;
        HttpPost method = new HttpPost(url);
      /*  String token1 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJleHAiOjE1NzQyOTk1ODQsImlhdCI6MTU3MzY5NDc4NCwiaXNzIjoidGltZXN2IiwiZGF0YSI6eyJ1c2VySWQiOjMxfX0.pE0FkgHYS1_Cc_ZYyAaCy8UgOCA4Hccn5pyuvXJX76Wa93LsQg3g6GrLXD2hPk0VeXaP_yQUPJFLbZUBtjTs2VghXwlbqylxLnL8t_xFlV2CdRrPmqWtAucQr5eRBlcjSfeC-yLQSFLFy0kMJxfNy3xTSUF9t8iTY_3pfyRc_xqZZnBVKwT-gSH14SbtKj_RNm4wdDoxC4-gwdFbPUUSFsHJHdIWP8TsDRyfJ0dDNV2t_eSsI3XXVi8cKLoVobPASKesDzwiEEKPYDcTUZE7BOJBMY8xSdgwWpE2aLrun8KNxfFMpx5f2w_6hnrYp9WQmJwfvjMx4K-KlOvuJ1e_kg";
        method.setHeader("Authorization", token1);*/
        method.setHeader("Authorization",token);
        try {
            if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    str = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                    /**把json字符串转换成json对象**/
                    jsonResult = JSON.parseObject(str);
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        }
        return jsonResult;
    }

    /**
     * get
     *
     * @param url 请求地址
     * @param par 请求参数
     * @return
     */
    public static JSON httpGet(String url, String key) {
        JSON jsonResult = null;
        //获取请求参数
        List<NameValuePair> parame = new ArrayList<NameValuePair>();
        parame.add(new BasicNameValuePair("参数名", key));
        // 获取httpclient
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String parameStr = null;
        try {
            parameStr = EntityUtils.toString(new UrlEncodedFormEntity(parame));
            //拼接参数
            StringBuffer sb = new StringBuffer();
            sb.append(url);
            sb.append("?");
            sb.append(parameStr);
            //创建get请求
            HttpGet httpGet = new HttpGet(sb.toString());
            // 设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(2000).setConnectTimeout(2000).build();
            httpGet.setConfig(requestConfig);
            // 提交参数发送请求
            response = httpclient.execute(httpGet);

            // 得到响应信息
            int statusCode = response.getStatusLine().getStatusCode();
            // 判断响应信息是否正确
            if (statusCode != HttpStatus.SC_OK) {
                // 终止并抛出异常
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                jsonResult = JSON.parseObject(result);
            }
            EntityUtils.consume(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭所有资源连接
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonResult;

    }

    /**
     * 获取BSV对人民币汇率
     * @return
     */
    public static BigDecimal getCny(){
        JSON j = httpGet("https://api.coingecko.com/api/v3/simple/price?ids=bitcoin-cash-sv&vs_currencies=cny");
        Double cny = ((JSONObject) j).getJSONObject("bitcoin-cash-sv").getDouble("cny");
        return new BigDecimal(cny);
    }

    public static void main(String[] args) {
        //https://api.coingecko.com/api/v3/simple/price?ids=bitcoin-cash-sv&vs_currencies=cny --人民币兑换BSV
        String json = "{\"orderId\":\"7224dcb1006845f5b2f547249dea24a\"}";
        String url = "https://www.timesv.com/timesv/order/v1/wechat/qrcode/generate";
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJleHAiOjE1NzQyOTk1ODQsImlhdCI6MTU3MzY5NDc4NCwiaXNzIjoidGltZXN2IiwiZGF0YSI6eyJ1c2VySWQiOjMxfX0.pE0FkgHYS1_Cc_ZYyAaCy8UgOCA4Hccn5pyuvXJX76Wa93LsQg3g6GrLXD2hPk0VeXaP_yQUPJFLbZUBtjTs2VghXwlbqylxLnL8t_xFlV2CdRrPmqWtAucQr5eRBlcjSfeC-yLQSFLFy0kMJxfNy3xTSUF9t8iTY_3pfyRc_xqZZnBVKwT-gSH14SbtKj_RNm4wdDoxC4-gwdFbPUUSFsHJHdIWP8TsDRyfJ0dDNV2t_eSsI3XXVi8cKLoVobPASKesDzwiEEKPYDcTUZE7BOJBMY8xSdgwWpE2aLrun8KNxfFMpx5f2w_6hnrYp9WQmJwfvjMx4K-KlOvuJ1e_kg";
        JSON J = httpPost(url,json,token);
        System.out.println(J);
    }
}
