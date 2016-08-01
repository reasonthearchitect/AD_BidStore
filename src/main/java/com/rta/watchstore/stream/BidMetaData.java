package com.rta.watchstore.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public interface BidMetaData {

    @Output("newbid")
    MessageChannel post();
}
