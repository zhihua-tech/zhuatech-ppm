/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ppm.service;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class PortfolioInvestmentGateServiceTest {
    private final PortfolioInvestmentGateService service = new PortfolioInvestmentGateService();

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void approvesViableInvestment() {
        var result = service.assess(new PortfolioInvestmentGateService.Request(
            "PRJ-001", 88, 30, 5_000_000, 8, 10, 10_000_000, 12_000_000, true, true, true));
        assertThat(result.decision()).isEqualTo(PortfolioInvestmentGateService.Decision.APPROVE);
        assertThat(result.fundingGapCents()).isZero();
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void blocksFundingAndCapacityGaps() {
        var result = service.assess(new PortfolioInvestmentGateService.Request(
            "PRJ-002", 90, 40, 2_000_000, 15, 10, 20_000_000, 10_000_000, false, true, false));
        assertThat(result.decision()).isEqualTo(PortfolioInvestmentGateService.Decision.BLOCKED);
        assertThat(result.blockers()).hasSize(4);
        assertThat(result.capacityGapFte()).isEqualTo(5);
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void conditionallyReviewsWeakBusinessCase() {
        var result = service.assess(new PortfolioInvestmentGateService.Request(
            "PRJ-003", 50, 80, -1, 4, 5, 2_000_000, 3_000_000, true, false, true));
        assertThat(result.decision()).isEqualTo(PortfolioInvestmentGateService.Decision.CONDITIONAL);
        assertThat(result.actions()).hasSize(4);
    }
}
