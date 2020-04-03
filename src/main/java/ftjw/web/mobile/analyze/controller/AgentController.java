package ftjw.web.mobile.analyze.controller;

import ftjw.web.mobile.analyze.core.Result;
import ftjw.web.mobile.analyze.core.ResultGenerator;
import ftjw.web.mobile.analyze.core.UpdateTool;
import ftjw.web.mobile.analyze.dao.*;
import ftjw.web.mobile.analyze.entity.*;
import ftjw.web.mobile.analyze.entity.User;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 代理商相关的api
 * 殷晓龙
 * 2020/4/3 11:30
 */
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
    UserPayLogRepository userPayLogRepository;

    /**
     * 代理商申请
     * @return
     */
    @PostMapping("/apply")
    public Result agentApply(Agent agent){
        try {
            agent.setCtime(new Date());
            agentRepository.save(agent);
        } catch (Exception e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult(agent);
    }

    /**
     * 代理商详情
     * @param id
     * @return
     */
    @PostMapping("/one")
    public Result findOne(Integer id){
        Optional<Agent> op = agentRepository.findById(id);
        return ResultGenerator.genSuccessResult(op.get());
    }


    /**
     * 代理商信息编辑
     * @param agent
     * @return
     */
    @PostMapping("/update")
    public Result updateAgent(Agent agent){
        if(agent.getId()==null){
            return ResultGenerator.genEmptyResult("少id啊");
        }
        agent.setStatus(null);
        Optional<Agent> op = agentRepository.findById(agent.getId());
        UpdateTool.copyNullProperties(op.get(),agent);
        agentRepository.save(agent);
        return ResultGenerator.genSuccessResult();
    }



    /**
     * 代理商客户列表
     * @param id
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @PostMapping("/user/list")
    public Result agentUsers(@RequestParam(name = "id") Integer id,
                             @RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam (defaultValue = "20") Integer pageSize){
        if(id==null){
            return ResultGenerator.genEmptyResult("少id啊");
        }
        PageRequest request= PageRequest.of(pageIndex-1,pageSize, Sort.by("userId"));
        AgentUser agent=new AgentUser();
        agent.setAgentId(id);
        agent.setStatus(1);
        Page<AgentUser> all = agentUserRepository.findAll(Example.of(agent), request);
       List<Integer> ids= all.toList().stream().map(AgentUser::getUserId).collect(Collectors.toList());
        List<User> userList = userRepository.findAllById(ids);
        return ResultGenerator.genSuccessResult(userList);
    }

    /**
     * 商户详情
     * @param id 商户id
     * @return
     */
    @PostMapping("/user/one")
    public Result angentUser(@RequestParam(name = "id") Integer id){
        Optional<User> op = userRepository.findById(id);
        return  ResultGenerator.genSuccessResult(op.get());
    }

    /**
     * 用户创建的站点集合
     * @return
     */
    @PostMapping("/user/sites")
    public Result userSites(@RequestParam(name = "id") Integer id,@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam (defaultValue = "10") Integer pageSize){
        Site site=new Site();
        site.setUid(id);
        PageRequest request= PageRequest.of(pageIndex-1,pageSize);
        Page<Site> sitePage = siteRepository.findAll(Example.of(site), request);
        return  ResultGenerator.genSuccessResult(sitePage);
    }

    /**
     * 客户消费记录
     * @param id
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @PostMapping("/pay/log/list")
    public Result payLog(@RequestParam(name = "id",required = false)Integer id
            ,@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam (defaultValue = "20") Integer pageSize){
        UserPayLog upl=new UserPayLog();
        upl.setUserId(id);
        PageRequest request= PageRequest.of(pageIndex-1,pageSize, Sort.by("id"));
        Page<UserPayLog> page = userPayLogRepository.findAll(Example.of(upl), request);
        return  ResultGenerator.genSuccessResult(page);
    }
}
