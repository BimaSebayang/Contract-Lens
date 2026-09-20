package com.contractlens.common.clawfo.response;

import com.contractlens.common.enums.WordingClawfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClawfoMappingResponse<T> {
    private String code;
    private MessageDto message;
    private T data;

    public static <T> ClawfoMappingResponse<T> of(
            WordingClawfo wording,
            T data
    ) {
        return ClawfoMappingResponse.<T>builder()
                .code(wording.getKey())
                .message(MessageDto.of(wording.getId(),wording.getEn()))
                .data(data)
                .build();
    }

    public static <T> ClawfoMappingResponse<T> of(
            String code,
            String message,
            T data
    ) {
        return ClawfoMappingResponse.<T>builder()
                .code(code)
                .message(MessageDto.of(message,message))
                .data(data)
                .build();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MessageDto{
        private String en;
        private String id;

        public static MessageDto of(
                String id,
                String en
        ) {
            return MessageDto.builder()
                    .en(en)
                    .id(id)
                    .build();
        }
    }

}

