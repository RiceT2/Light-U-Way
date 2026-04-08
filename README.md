# 小呦行（XiaoYouXing）— Java 8 企业级 AI 旅游小助手方案

本仓库提供一个可运行的 Java 8 + Spring Boot 基础骨架，并给出完整的产品交互与系统架构设计，重点满足：

- **少文字输入，全点击操作**
- **多智能体协作 + Function Calling（交通/酒店）+ RAG**
- **可快速部署并直接通过网址访问**

---

## 1. 产品交互设计（按你的流程落地）

### 1.1 启动页（零输入起步）
- 文案区：`Hello，你想去哪儿玩？`
- 文案可根据日期策略动态变体：
  - 周五/周六：`这个周末你想去哪儿？`
  - 法定节假日前 3 天：`假期想去哪里放松一下？`
  - 春季（3-5 月）：`想去哪里春游吗？`
- 交互区：
  - 热门目的地按钮（可横滑）
  - 随机推荐按钮（`手气不错`）
- 默认不强制文本输入。

### 1.2 目的地推荐（全屏模态瀑布流）
- 点击“查看更多目的地”后，弹出全屏模态。
- 城市卡片信息：城市图、热度标签、预算档位、推荐天数。
- 单击卡片即确定，不出现复杂确认弹窗。

### 1.3 时间选择
- 问句：`你想什么时候出发？`
- 使用三段 Picker：
  - 日期（未来 180 天）
  - 小时（00-23）
  - 分钟（00 / 30）
- 精确到半小时，如 `5月20日 09:30`。

### 1.4 去程导航 + 交通推荐
- 首次触发时申请定位权限。
- 规划“当前位置 → 车站/机场”的地面接驳路线。
- 根据到站耗时，筛选可赶上的高铁/航班。
- 列表卡片显示：
  - 交通类型图标（高铁/飞机）
  - 出发到达时间、票价、余票/余位、准点率
  - 推荐原因（如“总耗时最短”）

### 1.5 行程规划核心页面（板式 + 拖拽）
- 左侧：景点地图分布
- 右侧：`第1天 / 第2天...` 板式行程槽位
- 地图点悬浮卡片：
  - 景点亮点
  - 出片机位
  - 最佳拍摄时段
- 支持拖拽景点到日程板。
- 日程内景点间连线采用手绘 wiggly 虚线/曲线风格。

### 1.6 酒店与回程
- 酒店卡片网格：价格、评分、商圈、到景点/车站距离。
- 支持按“位置、预算、标签（看海/亲子/安静）”筛选。
- 回程自动匹配：酒店/最后景点 → 车站/机场 → 返程票务推荐。

### 1.7 语音与文本增强（次入口）
- 在点击面板下方保留文本输入框，支持语音转文本。
- 用户可说：
  - `我的酒店预算是200`
  - `我想去可以看海上日出的地方`
- 由意图识别后触发：搜索、筛选、自动点选。

---

## 2. Java 8 企业级技术架构

## 2.1 总体分层
1. **Client（Web/H5）**：点击优先交互、地图拖拽、语音入口。
2. **BFF 网关层**：统一聚合接口，做会话态与节流。
3. **AI Orchestrator（Spring AI）**：多智能体编排中心。
4. **Domain Services**：交通、酒店、POI、行程、用户偏好。
5. **Data Layer**：MySQL + Redis + Elasticsearch。
6. **External APIs**：地图、车票、机票、酒店供应商、天气。

## 2.2 多智能体角色（Spring AI）
- **Planner Agent**：拆解任务，生成每日行程骨架。
- **Transport Agent**：Function Calling 拉取高铁/航班并排序。
- **Hotel Agent**：Function Calling 拉取酒店并匹配预算/区域。
- **POI Agent**：结合 RAG 知识库做景点推荐与亮点解释。
- **Policy Agent**：检查时间冲突、预算超标、中转风险。

> 编排策略：`Planner -> (POI + Transport + Hotel) -> Policy -> Final Merge`

## 2.3 Function Calling 设计
建议定义标准 Tool 接口（JSON Schema）：
- `search_transport(origin, destination, departTime, type, budget)`
- `search_hotel(city, checkIn, checkOut, budget, geoFilter, tags)`
- `plan_transfer(fromGeo, toStation, departTime)`

返回统一结构后进入排序器（可解释推荐理由），前端直接卡片化展示。

## 2.4 RAG 设计
- 知识来源：官方文旅信息、精选攻略、内部运营词条。
- 流程：
  1. 文档清洗切片
  2. 向量化入库（ES 向量检索或独立向量库）
  3. Query 改写 + 召回 + 重排
  4. 将事实片段注入提示词
- 输出要求：推荐内容必须包含“可执行信息”（时间、地点、费用）。

## 2.5 数据库建议（核心表）
- `user_profile`：用户偏好、预算区间、常用出发地
- `trip_session`：一次会话上下文与状态机
- `trip_day_plan`：按天存储行程块
- `trip_poi_item`：每日景点项（含排序、停留时长）
- `transport_option_cache`：交通结果缓存
- `hotel_option_cache`：酒店结果缓存
- `voice_command_log`：语音指令与解析意图

Redis 用于：会话态、热点查询缓存、限流计数。

## 2.6 搜索与推荐
- Elasticsearch：
  - 城市/景点/酒店全文检索
  - 标签过滤 + 地理距离过滤
  - 热度与个性化混排

---

## 3. 关键接口草案（已在仓库放入示例接口）

- `GET /api/bootstrap`
  - 返回欢迎语、快捷目的地、关键功能清单
- 后续建议：
  - `POST /api/destination/select`
  - `POST /api/departure/time`
  - `POST /api/transport/recommend`
  - `POST /api/itinerary/drag`
  - `POST /api/hotel/recommend`
  - `POST /api/return/recommend`
  - `POST /api/voice/intent`

---

## 4. 部署与可访问网址

## 4.1 最小可运行（当前仓库）
```bash
mvn spring-boot:run
```
启动后访问：`http://localhost:8080`

## 4.2 生产部署建议
- 单体起步（Spring Boot）+ Nginx 反代。
- 使用 Docker 镜像发布到云主机（如阿里云/腾讯云/AWS）。
- 域名 + HTTPS（Let’s Encrypt）。
- 若流量增长：拆分 AI 编排服务与票务/酒店适配服务。

---

## 5. 前端“丝滑点击”实现建议

- 优先使用“预加载 + 骨架屏 + 乐观更新”。
- 所有推荐卡片采用虚拟列表避免卡顿。
- 拖拽场景降频更新（16ms/帧）并使用 GPU 加速样式。
- 重要按钮反馈：按下态 < 60ms、请求超时兜底文案。

---

## 6. 当前仓库内容

- Java 8 / Spring Boot 2.7.x 启动工程
- `GET /api/bootstrap` 示例接口
- 一个可直接打开的静态首页（点击目的地体验）

> 这是一版“架构 + 交互原型骨架”。如果你希望，我下一步可以直接补齐：
> 1) 多智能体编排代码骨架（Spring AI）
> 2) MySQL/Redis/ES 的 docker-compose
> 3) 交通/酒店 Function Calling 适配器模板

