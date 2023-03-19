# problem-solve

## 개발 환경
  - IDE: IntelliJ IDEA
  - OS: Mac OS X
  - GIT
  - Java11
  - Spring Boot 2.3.12
  - JPA
  - H2 (in memory)
  - Gradle
  - Junit

## 실행 환경
  - endpoint : http://localhost:8080/swagger-ui/index.html#/
  - excute cmd : java -jar search-blog-api-0.0.1-SNAPSHOT.jar 

## 추가 구현사항
- multi module 구성
- openapi spec, api document 제공
- resilience4j 를 사용 :
  - retry / circuit breaker / bulkhead / timeLimiter 를 사용하여
  - 블랙박스 영역의 api 사용에 대한 관리기능 제공
- 카카오 블로그 검색 오류시 네이버 블로그 검색 제공
- keyword 조회 성능개선 :
  - top 10 키워드 검색을 캐싱으로 제공
  - 캐시는 10초 스케쥴로 삭제
- custom error response 제공

## TODO 개선사항
- hitCount update 성능개선
  - 외부캐시 (redis)로 atomic 하게 hitCount를 관리
  - 시간 or hitCount size 단위로 persistence db 를 동기화 한다