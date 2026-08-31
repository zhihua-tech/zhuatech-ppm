# 企业级项目组合投资门禁

[知华科技（上海如静知华信息科技有限公司）](https://www.zhuatech.cn/)为 PPM 开源版补充投资决策阶段门。

`POST /api/enterprise/ppm/portfolio-investment-gate` 联合评估战略得分、风险、NPV、预算、FTE 容量、发起人、收益责任人和关键依赖，输出 `APPROVE / CONDITIONAL / BLOCKED`，同时量化资金与容量缺口。

生产应用可把决策结果接入投资委员会工作流，并对批准预算、资源基线、收益承诺及后续变更保留完整审计轨迹。
