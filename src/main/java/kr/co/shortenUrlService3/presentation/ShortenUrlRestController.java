package kr.co.shortenUrlService3.presentation;

import jakarta.validation.Valid;
import kr.co.shortenUrlService3.application.SimpleShortenUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class ShortenUrlRestController {
  private SimpleShortenUrlService simpleShortenUrlService;

  @Autowired
  public ShortenUrlRestController(SimpleShortenUrlService simpleShortenUrlService) {
    this.simpleShortenUrlService = simpleShortenUrlService;
  }


  //단축 URL을 생성하는 API
  @PostMapping("/shortenUrl")
  public ShortenUrlCreateResponseDto createShortenUrl(
          @Valid @RequestBody ShortenUrlCreateRequestDto shortenUrlCreateRequestDto
  ) {
    //서비스 코드를 추가해야한다.
    //그리고 DTO타입으로 반환해야한다.
    ShortenUrlCreateResponseDto shortenUrlCreateResponseDto = simpleShortenUrlService.generateShortenUrl(shortenUrlCreateRequestDto);

    return shortenUrlCreateResponseDto;
  }

  //key를 넘겨주면 리다이렉트해주는 API
  @GetMapping("{shortenUrlKey}")
  public ResponseEntity<?> redirectShortenUrl(
          @PathVariable String shortenUrlKey
  ) {
    String originalUrl = simpleShortenUrlService.getOriginalUrlByShortenUrlKey(shortenUrlKey);
    URI redirectUri = URI.create(originalUrl);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setLocation(redirectUri);
    return new ResponseEntity<>(httpHeaders,HttpStatus.MOVED_PERMANENTLY);
  }

  //key를 넘겨주면 정보를 조회해주는 API
  @GetMapping("/shortenUrl/{shortenUrlKey}")
  public ShortenUrlInformationDto getShortenUrlInformation(
          @PathVariable String shortenUrlKey
  ) {
    ShortenUrlInformationDto shortenUrlInformationDto = simpleShortenUrlService.getShortenUrlInformationByShortenUrlKey(shortenUrlKey);

    return shortenUrlInformationDto;
  }

}
