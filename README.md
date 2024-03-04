# 🐾 2023-2 융합캡스톤디자인
> 2023년 2학기 융합캡스톤디자인 5조 딩동팀입니다.
<br/>

## ✍️ 과제 소개
기존의 갤러리 플랫폼들에서 공유앨범 기능은 수많은 기능 중에 단지 일부의 기능으로서 자리 잡고 있습니다. 이러한 상황으로 공유앨범 기능이 제공하는 장점들이 충분히 활용되지 못하고 있습니다.   
본 프로젝트에서는 공유앨범 기능에 초점을 맞춘 갤러리 앱을 제작하고, 이에 더하여 사진의 메타데이터를 기반으로 지도 위에 사진을 띄움으로써, 여러 장소에 대한 정보를 제공하여 공유 앨범의 장점을 개인적, 사회적 측면에서 극대화시키고자 하였습니다.
<br/>

## 🧑‍💻 프로젝트 소개
> **프로젝트명**

지도기반 공유앨범 커뮤니티 **PicMap**   

> **프로젝트 목표**

딩동팀의 PicMap 서비스는 단순한 앨범 서비스나 소통 기능 뿐만 아니라 원하는 사람들끼리 그룹을 형성하여 앨범을 만들어나가는 공유앨범 기능과, 그룹의 사진을 지도 위에서 탐색할 수 있는 기능을 제공합니다.
<br/>

> **결과물 소개**

1. **마이페이지 사진탭**
<p><img src="https://github.com/Dingdongrami/PicMap_frontend/assets/87259219/9f142e93-eeef-4f0b-bb2c-52fc3d4b1135" width="60%" /></p>  
<div>마이페이지 사진탭은 자신이 속한 각 써클마다의 최신 사진 4장을 보여주는 탭이다. 이 부분에서 사진의 렌더링이 늦어지는 문제점을 AWS CloudFront를 이용하여 해결했다. 가장
위로 스크롤할 시 사진 데이터를 새로고침(refetch)할 수 있다.</div>
<div>써클 사진을 클릭할 시 두 번째 사진과 같이 해당 써클로 이동할 수 있다.    </div>

<br />

2. **마이페이지 지도탭**
<p><img src="https://github.com/Dingdongrami/PicMap_frontend/assets/87259219/a646799b-af8a-4572-8b60-89e6aef940d1" width="80%" /></p>
<div>마이페이지의 지도탭은 자신이 속한 써클에 업로드된 사진들 중 위치 정보가 있는 사진들을
지도 위에서 보여준다.   </div>
<div>Supercluster와 map marker 등의 라이브러리를 이용하여 지도위에
마커가 클러스터링 되도록 구현하였다.
여러 장의 사진이 모여있는 곳(클러스터)을 클릭하면 해당 클러스터에 속한 사진들의
리스트 스크린으로 이동한다. (두 번째 사진)   </div>
<div>해당 스크린에서 특정 사진을 누르면 해당 사진의 상세 스크린으로 이동한다.(세 번째 사진)</div>
<div>사진의 상세 스크린에서는 사진에 대해 좋아요를 누르고 댓글을 통해 여러 유저와 소통할
수 있다.</div>   
<div>개별 사진을 클릭하면 바로 해당 사진의 상세 스크린으로 이동한다.</div>

<br />

3. **마이페이지 써클탭, 써클생성 스크린**
<p><img src="https://github.com/Dingdongrami/PicMap_frontend/assets/87259219/dd64dabc-6b98-4be4-b11e-78f22ee424c4" width="60%" /></p>
<div>마이페이지의 써클탭은 자신이 속한 써클의 목록을 보여준다. 써클을 클릭하면 해당 써클의
공유앨범으로 이동할 수 있다. </div>
<div>왼쪽 하단의 ‘+’ 버튼을 클릭하면 새로운 써클을 생성할 수
있다.</div>   
<div>써클은 공개 써클과 비공개 써클로 나누었다. 공개 써클의 경우 친구의 초대와 타임라인
페이지를 통해 가입할 수 있고, 비공개 써클의 경우 친구의 초대로만 가입할 수 있도록
구현하였다.</div>

<br />

