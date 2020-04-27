package ftjw.web.mobile.analyze.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageInfo;
import ftjw.web.mobile.analyze.core.Result;
import ftjw.web.mobile.analyze.core.ResultGenerator;
import ftjw.web.mobile.analyze.dao.*;
import ftjw.web.mobile.analyze.entity.User;
import ftjw.web.mobile.analyze.entity.*;
import ftjw.web.mobile.analyze.security.AgentDetials;
import ftjw.web.mobile.analyze.service.AgentService;
import ftjw.web.mobile.analyze.utill.UpdateTool;
import ftjw.web.mobile.analyze.utill.YDZWUtill;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.stream.Collectors;

/**
 * 代理商相关的api
 * 殷晓龙
 * 2020/4/3 11:30
 */
@Api(tags = "代理商控制器", produces = "代理商相关功能")
@RestController
@RequestMapping("/agent")
public class AgentController {
    @Resource
    private AgentRepository agentRepository;

    @Resource
    private AgentUserRepository agentUserRepository;
    @Resource
    UserRepository userRepository;

    @Resource
    SiteRepository siteRepository;

    @Resource
    private AgentService agentService;

    @Resource
    AgentPayLogRepository agentPayLogRepository;
    @Resource
    OrderRepository orderRepository;


    @ApiOperation("代理商申请")
    @PostMapping("/apply")
    public Result agentApply(Agent agent) {
        try {
            agent.setStatus(2);
            agent.setCtime(new Date());
            agentRepository.save(agent);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult(agent);
    }

    /**
     * @return
     */
    @ApiOperation("代理商详情")
    @PostMapping("/one")
    public Result findOne(String username) {
        Agent one = agentRepository.findOneByAccount(username);
        return ResultGenerator.genSuccessResult(one);
    }


    /**
     * @param agent
     * @return
     */
    @ApiOperation("代理商信息编辑")
    @PostMapping("/update")
    public Result updateAgent(Agent agent) {
        if (agent.getId() == null) {
            return ResultGenerator.genEmptyResult("少id啊");
        }
        agent.setStatus(null);
        Optional<Agent> op = agentRepository.findById(agent.getId());
        UpdateTool.copyNullProperties(op.get(), agent);
        agentRepository.save(agent);
        return ResultGenerator.genSuccessResult();
    }


    /**
     * @param id
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @ApiOperation("代理商客户列表")
    @PostMapping("/user/list")
    public Result agentUsers(@RequestParam(name = "id") Integer id,
                             @RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "20") Integer pageSize) {
        if (id == null) {
            return ResultGenerator.genEmptyResult("少id啊");
        }
        PageRequest request = PageRequest.of(pageIndex - 1, pageSize, Sort.by(Sort.Direction.DESC, "userId"));
        AgentUser agent = new AgentUser();
        agent.setAgentId(id);
        agent.setStatus(1);
        Page<AgentUser> all = agentUserRepository.findAll(Example.of(agent), request);
        List<Integer> ids = all.toList().stream().map(AgentUser::getUserId).collect(Collectors.toList());
        List<User> userList = userRepository.findAllById(ids);
        PageImpl pi = new PageImpl(userList, request, all.getTotalElements());

        return ResultGenerator.genSuccessResult(pi);
    }

    /**
     * @param id 商户id
     * @return
     */
    @ApiOperation("商户详情")
    @PostMapping("/user/one")
    public Result angentUser(@RequestParam(name = "id") Integer id) {
        Optional<User> op = userRepository.findById(id);
        return ResultGenerator.genSuccessResult(op.get());
    }

    /**
     * @return
     */
    @ApiOperation("用户创建的站点集合")
    @PostMapping("/user/sites")
    public Result userSites(@RequestParam(name = "id") Integer id, @RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "10") Integer pageSize) {
        Site site = new Site();
        site.setUid(id);
        PageRequest request = PageRequest.of(pageIndex - 1, pageSize);
        Page<Site> sitePage = siteRepository.findAll(Example.of(site), request);
        return ResultGenerator.genSuccessResult(sitePage);
    }

//    /**
//     * @param id
//     * @param pageIndex
//     * @param pageSize
//     * @return
//     */
//    @ApiOperation("客户消费记录")
//    @PostMapping("/pay/log/list")
//    public Result payLog(@RequestParam(name = "id",required = false)Integer id,@RequestParam(name = "action",required = false)String action
//            ,@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam (defaultValue = "20") Integer pageSize){
//        UserPayLog upl=new UserPayLog();
//        upl.setUserId(id);
//        if(StringUtil.isNotEmpty(action)){
//            upl.setAction(action);
//        }
//        PageRequest request= PageRequest.of(pageIndex-1,pageSize, Sort.by(Sort.Direction.DESC,"id"));
//        Page<UserPayLog> page = userPayLogRepository.findAll(Example.of(upl), request);
//        return  ResultGenerator.genSuccessResult(page);
//    }

