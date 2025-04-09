package kr.co.shortenUrlService3.presentation;

import kr.co.shortenUrlService3.domain.ShortenUrl;

public class ShortenUrlInformationDto {
  private String originalUrl;
  private String shortenUrlKey;
  private Integer redirectCount;

  public ShortenUrlInformationDto(ShortenUrl shortenUrl) {
    this.originalUrl = shortenUrl.getOriginalUrl();
    this.shortenUrlKey = shortenUrl.getShortenUrlKey();
    this.redirectCount = shortenUrl.getRedirectCount(); // 🚀🔥 여기서 직접 받아와야 함!
  }

  public String getOriginalUrl() {
    return originalUrl;
  }

  public String getShortenUrlKey() {
    return shortenUrlKey;
  }

  public Integer getRedirectCount() {
    return redirectCount;
  }
}
