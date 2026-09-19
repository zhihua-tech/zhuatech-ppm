/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ppm.controller;

import cn.zhuatech.ppm.common.ApiResponse;
import cn.zhuatech.ppm.service.PortfolioCapacityAllocationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/ppm/insights")
public class PortfolioCapacityController {
    private final PortfolioCapacityAllocationService service;

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public PortfolioCapacityController(PortfolioCapacityAllocationService service) {
        this.service = service;
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/portfolio-capacity-allocation")
    public ApiResponse<PortfolioCapacityAllocationService.Result> allocate(
        @Valid @RequestBody PortfolioCapacityAllocationService.Request request) {
        return ApiResponse.ok(service.allocate(request));
    }
}
