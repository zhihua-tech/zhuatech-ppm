/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ppm.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class PortfolioCapacityAllocationService {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Result allocate(Request request) {
        List<Initiative> ranked = request.initiatives().stream().sorted((left, right) -> {
            int mandatory = Boolean.compare(right.mandatory(), left.mandatory());
            if (mandatory != 0) return mandatory;
            int strategic = Integer.compare(right.strategicScore(), left.strategicScore());
            return strategic != 0 ? strategic : Integer.compare(left.priority(), right.priority());
        }).toList();
        double remaining = request.availableFte();
        List<Allocation> allocations = new ArrayList<>();
        for (Initiative initiative : ranked) {
            double allocated = Math.min(remaining, initiative.demandFte());
            remaining = Math.max(0, remaining - allocated);
            String status = allocated == 0 ? "WAITLIST"
                : allocated + .0001 >= initiative.demandFte() ? "FUNDED" : "PARTIAL";
            allocations.add(new Allocation(initiative.initiativeCode(), round(initiative.demandFte()),
                round(allocated), status));
        }
        double allocatedTotal = request.availableFte() - remaining;
        boolean constrained = allocations.stream().anyMatch(item -> !"FUNDED".equals(item.status()));
        String decision = constrained ? "REBALANCE" : "BALANCED";
        double utilization = request.availableFte() == 0 ? 0 : round(allocatedTotal / request.availableFte());
        return new Result(round(request.availableFte()), round(allocatedTotal), utilization,
            round(remaining), decision, allocations);
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private double round(double value) {
        return Math.round(value * 10_000D) / 10_000D;
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Request(@DecimalMin("0") double availableFte,
                          @NotEmpty List<@Valid Initiative> initiatives) {}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Initiative(@NotBlank String initiativeCode,
                             @DecimalMin("0.1") double demandFte,
                             @Min(1) @Max(5) int priority,
                             @Min(0) @Max(100) int strategicScore,
                             boolean mandatory) {}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Allocation(String initiativeCode, double demandFte,
                             double allocatedFte, String status) {}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Result(double availableFte, double allocatedFte, double utilization,
                         double remainingFte, String decision, List<Allocation> allocations) {}
}
