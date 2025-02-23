package com.matchacloud.summerstarter.student.domain.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String username;

    private String password;

    /**
     * 角色
     */
    private String role;

    /**
     *
     * @param rawPassword 前端输入的密码
     * @return
     */
    public boolean checkPassword(String rawPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(rawPassword, password);
    }
}