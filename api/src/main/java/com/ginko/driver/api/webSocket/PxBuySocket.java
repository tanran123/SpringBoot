package com.ginko.driver.api.webSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ginko.driver.common.entity.MsgConfig;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author: tran
 * @Description:
 * @Date Create in 21:54 2019/8/17
 */

@ServerEndpoint(value = "/timesv/partner")
@Component
public class PxBuySocket {
    /**
     * 记录当前在线连接数
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的CumWebSocket对象。
     */
    private static CopyOnWriteArraySet<PxBuySocket> webSocketSet = new CopyOnWriteArraySet<PxBuySocket>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    private String code;

    private Integer userCode;



    /**
     * 连接建立成功调用的方法
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        //加入set中
        webSocketSet.add(this);
        //添加在线人数
        addOnlineCount();
        System.out.println("新连接接入。当前在线人数为：" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        //从set中删除
        webSocketSet.remove(this);
        //在线数减1
        subOnlineCount();
        System.out.println("有连接关闭。当前在线人数为：" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用
     *
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        JSON json = JSON.parseObject(message);
        if (userCode==null){
            String userCode = ((JSONObject) json).getString("userCode");
            setUserCode(Integer.parseInt(userCode));
        }
        System.out.println("客户端发送的消息：" + session.getId());
    }

    /**
     * 暴露给外部的群发
     *
     * @param message
     * @throws IOException
     */
    public static void sendInfo(String message) throws IOException {
        sendAll(message);
    }

    /**
     * 给特定UserId发送信息
     * @param message
     * @throws IOException
     */
    public static void sendByUserId(int userCode,Object message) throws IOException{
        Arrays.asList(webSocketSet.toArray()).forEach(item -> {
            PxBuySocket PxBuySocket = (PxBuySocket) item;
            try {
                if (userCode == PxBuySocket.getUserCode()){
                    String json = JSON.toJSONString(new MsgConfig(200,null,message));
                    PxBuySocket.sendMessage(json);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 群发
     *
     * @param message
     */
    private static void sendAll(String message) {
        Arrays.asList(webSocketSet.toArray()).forEach(item -> {
            CustomerWebSoket customWebSocket = (CustomerWebSoket) item;
            //群发
            try {
                customWebSocket.sendMessage(String.valueOf(customWebSocket.getUserId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("----websocket-------有异常啦");
        error.printStackTrace();
    }

    /**
     * 减少在线人数
     */
    private void subOnlineCount() {
        PxBuySocket.onlineCount--;
    }

    /**
     * 添加在线人数
     */
    private void addOnlineCount() {
        PxBuySocket.onlineCount++;
    }

    /**
     * 当前在线人数
     *
     * @return
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 发送信息
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        //获取session远程基本连接发送文本消息
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getUserCode() {
        return userCode;
    }


    public void setUserCode(Integer userCode) {
        this.userCode = userCode;
    }
}