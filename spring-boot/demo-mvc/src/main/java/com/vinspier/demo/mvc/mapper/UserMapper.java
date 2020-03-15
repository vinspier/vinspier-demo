package com.vinspier.demo.mvc.mapper;

import com.vinspier.demo.mvc.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends tk.mybatis.mapper.common.Mapper<User>{

}
