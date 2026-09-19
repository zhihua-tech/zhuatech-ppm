/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.ppm.controller; import cn.zhuatech.ppm.common.ApiResponse; import cn.zhuatech.ppm.dto.PpmDto.*; import cn.zhuatech.ppm.service.PpmService; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*; import java.util.List;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/admin") @PreAuthorize("hasAnyRole('PPM_DIRECTOR','QUALITY','ADMIN')") public class PortfolioAdminController {private final PpmService service;/**
                                                                                                                                                                                     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                     */
public PortfolioAdminController(PpmService service){this.service=service;}/**
                                                                                                                                                                                                                                                               * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                               */
@GetMapping("/dashboard") public ApiResponse<Dashboard> dashboard(){return ApiResponse.ok(service.adminDashboard());}/**
                                                                                                                                                                                                                                                                                                                                                                                    * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                                                                                                                    */
@GetMapping("/work-orders") public ApiResponse<List<InitiativeReviewView>> orders(){return ApiResponse.ok(service.initiativeReviews());}}
