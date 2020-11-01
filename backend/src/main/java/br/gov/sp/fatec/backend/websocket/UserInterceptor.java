package br.gov.sp.fatec.backend.websocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.LinkedMultiValueMap;

public class UserInterceptor implements ChannelInterceptor {
  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
    Object headers = message.getHeaders().get(StompHeaderAccessor.NATIVE_HEADERS);

    if (accessor.getCommand().equals(StompCommand.CONNECT)) {

      if(headers instanceof LinkedMultiValueMap) {
        LinkedMultiValueMap<String, String> messageHeaders = (LinkedMultiValueMap<String, String>) headers;
        String username = messageHeaders.get("username").get(0);

        accessor.setUser(new UserSocket(username));
        accessor.getSessionAttributes().put("username", username);
      }
    }

    return message;
  }
}