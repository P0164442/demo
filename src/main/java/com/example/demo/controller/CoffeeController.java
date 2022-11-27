package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.mapper.CoffeeMapper;
import com.example.demo.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.apache.ibatis.type.JdbcType;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.JDBCType;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/coffee")
@Slf4j
public class CoffeeController {
    private static final String CACHE = "springbucks-coffee";
    @Autowired
    private CoffeeMapper coffeeMapper;
    @Autowired
    private RedisTemplate<String, Coffee> redisTemplate;

    @GetMapping(value = "test.do")
    public void test() {
        //方便测试，省的每次都要去数据库删除
        coffeeMapper.deleteAll();

        //初始化一个coffee
        Coffee c = Coffee.builder().name("espresso").id(1l)
                .price(Money.of(CurrencyUnit.of("CNY"), 20.0)).build();

        //插入数据库
        this.add(c);

        //从数据库读取
        Coffee coffee = this.findById(c.getId());

        //更新数据库
        c.setPrice(Money.of(CurrencyUnit.of("CNY"), 30.0));
        c.setUpdateTime(new Date());
        this.update(c);

        //分页
        Page page = new Page().setCurrent(1).setSize(10);
        List<Coffee> coffeeList = this.selectPage(page);
        log.info("Page Coffee: {} {} {}", page.getCurrent(), page.getSize(), coffeeList);

    }

    @Transactional
    public int add(Coffee c) {
        int count = coffeeMapper.save(c);
        log.info("Save {} Coffee: {}", count, c);

        return count;
    }

    @Transactional
    public int update(Coffee c) {
        int count = coffeeMapper.update(c);
        log.info("update Coffee: {}", c);
        return count;
    }

    @Transactional
    public int delete(Long id) {
        int count = coffeeMapper.deleteById(id);
        log.info("delete Coffee: {}", id);
        return count;
    }

    public Coffee findById(Long id) {
        //redis
        HashOperations<String, String, Coffee> hashOperations = redisTemplate.opsForHash();
        if (redisTemplate.hasKey(CACHE) && hashOperations.hasKey(CACHE, "coffee")) {
            log.info("Get coffee {} from Redis.", "coffee");
            return hashOperations.get(CACHE, "coffee");
        }
        Coffee coffee = coffeeMapper.findById(id);
        hashOperations.put(CACHE, "coffee", coffee);
        redisTemplate.expire(CACHE, 1, TimeUnit.MINUTES);
        log.info("findById Coffee: {}", coffee);
        return coffee;
    }

    public List<Coffee> selectPage(IPage<Coffee> page) {
        List<Coffee> coffeeList = coffeeMapper.selectPage(page);
        log.info("Page   Coffee: {} {}", page.getCurrent(), page.getSize());
        return coffeeList;
    }

}
