# Spring Boot 示範專案

這是一個使用 Spring Boot 框架建立的基礎專案，展示了基本的 RESTful API 實作。

## 專案結構

```tree
demo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/demo/
│   │   │       ├── DemoApplication.java
│   │   │       └── controller/
│   │   │           └── HelloController.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/example/demo/
│               └── DemoApplicationTests.java
└── pom.xml
```

## 技術規格

- Java 版本: 17
- Spring Boot 版本: 3.x
- 建置工具: Maven

## 快速開始

### 系統需求

- JDK 17 或以上
- Maven 3.6.x 或以上

### 建置與執行

1. 複製專案

```bash
git clone https://github.com/khcjump/template_springboot_01.git
cd demo
```

1. 建置專案

```bash
./mvnw clean install
```

1. 執行專案

```bash
./mvnw spring-boot:run
```

應用程式將會在 [http://localhost:8080](http://localhost:8080) 啟動

### API 端點

- GET `/hello`: 回傳簡單的歡迎訊息

## 測試

執行單元測試：

```bash
./mvnw test
```

## 開發指南

1. 所有的 Controller 類別應放在 `com.example.demo.controller` 套件下
2. 遵循 RESTful API 設計原則
3. 確保所有的 API 端點都有適當的單元測試

## 貢獻指南

1. Fork 此專案
2. 建立您的功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交您的修改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 開啟一個 Pull Request

## 授權條款

此專案採用 MIT 授權條款 - 詳見 [LICENSE](LICENSE) 檔案

## 作者

- [khcjump](https://github.com/khcjump)

## 更新日誌

- 2025-09-24: 初始版本建立
  - 建立基礎專案結構
  - 新增簡單的 Hello World API
  - 完成基礎文件
