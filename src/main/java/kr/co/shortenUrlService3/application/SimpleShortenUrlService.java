package kr.co.shortenUrlService3.application;

import kr.co.shortenUrlService3.domain.NotFoundShortenUrlException;
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
    String shotenUrlkeys = getUniquesShortenUrlKey();

    //2. 원래 URL과 단축 URL키를 통해 ShortenUrl 객체 생성
    ShortenUrl shortenUrl = new ShortenUrl(originalUrl, shortenUrlkey);

    //생성된 ShortenUrl 객체를 레포지토리를 통해 저장
    shortenUrlRepository.saveShortenUrl(shortenUrl);

    //ShortenUrl을 ShortenUrlCreateResponseDto로 변환하여서 반환.
    ShortenUrlCreateResponseDto shortenUrlCreateResponseDto
            = new ShortenUrlCreateResponseDto(shortenUrl);


    return shortenUrlCreateResponseDto;
  }

  private String getUniquesShortenUrlKey() {
    //최대 5번까지 시도할꺼다
    final int MAX_RETRY_COUNT = 5;
    //현재 시도 횟수
    int count = 0;
    while(count++ < MAX_RETRY_COUNT) {
      String shortenUrlKeys  = ShortenUrl.generateShortenUrlKey();
      ShortenUrl shortenUrl =shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKeys);

      if(shortenUrl == null) {
        return shortenUrlKeys;
      }
    }
    throw new LayerInstantiationException();
  }

  public ShortenUrlInformationDto getShortenUrlInformationByShortenUrlKey(String shortenUrlKey) {
    //shortenUrlInfo를 찾기 전에 일단 Key로 shortenUrl 도메인 부터 찾는다.
    ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);
    if(shortenUrl == null) {
      throw new NotFoundShortenUrlException();
    }
    //찾은 shortenUrl을 이용해서 shortenUrlInfomationDto를 생성해서 반환한다.
    ShortenUrlInformationDto shortenUrlInformationDto = new ShortenUrlInformationDto(shortenUrl);
    return shortenUrlInformationDto;
  }

  public String getOriginalUrlByShortenUrlKey(String shortenUrlKey) {
    ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);
    if(shortenUrl == null) {
      throw new NotFoundShortenUrlException();
    }

    System.out.println("Before increase - Key: " + shortenUrlKey + ", Count: " + shortenUrl.getRedirectCount());

    shortenUrl.increaseRedirectCount();

    System.out.println("After increase - Key: " + shortenUrlKey + ", Count: " + shortenUrl.getRedirectCount());

    shortenUrlRepository.saveShortenUrl(shortenUrl);

    return shortenUrl.getOriginalUrl();
  }
}
