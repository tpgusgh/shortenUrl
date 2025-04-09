package kr.co.shortenUrlService3.presentation;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;


//사용자가 요청할 때 사용하는 데이터 형식(요청 DTO)
//실제로 요청이 날라올 때 JSON 형식으로 날라와서 스프링에서는 JSON이 DTO로 변환되어서 받아진다.
public class ShortenUrlCreateRequestDto {

  @NotNull
  @URL(regexp = "^((http|https)://)?(www.)?([a-zA-Z0-9]+)\\.[a-z]+([a-zA-z0-9.?#]+)?")
  private String originalUrl;

  public String getOriginalUrl() {
    return originalUrl;
  }
}
