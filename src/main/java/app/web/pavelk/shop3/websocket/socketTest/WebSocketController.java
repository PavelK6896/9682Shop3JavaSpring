package app.web.pavelk.shop3.websocket.socketTest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WebSocketController {

    private final List<Item> itemList = new ArrayList<>();
    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @ModelAttribute("items")                    // 1
    public List<Item> getItems() {
        return itemList;
    }

    @RequestMapping({"/socket2", "/socket.html"})       // 2
    public String get() {
        System.out.println("socket socket socket socket socket");
        return "page/socket/socket";
    }

    // 3
    @MessageMapping("/item") // вход — канал, куда JS-клиент отправляет сообщения
    @SendTo("/topic/items") // выход — канал, на который подписывается JS-клиент
    public Item addItem(String jsonString) throws JsonProcessingException {
        Item item = objectMapper.readValue(jsonString, Item.class);
        System.out.println("MessageMapping " + item);
        itemList.add(item);
        return item;
    }

    @Autowired
    private SimpMessagingTemplate template;

    @RequestMapping(value = "/socket2", method = RequestMethod.PUT)
    public ResponseEntity put(@RequestBody String body) throws Exception {
        System.out.println("RequestMapping PUT" + body);
        if (body != null && !body.trim().isEmpty()) {
            Item item = new Item();
            item.setContent(body);
            itemList.add(item);
            // оповещаем WebSocket-клиентов
            sendItem(item);
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public void sendItem(Item item) {
        System.out.println("///////////////// sendItem /////////////////////////");
        this.template.convertAndSend("/topic/items", item);
    }
}
