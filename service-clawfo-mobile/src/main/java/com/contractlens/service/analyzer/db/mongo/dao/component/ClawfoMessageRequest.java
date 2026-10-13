package com.contractlens.service.analyzer.db.mongo.dao.component;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClawfoMessageRequest extends ClawfoTimerComponent {

    private String messageId;

    private String type;

    private String name;

    private String message;

    private String time;

    @Override
    public String toString() {
        return "ClawfoMessageRequest{" +
                "messageId='" + messageId + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                ", time='" + time + '\'' +
                ", createdBy='" + getCreatedBy() + '\'' +
                ", createdDate=" + getCreatedDate() +
                ", updatedBy='" + getUpdatedBy() + '\'' +
                ", updatedDate=" + getUpdatedDate() +
                '}';
    }

}
