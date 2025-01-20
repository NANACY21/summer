package com.matchacloud.summerstarter.student.controller;
import com.matchacloud.summerstarter.config.SecurityConfig;
import com.matchacloud.summerstarter.student.domain.entity.User;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT(JSON Web Token)登录流程：
 * 一个登录信息 - 一个token
 * 1.输入账号、密码给后端，如果比对成功后端生成token返给前端，同时返前端用户信息
 * 2.之后每次请求 传token即可 后端验证通过继续请求 (过滤器)
 *
 * token组成：
 * 1.头部信息：有JWT的类型和所使用的签名算法
 * 2.负载：负载里有用户信息
 * 3.签名部分：使用一个密钥（Secret Key）对头部和负载进行签名
 *     这主要是确保token头部和负载的数据完整性：对头部和负载进行签名是表明此刻头部和负载和数据是怎样的
 *     密钥是服务器的(某微服务的标识)一个加密字符串 一个微服务 - 一个密钥
 *
 * 验证token步骤：(使用与生成 JWT 时相同的密钥和签名算法对 JWT 进行验证)
 * 1.签名验证：提取token的头部和负载重新计算签名 并和token签名部分比对 一样则证明数据传输过程数据未被篡改
 * 2.过期验证：token签名部分有过期时间 以此判断是否token过期
 * 3.受众验证：token负载部分有一个受众信息 验证是否是预期的受众
 * 4.token验证都通过后 解析出用户信息 检查该用户是否有权限访问该资源
 */
@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityConfig securityConfig;

    /**
     * 登录
     *
     * 输入账号密码->如果登录成功 则后端生成该账号的token返给前端，前端保存该token
     * @param loginUser 登录用户
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User loginUser) {

        //前端用户名
        String username = loginUser.getUsername();
        //前端密码
        String password = loginUser.getPassword();

        //数据库里的用户信息
        User user = securityConfig.getUser(username);

        //用户存在且密码正确
        if (user!= null && user.checkPassword(loginUser.getPassword())) {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(username, password)
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String token = securityConfig.createToken(username, user.getRole(),"your_secret_key");
                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (AuthenticationException e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 请求其他接口时 请求头携带token 后端验证token
     *
     * 后续请求中token作为请求头传给后端
     * @param request
     * @return
     */
    @PostMapping("/protected")
    public ResponseEntity<String> protectedResource(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader!= null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            try {
                // 解析 JWT 获取用户信息
                User user = getUserFromToken(token, "your_secret_key");
                if (user!= null) {
                    return new ResponseEntity<>("This is a protected resource. User: " + user.getUsername(), HttpStatus.OK);
                }
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    private User getUserFromToken(String token, String secretKey) {
        try {
            // 解析 JWT 并提取用户信息
            io.jsonwebtoken.Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
            String username = claims.getSubject();
            String role = (String) claims.get("role");
            return new User(username,"",role);
        } catch (Exception e) {
            return null;
        }
    }
}