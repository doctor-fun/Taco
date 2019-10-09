package com.example.tacocloud.dao;

import com.example.tacocloud.dao.Interface.TacoRepository;
import com.example.tacocloud.model.Ingredient;
import com.example.tacocloud.model.Taco;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements TacoRepository {
    private JdbcTemplate jdbc;
    public JdbcTacoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);//saveTacoInfo会随机生成tacoID，将id返回后
        taco.setId(tacoId);//保存到model中taco中
        for (Ingredient ingredient : taco.getIngredients()) {
            saveIngredientToTaco(ingredient, tacoId);//将
        }
        return taco;
    }
    private long saveTacoInfo(Taco taco) {
        taco.setCreatedAt(new Date());
        PreparedStatementCreator psc =
                new PreparedStatementCreatorFactory(//用于创建能够执行sql的preparedStatement对象，将sql传给sql
                        "insert into Taco (name, createdAt) values (?, ?)",//Taco这张表的name,createdAt的这两列，

                        Types.VARCHAR, Types.TIMESTAMP//类型是这两个
                ).newPreparedStatementCreator(
                        Arrays.asList(//将taco的name和时间戳以数组的形式加进到preparedStatementCreatorFactory
                                taco.getName(),
                                new Timestamp(taco.getCreatedAt().getTime())));//时间戳是以秒的形式存进去的
        KeyHolder keyHolder = new GeneratedKeyHolder();//生成id
        jdbc.update(psc, keyHolder);
        return keyHolder.getKey().longValue();//返回自增主键的值
    }
    private void saveIngredientToTaco(
            Ingredient ingredient, long tacoId) {
        jdbc.update(
                "insert into Taco_Ingredients (taco, ingredient) " +
                        "values (?, ?)",
                tacoId, ingredient.getId());
    }
}