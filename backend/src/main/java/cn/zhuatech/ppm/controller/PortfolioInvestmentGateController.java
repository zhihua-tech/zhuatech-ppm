/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ppm.controller;

import cn.zhuatech.ppm.common.ApiResponse;
import cn.zhuatech.ppm.service.PortfolioInvestmentGateService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enterprise/ppm")
public class PortfolioInvestmentGateController {
    private final PortfolioInvestmentGateService service;
    public PortfolioInvestmentGateController(PortfolioInvestmentGateService service) { this.service = service; }

    @PostMapping("/portfolio-investment-gate")
    public ApiResponse<PortfolioInvestmentGateService.Assessment> assess(
        @Valid @RequestBody PortfolioInvestmentGateService.Request request) {
        return ApiResponse.ok(service.assess(request));
    }
}
