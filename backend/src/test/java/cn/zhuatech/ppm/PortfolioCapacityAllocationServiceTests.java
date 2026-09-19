/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ppm;

import cn.zhuatech.ppm.service.PortfolioCapacityAllocationService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class PortfolioCapacityAllocationServiceTests {
    private final PortfolioCapacityAllocationService service = new PortfolioCapacityAllocationService();

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void fundsMandatoryInitiativeBeforeStrategicBacklog() {
        var result = service.allocate(new PortfolioCapacityAllocationService.Request(10, List.of(
            new PortfolioCapacityAllocationService.Initiative("REGULATORY", 6, 2, 70, true),
            new PortfolioCapacityAllocationService.Initiative("AI-TRANSFORM", 5, 1, 95, false),
            new PortfolioCapacityAllocationService.Initiative("PORTAL", 4, 3, 60, false))));

        assertEquals("REGULATORY", result.allocations().getFirst().initiativeCode());
        assertEquals(6, result.allocations().getFirst().allocatedFte());
        assertEquals("REBALANCE", result.decision());
        assertTrue(result.allocations().stream().anyMatch(item -> "PARTIAL".equals(item.status())));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void fullyFundsPortfolioWithinCapacity() {
        var result = service.allocate(new PortfolioCapacityAllocationService.Request(12, List.of(
            new PortfolioCapacityAllocationService.Initiative("ERP", 5, 1, 90, true),
            new PortfolioCapacityAllocationService.Initiative("CRM", 4, 2, 80, false))));

        assertEquals("BALANCED", result.decision());
        assertEquals(3, result.remainingFte());
    }
}
