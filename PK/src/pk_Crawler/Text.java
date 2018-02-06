/*package pk_Crawler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.mysql.fabric.xmlrpc.base.Data;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Text {
	@Test
	public void test1(){
		
		String url = "https://support.binance.com/hc/zh-cn/categories/115000056351";
		String result = SendGet(url);
		String[] split = result.split("<ul");
		
		String[] split2 = split[1].split("articles/");
		String id1 = split2[1].substring(0,12);
		String id2 = split2[2].substring(0,12);
		
		String[] split3 = split[1].split("link\">");
		String title1 = StringUtils.substringBefore(split3[1], "</a>");
		String title2 = StringUtils.substringBefore(split3[2], "</a>");
		
		
		String[] split4 = split[2].split("articles/");
		String id3 = split4[1].substring(0,12);
		String id4 = split4[2].substring(0,12);
		
		String[] split5 = split[2].split("link\">");
		String title3 = StringUtils.substringBefore(split5[1], "</a>");
		String title4 = StringUtils.substringBefore(split5[2], "</a>");
		
		Notice notice1 = new Notice();
		Notice notice2 = new Notice();
		Notice notice3 = new Notice();
		Notice notice4 = new Notice();
		
		notice1.setId(id2);
		notice1.setTitle(title2);
		notice1.setLink("https://support.binance.com/hc/zh-cn/articles/"+id2);
		notice1.setState(0);
		
		notice1.setId(id1);
		notice1.setTitle(title1);
		notice1.setLink("https://support.binance.com/hc/zh-cn/articles/"+id1);
		notice1.setState(0);
		
		notice1.setId(id3);
		notice1.setTitle(title3);
		notice1.setLink("https://support.binance.com/hc/zh-cn/articles/"+id3);
		notice1.setState(0);
		
		notice1.setId(id4);
		notice1.setTitle(title4);
		notice1.setLink("https://support.binance.com/hc/zh-cn/articles/"+id4);
		notice1.setState(0);
		
		
		System.out.println(title1);
		System.out.println(title2);
		System.out.println(title3);
		System.out.println(title3);
		System.out.println(id1);
		System.out.println(id2);
		System.out.println(id3);
		System.out.println(id4);
		
		
		
		//System.out.println(result);
		
	}
	
	@Test
	public void test2() throws Exception{
		
		HttpsBerBer httpsBerBer = new HttpsBerBer(Text.class.toString());
		String url = "http://www.jinse.com/ajax/lives/getList?id=1&flag=up";
		String result = SendGet(url);
		//System.out.println(result);
		JSONObject json = JSONObject.fromObject(result);
		JSONObject jsonObject = json.getJSONObject("data");
		System.out.println(jsonObject);
		JSONArray jsonArray = jsonObject.getJSONArray("2018-02-01");
		System.out.println(jsonArray.getJSONObject(0));
	}
	
	@Test
	public void test8() throws Exception{
		
		long timeMillis = System.currentTimeMillis();
		String temp = ""+timeMillis;
		String substring = temp.substring(0, 10);
		System.out.println(substring);
		String result = SendGet("http://www.bishijie.com/api/news/?size=50&timestamp="+substring);
		JSONObject json = JSONObject.fromObject(result);
		System.out.println(result);
	}
	
	@Test
	public void test6() throws Exception{
		
		HttpsBerBer httpsBerBer = new HttpsBerBer(Text.class.toString());
		String url = "https://support.okex.com/hc/zh-cn/categories/115000275131";
		String result = SendGet(url);
		System.out.println(result);
	}
	
	@Test
	public void test5(){
		
		
		
	}
	
	@Test
	public void test3() throws Exception{
		
		URL url1 = new URL("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx0eaeb54dc9775039&secret=42bbd020be0b5bbb3fd1a8fc0ab6005c");
		HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
		conn1.setRequestMethod("GET");
		conn1.setDoOutput(true);
		conn1.setDoInput(true);
		conn1.connect();
		BufferedReader reader1 = new BufferedReader(new InputStreamReader(conn1.getInputStream(),"UTF-8"));
		StringBuffer buffer1 = new StringBuffer();
		String strRead1 = null;
		while ((strRead1 = reader1.readLine()) != null) {
			buffer1.append(strRead1);
		}
		String string1 = buffer1.toString();
		reader1.close();
		conn1.disconnect();
		System.out.println(string1);
		JSONObject json = JSONObject.fromObject(string1);
		String access_token = json.getString("access_token");
		
		
		URL url = new URL("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.connect();
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.writeBytes(jsonToString());
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
		StringBuffer buffer = new StringBuffer();
		String strRead = null;
		while ((strRead = reader.readLine()) != null) {
			buffer.append(strRead);
		}
		String string = buffer.toString();
		reader.close();
		conn.disconnect();
		System.out.println(string);
		
	}
	
	static String jsonToString(){
		
		JsonObject json1 = new JsonObject();
		json1.addProperty("value", "11");
		json1.addProperty("color", "#173177");
		
		JsonObject json2 = new JsonObject();
		json2.addProperty("value", "22");
		json2.addProperty("color", "#173177");
		
		JsonObject json4 = new JsonObject();
		json4.addProperty("value", "");
		json4.addProperty("color", "#173177");
		
		JsonObject json5 = new JsonObject();
		json5.add("first",json1);
		json5.add("from",json2);
		json5.add("remark",json4);
		System.out.println(json5);
		
		JsonObject json = new JsonObject();
		json.addProperty("touser", "o7PJVwejtW1KNc7vk97RFj5tkm8A");
		json.addProperty("template_id", "4BpYZQDJLHJKz_bqGhticCNgQX_5cB3Eb6pZU7pKfU4");
		json.addProperty("url", "https://support.binance.com/hc/zh-cn/articles/360000447931");
		json.addProperty("topcolor", "#FF0000");
		json.add("data",json5);
		System.out.println(json);
		String string = json.toString();
		return string;
	}
	
	static String SendGet(String url)
	{
		String result = "";
		BufferedReader in = null;
		try
		{
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			connection.connect();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null)
			{
				result += line;
			}
		} catch (Exception e)
		{
			System.out.println("错误" + e);
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (in != null)
				{
					in.close();
				}
			} catch (Exception e2)
			{
				e2.printStackTrace();
			}
		}
		return result;
	}

	static String RegexString(String targetStr, String patternStr)
	{
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(targetStr);
		if (matcher.find())
		{
			return matcher.group(1);
		}
		return "Nothing";
	}
	
}
*/