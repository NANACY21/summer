package com.matchacloud.summerstarter.config;
import com.matchacloud.summerstarter.student.domain.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private Map<String, User> userMap = new HashMap<>();

    public SecurityConfig() {
        // 模拟用户存储，实际应用中可使用数据库存储用户信息
        userMap.put("user1", new User("user1", new BCryptPasswordEncoder().encode("password1")));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated();
    }

    public static String createToken(String username, String role,String secretKey) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) // 30 分钟有效期
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * 查询数据库
     * @param username
     * @return
     */
    public User getUser(String username) {
        return userMap.get(username);
    }
}