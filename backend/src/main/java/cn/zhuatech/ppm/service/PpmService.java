/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.ppm.service;
import cn.zhuatech.ppm.common.BusinessException; import cn.zhuatech.ppm.dto.PpmDto.*; import cn.zhuatech.ppm.model.*; import cn.zhuatech.ppm.repository.*; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.util.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service @Transactional(readOnly=true) public class PpmService {
    private final InitiativeReviewRepository orders; private final GovernanceRecordRepository reports; private final ResourcePoolRepository resourcePool; private final PortfolioRiskRepository portfolioRisks; private final CurrentUserService current;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public PpmService(InitiativeReviewRepository orders,GovernanceRecordRepository reports,ResourcePoolRepository resourcePool,PortfolioRiskRepository portfolioRisks,CurrentUserService current){this.orders=orders;this.reports=reports;this.resourcePool=resourcePool;this.portfolioRisks=portfolioRisks;this.current=current;}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Dashboard shopfloorDashboard(){String center=current.get().getProjectPortfolioCode();List<InitiativeReview> list=center==null?orders.findAllByOrderByDueDateAsc():orders.findByProjectPortfolioCodeOrderByDueDateAsc(center);return dashboard(list);}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Dashboard adminDashboard(){return dashboard(orders.findAllByOrderByDueDateAsc());}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public List<InitiativeReviewView> initiativeReviews(){return orders.findAllByOrderByDueDateAsc().stream().map(this::view).toList();}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Transactional public ReportResult report(Long id,ReportRequest request){InitiativeReview order=orders.findById(id).orElseThrow(()->new BusinessException("项目评审不存在"));if(order.getStatus()==InitiativeReview.Status.COMPLETED)throw new BusinessException("已完成任务不能继续反馈");if(order.getCompletedQty()+request.goodQty()>order.getPlannedQty())throw new BusinessException("完成数量不能超过任务剩余数量");order.report(request.goodQty(),request.defectQty());reports.save(new GovernanceRecord(order,request.operationName(),request.goodQty(),request.defectQty(),current.get().getFullName(),request.remark()));return new ReportResult(order.getOrderNo(),order.getCompletedQty(),order.getDefectQty(),progress(order),order.getStatus().name());}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private Dashboard dashboard(List<InitiativeReview> list){int planned=list.stream().mapToInt(InitiativeReview::getPlannedQty).sum(),done=list.stream().mapToInt(InitiativeReview::getCompletedQty).sum(),defects=list.stream().mapToInt(InitiativeReview::getDefectQty).sum();int rate=planned==0?0:Math.round(done*100f/planned);List<Metric> metrics=List.of(new Metric("今日评审",String.format("%,d",planned),list.size()+" 张项目评审","blue"),new Metric("评审完成率",rate+"%",String.format("%,d / %,d",done,planned),"green"),new Metric("结果组合治理率",String.format("%.1f%%",done+defects==0?100d:done*100d/(done+defects)),defects+" 项异常","warn"),new Metric("资源池异常",resourcePool.countByStatus(ResourcePool.Status.ALARM)+"",portfolioRisks.countByResult(PortfolioRisk.Result.PENDING)+" 项待复核","red"));return new Dashboard(metrics,list.stream().map(this::view).toList(),resourcePool.findAllByOrderByCodeAsc().stream().map(e->new ControlView(e.getCode(),e.getName(),e.getProjectPortfolio().getName(),e.getStatus().name(),e.getOee(),e.getLastHeartbeat())).toList(),portfolioRisks.findTop10ByOrderByIdDesc().stream().map(i->new PortfolioRiskView(i.getPortfolioRiskNo(),i.getInitiativeReview().getOrderNo(),i.getInitiativeReview().getProductName(),i.getPortfolioRiskType(),i.getPortfolioRiskQty(),i.getDefectQty(),i.getResult().name(),i.getInspector())).toList());}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private InitiativeReviewView view(InitiativeReview o){return new InitiativeReviewView(o.getId(),o.getOrderNo(),o.getProductCode(),o.getProductName(),o.getProjectPortfolio().getName(),o.getProjectPortfolio().getWorkshop(),o.getPlannedQty(),o.getCompletedQty(),o.getDefectQty(),o.getDueDate(),o.getStatus().name(),o.getBatchNo(),progress(o));}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private int progress(InitiativeReview o){return o.getPlannedQty()==0?0:Math.min(100,Math.round(o.getCompletedQty()*100f/o.getPlannedQty()));}
}