4. **써클 공유앨범 스크린**
<p><img src="https://github.com/Dingdongrami/PicMap_frontend/assets/87259219/41dbe88f-d3d1-43dc-a38c-ef6e6b4ffa3b" width="80%" /></p>
<div>공유앨범 스크린의 상단에는 앨범에 속한 유저들의 리스트를 보여준다. 특정 유저를
클릭하면 해당 유저의 프로필 페이지로 이동한다. </div><div>지도 부분에는 해당 공유앨범에 업로드
된 사진들 중 위치 정보가 있는 사진들을 띄워준다. 지도의 아래에는 업로드 된 사진들을
3열로 보여준다. </div><div>사진 리스트의 오른쪽 상단에 ‘선택’ 버튼을 누르면, 사진을 선택하여
삭제하거나 자신의 기기에 저장할 수 있다. </div><div>헤더에는 가장 왼쪽에 써클명이, 가장
오른쪽에는 설정 버튼이 있다. </div><div>설정버튼을 통해 사진 정렬, 유저 추가 등을 할 수 있다.
여기서 사진 정렬은 최신순, 과거순, 좋아요순 중에서 선택할 수 있으며, 유저 추가를 통해
친구를 해당 써클에 초대할 수 있다.</div>
<div>써클 내의 지도의 클러스터를 클릭하면 확대된 지도 스크린으로 이동한다.</div>
<div>각 사진을 클릭하면 [그림 2]의 세 번째 사진과 같은 사진 상세 스크린으로 이동한다.</div>

<br />

5. **써클 공유앨범 스크린 - 사진 업로드**
<p><img src="https://github.com/Dingdongrami/PicMap_frontend/assets/87259219/b5f1f37b-d626-42d6-9e54-c0db51665abb" width="80%" /></p>
<p>왼쪽 하단의 버튼을 통해 사진을 업로드할 수 있다. 여러 장의 사진을 선택하여 업로드 할
수 있으며, 직접 사진을 촬영하여 업로드할 수도 있다.</p>

<br />

6. **타임라인(공개써클 목록) 스크린**
<p><img src="https://github.com/Dingdongrami/PicMap_frontend/assets/87259219/4819345d-d770-43c9-8899-739886db66c5" width="60%" /></p>
<p>공개 써클의 목록을 볼 수 있으며, 미가입 써클에 가입할 수 있다. 가입 후 바로 접속이
가능하다. 위의 두 번째 사진과 같은 퍼블릭 써클은 공적인 커뮤니티의 역할을 할 수 있다.</p>

<br />

7. **검색 스크린**
<p><img src="https://github.com/Dingdongrami/PicMap_frontend/assets/87259219/5c4ba91b-febe-4d0a-8727-0e1fb277633f" width="80%" /></p>
<div>유저의 경우 유저네임으로 검색할 수 있으며, 써클의 경우 써클명으로 검색할 수 있다.</div>
<div>지도의 경우 특정 지역을 검색하면 해당 지역에 대한 사진이 있는 퍼블릭 써클의 사진을
조회할 수 있다.</div>

<br />

8. **프로필 편집 및 친구목록**
<p><img src="https://github.com/Dingdongrami/PicMap_frontend/assets/87259219/0fc6a783-80d5-4e6b-a5e8-3df242304cdb" width="80%" /></p>
<div>마이페이지의 상단의 프로필에서 프로필 편집 버튼을 통해 자신의 프로필을 수정할 수
있다. 프로필 사진을 직접 자신의 갤러리로부터 혹은 촬영하여 수정할 수 있다.</div>
<div>또한 프로필의 친구목록 버튼을 통해 친구목록을 조회할 수 있다. 상단 헤더의 오른쪽 설정
버튼을 클릭하면 받은 친구요청을 확인할 수 있고, 친구를 삭제할 수도 있다.</div>

<br />

9. **다른 유저 페이지 스크린**
<p><img src="https://github.com/Dingdongrami/PicMap_frontend/assets/87259219/88afc2b9-0d5d-4fad-b0bb-8dfb9033dfdf" width="80%" /></p>
<div>다른 유저를 클릭하면 해당 유저의 페이지에 접속할 수 있다. 이미 친구인 경우 ‘친구’라는
텍스트가 표시된다.</div>
<div>친구가 아닌 비공개 계정 사용자의 경우 사진탭과 지도탭 또한 비공개 된다.</div>

<br />

## 🎞️ 시연영상
<video src="https://github.com/Dingdongrami/PicMap_frontend/assets/87259219/197d6d81-d4a3-4e35-875f-20b8d33750b1" width="60%" height="50%"></p>

