package ftjw.web.mobile.analyze.controller;

import ftjw.web.mobile.analyze.core.Result;
import ftjw.web.mobile.analyze.core.ResultGenerator;
import ftjw.web.mobile.analyze.utill.UpdateTool;
import ftjw.web.mobile.analyze.dao.AgentPayLogRepository;
import ftjw.web.mobile.analyze.dao.AgentRepository;
import ftjw.web.mobile.analyze.entity.Agent;
import ftjw.web.mobile.analyze.entity.AgentPayLog;
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
import java.util.Optional;

/**
 * 管理员相关的api
 * 殷晓龙
 * 2020/4/3 11:26
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AgentRepository agentRepository;

    /**
     * 管理员直接创建代理商账号
     * @return
     */
    @PostMapping("/agent/create")
    public Result agentApply(@Valid Agent agent){
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
     * 代理商列表
     * @param keywords
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @PostMapping("/agent/list")
    public Result agentList(@RequestParam(name = "keywords",required = false) String keywords, @RequestParam(name = "status",defaultValue = "1") Integer status,
                            @RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam (defaultValue = "20") Integer pageSize){

        PageRequest request= PageRequest.of(pageIndex-1,pageSize, Sort.by(Sort.Direction.DESC,"id"));
        Agent agent=new Agent();
        if(!StringUtils.isEmpty(keywords)){
            agent.setName(keywords);
            agent.setAccount(keywords);
        }
        agent.setStatus(status);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("account", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());

        Page<Agent> all = agentRepository.findAll(Example.of(agent,matcher), request);
        return ResultGenerator.genSuccessResult(all);
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/agent/update")
    public Result updateAgent(@RequestParam(name = "id") Integer id,@RequestParam(name = "status") Integer status){
        if(id==null){
            return ResultGenerator.genEmptyResult("少id啊");
        }

        Optional<Agent> op = agentRepository.findById(id);
        Agent  agent = op.get();

        Agent a=new Agent();
        if(1==status&&agent.getAccount()==null&&agent.getPassword()==null){
            genAccount(agent);
        }
        a.setStatus(status);
        UpdateTool.copyNullProperties(agent,a);

        agentRepository.save(a);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 自动根据手机号生成账号密码
     * @param agent
     */
    private void genAccount(Agent agent) {
        agent.setAccount(agent.getPhone());
        agent.setPassword(passwordEncoder.encode(agent.getPhone()));
    }

    @Resource
    AgentPayLogRepository agentPayLogRepository;

    @PostMapping("/pay/log/list")
    public Result payLog(@RequestParam(name = "id",required = false)Integer id,@RequestParam(name = "action")String action
    ,@RequestParam(defaultValue = "1") Integer pageIndex, @RequestParam (defaultValue = "20") Integer pageSize){
        AgentPayLog apl=new AgentPayLog();
        apl.setAgentId(id);
        apl.setAction(action);
        PageRequest request= PageRequest.of(pageIndex-1,pageSize, Sort.by(Sort.Direction.DESC,"id"));
        Page<AgentPayLog> page = agentPayLogRepository.findAll(Example.of(apl), request);
        return  ResultGenerator.genSuccessResult(page);
    }
}
