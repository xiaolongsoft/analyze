package ftjw.web.mobile.analyze.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ftjw.web.mobile.analyze.core.Result;
import ftjw.web.mobile.analyze.core.ResultGenerator;
import ftjw.web.mobile.analyze.dao.AgentPayLogRepository;
import ftjw.web.mobile.analyze.dao.AgentRepository;
import ftjw.web.mobile.analyze.dao.OrderRepository;
import ftjw.web.mobile.analyze.dao.ProductRepository;
import ftjw.web.mobile.analyze.entity.Agent;
import ftjw.web.mobile.analyze.entity.AgentPayLog;
import ftjw.web.mobile.analyze.entity.Product;
import ftjw.web.mobile.analyze.entity.YdhOrder;
import ftjw.web.mobile.analyze.service.AgentService;
import ftjw.web.mobile.analyze.utill.UpdateTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 管理员相关的api
 * 殷晓龙
 * 2020/4/3 11:26
 */
@Api(tags = "管理员控制器", produces = "管理员功能api")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AgentRepository agentRepository;
    @Resource
    private ProductRepository productRepository;
    @Resource
    private OrderRepository orderRepository;
    @Resource
    private AgentService agentService;

    /**
     * @return
     */
    @ApiOperation("管理员直接创建代理商账号")
    @PostMapping("/agent/create")
    public Result agentApply(@Valid Agent agent) {
        try {
            agent.setStatus(2);
            agent.setCtime(new Date());
            genAccount(agent);
            agent.setStatus(1);
            agentRepository.save(agent);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult(agent);
    }

    /**
     * @param keywords
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @ApiOperation("代理商列表")
    @PostMapping("/agent/list")
    public Result agentList(@RequestParam(name = "keywords", required = false) String keywords, @RequestParam(name = "status", defaultValue = "1") Integer status,
                            @RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "20") Integer pageSize) {

        PageRequest request = PageRequest.of(pageIndex - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Agent agent = new Agent();
        if (!StringUtils.isEmpty(keywords)) {
            agent.setName(keywords);
            agent.setAccount(keywords);
        }
        agent.setStatus(status);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("account", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());

        Page<Agent> all = agentRepository.findAll(Example.of(agent, matcher), request);
        return ResultGenerator.genSuccessResult(all);
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @ApiOperation("管理员审核")
    @PostMapping("/agent/update")
    public Result updateAgent(@RequestParam(name = "id") Integer id, @RequestParam(name = "status") Integer status) {
        if (id == null) {
            return ResultGenerator.genEmptyResult("少id啊");
        }

        Optional<Agent> op = agentRepository.findById(id);
        Agent agent = op.get();

        Agent a = new Agent();
        if (1 == status && agent.getAccount() == null && agent.getPassword() == null) {
            genAccount(agent);
        }
        a.setStatus(status);
        UpdateTool.copyNullProperties(agent, a);

        agentRepository.save(a);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 自动根据手机号生成账号密码
     *
     * @param agent
     */
    private void genAccount(Agent agent) {
        agent.setAccount(agent.getPhone());
        agent.setPassword(passwordEncoder.encode(agent.getPhone()));
    }

    @Resource
    AgentPayLogRepository agentPayLogRepository;


    /**
     * 添加记录
     *
     * @param agentId
     * @param action
     * @return
     */
    @PostMapping("/agentPayLog/create")
    public Result createAgentPayLog(@RequestParam("agentId") Integer agentId, @RequestParam("action") Integer action, @RequestParam("money") Double money, @RequestParam("payType") Integer payType, @RequestParam("payChannel") Integer payChannel) {
        if (agentId == null) {
            return ResultGenerator.genEmptyResult("id不能为空");
        }
        AgentPayLog agentPayLog = new AgentPayLog();
        if (action == 1) {
            //充值
            Integer integer = agentRepository.updateById(agentId, money);
            if (integer > 0) {
                System.out.println("充值成功！");
            } else {
                return ResultGenerator.genFailResult("充值失败");
            }
            agentPayLog.setAction("充值");
        } else if (action == 0) {
            //消费——考虑余额够不够
            Integer integer = agentRepository.updateById(agentId, -money);
            if (integer > 0) {
                System.out.println("消费成功！");
            } else {
                return ResultGenerator.genFailResult("消费失败，余额不足");
            }
            agentPayLog.setAction("消费");
        } else {
            return ResultGenerator.genFailResult("状态不对！");
        }
        agentPayLog.setMoney(money);
        agentPayLog.setAgentId(agentId);
        agentPayLog.setCtime(new Date());
        agentPayLog.setPayType(payType);
        agentPayLog.setPayChannel(payChannel);
        AgentPayLog save = agentPayLogRepository.save(agentPayLog);
        return ResultGenerator.genSuccessResult(save);
    }

    /**
     * 修改套餐接口
     *
     * @param product
     * @return
     */
    @PostMapping("/updateProduct")
    public Result updateProduct(Product product) {
        Integer id = product.getId();
        if (id == null) {
            return ResultGenerator.genEmptyResult("id不能为空");
        }
        Optional<Product> byId = productRepository.findById(id);
        product.setAgentGrade(product.getAgentGrade() == null ? byId.get().getAgentGrade() : product.getAgentGrade());
        product.setAgentPrice(product.getAgentPrice() == null ? byId.get().getAgentPrice() : product.getAgentPrice());
        product.setProName(product.getProName().isEmpty() ? byId.get().getProName() : product.getProName());
        productRepository.saveAndFlush(product);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/findProductList")
    public Result findProductList() {
        List<Product> all = productRepository.findAll();
        return ResultGenerator.genSuccessResult(all);
    }

    /**
     * 管理员去确认订单
     *
     * @return
     */
    @PostMapping("/confirmOrder")
    public Result confirmOrder(@RequestParam("orderId") Integer orderId) {
        if (orderId == null) {
            return ResultGenerator.genEmptyResult("id不能为空");
        }
        Optional<YdhOrder> byId = orderRepository.findById(orderId);
        YdhOrder ydhOrder = byId.get();
        //先去扣除费用
        Integer integer = agentService.deductionFee(ydhOrder.getAgentId(), ydhOrder.getOrderRealPrice());
        if (integer > 0) {
            ydhOrder.setState(2);
            ydhOrder.setConfirmTime(new Date());
            orderRepository.saveAndFlush(ydhOrder);
            return ResultGenerator.genSuccessResult("确认订单，扣费成功");
        } else {
            return ResultGenerator.genFailResult("确认订单，扣费失败，请充值");
        }
    }

    /**
     * 修改订单
     *
     * @param orderId
     * @param webPage
     * @return
     */
    @PostMapping("/updateOrder")
    public Result updateOrder(@RequestParam("orderId") Integer orderId, @RequestParam("webPage") Integer webPage) {
        if (orderId == null) {
            return ResultGenerator.genEmptyResult("id不能为空");
        }
        Optional<YdhOrder> byId = orderRepository.findById(orderId);
        YdhOrder ydhOrder = byId.get();
        ydhOrder.setWebPage(webPage);
        ydhOrder.setState(3);
        int i = ydhOrder.getProductMakePrice() + ydhOrder.getProductPrice() + webPage * 800;
        ydhOrder.setOrderPrice(i);
        ydhOrder.setOrderRealPrice(i);
        orderRepository.saveAndFlush(ydhOrder);
        return ResultGenerator.genSuccessResult("修改订单成功！");

    }
    @PostMapping("/allOrderList")
    public Result allOrderList(@RequestParam("state") Integer state,@RequestParam("pageNun") Integer pageNun, @RequestParam("pageSize") Integer pageSize) {
        PageHelper.startPage(pageNun, pageSize);
        List<Map> maps = agentService.findorderList(state);
        PageInfo pageInfo = new PageInfo(maps);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

}
