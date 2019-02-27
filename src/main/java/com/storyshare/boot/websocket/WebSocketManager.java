//package com.storyshare.boot.websocket;
//
//import com.corundumstudio.socketio.*;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//
//import com.storyshare.boot.repositories.dto.MessageDTO;
//import com.storyshare.boot.services.MessageService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class WebSocketManager implements ServletContextListener {
////    @Value("${rt-server.host}")
////    private String host;
////    @Value("${rt-server.port}")
////    private Integer port;
//    @Autowired
//    private MessageService messageService;
//
//    public void contextInitialized(ServletContextEvent sce) {
//        Configuration config = new Configuration();
//        //.setHostname(System.getenv("HOSTNAME"));
//        config.setHostname("localhost");
//        //config.setHostname("https://limitless-anchorage-49854.herokuapp.com");
//        //config.setPort(Integer.parseInt(System.getenv("PORT")) + 1);
//        config.setPort(8181);
//
//        log.warn("NETTY SOCKET STARTED!!!!!!!!!!");
//        log.warn("NETTY SOCKET STARTED!!!!!!!!!!");
//        log.warn("NETTY SOCKET STARTED!!!!!!!!!!");
//        log.warn("NETTY SOCKET STARTED!!!!!!!!!!");
//
////        config.setHostname("localhost");
////        config.setPort(8181);
//
//        final SocketIOServer server = new SocketIOServer(config);
//
//        try {
//            server.addEventListener("join", String.class,
//                    (SocketIOClient socketIOClient, String roomNumber, AckRequest ackRequest) -> {
//                log.warn("CONNECTED!!!!!!!!!");
//                log.warn("CONNECTED!!!!!!!!!");
//                log.warn("CONNECTED!!!!!!!!!");
//                log.warn("CONNECTED!!!!!!!!!");
//                log.warn("CONNECTED!!!!!!!!!");
//                log.warn("CONNECTED!!!!!!!!!");
//                log.warn("CONNECTED!!!!!!!!!");
//                        socketIOClient.joinRoom(roomNumber);
//                    });
//
//            server.addEventListener("leave", String.class,
//                    (SocketIOClient socketIOClient, String roomNumber, AckRequest ackRequest) -> {
//                        log.warn("LEAVE!!!!!!!!!");
//                        log.warn("LEAVE!!!!!!!!!");
//                        log.warn("LEAVE!!!!!!!!!");
//                        log.warn("LEAVE!!!!!!!!!");
//                        log.warn("LEAVE!!!!!!!!!");
//
//                        socketIOClient.leaveRoom(roomNumber);
//                    });
//
//            server.addEventListener("sendMessage", MessageDTO.class,
//                    (SocketIOClient client, MessageDTO data, AckRequest ackRequest) -> {
//                        log.warn("SEND");
//                        log.warn("SEND");
//                        log.warn("SEND");
//                        log.warn("SEND");
//                        log.warn("SEND");
//                        server.getRoomOperations(getChatRoomNumber(data)).sendEvent("sendMessage", data);
//                        server.getRoomOperations(String.valueOf(data.getReceiverID())).sendEvent("sendMessage", data);
//                        server.getRoomOperations(String.valueOf(data.getSenderID())).sendEvent("sendMessage", data);
//                        messageService.save(data.getText(), data.getDate().toLocalDateTime(),
//                                data.getSenderID(), data.getReceiverID());
//                    });
//
//            server.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public void contextDestroyed(ServletContextEvent sce) {
//        log.info("Netty socket server has been stopped");
//        log.info("Netty socket server has been stopped");
//        log.info("Netty socket server has been stopped");
//        log.info("Netty socket server has been stopped");
//    }
//
//    private String getChatRoomNumber(MessageDTO data) {
//        return data.getSenderID() < data.getReceiverID()
//                ? data.getSenderID() + "@" + data.getReceiverID()
//                : data.getReceiverID() + "@" + data.getSenderID();
//    }
//}