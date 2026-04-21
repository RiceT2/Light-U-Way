package com.xiaoyouxing.function.transport;

import java.util.Arrays;
import java.util.List;

/**
 * 交通 Function Calling 适配器模板。
 * 后续可替换为：调用真实机票/高铁供应商 API，并把返回结果归一化。
 */
public class TransportFunctionAdapter {

    public List<String> searchTransport(TransportSearchRequest request) {
        return Arrays.asList(
                "🚄 高铁 G1234 09:00-13:45 ¥560",
                "✈️ 航班 MU520 10:30-13:05 ¥880"
        );
    }
}
