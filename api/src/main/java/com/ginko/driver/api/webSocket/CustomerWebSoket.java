package com.ginko.driver.api.webSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ginko.driver.common.entity.MsgConfig;
import com.ginko.driver.framework.entity.UserPartner;
import com.ginko.driver.framework.entity.WebSocketReturnType;
import com.ginko.driver.framework.service.PartnerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author: tran
 * @Description:支付时连接的websocket
 * @Date Create in 15:13 2019/8/17
 */
@ServerEndpoint(value = "/payment/pay")
@Component
public class CustomerWebSoket  {
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的CumWebSocket对象。
     */
    private static CopyOnWriteArraySet<CustomerWebSoket> webSocketSet = new CopyOnWriteArraySet<CustomerWebSoket>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */

    public static PartnerService partnerService;

    private Session session;

    private String code;

    private int userId;

    private String orderCode;

    private int partnerId;

    private String socketId;

    private String payType; //cancel





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
        String orderCode = ((JSONObject) json).getString("orderCode");
        if (getOrderCode()==null||getOrderCode().equals("")){
            setOrderCode(orderCode);
        }
        String payType = ((JSONObject) json).getString("payType");
        if (StringUtils.equals("cancel",payType)){
            System.out.println("交易取消，订单号为：" + orderCode);

            UserPartner userPartner = partnerService.findByOrderCode(orderCode);
            partnerService.clearLockAndCancelOrder(userPartner);

        }
        else{
            System.out.println("发起交易，订单号为：" + orderCode);
        }

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
     * 如果在交易给自身发送信息
     * @param message
     * @throws IOException
     */
  /*  public void sendBySocketId(int partnerId) throws IOException{
        Arrays.asList(webSocketSet.toArray()).forEach(item -> {
            CustomerWebSoket customWebSocket = (CustomerWebSoket) item;
            //判断当前是否已存在交易
            try {
                if (partnerId==customWebSocket.getPartnerId()){
                    WebSocketReturnType webSocketReturnType = new WebSocketReturnType();
                    webSocketReturnType.setResult(false);
                    webSocketReturnType.setType("connection");
                    String json = JSON.toJSONString(new MsgConfig("0",null,webSocketReturnType));
                    this.sendMessage(json);
                    this.onClose();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }*/

    /**
     * 给code发送信息
     * @param message
     * @throws IOException
     */
    public static void sendByOrderCode(WebSocketReturnType webSocketReturnType) throws IOException{
        for (Object customerWebSoketA:Arrays.asList(webSocketSet.toArray())){
            CustomerWebSoket customWebSocket = (CustomerWebSoket) customerWebSoketA;
            //群发
            try {
                if (StringUtils.equals(webSocketReturnType.getOrderCode(),customWebSocket.getOrderCode())){
                    String json = JSON.toJSONString(new MsgConfig("0",null,webSocketReturnType));
                    customWebSocket.sendMessage(json);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        CustomerWebSoket.onlineCount--;
    }

    /**
     * 添加在线人数
     */
    private void addOnlineCount() {
        CustomerWebSoket.onlineCount++;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }
}
