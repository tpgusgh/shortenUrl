package kr.co.shortenUrlService3.application;

import kr.co.shortenUrlService3.domain.ShortenUrl;
import kr.co.shortenUrlService3.domain.ShortenUrlRepository;
import kr.co.shortenUrlService3.presentation.ShortenUrlCreateRequestDto;
import kr.co.shortenUrlService3.presentation.ShortenUrlCreateResponseDto;
import kr.co.shortenUrlService3.presentation.ShortenUrlInformationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



//서비스 코드의 역할은
//컨트롤러로 부터 넘어온 requestDto를 가지고 도메인객체를 만들어서 레포지토리에게 전달
//그리고 만든 도메인 객체를 반환하기위해 responseDto로 변환하여 반환
@Service
public class SimpleShortenUrlService {
  private final ShortenUrlRepository shortenUrlRepository;

  @Autowired
  public SimpleShortenUrlService(ShortenUrlRepository shortenUrlRepository) {
    this.shortenUrlRepository = shortenUrlRepository;
  }


  public ShortenUrlCreateResponseDto generateShortenUrl(
          ShortenUrlCreateRequestDto shortenUrlCreateRequestDto
  ) {
    //해야하는 일 4가지
    //1. 원래 URL과 단축URL키 생성
    String originalUrl = shortenUrlCreateRequestDto.getOriginalUrl();
    String shortenUrlkey = ShortenUrl.generateShortenUrlKey();

    //2. 원래 URL과 단축 URL키를 통해 ShortenUrl 객체 생성
    ShortenUrl shortenUrl = new ShortenUrl(originalUrl, shortenUrlkey);

    //생성된 ShortenUrl 객체를 레포지토리를 통해 저장
    shortenUrlRepository.saveShortenUrl(shortenUrl);

    //ShortenUrl을 ShortenUrlCreateResponseDto로 변환하여서 반환.
    ShortenUrlCreateResponseDto shortenUrlCreateResponseDto
            = new ShortenUrlCreateResponseDto(shortenUrl);


    return shortenUrlCreateResponseDto;
  }

  public ShortenUrlInformationDto getShortenUrlInformationByShortenUrlKey(String shortenUrlKey) {
    //shortenUrlInfo를 찾기 전에 일단 Key로 shortenUrl 도메인 부터 찾는다.
    ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);
    //찾은 shortenUrl을 이용해서 shortenUrlInfomationDto를 생성해서 반환한다.
    ShortenUrlInformationDto shortenUrlInformationDto = new ShortenUrlInformationDto(shortenUrl);
    return shortenUrlInformationDto;
  }

  public String getOriginalUrlByShortenUrlKey(String shortenUrlKey) {
    //레포지토리의 힘을 빌려서 키를 이용해서 shortenUrl을 우선 찾기!
    ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);
    //리다이렉트 한번 했으니 redirectCount를 +1해주는 코드 추가!
    //자바빈 규약에 따라 도메인의 get(), set() 메서드를 난발하면 안된다.
    //대신 도메인 메서드를 따로 만들어서 호출해야하며
    //이는 캡슐화(encapsulation)를 시켰다라고도 표현한다.
    shortenUrl.increaseRedirectCount();
    //그 찾은 shortenUrl의 originalUrl을 찾아서 반환
    String originalUrl = shortenUrl.getOriginalUrl();
    return originalUrl;
  }
}
