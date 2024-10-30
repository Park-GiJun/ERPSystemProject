# ERP System Project

이 프로젝트는 개인 공부용 **ERP 시스템**을 개발하기 위한 것입니다. 이 시스템은 인사 관리, 회원 관리, 로그인 기능 등 다양한 비즈니스 프로세스를 지원합니다.

## 기능

- 사용자 등록 및 관리
- 로그인 및 인증
- 비밀번호 재설정
- 사용자 활동 기록
- 사용자 목록 조회
- 각종 통계 및 리포트 기능

## 기술 스택

- **프론트엔드**: Vue.js, Tailwind CSS
- **백엔드**: Spring Boot
- **데이터베이스**: MySQL, Hibernate
- **보안**: JWT (JSON Web Token)
- **버전 관리**: Git

## 설치 방법

1. **이 리포지토리를 클론합니다.**
   ```bash
   git clone https://github.com/Park-GiJun/ERPSystemProject.git
   ```

2. **필요한 라이브러리 및 종속성을 설치합니다.**
   ```bash
   cd ERPSystemProject
   # 프론트엔드와 백엔드 각각에서 npm install 또는 mvn install을 실행하세요.
   ```

3. **데이터베이스를 설정하고 초기 데이터를 불러옵니다.**

4. **애플리케이션을 실행합니다.**
   ```bash
   # 백엔드 실행
   mvn spring-boot:run
   ```

5. **프론트엔드를 실행합니다.**
   ```bash
   npm run serve
   ```

## 사용 방법

1. 브라우저에서 `http://localhost:8080`으로 접속하여 애플리케이션을 이용합니다.
2. 사용자 등록 후 로그인하여 다양한 기능을 사용해 보세요.

## 기여

기여는 언제나 환영합니다! 기여하고 싶으신 분은 아래 절차를 따르세요.

1. **Fork 이 리포지토리.**
2. **새로운 브랜치를 생성합니다.**
   ```bash
   git checkout -b feature-YourFeature
   ```
3. **변경 사항을 커밋합니다.**
   ```bash
   git commit -m 'Add some feature'
   ```
4. **브랜치에 푸시합니다.**
   ```bash
   git push origin feature-YourFeature
   ```
5. **Pull Request를 생성합니다.**

## 라이센스

이 프로젝트는 [MIT 라이센스](LICENSE)에 따라 라이센스가 부여됩니다.

## 연락처

- 개발자: [GiJun Park](https://github.com/Park-GiJun)
- 이메일: tpgj98@naver.com
