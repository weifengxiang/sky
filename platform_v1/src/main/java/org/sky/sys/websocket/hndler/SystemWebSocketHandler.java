package org.sky.sys.websocket.hndler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 *
 * @author dayu
 */
@Component
public class SystemWebSocketHandler implements WebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("connect to the websocket success......");
        session.sendMessage(new TextMessage("Server:connected OK!"));
    }

    @Override
    public void handleMessage(WebSocketSession wss, WebSocketMessage<?> wsm) throws Exception {
        TextMessage returnMessage = new TextMessage(wsm.getPayload()
				+ " received at server");
        System.out.println(wss.getHandshakeHeaders().getFirst("Cookie"));
		wss.sendMessage(returnMessage);
    }

    @Override
    public void handleTransportError(WebSocketSession wss, Throwable thrwbl) throws Exception {
        if(wss.isOpen()){
            wss.close();
        }
       System.out.println("websocket connection closed......");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession wss, CloseStatus cs) throws Exception {
        System.out.println("websocket connection closed......");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
    
}
