# 小呦行（XiaoYouXing）— Java 25 AI 旅游小助手

本次调整完成：
- Java 版本升级到 **25**。
- 前端保持“**点击优先**”交互：目的地点击 → 时间点击 → 一键生成行程。
- 后端底层结构参考 `Szh0595/TravelAssistant`：引入 `BaseAgent/ReActAgent/ToolCallAgent`、`tools`、`rag`、`app` 分层。

---

## 1. 技术栈
- Java 25
- Spring Boot 3.4.5
- Spring AI（模型与 ES 向量存储 starter）
- MySQL / Redis / Elasticsearch

---

## 2. 点击式前端流程
1. 启动页展示欢迎语与热门目的地按钮。
2. 点击目的地后出现出发时间按钮（半小时粒度示例）。
3. 点击“生成板式行程”调用 `/api/plan/generate`。

> 仍以点击操作为主，避免依赖大量文字输入。

---

## 3. 参考 TravelAssistant 的底层实现映射
参考仓库：`https://github.com/Szh0595/TravelAssistant`

- `agent/BaseAgent`：状态机 + Step 循环
- `agent/ToolCallAgent`：工具注册和调用
- `tools/*`：WebSearch / WebScraping / Terminate
- `rag/TravelVectorStoreConfig`：向量检索入口
- `app/TravelAssistantApp`：应用编排入口

本项目对应文件：
- `src/main/java/com/xiaoyouxing/agent/*`
- `src/main/java/com/xiaoyouxing/tools/*`
- `src/main/java/com/xiaoyouxing/rag/TravelVectorStoreConfig.java`
- `src/main/java/com/xiaoyouxing/app/TravelAssistantApp.java`

---

## 4. API
- `GET /api/bootstrap`
- `POST /api/plan/generate`
- `GET /api/assistant/chat?message=xxx`

---

## 5. 启动
```bash
docker compose up -d
mvn spring-boot:run
```

访问：`http://localhost:8080`

