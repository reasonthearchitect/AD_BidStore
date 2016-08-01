package com.rta.watchstore.stream;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rta.watchstore.dto.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import lombok.extern.java.Log;

@Log
@Component
@EnableBinding(BidMetaData.class)
public class BidSource {

    @Autowired @Qualifier("newbid")
    private MessageChannel post;

    @Autowired
    ObjectMapper mapper;

    public void send(Bid bid) {
        try {
            Message<?> message = MessageBuilder.withPayload(
                    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bid)
            )
                    .setHeader("contentType", "application/json")
                    .build();
            post.send(message);
        } catch (Exception ex) {
            log.log(Level.SEVERE, "Error trying to send a message to a queue: ", ex);
        }
    }
}
