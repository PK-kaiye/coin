package pk_Crawler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.lang.StringUtils;

import com.google.gson.JsonObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SendNotice {

	public static void main(String[] args) throws Exception
	{
		while (true) {
			if (System.currentTimeMillis()%600000 == 0) {
				runJs("http://www.jinse.com/ajax/lives/getList?id=1&flag=up","金色财经");
				runBsj("币世界");
				run("火币");
				run1("https://support.binance.com/hc/zh-cn/categories/115000056351","https://support.binance.com/hc/zh-cn/articles/","币安");
				run1("https://help.big.one/hc/zh-cn/categories/115000217873","https://help.big.one/hc/zh-cn/articles/","bigone");
				run1("https://support.okex.com/hc/zh-cn/categories/115000275131","https://support.okex.com/hc/zh-cn/articles/","Okex");
			}
		}
		
		/*runJs("http://www.jinse.com/ajax/lives/getList?id=1&flag=up","金色财经");
		runBsj("币世界");
		run("火币");
		run1("https://support.binance.com/hc/zh-cn/categories/115000056351","https://support.binance.com/hc/zh-cn/articles/","币安");
		run1("https://help.big.one/hc/zh-cn/categories/115000217873","https://help.big.one/hc/zh-cn/articles/","bigone");
		run1("https://support.okex.com/hc/zh-cn/categories/115000275131","https://support.okex.com/hc/zh-cn/articles/","Okex");
		*/
	}
	
	static void runBsj(String name) throws Exception {
			
		//币世界
				//获取网站数据
		long timeMillis = System.currentTimeMillis();
		String temp = ""+timeMillis;
		String result = SendGet("http://www.bishijie.com/api/news/?size=50&timestamp="+temp.substring(0, 10));
		JSONObject json = JSONObject.fromObject(result);
				
		Date date = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		String format = sd.format(date);
				
		JSONObject jsonObject1 = json.getJSONObject("data");
		JSONObject jsonObject= jsonObject1.getJSONObject(format);
		JSONArray jsonArray = jsonObject.getJSONArray("buttom");
		for (int i = 0; i < 10; i++) {
					//封装notice
					Notice notice = new Notice();
					JSONObject jsonObject2 = jsonArray.getJSONObject(i);
					String id = jsonObject2.getString("newsflash_id");
					notice.setId(id);
					notice.setTitle(jsonObject2.getString("content"));
					notice.setLink(jsonObject2.getString("link"));
					notice.setState(0);
					notice.setFrom(name);
					notice.setCreatTime(timeMillis);
					//对比索引
					Notice notice1 = findById(id);
					//System.out.println(id);
					if (notice1 == null) {
						//保存到表
						addNotice(notice);
						//发送到公众号
						send(notice,"owPEc1UJCRCgMYLd4GiPZ1KBzanI");
						send(notice,"owPEc1b18fzzuTJ7kgEX_EATSvlo");
						send(notice,"owPEc1W77PCVIVhc7XK2ATXONGjo");
					}
				}
					
		
		}

	static void runJs(String url, String name) throws Exception {
		//金色财经
		//获取网站数据
		String result = SendGet(url);
		JSONObject json = JSONObject.fromObject(result);
		
		Date date = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		String format = sd.format(date);
		
		JSONObject jsonObject = json.getJSONObject("data");
		JSONArray jsonArray = jsonObject.getJSONArray(format);
		for (int i = 0; i < 10; i++) {
			//封装notice
			Notice notice = new Notice();
			JSONObject jsonObject2 = jsonArray.getJSONObject(i);
			String id = jsonObject2.getString("id");
			notice.setId(id);
			notice.setTitle(jsonObject2.getString("content"));
			notice.setLink(jsonObject2.getString("source_url"));
			notice.setState(0);
			notice.setFrom(name);
			long timeMillis = System.currentTimeMillis();
			notice.setCreatTime(timeMillis);
			//对比索引
			Notice notice1 = findById(id);
			//System.out.println(id);
			if (notice1 == null) {
				//保存到表
				addNotice(notice);
				//发送到公众号
				send(notice,"owPEc1UJCRCgMYLd4GiPZ1KBzanI");
				send(notice,"owPEc1b18fzzuTJ7kgEX_EATSvlo");
				send(notice,"owPEc1W77PCVIVhc7XK2ATXONGjo");
			}
		}
			
	}

static void run1(String url , String link , String name) throws Exception{
		
				@SuppressWarnings("unused")
				HttpsBerBer httpsBerBer = new HttpsBerBer(SendNotice.class.toString());
				
				String result1 = SendGet(url);
				String[] split = result1.split("<ul");
				
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
				
				String[] arrId = {id1,id2,id3,id4};
				String[] arrTitle = {title1,title2,title3,title4};
				
				for (int j = 0; j < arrTitle.length; j++) {
					Notice notice = new Notice();
					notice.setId(arrId[j]);
					notice.setTitle(arrTitle[j]);
					notice.setLink(link+arrId[j]);
					notice.setState(0);
					long timeMillis = System.currentTimeMillis();
					notice.setCreatTime(timeMillis);
					notice.setFrom(name);
					Notice notice1 = findById(arrId[j]);
					if (notice1 == null) {
						addNotice(notice);
						send(notice,"owPEc1UJCRCgMYLd4GiPZ1KBzanI");
						send(notice,"owPEc1b18fzzuTJ7kgEX_EATSvlo");
						send(notice,"owPEc1W77PCVIVhc7XK2ATXONGjo");
					}
				}
		
	}
	
	static void run(String name) throws Exception{
		
		//火币
				//获取网站数据
				String url = "https://www.huobi.com/p/api/contents/pro/list_notice?r=d4l8nqp1g28&page=1&limit=10&language=zh-cn";
				String result = SendGet(url);
				JSONObject json = JSONObject.fromObject(result);
				JSONObject jsonObject = json.getJSONObject("data");
				JSONArray jsonArray = jsonObject.getJSONArray("items");
				for (int i = 0; i < 10; i++) {
					//封装notice
					Notice notice = new Notice();
					JSONObject jsonObject2 = jsonArray.getJSONObject(i);
					String id = jsonObject2.getString("id");
					notice.setId(id);
					notice.setTitle(jsonObject2.getString("title"));
					notice.setLink("https://www.huobi.pro/zh-cn/notice_detail/?id="+id);
					notice.setState(0);
					notice.setFrom(name);
					long timeMillis = System.currentTimeMillis();
					notice.setCreatTime(timeMillis);
					//对比索引
					Notice notice1 = findById(id);
					//System.out.println(id);
					if (notice1 == null) {
						//保存到表
						addNotice(notice);
						//发送到公众号
						send(notice,"owPEc1UJCRCgMYLd4GiPZ1KBzanI");
						send(notice,"owPEc1b18fzzuTJ7kgEX_EATSvlo");
						send(notice,"owPEc1W77PCVIVhc7XK2ATXONGjo");
					}
				}
				
	}
	
	static void send(Notice notice , String touser) throws Exception{
		Token token = findToken();
		String errcode = sendMessage(token.getToken(), notice, touser);
		if (errcode.equals("40014")&&errcode.equals("40001")&&errcode=="40014"&&errcode=="40001") {
				updateToken(token);
				String access_token = getToken();
				String errcode2 = sendMessage(access_token, notice, touser);
				System.out.println("finalErrcode:"+errcode2);
		}else {
			System.out.println("Errcode:"+errcode);
		}
	}
	
	static String sendMessage(String token,Notice notice , String touser) throws Exception{
		
		//连接到微信
		URL url = new URL("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+token);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setConnectTimeout(30000);
		conn.setReadTimeout(30000);
		
		conn.connect();
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		//发送json
		out.write(jsonToString(notice , touser).getBytes());
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
		StringBuffer buffer = new StringBuffer();
		String strRead = null;
		//获取响应
		while ((strRead = reader.readLine()) != null) {
			buffer.append(strRead);
		}
		String string = buffer.toString();
		reader.close();
		conn.disconnect();
		JSONObject json = JSONObject.fromObject(string);
		String errcode = json.getString("errcode");
		return errcode;
	}
	
	static String getToken() throws Exception{
		//连接到微信
				URL url = new URL("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxc37ba719c20e07e1&secret=3feca7f6d094514c268db70c3afbdd43");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setDoOutput(true);
				conn.setDoInput(true);
				//请求到微信
				conn.connect();
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
				StringBuffer buffer = new StringBuffer();
				String strRead = null;
				//获取token
				while ((strRead = reader.readLine()) != null) {
					buffer.append(strRead);
				}
				String string = buffer.toString();
				reader.close();
				conn.disconnect();
				JSONObject json = JSONObject.fromObject(string);
				System.out.println(json);
				String access_token = json.getString("access_token");
				Token token = new Token();
				token.setToken(access_token);
				token.setState(0);
				addToken(token);
		
		return access_token;
	}
	
	//定义json
	static String jsonToString(Notice notice , String touser){
		
		JsonObject json1 = new JsonObject();
		json1.addProperty("value", notice.getTitle());
		json1.addProperty("color", "#173177");
		
		JsonObject json2 = new JsonObject();
		json2.addProperty("value", notice.getFrom());
		json2.addProperty("color", "#173177");
		
		JsonObject json4 = new JsonObject();
		json4.addProperty("value", "");
		json4.addProperty("color", "#173177");
		
		JsonObject json5 = new JsonObject();
		json5.add("from",json2);
		json5.add("first",json1);
		json5.add("remark",json4);
		
		JsonObject json = new JsonObject();
		json.addProperty("touser", touser );
		json.addProperty("template_id", "BDyu01vcAu3vBNuVMsovSaCwPLaGyetKyx0afg-0rPY");
		json.addProperty("url", notice.getLink());
		json.addProperty("topcolor", "#FF0000");
		json.add("data",json5);
		//System.out.println(json);
		String string = json.toString();
		return string;
	}
	
	//索引数据
		static Token findToken() throws SQLException {
						QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
						String sql = "select * from token where state=0";
						Token token = runner.query(sql, new BeanHandler<Token>(Token.class));
						return token;
					}
		//保存数据		 
		static int addToken(Token token) throws SQLException {
						QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
						String sql = "insert into token values(?,?)";
						int update = runner.update(sql,token.getToken(),token.getState());
						return update;
					}
		//保存数据		 
		static int updateToken(Token token) throws SQLException {
			QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
			String sql = "update token set state=1 where token=?";
			int update = runner.update(sql,token.getToken());
			return update;
		}
				 
	
	//索引数据
	static Notice findById(String id) throws SQLException {
					QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
					String sql = "select * from notice where id=?";
					Notice notice = runner.query(sql, new BeanHandler<Notice>(Notice.class), id);
					return notice;
				}
	//保存数据		 
	static int addNotice(Notice notice) throws SQLException {
					QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
					String sql = "insert into notice values(?,?,?,?,?,?)";
					int update = runner.update(sql, notice.getId(),notice.getTitle(),notice.getLink(),notice.getState(),notice.getCreatTime(),notice.getFrom());
					return update;
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



