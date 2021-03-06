# 코드스쿼드 도서관

도메인: https://librarycodesquad.com

프론트 저장소 V1(React + JavaScript): https://github.com/Library-solider/codesquad-library-frontend

프론트 저장소 V2(React + TypeScript): https://github.com/Library-solider/codesquad-library-frontend-v2
# 프로젝트 소개
코드스쿼드 내 도서 검색 및 대여 서비스.

코드스쿼드 학생이 주 대상입니다.


![여기에 사진](https://user-images.githubusercontent.com/49897409/90892759-d0a39f00-e3f8-11ea-98ef-e6bdaac256b8.gif)

# 기술  스택
## Server-side
- Java 8
- Spring Boot 2.2.8
- Spring Data JPA
- Spring Security
    - Spring OAuth2 Client
- Spring thymeleaf
- Spring Swagger 2.9.2
- MySQL 5.7.30
- JUnit 5
- Nginx
- Let's Encrypt (EC2 Nginx 설정)
- Github OAuth2
- Github Action

## Client-side
- React.js
- styled-component
- TypeScript
- Redux
- Redux-thunk
- Webpack

## Dev-ops
### AWS
- EC2 t2 micro
- S3
- Route 53
- Amazon Certificate Manager
- CloudFront

### etc
- 인터파크 도서 Open API

## 멤버
- [손상희(써니)](https://github.com/kses1010) : 백엔드 개발자 / 기획
- [김무섭(랙돌)](https://github.com/MuseopKim) : 백엔드 개발자
- [최장호(호이)](https://github.com/choichoigang) : 프론트 개발자


## 문서화
- [Wiki](https://github.com/Library-solider/codesquad-library/wiki) 
- [기획서](https://www.notion.so/74b5d7ea82f6465081638b04b6acdef4)
- [회의록](https://www.notion.so/1f3cb3cd81a845ea84bfe7d8dbd1ce46?v=a16a800d5139427fb24426a38e09084b)


## 프로젝트 세팅
EC2 환경(env) 설정이 필요합니다. (vi ~/.profile)
```shell script
# Envirionment DB
export DB_URL={RDS URL}
export DB_NAME={DB username}
export DB_PASSWORD={DB password}

# Environment OAuth
export OAUTH_CLIENTID={OAuth2 client}
export OAUTH_SECRET={OAuth2 secret}

# Environment OpenAPI
export INTERPARK_KEY={Interpark OpenAPI Key}
```
저장한 뒤 `source .profile` 입력

환경 확인 `env`로 제대로 입력됐는지 확인하세요. 확인 결과 입력이 올바르게 됐을 때 서버를 실행하세요.


