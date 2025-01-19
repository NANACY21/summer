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