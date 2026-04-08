package com.xiaoyouxing.function.hotel;

import java.util.Arrays;
import java.util.List;

/**
 * 酒店 Function Calling 适配器模板。
 * 后续可替换为：调用 OTA/酒店供应商 API，并做地理位置过滤和预算筛选。
 */
public class HotelFunctionAdapter {

    public List<String> searchHotels(HotelSearchRequest request) {
        int budget = request.getBudget() == null ? 400 : request.getBudget().intValue();
        return Arrays.asList(
                "🏨 海景轻居酒店 ¥" + budget,
                "🏨 城市中心精选酒店 ¥" + (budget + 120)
        );
    }
}
