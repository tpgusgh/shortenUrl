package kr.co.shortenUrlService3.presentation;


import kr.co.shortenUrlService3.domain.NotFoundShortenUrlException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
//글로벌한 예외 처리자를 만들 때 사용하는 어노테이션
public class GlobalExecptionHandler {
    //NotFoundShortenUrlException을 처리하는 메서드를 만듦. 그 메서드의 이름은 앞에 handle을 붙인다. ;
    public ResponseEntity<?> handleNotFoundShortenUrlException(
            NotFoundShortenUrlException e
    ) {
        return new ResponseEntity<>("단축 URL을 찾지 못했습니다.", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(LayerInstantiationException.class)
    public ResponseEntity<?> handleLayerInstantiationException(
            LayerInstantiationException e
    ){
        return new ResponseEntity<>("단축 URL 자원이 부족합니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
