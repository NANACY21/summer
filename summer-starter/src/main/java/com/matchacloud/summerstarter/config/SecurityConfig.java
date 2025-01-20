package com.matchacloud.summerstarter.config;
import com.matchacloud.summerstarter.student.domain.entity.User;
import com.matchacloud.summerstarter.student.utils.ConstPool;
import org.apache.commons.lang3.StringUtils;
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

/**
 * 登录相关
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
     * @return 数据库中该用户的信息
     */
    public User getUser(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        if (username.equals(ConstPool.userName)) {
            return new User(username, ConstPool.password, ConstPool.role);
        }
        return null;
    }
}