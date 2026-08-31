/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ppm.service;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class PortfolioInvestmentGateService {
    public Assessment assess(Request request) {
        long fundingGap = Math.max(0, request.budgetRequestedCents() - request.budgetAvailableCents());
        double capacityGap = Math.max(0, request.capacityDemandFte() - request.availableFte());
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        if (fundingGap > 0) blockers.add("申请预算超过可用预算");
        if (capacityGap > 0) blockers.add("资源需求超过可用 FTE");
        if (!request.sponsorAssigned()) blockers.add("未指定项目发起人");
        if (!request.dependenciesAssessed()) blockers.add("关键依赖尚未评估");
        if (!blockers.isEmpty()) {
            actions.add("调整资金与资源方案，补齐治理责任后重新进入投资评审");
            return new Assessment(Decision.BLOCKED, fundingGap, capacityGap, blockers, actions);
        }
        if (request.strategicScore() < 60 || request.riskScore() > 70
            || request.npvCents() < 0 || !request.benefitsOwnerAssigned()) {
            if (request.strategicScore() < 60) actions.add("补充战略关联与可量化目标");
            if (request.riskScore() > 70) actions.add("提交高风险缓释计划");
            if (request.npvCents() < 0) actions.add("复核商业案例与非财务收益");
            if (!request.benefitsOwnerAssigned()) actions.add("指定收益责任人");
            return new Assessment(Decision.CONDITIONAL, fundingGap, capacityGap, blockers, actions);
        }
        actions.add("批准投资并建立预算、容量和收益基线");
        return new Assessment(Decision.APPROVE, fundingGap, capacityGap, blockers, actions);
    }

    public record Request(@NotBlank String projectCode, @Min(0) @Max(100) int strategicScore,
                          @Min(0) @Max(100) int riskScore, long npvCents,
                          @Min(0) double capacityDemandFte, @Min(0) double availableFte,
                          @Min(0) long budgetRequestedCents, @Min(0) long budgetAvailableCents,
                          boolean sponsorAssigned, boolean benefitsOwnerAssigned,
                          boolean dependenciesAssessed) {}
    public record Assessment(Decision decision, long fundingGapCents, double capacityGapFte,
                             List<String> blockers, List<String> actions) {}
    public enum Decision { APPROVE, CONDITIONAL, BLOCKED }
}