<br />

## ⚙️ 시스템 아키텍처
<p align="center"><img src="https://github.com/Dingdongrami/PicMap_frontend/assets/87259219/e0eb9e50-bc3c-45c9-8e9d-4dd902317bb2" width="70%" height="50%"></p>

<br/>

## 📚 기술 스택

<b>Frontend</b>

![React Native](https://img.shields.io/badge/React_Native-61DAFB?style=flat-square&logo=react&logoColor=white)
![Expo](https://img.shields.io/badge/Expo-000020?style=flat-square&logo=expo&logoColor=white)
![VSCode](https://img.shields.io/badge/Visual_Studio_Code-007ACC?style=flat-square&logo=visual-studio-code&logoColor=white)
![React Query](https://img.shields.io/badge/React_Query-000000?style=flat-square&logo=react-query&logoColor=white)
![Axios](https://img.shields.io/badge/Axios-000000?style=flat-square&logo=axios&logoColor=white)
![Recoil](https://img.shields.io/badge/Recoil-60B5CC?style=flat-square&logo=recoil&logoColor=white)
![Android SDK](https://img.shields.io/badge/Android_SDK-3DDC84?style=flat-square&logo=android&logoColor=white)

<b>Backend</b>

![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat-square&logo=spring-boot&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ_IDEA-000000?style=flat-square&logo=intellij-idea&logoColor=white)
![AWS EC2](https://img.shields.io/badge/AWS_EC2-232F3E?style=flat-square&logo=amazon-aws&logoColor=white)
![AWS RDS](https://img.shields.io/badge/AWS_RDS-232F3E?style=flat-square&logo=amazon-aws&logoColor=white)
![AWS CloudFront](https://img.shields.io/badge/AWS_CloudFront-232F3E?style=flat-square&logo=amazon-aws&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=flat-square&logo=github-actions&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=redis&logoColor=white)

<br/>

## 🫧 기대효과

- 단순히 사진을 공유하는 것 뿐만 아니라 지도에서 확인하고, 좋아요와 댓글과 같은 커뮤니티적 요소를 통해 상호작용 할 수 있다는 점에서 새로운 사용자를 유치할 수 있음.
- 다양한 지역에서 업로드 및 공유되는 사진들을 통해 지역의 명소나 특정 장소에 대한 관광 산업이 촉진될 수 있음.
- 소셜미디어에 강조되지 않는, 소소한 순간들에 중점을 두어 의미있는 순간을 느끼고 소통하는 공간을 제공함.
- 환경 변화나 오염 문제 등을 사진으로 공유함으로써 환경 보호 단체나 정부 기관에서 모니터링 하거나 환경 문제를 알릴 수 있음.
- 정부와 공공기관과의 연계로 투표 장소나 대피소 등의 위치 정보가 중요한 공지사항을 사진으로 공유하는 기능으로 확장 가능함.

<br/>

## 👻 팀원구성

|       팀원       | 팀원 | 팀장 |
|:--------------:|:--:|:--:|
|    **공소연**     |**김민정**|**이지민**|
|     **FE**     |**BE**|**FE**|
|   **경제학과**   |**의생명공학과**|**수학과**|
|   **2020110210**   |**2019111791**|**2020110408**|

<br/>

## 🗝️ 팀원 역할

**공소연**
: UI/UX, 친구 추가/요청/삭제, 써클 만들기, 프로필 편집, 사진 업로드 및 선택/삭제/ 정렬, 사진 좋아요 기능, 댓글 추가/삭제, 써클 가입 및 초대 <br/>
**김민정**
: 서버 및 백엔드, AWS EC2, RDS, S3로 서버 구축, Github-Actions로 CI/CD 구축, S3 bucket 으로 이미지 업로드, AWS CloudFront를 사용한 CDN 서버 구축으로 최적화된 이미지 데이터 조회, 로그인 시 jwt token 발급 및 redis를 통한 refresh token 관리, Google Map의 Geocoding API를 통해 장소 검색 시 반경 2km 내의 사진 검색되도록 구현 <br/>
**이지민**
: UI/UX, 발표, 사진 마커를 이용한 지도 클러스터링, 써클 앨범 지도 기능 등 전체 지도 기능, 사진 저장, 써클 이름 변경, 유저/써클/지도 검색 기능 <br/>
