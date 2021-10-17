package com.cosmos.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Sentinel")
public class SentinelController {
    private static final String RESOURCE_NAME = "/SentinelControl1";
    private static final String RESOURCE_NAME2 = "/SentinelDowngrade1";
    @PostConstruct
    private static void initSystemRule() {
        //流控规则
        List<FlowRule> flowRules = new ArrayList<>();
        //流控
        FlowRule rule = new FlowRule();
        //设置受保护的资源
        rule.setResource(RESOURCE_NAME);
        //设置流控规则QPS
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);//可通过线程数或者QPS进行控制
        //设置受保护的资源阈值
        rule.setCount(1);//一秒访问一次
        flowRules.add(rule);
        //加载配置
        FlowRuleManager.loadRules(flowRules);

        //降级规则
        List<DegradeRule> degradeRules = new ArrayList<>();
        //降级
        DegradeRule degradeRule = new DegradeRule();
        //设置降级资源
        degradeRule.setResource(RESOURCE_NAME2);
        //设置降级规则
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);//通过异常数量,异常比例或慢调用比例来判断降级
        //设置阈值
        degradeRule.setCount(2);//触发2个异常就进行降级
        //设置进行降级的最少请求数
        degradeRule.setMinRequestAmount(2);//至少两次请求
        //设置时间间隔
        degradeRule.setStatIntervalMs(60*1000);//两次异常之间的间隔,默认是1秒
        //熔断持续的时长
        degradeRule.setTimeWindow(10);//触发降级后,降级的持续时间
        //发生熔断后,再次请求的接口会直接调用降级的接口,等降级的持续时间过后,恢复接口使用,如果第一次请求还是异常,则再次降级
        //加载配置
        degradeRules.add(degradeRule);
        DegradeRuleManager.loadRules(degradeRules);

    }
    //流量控制(方法1)
    @GetMapping("/Control1")
    public String Control1(){
        Entry entry = null;
        // 务必保证 finally 会被执行
        try {
            // 资源名可使用任意有业务语义的字符串，注意数目不能太多（超过 1K），超出几千请作为参数传入而不要直接作为资源名
            // EntryType 代表流量类型（inbound/outbound），其中系统规则只对 IN 类型的埋点生效
            entry = SphU.entry(RESOURCE_NAME);
            // 被保护的业务逻辑
            // do something...
            System.out.println("业务逻辑执行!");
            return "业务逻辑执行!";
        } catch (BlockException ex) {
            // 资源访问阻止,被限流或被降级,进行相应的处理操作
            System.out.println("限流了");
            return "限流了";
        } catch (Exception ex) {
            // 若需要配置降级规则，需要通过这种方式记录业务异常
            Tracer.traceEntry(ex, entry);
        } finally {
            // 务必保证 exit，务必保证每个 entry 与 exit 配对
            if (entry != null) {
                entry.exit();
            }
        }
        return "??";
    }

    /*
        流量控制(方法2)
        @SentinelResource注解代替方法1
        参数:
          value = 控制的资源,
          blockHandler = 限流的方法(如果没有指定限流类,则该方法必须在同一个类中)
          blockHandlerClass = 限流的类(如果指定了其他的类,其他类中的方法必须是static)
          fallback = 出现异常后处理的方法(如果blockHandler和fallback同时指定了,则blockHandler优先级更高)
          fallbackClass = 异常处理类(如果指定了其他的类,其他类中的方法必须是static)
          exceptionsToIgnore = 不进行处理的异常
    */
    @GetMapping("/Control2")
    @SentinelResource(value = RESOURCE_NAME,blockHandler = "limit")
    public String Control2(){
        System.out.println("业务逻辑执行!");
        return "业务逻辑执行!";
    }
    //限流策略方法,必须是public,返回值要和源方法保持一致.入参多加一个BlockException
    public String limit(BlockException e){
        System.out.println("限流了!!");
        return "限流了!!";
    }

    /*
        降级规则
        @SentinelResource注解
     */
    @GetMapping("/Downgrade1")
    @SentinelResource(value = RESOURCE_NAME2,blockHandler = "DowngradeWay")
    public String Downgrade1() throws Exception {
        throw new Exception("主动抛出异常");
    }
    //降级策略方法,必须是public,返回值要和源方法保持一致.入参多加一个BlockException
    public String DowngradeWay(BlockException e){
        System.out.println("降级了!!");
        return "降级了!!";
    }

}
