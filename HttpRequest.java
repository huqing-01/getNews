package com.example.newsdemo.News;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class HttpRequest {   //这是我自己写的一个类

    /**
     * url发送get请求
     * 参数：type,String类型，key也是参数类型，不过每个人都有自己的参数key，
     * 这里就先不在方法的参数中写key了，所以参数只有两个，就是url和type
     */
//    String url = "http://zhouxunwang.cn/data/?id=75";
//    String key = "VOGU/IVuSNj+jJqB+oM6T2jHMgTgsJeZ/px16A";
    public static String sendPost(String type) {
        //这是接口的地址，当然它还需要参数
        String url = "http://zhouxunwang.cn/data/?id=75";
        String key = "VOGU/IVuSNj+jJqB+oM6T2jHMgTgsJeZ/px16A";
        //buffer用于接收返回的字符串
        StringBuffer result = null;
        BufferedReader in = null;

        try {
            //把url跟参数key和type连接起来，形成完整的url.
            url = url + "&key=" + key + "&type=" + type;
            //这里创建url
            URL realUrl = new URL(url);
            //打开和URL之间的链接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestProperty("Charset", "utf-8");
            //建立实际的连接
            connection.connect();

            result = new StringBuffer();
            //读取url的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null)
                result.append(line);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static void main(String args[]){
        String result = sendPost("top");
        System.out.println(result);   //result就是获取到的数据，但是它不是json格式,它是字符串类型
        //先将其转化为json类型然后获取到“result“json数据
        JSONObject object = JSONObject.fromObject(result).getJSONObject("result");
        System.out.println("************************");
        System.out.println(object);

        //下面这两种写法等同的，我一直用data=boject.getJSONObject("data"),一直报错
        //搞了半天才发现，data是数组，不能用getJSONObject方法，应该用getJSONArray方法
        //JSONArray data = JSONObject.fromObject(result).getJSONObject("result").getJSONArray("data");
        JSONArray data = object.getJSONArray("data");
        System.out.println(data);

        //data是个数组，但它里面却是一个个大括号的json对象，获取第0个元素，即第0个json对象，就要用getJSONObject(0)
        JSONObject data0 = data.getJSONObject(0);
        System.out.println(data0);

        //data0s是json对象，里面全是键值对，而且键和值都是字符串类型，获取title直接用data0.getString("title")
//        String title = data0.getString("title");
//        System.out.println(title);

        for(int i=0;i<data.size();i++){
            //这里是获取所有的title
            String title = data.getJSONObject(i).getString("title");
            System.out.println(title);
            //获取所有的时间
            String date = data.getJSONObject(i).getString("date");
            System.out.println(date);
            //获取所有的类型
            String category = data.getJSONObject(i).getString("category");
            System.out.println(category);
            //获取所有的作者
            String author_name = data.getJSONObject(i).getString("author_name");
            System.out.println(author_name);
            //获取所有的url
            String url = data.getJSONObject(i).getString("url");
            System.out.println(url);

        }
    }
}

