package org.babyshark.chagok.domain.clova.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ClovaDto {

  @Builder
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public static class ChatBotRequestDto {

    private ArrayList<Message> messages;
    private double topP;
    private double topK;
    private double temperature;
    private int maxTokens;
    private double repeatPenalty;
    private boolean includeAiFilters;

    public static ChatBotRequestDto entjRequestOf() {
      ArrayList<Message> messages = new ArrayList<>();
      messages.add(Message.createENTJOf());

      return ChatBotRequestDto.builder()
          .messages(messages)
          .topP(0.8)
          .topK(0)
          .temperature(0.65)
          .maxTokens(2000)
          .repeatPenalty(5.0)
          .includeAiFilters(true)
          .build();
    }

    public static ChatBotRequestDto istjRequestOf() {
      ArrayList<Message> messages = new ArrayList<>();
      messages.add(Message.createISTJOf());

      return ChatBotRequestDto.builder()
          .messages(messages)
          .topP(0.8)
          .topK(0)
          .temperature(0.3)
          .maxTokens(2000)
          .repeatPenalty(5.0)
          .build();
    }

    public static ChatBotRequestDto enfpRequestOf() {
      ArrayList<Message> messages = new ArrayList<>();
      messages.add(Message.createENFPOf());

      return ChatBotRequestDto.builder()
          .messages(messages)
          .topP(0.8)
          .topK(0)
          .temperature(0.3)
          .maxTokens(2000)
          .repeatPenalty(5.0)
          .build();
    }

    public static ChatBotRequestDto isfpRequestOf() {
      ArrayList<Message> messages = new ArrayList<>();
      messages.add(Message.createISFPOf());

      return ChatBotRequestDto.builder()
          .messages(messages)
          .topP(0.8)
          .topK(0)
          .temperature(0.3)
          .maxTokens(2000)
          .repeatPenalty(5.0)
          .build();
    }

    public static ChatBotRequestDto AssistRequestOf() {
      ArrayList<Message> messages = new ArrayList<>();
      messages.add(Message.createAssistOf());

      return ChatBotRequestDto.builder()
          .messages(messages)
          .topP(0.8)
          .topK(0)
          .temperature(0.3)
          .maxTokens(2000)
          .repeatPenalty(5.0)
          .build();
    }

    public static ChatBotRequestDto JsonRequestOf() {
      ArrayList<Message> messages = new ArrayList<>();
      messages.add(Message.createJsonOf());

      return ChatBotRequestDto.builder()
          .messages(messages)
          .topP(0.8)
          .topK(0)
          .temperature(0.3)
          .maxTokens(2000)
          .repeatPenalty(5.0)
          .build();
    }
  }

  @Builder
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ChatBotResponse {

    private Result result;
  }

}
