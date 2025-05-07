# 주문 관리 시스템 (Order Management System)
## 프로젝트 소개
이 프로젝트는 한 번의 주문으로 다양한 상품을 구매할 수 있는 API를 제공합니다. 각 상품은 개별적으로 할인 금액이 적용되며, 주문 생성, 주문 상품 취소, 주문 상품 조회와 같은 기능을 제공합니다. 이 프로젝트는 Spring Boot를 기반으로 개발되었으며, 간단한 H2 In-Memory 데이터베이스를 사용하여 데이터 관리를 수행합니다.
## 주요 기능
### 1. 주문 생성 API
- **설명**: 입력받은 상품 리스트와 수량 정보를 바탕으로 주문을 생성합니다.
- **입력**: 상품 ID 및 수량의 리스트.
- **출력**: 주문 번호, 각 상품의 실구매 금액, 주문 전체 금액.
- **요구사항**:
  - 재고가 부족한 상품이 있는 경우, 주문 생성 실패 처리.
  - 주문 생성 시 상품 재고를 차감.

### 2. 주문 상품 취소 API
- **설명**: 입력받은 주문 번호와 취소 대상 상품 정보를 바탕으로 상품 취소를 처리합니다.
- **입력**: 주문 번호, 취소할 상품 ID.
- **출력**: 취소된 상품 정보, 환불 금액, 취소 후 남은 전체 주문 금액.
- **요구사항**:
  - 취소된 상품의 재고를 복구.
  - 존재하지 않는 주문 번호 또는 상품 ID로 요청 시 에러 처리.
  - 이미 취소된 상품에 대한 재취소 요청 시 에러 처리.

### 3. 주문 상품 조회 API
- **설명**: 입력받은 주문 번호를 바탕으로 해당 주문 내역을 조회합니다.
- **입력**: 주문 번호.
- **출력**: 주문 상품 리스트, 각 상품의 수량, 실구매 금액, 주문 전체 금액.
- **요구사항**:
  - 존재하지 않는 주문 번호 조회 시 에러 처리.

## 프로젝트 구조
``` plaintext
src/
├── main/
│   ├── java/com/ssg/order_management/
│   │   ├── controller/     // REST API 컨트롤러
│   │   ├── service/        // 비즈니스 로직
│   │   ├── repository/     // JpaRepository 인터페이스 (DB와 상호작용)
│   │   ├── dto/            // 요청과 응답을 처리하는 DTO
│   │   └── entity/         // JPA 엔티티 클래스
│   └── resources/
│       ├── application.yml // 애플리케이션 설정 (H2 DB 연결 포함)
│       └── data.sql         // 초기 상품 데이터
└── test/
    ├── java/com/ssg/order_management/
        ├── controller/     // API 단위 테스트
        ├── service/        // 서비스 로직 테스트
```
## 기술 스택
- **백엔드**: Java 8, Spring Boot 2.7, Spring Data JPA
- **데이터베이스**: H2 데이터베이스(In-Memory)
- **빌드 도구**: Gradle
- **문서화**: Swagger-UI 또는 Spring REST Docs
- **테스트**: JUnit5, Mockito

## 초기 데이터 (data.sql)
다음은 애플리케이션 실행 시 자동으로 데이터베이스에 로드되는 초기 상품 데이터입니다.

| 상품 ID | 상품명 | 판매가격 (원) | 할인금액 (원) | 재고 |
| --- | --- | --- | --- | --- |
| 1000000001 | 이마트 생수 | 800 | 100 | 1000 |
| 1000000002 | 신라면 멀티팩 | 4200 | 500 | 500 |
| 1000000003 | 바나나 한 송이 | 3500 | 300 | 200 |
| 1000000004 | 삼겹살 500g | 12000 | 2000 | 100 |
| 1000000005 | 오리온 초코파이 | 3000 | 400 | 300 |
## API 상세 문서화
### 1. 주문 생성 API (POST `/api/order`)
- **요청 Body 예시**:
``` json
{
  "orderItems": [
    { "productId": 1000000001, "quantity": 2 },
    { "productId": 1000000002, "quantity": 1 }
  ]
}
```
- **응답 Body 예시**:
``` json
{
  "orderId": 1,
  "totalAmount": 8900,
  "items": [
    { "productId": 1000000001, "finalPrice": 1400 },
    { "productId": 1000000002, "finalPrice": 3700 }
  ]
}
```
### 2. 주문 상품 취소 API (POST `/api/order/{orderId}/cancel`)
- **요청 Body 예시**:
``` json
{
  "productId": 1000000001
}
```
- **응답 Body 예시**:
``` json
{
  "cancelledProduct": { "productId": 1000000001, "refundAmount": 1400 },
  "remainingAmount": 3700
}
```
### 3. 주문 상품 조회 API (GET `/api/order/{orderId}`)
- **응답 Body 예시**:
``` json
{
  "orderId": 1,
  "totalAmount": 8900,
  "items": [
    { "productId": 1000000001, "quantity": 2, "finalPrice": 1400 },
    { "productId": 1000000002, "quantity": 1, "finalPrice": 3700 }
  ]
}
```
## 실행 방법
### 1. 프로젝트 클론
``` bash
git clone https://github.com/2chung9/order-management
cd order-management
```
### 2. 프로젝트 실행
``` bash
./gradlew bootRun
```
### 3. H2 콘솔 접속 (H2 데이터베이스 확인용)
- 접속 경로: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- 사용자명: `sa`, 비밀번호: `password`

### 4. Swagger-UI 접속 (API 문서 확인용)
- 접속 경로: `http://localhost:8080/swagger-ui/index.html#/`

## 테스트
- 테스트 실행 명령:
``` bash
./gradlew test
```
- JUnit5 및 Mockito를 사용하여 서비스 로직과 API 컨트롤러에 대한 단위 테스트를 작성했습니다.

### 5. 테스트 페이지
애플리케이션에는 간단한 테스트 페이지가 포함되어 있습니다. 해당 페이지는 브라우저를 통해 접근 가능하며, 주문 API를 간편하게 테스트할 수 있도록 설계되었습니다.
- **접속 경로**: [http://localhost:8080/test.html](http://localhost:8080/test.html)
- **설명**:
  - 주문 생성, 상품 취소, 주문 조회 등 주요 기능들과 연동하여 기본적인 API 테스트를 수행할 수 있습니다.
  - JSON 입력 및 결과 응답 확인이 가능한 간단한 UI를 제공합니다.
  - 개발 또는 테스트 환경에서 사용하기 위해 추가된 선택 요소입니다.

## 라이센스
이 프로젝트는 **SSG 제출용**으로 허가되어 배포됩니다.

## 문의
문의 사항은 `chung_9@naver.com`으로 연락해주세요.
위 텍스트는 README 파일로 바로 사용할 수 있도록 제공된 형식입니다. 더 추가할 부분이나 수정할 내용이 있으면 말씀해주세요.
