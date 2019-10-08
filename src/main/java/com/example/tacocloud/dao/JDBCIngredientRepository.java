package com.example.tacocloud.dao;

import com.example.tacocloud.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

@Repository
//By annotating JdbcIngredientRepository with
//@Repository , you declare that it should be automatically discovered by Spring compo-
//        nent scanning and instantiated as a bean in the Spring application context.
public class JDBCIngredientRepository implements IngredientRepository{
        private JdbcTemplate jdbc;//必须要自己用的Jdbc，

        @Autowired
        public JDBCIngredientRepository(JdbcTemplate jdbc){
            this.jdbc=jdbc;
        }

    @Override
    //query的入口参数要有sql，rowmapper（负责将行数据转换为对象)
    public Iterable<Ingredient> findAll() {
        return jdbc.query("select id,name,type from Ingredient",this::mapRowToIngredient);
    }

    @Override
    public Ingredient findOne(String id) {
        return jdbc.queryForObject(
                "select id, name,type from Ingredient where id=?",
                this::mapRowToIngredient,id);

    }

//    Spring’s RowMapper for the purpose of mapping each row in the result set to
//    an object.
    private Ingredient mapRowToIngredient(ResultSet rs,int rowNum)
        throws SQLException{
            return new Ingredient(
                    rs.getString("id"),
                    rs.getString("name"),
                    Ingredient.Type.valueOf(rs.getString("type")));

    }
    @Override
    //Because it isn’t necessary to map ResultSet data to an object, the update() method is
    //much simpler than query() or queryForObject()
    public Ingredient save(Ingredient ingredient) {
       jdbc.update("insert into Ingredient (id, name, type) values (?, ?, ?)",
              //以下为3个？提供内容
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString());
        return  ingredient;
    }
}
