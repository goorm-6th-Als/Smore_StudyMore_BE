# Smore_StudyMore_BE
Kakao x goorm 기업 연계 프로젝트 1조 알쓰조 


## 📕 스터디 모집 및 관리 WEB 서비스 SMore (study more)
![smore-logo-ver1](https://github.com/user-attachments/assets/3459742c-f72e-421f-8dde-a97619d25102)



- [배포 URL](http://ec2-43-202-238-3.ap-northeast-2.compute.amazonaws.com:3000)
- 카카오톡으로 로그인 가능합니다.

<br>

## 프로젝트 소개

- SMore는 스터디를 위한 플랫폼입니다.
- 스터디 모집 및 스터디 가입이 한 플랫폼에서 가능하며
- 스터디 운영에 필요한 기능들이 포함되어 있습니다.

- ### 주요 기능
  - 1. 스터디 생성
  - 2. 스터디 가입 신청
  - 3. 출석 체크 및 공부시간 랭킹
  - 4. 스터디 일정
  - 5. 문제 풀이
  - 6. 개인 목표
  - 7. 스터디원 관리

<br>

</div>

##  <img src="https://github.com/Kangjunesu/Web_IDE_Project_BE/assets/108870712/6b3433e1-b807-438b-a50a-a3cf065fa1a1" width="30" heght="30"/> FrontEnd 레포지토리
<a href="https://github.com/goorm-6th-Als/Smore_StudyMore_FE"> FE 레포지토리 바로가기

</div>

<br>

##  🛠 아키텍처
![image](https://github.com/user-attachments/assets/f911955e-4735-441f-a52a-7327e648c85a)


## 📚 기술 스택
### BackEnd
|   **구분**   | **요소** |
| :----: | :----: |
| Language & Library|<img src="https://img.shields.io/badge/java-007396?style=flat-square&logo=openjdk&logoColor=white"/> <img src="https://img.shields.io/badge/springsecurity-6DB33F?style=flat-square&logo=springsecurity&logoColor=white"/> <img src="https://img.shields.io/badge/STOMP-6DB33F?style=flat-square&logo=stomp&logoColor=white"/> <img src="https://img.shields.io/badge/WebSocket-000000?style=flat-square&logo=websocket&logoColor=white"/> <img src="https://img.shields.io/badge/jwt-007396?style=flat-square&logo=jwt&logoColor=white"/> <img src="https://img.shields.io/badge/jpa-007396?style=flat-square&logo=jwt&logoColor=white"/>|
| build | <img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=Gradle&logoColor=white"/>|
| DATABASE | <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/> <img width="58" src="https://img.shields.io/badge/rails-%23CC0000.svg?style=for-the-badge&logo=ruby-on-rails&logoColor=white"/> |
| Framework |  <img src="https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=Spring&logoColor=white"/> <img src="https://img.shields.io/badge/springboot-6DB33F?style=flat-square&logo=springboot&logoColor=white"/>|
| AWS |  <img src="https://img.shields.io/badge/ec2-FF9900?style=flat-square&logo=amazonec2&logoColor=black"/> <img src="https://img.shields.io/badge/S3-FF9900?style=flat-square&logo=Amazon S3&logoColor=black"/> <img src="https://img.shields.io/badge/ROUTE 53-FF9900?style=flat-square&logo=amazonroute53&logoColor=black"/>|
| Http | <img src="https://img.shields.io/badge/Apache Tomcat-F8DC75?style=flat-square&logo=apachetomcat&logoColor=black"/> |
| TEST | <img src="https://img.shields.io/badge/Postman-FF6C37?style=flat-square&logo=Postman&logoColor=white"/> |
| etc | <img src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=white"/> <img src="https://img.shields.io/badge/Jira-0052CC?style=flat-square&logo=jira&logoColor=white"/> <img src="https://img.shields.io/badge/IntelliJIDEA-000000?style=flat-square&logo=intellij-idea&logoColor=white"/> <img src="https://img.shields.io/badge/Notion-000000?style=flat-square&logo=notion&logoColor=white"/> <img src="https://img.shields.io/badge/Discord-000000?style=flat-square&logo=discord&logoColor=#5865F2"/> <img src="https://img.shields.io/badge/Figma-000000?style=flat-square&logo=figma&logoColor=#F24E1E"/>|

    

### FrontEnd
<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=javascript&logoColor=black"/> <img src="https://img.shields.io/badge/react-20232a?style=flat-square&logo=react&logoColor=61DAFB"/> <img src="https://img.shields.io/badge/axios-5A29E4?style=flat-square&logo=axios&logoColor=white"/> <img src="https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=html5&logoColor=white"/> <img src="https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=css3&logoColor=white"/> <img src="https://img.shields.io/badge/Bootstrapap-7952B3?style=flat-square&logo=bootstrap&logoColor=white"/> <img src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=white"/> 

## 3. 프로젝트 구조

<details> <summary> ⛏ ERD </summary>

    ![image](https://github.com/user-attachments/assets/624f2789-866c-4dc7-b970-162488845c97)
</details>

<details><summary>📂 파일 구조</summary>
    
```

├─build
│  ├─classes
│  │  └─java
│  │      └─main
│  │          └─com
│  │              └─als
│  │                  └─SMore
│  │                      ├─domain
│  │                      │  ├─entity
│  │                      │  └─repository
│  │                      ├─global
│  │                      │  └─json
│  │                      ├─log
│  │                      │  └─timeTrace
│  │                      ├─notification
│  │                      │  ├─controller
│  │                      │  ├─dto
│  │                      │  ├─repository
│  │                      │  └─service
│  │                      ├─study
│  │                      │  ├─attendance
│  │                      │  │  ├─controller
│  │                      │  │  ├─DTO
│  │                      │  │  │  ├─request
│  │                      │  │  │  └─response
│  │                      │  │  ├─service
│  │                      │  │  │  └─impl
│  │                      │  │  └─validator
│  │                      │  ├─calendar
│  │                      │  │  ├─controller
│  │                      │  │  ├─dto
│  │                      │  │  │  ├─request
│  │                      │  │  │  └─response
│  │                      │  │  ├─service
│  │                      │  │  │  └─impl
│  │                      │  │  └─validator
│  │                      │  ├─chatting
│  │                      │  │  ├─config
│  │                      │  │  ├─controller
│  │                      │  │  ├─DTO
│  │                      │  │  └─service
│  │                      │  ├─dashboard
│  │                      │  │  ├─controller
│  │                      │  │  ├─DTO
│  │                      │  │  ├─mapper
│  │                      │  │  └─service
│  │                      │  ├─enter
│  │                      │  │  ├─controller
│  │                      │  │  ├─DTO
│  │                      │  │  ├─mapper
│  │                      │  │  └─service
│  │                      │  ├─management
│  │                      │  │  ├─controller
│  │                      │  │  ├─DTO
│  │                      │  │  ├─mapper
│  │                      │  │  └─service
│  │                      │  ├─notice
│  │                      │  │  ├─controller
│  │                      │  │  ├─DTO
│  │                      │  │  ├─service
│  │                      │  │  └─validator
│  │                      │  ├─problem
│  │                      │  │  ├─controller
│  │                      │  │  ├─DTO
│  │                      │  │  │  ├─request
│  │                      │  │  │  │  ├─problem
│  │                      │  │  │  │  └─problemBank
│  │                      │  │  │  └─response
│  │                      │  │  │      ├─problem
│  │                      │  │  │      └─problemBank
│  │                      │  │  ├─service
│  │                      │  │  │  └─impl
│  │                      │  │  │      ├─problem
│  │                      │  │  │      └─problemBank
│  │                      │  │  └─validator
│  │                      │  ├─studyCRUD
│  │                      │  │  ├─controller
│  │                      │  │  ├─DTO
│  │                      │  │  ├─mapper
│  │                      │  │  ├─service
│  │                      │  │  └─utils
│  │                      │  └─todo
│  │                      │      ├─controller
│  │                      │      ├─DTO
│  │                      │      ├─mapper
│  │                      │      └─service
│  │                      └─user
│  │                          ├─login
│  │                          │  ├─config
│  │                          │  ├─controller
│  │                          │  ├─dto
│  │                          │  │  └─response
│  │                          │  ├─service
│  │                          │  └─util
│  │                          │      └─aop
│  │                          │          ├─annotation
│  │                          │          └─dto
│  │                          ├─mypage
│  │                          │  ├─config
│  │                          │  ├─controller
│  │                          │  ├─dto
│  │                          │  │  ├─request
│  │                          │  │  └─response
│  │                          │  └─service
│  │                          └─mystudy
│  │                              ├─controller
│  │                              ├─dto
│  │                              │  ├─request
│  │                              │  └─response
│  │                              └─service

 ```

</details>

<br>

## 팀원 구성

<div align="center">

|   **이름**   | **포지션** | **구분** | **Github** |   **이름**   | **포지션** | **구분** | **Github** |   **이름**   | **포지션** | **구분** |       **Github** |
| ---- | ---- | ---- | ------ | ---- | ---- | ---- | ------ | ---- | ---- | ---- | ------ |
| <div align="center"><img src="https://avatars.githubusercontent.com/u/96505736?v=4" width="50" height="50"/><br><b>김현빈</b></div> | <div align="center"><b>BE</b></div> | <div align="center"><b>팀장</b></div> | <div align="center"><b>[링크](https://github.com/khv9786)</b></div> | <div align="center"><img src="https://avatars.githubusercontent.com/u/108870712?v=4" width="50" height="50"/><br><b>강준수</b></div> | <div align="center"><b>BE</b></div> | <div align="center"><b>팀원</b></div> | <div align="center"><b>[링크](https://github.com/Kangjunesu)</b></div> | <div align="center"><img src="https://avatars.githubusercontent.com/u/104208670?v=4" width="50" height="50"/><br><b>이수지</b></div> | <div align="center"><b>FE</b></div> | <div align="center"><b>팀원</b></div> | <div align="center"><b>[링크](https://github.com/ssssuji)</b></div> |
| <div align="center"><img src="https://avatars.githubusercontent.com/u/75283640?v=4" width="50" height="50"/><br><b>박진수</b></div> | <div align="center"><b>BE</b></div> | <div align="center"><b>팀원</b></div> | <div align="center"><b>[링크](https://github.com/qkrwlstn1)</b></div> | <div align="center"><img src="https://avatars.githubusercontent.com/u/53739820?v=4" width="50" height="50"/><br><b>남수연</b></div> | <div align="center"><b>BE</b></div> | <div align="center"><b>팀원</b></div> | <div align="center"><b>[링크](https://github.com/namtndus)</b></div> | <div align="center"><img src="https://avatars.githubusercontent.com/u/109202222?v=4" width="50" height="50"/><br><b>서다영</b></div> | <div align="center"><b>FE</b></div> | <div align="center"><b>팀원</b></div> | <div align="center"><b>[링크](https://github.com/savedinstancestate)</b></div> |
</div>


## 4. 역할 분담
### BackEnd
![image](https://github.com/user-attachments/assets/7804068f-b445-493f-91e7-2cb7abe87ed9)
### FrontEnd
![image](https://github.com/user-attachments/assets/da995bc7-b7c3-40fb-bd58-2934769162fe)

