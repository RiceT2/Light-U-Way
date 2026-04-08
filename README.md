# 小呦行（XiaoYouXing）— Java 8 企业级 AI 旅游小助手

本项目已继续补齐三部分：
1. **多智能体编排代码骨架（Spring 风格，可平滑接入 Spring AI）**
2. **MySQL / Redis / Elasticsearch 的 docker-compose**
3. **交通 / 酒店 Function Calling 适配器模板**

---

## 1) 当前已实现的代码骨架

## 1.1 API
- `GET /api/bootstrap`：启动页欢迎语与快捷目的地
- `POST /api/plan/generate`：触发多智能体编排，返回行程板、交通与酒店推荐

示例请求：
```json
{
  "destination": "三亚",
  "departureTime": "2026-05-20 09:30",
  "hotelBudget": 300
}
```

## 1.2 多智能体编排链路
已实现编排顺序：
`Planner -> (POI + Transport + Hotel) -> Policy -> Merge`

对应类：
- `AiOrchestratorService`：总编排器
- `PlannerAgent`：生成“第1天/第2天...”板式结构
- `PoiAgent`：景点亮点（可替换为 RAG 检索）
- `TransportAgent`：调用交通函数适配器
- `HotelAgent`：调用酒店函数适配器
- `PolicyAgent`：进行预算/可行性审查

> 说明：当前实现为“可运行骨架 + Mock 推荐数据”，后续可将 Agent 内部替换为 Spring AI 的 ChatClient、Tool Calling 与 RAG 检索组件。

## 1.3 Function Calling 适配器模板
- `TransportFunctionAdapter#searchTransport(...)`
- `HotelFunctionAdapter#searchHotels(...)`

模板职责：
- 对接第三方供应商 API（机票/高铁/酒店）
- 统一返回结构，供 Agent 进行排序与解释

---

## 2) 基础设施编排（MySQL / Redis / ES）

仓库新增 `docker-compose.yml`，可一键启动：
- MySQL 8
- Redis 7
- Elasticsearch 8
- Kibana 8

启动命令：
```bash
docker compose up -d
```

常用地址：
- MySQL: `localhost:3306`
- Redis: `localhost:6379`
- Elasticsearch: `http://localhost:9200`
- Kibana: `http://localhost:5601`

---

## 3) 本地运行

```bash
mvn spring-boot:run
```

应用地址：`http://localhost:8080`

---

## 4) 下一步建议（可继续分步）

1. 接入真实 Spring AI：
   - ChatClient
   - Tool Calling（绑定交通/酒店工具）
   - VectorStore（ES 向量检索）
2. 增加行程拖拽保存接口：
   - `POST /api/itinerary/drag`
3. 增加语音意图接口：
   - `POST /api/voice/intent`
4. 将 Mock 适配器切换为真实供应商 SDK/API。

