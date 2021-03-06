package ftjw.web.mobile.analyze.security;

import ftjw.web.mobile.analyze.dao.AgentRepository;
import ftjw.web.mobile.analyze.entity.Agent;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 殷晓龙
 * 2020/3/25 17:36
 */
@Component("userDetailService")
public class AgentUserDetailService implements UserDetailsService {

    @Resource
    AgentRepository agentRepository;
    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        Agent agent = agentRepository.findOneByAccount(loginName);
        if(agent==null){
            throw new UsernameNotFoundException("User " + loginName + " was not found in db");
        }
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String role:agent.getRole().split(",")){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+role));
        }
        AgentDetials agentDetials = new AgentDetials(loginName, agent.getPassword(), grantedAuthorities);
        agentDetials.setAgentid(agent.getId());
        agentDetials.setDesc(agent.getName());
        return agentDetials;
    }


}