    @Autowired
    PasswordEncoder passwordEncoder;


    @ApiOperation("代理商资金流水记录")
    @PostMapping("/pay/log/list")
    public Result payLog(@RequestParam(name = "id", required = false) Integer id, @RequestParam(name = "action", required = false) String action
            , @RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam(defaultValue = "20") Integer pageSize) {
        AgentPayLog apl = new AgentPayLog();
        apl.setAgentId(id);
        if (!StringUtils.isEmpty(action)) {
            apl.setAction(action);
        }
        PageRequest request = PageRequest.of(pageIndex - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<AgentPayLog> page = agentPayLogRepository.findAll(Example.of(apl), request);
        return ResultGenerator.genSuccessResult(page);
    }


    /**
     * @param account
     * @param oldpwd
     * @param newpwd
     * @return
     */
    @ApiOperation("代理商修改密码")
    @ApiParam(name = "account", value = "账户id")
    @PostMapping("/change/password")
    public Result changePassword(@ApiParam(name = "account", value = "代理商账户") String account, @ApiParam(name = "oldpwd", value = "旧密码") String oldpwd, @ApiParam(name = "newpwd", value = "新密码") String newpwd) {
        Agent agent = agentRepository.findOneByAccount(account);
        boolean matches = passwordEncoder.matches(oldpwd, agent.getPassword());
        if (matches) {
            agent.setPassword(passwordEncoder.encode(newpwd));
            agentRepository.save(agent);
        } else {
            return ResultGenerator.genFailResult("原始账号/密码不正确。");
        }
        return ResultGenerator.genSuccessResult("修改成功");
    }

    @ApiOperation("代理商创建用户账号")
    @PostMapping("/user/create")
    public Result createUser(@Valid User user) {
        Map<String, Object> map = BeanUtil.beanToMap(user, false, true);
        String josn = YDZWUtill.createUser(map);
        JSONObject result = JSONUtil.parseObj(josn);

        AgentDetials agentDetials = getCurrentUserDetials();
        Integer serviceid = Integer.valueOf((String) map.get("serviceid"));
        Integer webPage = (Integer) map.get("webPage");
        Integer serveYear = (Integer) map.get("serveYear");
        String website = (String) map.get("website");
        String login = (String) map.get("login");
        String orgname = (String) map.get("orgname");
        if (result.getInt("code") == 0) {
            agentService.adduser(result, agentDetials, serviceid, webPage, serveYear,website,login,orgname);

            return ResultGenerator.genSuccessResult("用户创建成功.");
        } else {
            return ResultGenerator.genFailResult(result.getStr("message"));
        }

    }


    /**
     * 获取当前登录用户
     */
    private AgentDetials getCurrentUserDetials() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (AgentDetials) authentication.getPrincipal();
    }

    /**
     * 查找代理等级的产品列表
     *
     * @param agentId
     * @return
     */
    @ApiOperation("查找代理等级的产品列表")
    @PostMapping("/productList")
    public Result productList(@RequestParam("agentId") Integer agentId) {
        List productList = agentService.findProductList(agentId);
        return ResultGenerator.genSuccessResult(productList);
    }

    /**
     * 概况
     *
     * @param agentId
     * @return
     */
    @PostMapping("/survey")
    public Result survey(@RequestParam("agentId") Integer agentId) {
        Map allData = agentService.findAllData(agentId);
        return ResultGenerator.genSuccessResult(allData);
    }

    /**
     * 代理商账户信息
     *
     * @param agentId
     * @return
     */
    @PostMapping("/agentAccount")
    public Result agentAccount(@RequestParam("agentId") Integer agentId) {
        Map map = agentService.findAccountInfor(agentId);
        return ResultGenerator.genSuccessResult(map);
    }

    /**
     * 订单列表
     *
     * @param agentId
     * @param state
     * @return
     */
    @PostMapping("/orderList")
    public Result orderList(@RequestParam("agentId") Integer agentId, @RequestParam("state") Integer state,@RequestParam(defaultValue = "1") Integer pageNun, @RequestParam(defaultValue = "20") Integer pageSize) {
        PageInfo pageInfo = agentService.findorderList(agentId, state, pageNun, pageSize);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 订单详情
     *
     * @param orderId
     * @return
     */
    @PostMapping("/orderDetail")
    public Result orderList(@RequestParam("orderId") Integer orderId) {
        YdhOrder ydhOrder = agentService.findorderDetail(orderId);
        return ResultGenerator.genSuccessResult(ydhOrder);
    }

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
            return ResultGenerator.genFailResult("确认订单，扣费失败,请充值");
        }
    }
}
