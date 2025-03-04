package org.babyshark.chagok.domain.clova.application;

import lombok.RequiredArgsConstructor;
import org.babyshark.chagok.domain.clova.dto.ClovaDto;
import org.babyshark.chagok.domain.clova.dto.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
@RequiredArgsConstructor
public class ClovaService {

  @Value("${clova.api.url}")
  public String apiUrl;
  @Value("${clova.api.api-key}")
  private String apiKey;
  @Value("${clova.api.request-id}")
  private String requestId;
  private final WebClient webClient;

  // ENTJ 대화 메서드
  public Flux<String> createENTJPersona(String message) {
    ClovaDto.ChatBotRequestDto request = ClovaDto.ChatBotRequestDto.entjRequestOf();
    request.getMessages().add(Message.creatUserOf(message));

    return requestWebClient(request);
  }

  // ISTJ 대화 메서드
  public Flux<String> createISTJPersona(String message) {
    ClovaDto.ChatBotRequestDto request = ClovaDto.ChatBotRequestDto.istjRequestOf();
    request.getMessages().add(Message.creatUserOf(message));

    return requestWebClient(request);
  }

  // ENFP 대화 메서드
  public Flux<String> createENFPPersona(String message) {
    ClovaDto.ChatBotRequestDto request = ClovaDto.ChatBotRequestDto.enfpRequestOf();
    request.getMessages().add(Message.creatUserOf(message));

    return requestWebClient(request);
  }

  // ISFP 대화 메서드
  public Flux<String> createISFPPersona(String message) {
    ClovaDto.ChatBotRequestDto request = ClovaDto.ChatBotRequestDto.isfpRequestOf();
    request.getMessages().add(Message.creatUserOf(message));

    return requestWebClient(request);
  }

  // 예산안 짜주는 메서드
  public Flux<String> createAssetAssist(String message) {
    ClovaDto.ChatBotRequestDto request = ClovaDto.ChatBotRequestDto.AssistRequestOf();
    request.getMessages().add(Message.creatUserOf(message));

    return requestWebClient(request);
  }

  public Flux<String> createJson(String message) {
    ClovaDto.ChatBotRequestDto request = ClovaDto.ChatBotRequestDto.JsonRequestOf();
    request.getMessages().add(Message.creatUserOf(message));

    return requestWebClient(request);
  }
  // CLOVA와 통신하여 답변을 가져오는 메서드
  public Flux<String> requestWebClient(ClovaDto.ChatBotRequestDto request) {
    return webClient.post()
        .uri(apiUrl)
        .header("Authorization", "Bearer " + apiKey)
        .header("X-NCP-CLOVASTUDIO-REQUEST-ID", requestId)
        .header("Content-Type", "application/json")
//        .header("Accept", "text/event-stream; charset=utf-8") // 스트리밍 데이터 처리
        .body(Mono.just(request), request.getClass())
        .retrieve()
        .bodyToFlux(String.class); // 스트리밍 응답 처리
  }

  public Flux<String> requestStreamWebClient(ClovaDto.ChatBotRequestDto request) {
    return webClient.post()
        .uri("https://clovastudio.stream.ntruss.com/testapp/v1/chat-completions/HCX-003")
        .header("X-NCP-CLOVASTUDIO-API-KEY", "NTA0MjU2MWZlZTcxNDJiY0Ikp4zHEBcxTiaD/oP+FbX0Ki7+l6QfVnh0uiVX11R7")
        .header("X-NCP-APIGW-API-KEY","g6iD7ZjGVm206to5vDxXrVQNd9OaKJaO2ezNXyY3")
        .header("X-NCP-CLOVASTUDIO-REQUEST-ID", "bed143d429854c9b83d43f9ab1c698d7")
        .header("Content-Type", "application/json")
        .header("Accept", "text/json; charset=utf-8") // 스트리밍 데이터 처리
        .body(Mono.just(request), request.getClass())
        .retrieve()
        .bodyToFlux(String.class); // 모노 응답 처리
  }
}
