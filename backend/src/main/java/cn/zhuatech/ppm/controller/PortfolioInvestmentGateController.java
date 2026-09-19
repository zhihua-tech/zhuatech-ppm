/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ppm.controller;

import cn.zhuatech.ppm.common.ApiResponse;
import cn.zhuatech.ppm.service.PortfolioInvestmentGateService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/enterprise/ppm")
public class PortfolioInvestmentGateController {
    private final PortfolioInvestmentGateService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public PortfolioInvestmentGateController(PortfolioInvestmentGateService service) { this.service = service; }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/portfolio-investment-gate")
    public ApiResponse<PortfolioInvestmentGateService.Assessment> assess(
        @Valid @RequestBody PortfolioInvestmentGateService.Request request) {
        return ApiResponse.ok(service.assess(request));
    }
}
