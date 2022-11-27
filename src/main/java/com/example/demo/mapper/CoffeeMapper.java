package com.example.demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.model.Coffee;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CoffeeMapper extends BaseMapper<Coffee> {
    @Insert({
            "insert into t_coffee (ID,NAME, PRICE, ",
            "CREATE_TIME, UPDATE_TIME)",
            "values (#{id,jdbcType=BIGINT},#{name,jdbcType=VARCHAR}, #{price,jdbcType=BIGINT,typeHandler=com.example.demo.handler.MoneyTypeHandler}, ",
            "#{createTime,jdbcType=DATE}, #{updateTime,jdbcType=DATE})"
    })
    @Options(useGeneratedKeys = true)
    int save(Coffee coffee);

    @Update({
            "update t_coffee t set t.name=#{name,jdbcType=VARCHAR}," +
                    "t.price=#{price,jdbcType=BIGINT,typeHandler=com.example.demo.handler.MoneyTypeHandler}," +
                    "t.update_time=#{updateTime,jdbcType=DATE} where id=#{id}"
    })
    @Options(useGeneratedKeys = true)
    int update(Coffee coffee);

    @Select("select * from t_coffee where id = #{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "create_time", property = "createTime"),
            // map-underscore-to-camel-case = true 可以实现一样的效果
            // @Result(column = "update_time", property = "updateTime"),
    })
    Coffee findById(@Param("id") Long id);




    @Select("delete from t_coffee  ")
    void deleteAll( );


    List<Coffee> selectPage(IPage<Coffee> page );
}
