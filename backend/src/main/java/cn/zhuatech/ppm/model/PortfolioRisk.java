/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.ppm.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity @Table(name="ppm_portfolio_risk") public class PortfolioRisk extends BaseEntity {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Result { PENDING, PASSED, FAILED }
    @Column(nullable=false,unique=true,length=32) private String portfolioRiskNo; @ManyToOne(optional=false,fetch=FetchType.LAZY) private InitiativeReview initiativeReview;
    @Column(nullable=false,length=30) private String portfolioRiskType; @Column(nullable=false) private int portfolioRiskQty; @Column(nullable=false) private int defectQty; @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private Result result;
    @Column(length=50) private String inspector; @Column(nullable=false) private LocalDateTime createdAt;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected PortfolioRisk(){} /**
                                 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                 */
public PortfolioRisk(String portfolioRiskNo,InitiativeReview initiativeReview,String portfolioRiskType,int portfolioRiskQty,int defectQty,Result result,String inspector){this.portfolioRiskNo=portfolioRiskNo;this.initiativeReview=initiativeReview;this.portfolioRiskType=portfolioRiskType;this.portfolioRiskQty=portfolioRiskQty;this.defectQty=defectQty;this.result=result;this.inspector=inspector;this.createdAt=LocalDateTime.now();}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getPortfolioRiskNo(){return portfolioRiskNo;} /**
                                                                 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                 */
public InitiativeReview getInitiativeReview(){return initiativeReview;} /**
                                                                                                                                         * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                         */
public String getPortfolioRiskType(){return portfolioRiskType;} /**
                                                                                                                                                                                                         * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                         */
public int getPortfolioRiskQty(){return portfolioRiskQty;} /**
                                                                                                                                                                                                                                                                    * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                    */
public int getDefectQty(){return defectQty;} /**
                                                                                                                                                                                                                                                                                                                 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                                                 */
public Result getResult(){return result;} /**
                                                                                                                                                                                                                                                                                                                                                           * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                                                                                                           */
public String getInspector(){return inspector;}
}
