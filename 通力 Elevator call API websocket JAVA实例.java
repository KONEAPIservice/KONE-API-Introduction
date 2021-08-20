package kone.srd.ecosystem;

import java.util.concurrent.TimeUnit;

import okhttp3.*;
import okio.ByteString;

/**
 * KONE websocket API java sample
 *
 */
public class App 
{
	
    public static void main( String[] args ) 
    {
    	String token="token"; //粘贴KONE API的token
    	OkHttpClient mClient = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(3, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(3, TimeUnit.SECONDS)//设置连接超时时间
                .build();
        String url = "wss://dev.kone.cn/stream-v1?accessToken=" + token;
      //构建一个连接请求对象
                  Request request = new Request.Builder().get().url(url).addHeader("Sec-WebSocket-Protocol", "koneapi").build();

                  WebSocket websocket = mClient.newWebSocket(request, new WebSocketListener() {
                      @Override
                      public void onOpen(WebSocket webSocket, Response response) {
                          super.onOpen(webSocket, response);
                          System.out.println("server onOpen");
                          System.out.println("server request header:" + response.request().headers());
                          System.out.println("server response header:" + response.headers());
                          System.out.println("server response:" + response);
                      }

                      @Override
                      public void onMessage(WebSocket webSocket, String text) {
                          super.onMessage(webSocket, text);
                          System.out.println(text);
                      }

                      @Override
                      public void onMessage(WebSocket webSocket, ByteString bytes) {
                          super.onMessage(webSocket, bytes);
                        
                      }

                      @Override
                      public void onClosed(WebSocket webSocket, int code, String reason) {
                          super.onClosed(webSocket, code, reason);
                          System.out.println("Closed");
                          System.out.println(reason);
                      }

                      @Override
                      public void onFailure(WebSocket webSocket, Throwable throwable, Response response) {
                          super.onFailure(webSocket, throwable, response);
                          System.out.println(response);
                      }
                  });
                  //发送呼梯消息
                  String callMsg="{\r\n"
                  		+ "            \"type\": \"lift-call\",\r\n"
                  		+ "            \"callType\": \"normal\",\r\n"
                  		+ "            \"callAction\": \"destination\",\r\n"
                  		+ "            \"requestId\": \"01841d1c-f4ba-4f9c-a348-6f679bfae86e\",\r\n"
                  		+ "            \"buildingId\": \"building:9995000520\",\r\n"
                  		+ "            \"sourceId\": \"area:9995000520:3000\",\r\n"
                  		+ "            \"destinationId\": \"area:9995000520:10000\",\r\n"
                  		+ "            \"monitorEvents\": [\"call\",\"deck\",\"door\"],\r\n"
                  		+ "            \"keepAlive\":true,\r\n"
                  		+ "            \"passengerArrivalTimeSeconds\":\"10\"\r\n"
                  		+ "        }";
                  websocket.send(callMsg);
      }
}
