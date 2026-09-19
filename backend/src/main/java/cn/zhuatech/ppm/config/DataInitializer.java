/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ppm.config;

import cn.zhuatech.ppm.model.*;
import cn.zhuatech.ppm.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Configuration
public class DataInitializer {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Bean
    CommandLineRunner seed(ProjectPortfolioRepository projectPortfolios, InitiativeReviewRepository orders,
                           ResourcePoolRepository resourcePools, PortfolioRiskRepository portfolioRisks,
                           UserRepository users, PasswordEncoder encoder) {
        return args -> {
            if (projectPortfolios.count() > 0) return;
            ProjectPortfolio chemistry = projectPortfolios.save(new ProjectPortfolio("PPM-CHEM", "数字化转型组合", "项目管理办公室", 180));
            ProjectPortfolio micro = projectPortfolios.save(new ProjectPortfolio("PPM-MICRO", "产品研发组合", "研发中心", 120));
            ProjectPortfolio material = projectPortfolios.save(new ProjectPortfolio("PPM-MAT", "客户交付组合", "工程中心", 96));

            InitiativeReview t1 = orders.save(new InitiativeReview("INIT-260801-018", "GB-T-228", "ERP 升级项目", material, 24, 16, 1, LocalDate.now().plusDays(1), InitiativeReview.Status.RUNNING, "Q3-BL"));
            InitiativeReview t2 = orders.save(new InitiativeReview("INIT-260801-021", "PRJ-EDGE-08", "新一代边缘网关研发", chemistry, 18, 8, 0, LocalDate.now().plusDays(1), InitiativeReview.Status.RUNNING, "R2.3"));
            InitiativeReview t3 = orders.save(new InitiativeReview("INIT-260802-006", "ISO-4833", "华东客户交付群", micro, 12, 0, 0, LocalDate.now().plusDays(3), InitiativeReview.Status.RELEASED, "W32"));
            InitiativeReview t4 = orders.save(new InitiativeReview("INIT-260726-015", "PRJ-DATA-06", "数据中台建设", chemistry, 20, 20, 1, LocalDate.now(), InitiativeReview.Status.COMPLETED, "M6"));

            resourcePools.saveAll(List.of(
                new ResourcePool("POOL-HPLC-03", "Java 交付资源池", chemistry, ResourcePool.Status.RUNNING, 88),
                new ResourcePool("POOL-ICP-02", "产品设计资源池", chemistry, ResourcePool.Status.IDLE, 76),
                new ResourcePool("POOL-UTM-05", "项目管理资源池", material, ResourcePool.Status.RUNNING, 91),
                new ResourcePool("POOL-INC-08", "数据工程资源池", micro, ResourcePool.Status.ALARM, 62)
            ));
            portfolioRisks.saveAll(List.of(
                new PortfolioRisk("RISK-260801-032", t1, "留样复盘", 6, 0, PortfolioRisk.Result.PASSED, "周妍"),
                new PortfolioRisk("RISK-260801-011", t2, "前处理复盘", 3, 0, PortfolioRisk.Result.PASSED, "陆承"),
                new PortfolioRisk("RISK-260801-018", t4, "项目复核", 5, 1, PortfolioRisk.Result.FAILED, "周妍"),
                new PortfolioRisk("RISK-260802-003", t3, "项目入池确认", 4, 0, PortfolioRisk.Result.PENDING, "陆承")
            ));
            String demo = encoder.encode("Demo@2026");
            users.saveAll(List.of(
                new UserAccount("operator", demo, "陆承", UserAccount.Role.PROJECT_MANAGER, "PPM-CHEM"),
                new UserAccount("planner", demo, "周妍", UserAccount.Role.PPM_DIRECTOR, null),
                new UserAccount("quality", demo, "顾清", UserAccount.Role.QUALITY, null),
                new UserAccount("admin", encoder.encode("ZhuaTech@2026"), "系统管理员", UserAccount.Role.ADMIN, null)
            ));
        };
    }
}
