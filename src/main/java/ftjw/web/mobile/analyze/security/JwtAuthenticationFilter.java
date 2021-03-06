package ftjw.web.mobile.analyze.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import ftjw.web.mobile.analyze.core.Result;
import ftjw.web.mobile.analyze.core.ResultGenerator;
import ftjw.web.mobile.analyze.utill.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /*
    过滤器一定要设置 AuthenticationManager，所以此处我们这么编写，这里的 AuthenticationManager
    我会从 Security 配置的时候传入
    */
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        /*
        运行父类 UsernamePasswordAuthenticationFilter 的构造方法，能够设置此滤器指定
        方法为 POST [\login]
        */
        super();
        setAuthenticationManager(authenticationManager);
        setFilterProcessesUrl("/api/login");

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 从请求的 POST 中拿取 username 和 password 两个字段进行登入
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        // 设置一些客户 IP 啥信息，后面想用的话可以用，虽然没啥用
        setDetails(request, token);
        // 交给 AuthenticationManager 进行鉴权
        return getAuthenticationManager().authenticate(token);
    }

    /*
    鉴权成功进行的操作，我们这里设置返回加密后的 token
    */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        handleResponse(request, response, authResult, null);
    }

    /*
    鉴权失败进行的操作，我们这里就返回 用户名或密码错误 的信息
    */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        handleResponse(request, response, null, failed);
    }

    private void handleResponse(HttpServletRequest request, HttpServletResponse response, Authentication authResult, AuthenticationException failed) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();

        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        if (authResult != null) {
            // 处理登入成功请求
            AgentDetials user = (AgentDetials) authResult.getPrincipal();
            user.getUsername();

            String token = JwtUtil.sign(user.getUsername(), user.getPassword());
            Collection<GrantedAuthority> authorities = user.getAuthorities();
            String roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
            Map map=new HashMap();
            map.put("username",user.getUsername());
            map.put("token","YDH:"+token);
            map.put("role",roles);
            map.put("agentid",user.getAgentid());
            Result responseEntity = ResultGenerator.genSuccessResult(map);
            response.setStatus(HttpStatus.OK.value());
            response.getWriter().write(mapper.writeValueAsString(responseEntity));
        } else {
            // 处理登入失败请求
            Result<String> responseEntity = ResultGenerator.genFailResult("用户名或密码错误");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(mapper.writeValueAsString(responseEntity));
        }
    }
}
