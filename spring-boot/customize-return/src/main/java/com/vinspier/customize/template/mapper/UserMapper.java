package com.vinspier.customize.template.mapper;

import com.vinspier.customize.template.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * @ClassName: UserMapper
 * @Description:
 * @Author:
 * @Date: 2020/3/19 11:51
 * @Version V1.0
 **/

public interface UserMapper extends Mapper<User>{
    @Select("select * from tb_user where id = ${id}")
    User getById(@Param("id") Long id);

    User getByUsername(String username);

}
