package ws_demo;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.lang.String;
@ServerEndpoint("/chat")
public class ChatEndpoint {
    private static Set<Session>sessions=new CopyOnWriteArraySet<>();
    @OnOpen
    public void onOpen(Session session){
        sessions.add(session);
        System.out.println("新连接"+session.getId());
    }
    @OnMessage
    public void OnMessage(String message,Session session) throws IOException{
        System.out.println("收到消息"+message);
        try{
           JsonObject json=Json.createReader(new StringReader(message)).readObject();
            String sender = json.getString("from");
            String content=json.getString("text");
            chatDAO.SaveMessage(sender,content);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for(Session s:sessions){
            if(s.isOpen()){
                s.getBasicRemote().sendText(message);
            }
        }
    }
    @OnClose
    public void OnClose(Session session){
        sessions.remove(session);
        System.out.println("连接断开"+session.getId());
    }
    @OnError
    public void OnError(Session session,Throwable throwable){
        System.out.println("发生错误"+throwable.getMessage());
    }
}
