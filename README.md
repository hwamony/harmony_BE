# 화목🌼🌳
![image](https://user-images.githubusercontent.com/84499458/190884366-65afd725-720f-4777-8447-3d50aba77a4d.png)
### [프로젝트 시연(Youtube Link)](https://youtu.be/G_1d8exS180?t=36)

## 프로젝트 소개
**가족의 일정, 사진, 목소리를 공유하고 관리해보세요. 화목은 가족 커뮤니케이션 종합 서비스입니다.**

📌 가족 일정을 한 번에 ! '그룹 구성원 간에 캘린더 공유 기능'

📌 소중한 사람들과 함께한 추억을 보관하는 '공유 갤러리 기능'

📌 그리운 우리 엄마 목소리, 듣고싶을 때 '음성 메세지 기능'

📌 중간에 끼인 둘째 서러움 누가 알아주나..! '커뮤니티 역할별 게시판'

📌 우리 가족의 화목 지수는?  ‘가족 등급 및 랭킹 시스템’

## 프로젝트 기간 (6주)

- 기획: 8월 5일 ~ 8월 8일
- 개발: 2022년 8월 9일 ~ 9월 4일
- 런칭/마케팅 및 유저 테스트를 통한 개선: 9월 5일 ~ 9월 15일
- 최종 발표: 9월 16일

## 아키텍처

![https://user-images.githubusercontent.com/84499458/190880928-c7626661-f5de-4b3d-89db-d3ad418a7590.png](https://user-images.githubusercontent.com/84499458/190880928-c7626661-f5de-4b3d-89db-d3ad418a7590.png)

## 기술 스택
### FrontEnd
<div style="display: flex">
<img src="https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB">
<img src="https://img.shields.io/badge/Redux Toolkit-593D88?style=for-the-badge&logo=redux&logoColor=white">
<img src="https://img.shields.io/badge/React Query-FF4154?style=for-the-badge&logo=React Query&logoColor=white">
<img src="https://img.shields.io/badge/Axios-5A29E4?style=for-the-badge&logo=Axios&logoColor=white">
<img src="https://img.shields.io/badge/styled components-DB7093?style=for-the-badge&logo=styledcomponents&logoColor=white">
<img src="https://img.shields.io/badge/Material%20UI-007FFF?style=for-the-badge&logo=mui&logoColor=white">
<img src="https://img.shields.io/badge/React Hook Form-EC5990?style=for-the-badge&logo=ReactHookForm&logoColor=white">
<img src="https://img.shields.io/badge/React_Router-CA4245?style=for-the-badge&logo=react-router&logoColor=white">
<img src="https://img.shields.io/badge/Stomp-000000?style=for-the-badge&logo=Stomp&logoColor=white">
<img src="https://img.shields.io/badge/SockJS-010101?style=for-the-badge&logo=Socket.io&logoColor=white">

</div>

### BackEnd
<div style="display: flex">
<img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white">
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white">
<img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=for-the-badge&logo=SpringSecurity&logoColor=white">
<img src="https://img.shields.io/badge/JSON Web Tokens-000000?style=for-the-badge&logo=JSON Web Tokens&logoColor=white">
</div>

### DevOps.
<div style="display: flex">
<img src="https://img.shields.io/badge/GitHub Actions-2088FF?style=for-the-badge&logo=GitHub Actions&logoColor=F7BA3E">
<img src="https://img.shields.io/badge/Amazon S3-569A31?style=for-the-badge&logo=Amazon S3&logoColor=white">
<img src="https://img.shields.io/badge/Amazon CloudFront-232F3E?style=for-the-badge&logo=Amazon AWS&logoColor=white">
<img src="https://img.shields.io/badge/Route 53-68337?style=for-the-badge&logo=Route53&logoColor=white">
<img src="https://img.shields.io/badge/ACM-0085CA?style=for-the-badge&logo=ACM&logoColor=white">
<img src="https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=Jenkins&logoColor=white">
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">

</div>

### Etc.
<div style="display: flex">
<img src="https://img.shields.io/badge/Google Analytics-E37400?style=for-the-badge&logo=Google Analytics&logoColor=white">
<img src="https://img.shields.io/badge/eslint-3A33D1?style=for-the-badge&logo=eslint&logoColor=white">
<img src="https://img.shields.io/badge/prettier-1A2C34?style=for-the-badge&logo=prettier&logoColor=F7BA3E">
<img src="https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white">

</div>

### [라이브러리 상세(Notion link)](https://ddooyn.notion.site/10e4c333c42546c68d7628c0526995b1)

## 팀원 소개

- FE: [윤수영](https://github.com/ddooyn)(L), [김다흰](https://github.com/doa12), [이동현](https://github.com/slozche)
- BE: [김 현](https://github.com/uoahy)(VL), [강서의](https://github.com/lemon203213), [권유경](https://github.com/Eachkwon)
- Design: 정찬울

## 트러블 슈팅

### 1. Jenkins, Github Actions를 사용한 CI/CD 적용 (BackEnd)
- **도입 이유**
  - 코드 통합 및 배포 자동화를 통해 원활한 협업과 개발에만 집중할 수 있는 환경 제공
- **문제 상황**
  - 프로젝트 개발 단계에서 테스트 및 배포가 자주 일어나는데, 그럴 때마다 사람이 수동으로 하면 시간이 오래 걸리고 실수할 가능성이 있음
  - 테스트 또는 빌드가 실패했을 때 어느 시점부터 에러가 발생했는지 직접 찾아야 하는 불편함 발생
- **해결 방안**
  1. Jenkins
  2. GitHub Actions
- **의견 조율**
  1. Jenkins를 사용하기 위해선 별도의 서버 설치 및 설정이 필요하지만 다양한 플러그인과 많은 레퍼런스가 존재
  2. GitHub Actions는 복잡한 설정 필요 없이 손쉽게 적용 가능
- **의견 결정**
  - CI 단계에서는 빌드 테스트만 진행하면 되기 때문에 간단하게 GitHub Actions를 사용하여 빌드가 성공했을 때만 머지가 가능하게 하고, CD 단계에서는 파이프라인을 자유롭게 커스텀하기 위해 Jenkins를 사용

---

### 2. 루트 도메인(non-www) → 서브 도메인(www) 리다이렉트 (FrontEnd)
- **도입 이유**
  - 유저가 http/https, non-www/www, 4가지 조합 중 어떤 URL로 접근하더라도 모두 서비스를 제공할 수 있어야 함
- **문제 상황**
  - http로 접근했을 때 https로 리다이렉트 되도록 하는 것은 ACM 인증서 발급 후 CloudFront 사용으로 해결함
  - 하지만 Route 53으로 서브 도메인 www 설정 후 테스트 결과, non-www와 www 각각 다른 주소로 인식해서 로컬 스토리지에 토큰을 각각 갖게 되는 문제가 발생함
  - 토큰 관리 및 SEO 측면에서도 non-www와 www 중 통일할 필요가 있었기에 둘 중 하나로 결정해야 하는 상황
- **의견 조율 및 결정**
  - URL에 www를 없애는 것이 트렌드이지만 가족을 위한 서비스이기 때문에 다양한 연령대를 고려해서 전통적인 www를 메인 도메인으로 사용하기로 결정
  - non-www로 접근했을 때도 www로 리다이렉트 되도록 할 필요성이 생김
- **문제 발생**
  - (1차 리다이렉트 설정 시도 후) 특정 페이지 접근 시 크롬 에러
  : `페이지가 작동하지 않습니다 리디렉션한 횟수가 너무 많습니다`
  - S3 버킷 변화 시 CloudFront에 바로 반영되지 않음
- **해결 방안**
  - 루트 도메인에서 서브 도메인(www)으로 안정적으로 액세스할 수 있도록 AWS docs를 참고하여 S3, CloudFront, Route 53 설정 전면 재검토
  - 앱 내에서도 유저의 로그인 여부, 가족 연결 여부, 역할 설정 여부에 따라 캘린더에 진입시키지 않고 로그인 또는 정보 입력 페이지로 리다이렉트 해주고 있었기 때문에 www 리다이렉트와 충돌되지 않도록 FrontEnd 코드 리팩터링
- **문제 해결**
  - S3 버킷을 non-www와 www 두 개를 만들고 → 프론트에서 Github Actions를 통해 www가 붙은 버킷에 빌드 후 자동 배포 → 비어있는 non-www 버킷에서는 메인 도메인으로 리다이렉트 설정 → 각각의 버킷에 대해 CloudFront 배포 생성 및 대체 도메인 설정 → Route 53에서 각 CloudFront에 대한 단순 라우팅 레코드를 생성해 해결
  - 기존 라우팅은 캘린더 페이지(`/`)에서 로그인하지 않았을 때 로그인 페이지(`/login`)로 보내주는 방식을 사용하고 있었는데, 토큰 유무에 따른 진입 가능 route들을 분리함으로써 각각의 홈(`/`) 라우트를 토큰이 있을 때는 캘린더 페이지, 토큰이 없을 때는 로그인 페이지가 되게 해 앱 내 리다이렉트를 줄이는 방식으로 개선함.
  - CloudFront의 캐시 정책을 CashingDisabled로 변경해 S3 변화가 바로 반영될 수 있게 수정 → 서비스 안정화 후 다시 캐싱 사용하도록 수정함 (S3에 저장된 컨텐츠를 직접 접근하지 않아도 되므로 S3 비용이 감소하며, 더 빠른 응답을 지원하기 때문)

---

### 3. 이미지 리사이징 업로드 속도 개선 (FrontEnd)
- **도입 이유**
  - 갤러리에 이미지를 업로드하는 유저들의 사용성을 개선하기 위해
- **문제 상황**
  - 갤러리에 이미지를 업로드할 경우, 스토리지의 용량을 절약하기 위해 1MB의 파일 용량 제한을 걸어두었음
  - 이미지가 1MB를 초과하는 경우 업로드가 이루어지지 않는데, 스마트폰에서 촬영한 이미지가 이를 초과하는 경우가 있어 사용성에 문제가 발생함
- **해결 방안**
  - 스토리지의 사용 용량을 늘려 저장 공간을 추가적으로 확보함과 동시에 용량 제한을 푸는 방법
  - 사용자가 1MB 이상의 이미지를 업로드할 때, 자동으로 이미지를 리사이징해서 용량을 줄이는 방법
- **의견 조율**
  - 가족끼리 공유하는 캘린더라는 기획 의도 상 스마트폰을 통해 업로드하는 빈도가 더 높을 것으로 추정
  - 용량 제한을 푸는 방법을 사용하기 위해서는 추가적인 비용이 필요함
  - 프론트단에서 이미지를 리사이징하는 라이브러리를 사용한다면 서버에서의 추가적인 코드 변경 없이 간단하게 문제를 해결할 수 있음
- **의견 결정**
  - **browser-image-compression** 라이브러리를 사용해 1MB를 초과한 이미지를 업로드할 시 리사이징을 거치도록 코드 변경
- **문제 해결**
  - 해당 라이브러리가 promise를 반환하는 함수를 제공하여 사용자가 업로드 버튼을 클릭하는 시간과 이미지가 리사이징되어 실제로 업로드되는 시간 사이의 딜레이가 발생
  - promise.all을 사용하여 모든 promise가 처리된 후 값을 불러오는 방식을 통해 약 70%의 속도 개선을 이루어 냄
  ![image](https://user-images.githubusercontent.com/84499458/190884316-433fe331-cb09-42fa-b232-a275516f33fa.png)  

---

### 4. 무중단 배포 (BackEnd)
- **도입 이유**
  - 배포 중에도 사용자가 서비스를 이용할 수 있는 환경 제공
- **문제 상황**
  - 애플리케이션이 배포되는 동안 서버가 다운되어 사용자가 잠시동안이나마 서비스를 이용하지 못하는 불편함 발생
- **해결 방안**
  1. Blue/Green
  2. 롤링
- **의견 조율**
  1. Blue/Green 배포는 서버 자원이 2배로 필요하지만 문제가 생겼을 경우 신속하게 롤백이 가능하고, 구버전과 신버전이 공존하지 않고 서비스 가능
  2. 롤링 배포는 배포를 위해 추가로 인스턴스를 사용하지 않는다면 사용 중인 인스턴스에 트래픽이 몰릴 수 있고, 구버전과 신버전의 공존으로 인해 호환성 문제가 발생
- **의견 결정**
  - 배포 중에도 서버 용량을 그대로 유지하면서 구버전과 신버전이 공존하지 않기를 원했기 때문에 Blue/Green 배포 방식으로 결정
