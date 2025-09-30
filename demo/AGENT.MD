# AI 代理日誌：Spring Boot 專案修改紀錄

本文件概述了 AI 代理對 Spring Boot 專案所做的變更。

## 1. 依賴與組態設定

### 1.1. `pom.xml` 修改

- **Redisson**：新增 `redisson-spring-boot-starter` 依賴，以整合 Redisson 進行基於 Redis 的操作。
- **MongoDB**：新增 `spring-boot-starter-data-mongodb` 依賴，以啟用與 MongoDB 資料庫的互動。
- **Java 版本**：將 `java.version` 屬性修正為相容版本 (`17`)，以解決使用現代 Java 功能 (如 Stream API) 時的編譯問題。

### 1.2. `application.properties` 組態

- **Redis 連線**：設定應用程式以連線至本機 Redis 伺服器。
  ```properties
  spring.redis.address=redis://127.0.0.1:6379
  ```
- **MongoDB 連線**：設定與本機 MongoDB 執行個體的連線，目標為 `mono1` 資料庫。
  ```properties
  spring.data.mongodb.uri=mongodb://localhost:27017
  spring.data.mongodb.database=mono1
  ```

## 2. API 開發

### 2.1. Redis API

使用 Redisson 建立了一個簡單的鍵值儲存 API。

- **控制器 (Controller)**：`RedissonController.java`
- **服務 (Service)**：`RedissonMMService.java`

**端點 (Endpoints)**：
- `POST /api/redis/set?key={key}&value={value}`：在 Redis 中為指定的鍵設定一個字串值。
- `GET /api/redis/get?key={key}`：從 Redis 中根據指定的鍵擷取其字串值。

### 2.2. MongoDB Carts API

實作了一個用於管理 `mono1` MongoDB 資料庫中 `Carts` 集合的 API。

- **控制器 (Controller)**：`CartController.java`
- **服務 (Service)**：`CartService.java`
- **儲存庫 (Repository)**：`CartRepository.java` (繼承 `MongoRepository`)
- **模型 (Model)**：`Cart.java` (代表 `Carts` 集合)

**端點 (Endpoints)**：
- `POST /api/carts`：建立一個新的購物車項目。請求主體應為符合 `Cart` 模型的 JSON 物件 (例如：`{"name": "MyItem", "quantity": 5}`)。
- `GET /api/carts/{id}`：根據 ID 擷取特定的購物車項目。

## 3. 程式碼重構與錯誤修復

- **服務層抽象化**：將與 `RedissonClient` 互動的邏輯從 `RedissonController` 移至專用的 `RedissonMMService`。這改善了關注點分離和程式碼的可維護性。
- **依賴注入**：在所有服務和控制器中，將欄位注入 (`@Autowired` 於私有欄位) 切換為建構函式注入。這是建立可測試且可靠元件的建議最佳實踐。
- **Stream API 修復**：修正了 `RedissonMMService` 中與在 `Iterable` 上使用 Java Stream API 相關的問題。更新了實作，以使用 `StreamSupport.stream()` 正確地將 `Iterable` 轉換為 `Stream`。
