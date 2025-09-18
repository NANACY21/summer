package com.matchacloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.matchacloud.entity.Athlete;
import com.matchacloud.SportCenterApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 运动员服务测试类
 * 测试查询运动员表数据的各种场景
 */
@SpringBootTest(classes = SportCenterApplication.class) // 指定启动类
class AthleteServiceTest {

    @Autowired
    private AthleteService athleteService;

    /**
     * 测试根据ID查询运动员
     */
    @Test
    void testGetById() {
        Long testId = 1L; // 假设数据库中存在ID为1的记录
        Athlete athlete = athleteService.getById(testId);

        // 断言查询结果不为空
        assertNotNull(athlete, "查询ID为" + testId + "的运动员不存在");
        System.out.println("查询到的运动员信息：" + athlete);
    }

    /**
     * 测试查询所有运动员
     */
    @Test
    void testList() {
        List<Athlete> athleteList = athleteService.list();

        // 断言列表不为空（如果数据库有数据）
        assertFalse(athleteList.isEmpty(), "运动员表数据为空");
        System.out.println("运动员总数：" + athleteList.size());
        // 打印前3条数据
        athleteList.stream().limit(3).forEach(System.out::println);
    }

    /**
     * 测试分页查询运动员
     */
    @Test
    void testSelectByPage() {
        int pageNum = 1; // 第一页
        int pageSize = 5; // 每页5条
        Map<String, Object> params = new HashMap<>();
        // 可以添加查询条件，例如查询国籍为"中国"的运动员
        // params.put("nationality", "中国");

        IPage<Athlete> page = athleteService.selectByPage(pageNum, pageSize, params);

        // 断言分页结果不为空
        assertNotNull(page, "分页查询失败");
        System.out.println("总页数：" + page.getPages());
        System.out.println("总记录数：" + page.getTotal());
        System.out.println("当前页数据：");
        page.getRecords().forEach(System.out::println);
    }

    /**
     * 测试根据项目查询运动员
     */
    @Test
    void testFindByEvent() {
        String event = "篮球"; // 测试项目
        Athlete athlete = athleteService.findByEvent(event);

        if (athlete != null) {
            System.out.println("查询到" + event + "项目的运动员：" + athlete);
        } else {
            System.out.println("未查询到" + event + "项目的运动员");
        }
    }

    /**
     * 测试条件查询（例如查询现役运动员）
     */
    @Test
    void testQueryByStatus() {
        int status = 1; // 1:现役
        Map<String, Object> params = new HashMap<>();
        params.put("status", status);

        IPage<Athlete> page = athleteService.selectByPage(1, 10, params);

        System.out.println("现役运动员总数：" + page.getTotal());
        page.getRecords().forEach(athlete ->
                System.out.println(athlete.getName() + " - " + athlete.getEvent())
        );
    }
}
