### 👤팀원 소개

|                                               Backend                                                |                                                                      Backend                                                                      |Backend|                                           Backend                                            |                                           Backend                                            |
|:----------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------:|
|       <img src="https://avatars.githubusercontent.com/u/65541248?v=4" width=130px alt="이유상"/>        |                           <img src="https://avatars.githubusercontent.com/u/55842092?s=64&v=4" width=130px alt="이주연"/>                            | <img src="https://avatars.githubusercontent.com/u/64956292?v=4" width=130px alt="백인권"/> | <img src="https://avatars.githubusercontent.com/u/85631282?s=64&v=4" width=130px alt="김동민"/> | <img src="https://avatars.githubusercontent.com/u/97028441?s=64&v=4" width=130px alt="박준모"/> |
|                                 [이유상](https://github.com/liyusang1)                                  |                                                          [이주연](https://github.com/jo0oy)                                                          |[백인권](https://github.com/BackInGone)|                             [김동민](https://github.com/meena2003)                              |                              [박준모](https://github.com/junmo95)                               |
| 💡spring security<br/> 💡회원가입 및 로그인 <br/>💡SNS 로그인 (카카오,네이버,구글) <br/>💡여행 후기 작성<br/> 💡여행 계획 작성 및 조회 | 💡Github Actions CI/CD, EC2, Docker<br/>💡홈 메인 피드 관련 api<br/>💡홈 검색 피드 관련 api<br/>💡도시 상세 관련 api<br/> 💡환율 open api 스케줄러 호출 및 환율 정보 redis 저장<br/> |💡마이 페이지 관련 api<br/>💡보관 기능 관련 api <br/>💡좋아요 관련 api    |  💡S3 이미지 업로드 삭제 관련 api <br/> 💡 여행 후기 리뷰 및 리뷰댓글 관련 api <br/> 💡여행지 리뷰 및 리뷰댓글 관련 api <br/>   |                 💡여행 후기 메인페이지 api<br/>💡여행 후기 상세페이지 api <br/>💡여행지 상세페이지 api                 |

-----------------------
### 📌기술스택 & 구현환경
> -  Java : ![Java](https://img.shields.io/badge/java-17-red.svg)
> -  FrameWork : ![Spring Boot](https://img.shields.io/badge/springboot-3.2.1-brightgreen.svg)  ![Spring Security](https://img.shields.io/badge/springsecurity-brightgreen.svg) ![Spring Data JPA](https://img.shields.io/badge/spring%20data%20JPA-brightgreen.svg)  ![Spring Web](https://img.shields.io/badge/spring%20web-brightgreen.svg)
> -  Build : ![Gradle](https://img.shields.io/badge/Build-Gradle-blue.svg)
> -  VCS : ![Git](https://img.shields.io/badge/VCS-Git-orange.svg) ![GitHub](https://img.shields.io/badge/Github-black.svg)
> -  Database : ![GCP Cloud SQL](https://img.shields.io/badge/Database-AmazonEC2-yellow.svg)
> -  DBMS : ![MySQL](https://img.shields.io/badge/DBMS-MySQL-blue.svg)
> -  배포환경 : ![GCP VM](https://img.shields.io/badge/배포%20환경-AmazonEc2-blue.svg)
> -  컨벤션 : ![Code Convention](https://img.shields.io/badge/Code%20Convention-IntelliJ%20Java%20Google%20Style-brightgreen.svg)
> -  브랜치 전략 : ![GitFlow](https://img.shields.io/badge/GitFlow-Workflow-orange.svg)

### 📌API 명세서 & 서비스 배포 주소
- 서비스 배포 주소 : [https://tripcometrue.vercel.app](https://tripcometrue.vercel.app)
- API 명세서:
    - [Postman 1](https://documenter.getpostman.com/view/14269013/2s9YsJCYY9#47909ddc-026a-4731-b0ca-5088b8e8574f)
    - [Postman 2](https://documenter.getpostman.com/view/24478928/2s9YsRaUDD)
    - [Notion](https://arrow-halibut-e8d.notion.site/API-9d3aa3736a764af6a513efda552211b5?pvs=4)

    
### 📌패키지 구조
```
 com.example.yanolja  
 ├── domain  
 │   ├── alarm
 │   ├── city
 │   ├── comment
 │   ├── likes
 │   ├── member
 │   ├── place
 │   ├── review
 │   ├── store
 │   ├── tripplan
 │   ├── triprecord
 │   ...  
 └── global  
      ├── config
      ├── entity
      ├── enums
      ├── exception
      ├── jwt 
      ├── s3
      ├── springsecurity
      ├── util
      └── validator 
```

--------------------

### ⭐ERD
![erd](https://github.com/TripComeTrue/TripComeTrue_BE/assets/65541248/33ff36cc-0e04-4285-82d4-b2939e77b5ab)


### ⭐Project Architecture
![image](https://github.com/TripComeTrue/TripComeTrue_BE/assets/65541248/a6a64b92-2d77-4240-a64e-fe6770e1703e)