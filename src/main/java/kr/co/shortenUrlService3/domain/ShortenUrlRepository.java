package kr.co.shortenUrlService3.domain;

import org.springframework.stereotype.Repository;

@Repository
public interface ShortenUrlRepository {
  void saveShortenUrl(ShortenUrl shortenUrl);

  ShortenUrl findShortenUrlByShortenUrlKey(String shortenUrlKey);
}
