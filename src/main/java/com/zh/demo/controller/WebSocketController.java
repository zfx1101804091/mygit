package com.zh.demo.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * websocket 简易聊天
 * http://coolaf.com/tool/chattest  测试网站
 *
 */
//由于是websocket 所以原本是@RestController的http形式
//直接替换成@ServerEndpoint即可，作用是一样的 就是指定一个地址
//表示定义一个websocket的Server端
@Component
@ServerEndpoint("/my-chat/{usernick}")
public class WebSocketController {
    private final static Logger log = Logger.getLogger(WebSocketController.class);


    @OnOpen
    public void onOpen(@PathParam(value = "usernick")String userNick, Session session){
        String message = "有新游客[" + userNick + "]加入聊天室!";
        log.info(message);
        WebSocketUtil.addSession(userNick, session);
        //此时可向所有的在线通知 某某某登录了聊天室
        WebSocketUtil.sendMessageForAll(message);
    }

    @OnClose
    public void onClose(@PathParam(value = "usernick") String userNick,Session session) {
        String message = "游客[" + userNick + "]退出聊天室!";
        log.info(message);
        WebSocketUtil.remoteSession(userNick);
        //此时可向所有的在线通知 某某某登录了聊天室
        WebSocketUtil.sendMessageForAll(message);
    }

    @OnMessage
    public void OnMessage(@PathParam(value = "usernick") String userNick, String message) {
        //类似群发
        String info = "游客[" + userNick + "]：" + message;
        log.info(info);
//        WebSocketUtil.sendMessageForAll(message);
        WebSocketUtil.sendMessageForAll("游客[" + userNick + "]：" + message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("异常:", throwable);
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throwable.printStackTrace();
    }
}
