package com.contractlens.service.analyzer.db.mongo.dao;

import com.contractlens.service.analyzer.db.mongo.dao.component.ClawfoMessageRequest;
import com.contractlens.service.analyzer.db.mongo.dao.component.ClawfoTimerComponent;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "clawfo_chat_document")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClawfoChatDocument extends ClawfoTimerComponent {

    @Id
    private String chatId;

    @Indexed
    private String laundryCode;

    private List<ClawfoMessageRequest> messages;


}
