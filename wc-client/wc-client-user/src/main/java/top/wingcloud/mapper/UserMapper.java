package top.wingcloud.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.wingcloud.domain.User;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 登录验证查询
     * @param user_name
     * @param user_password
     * @return
     */
    @Select("select user_id,user_name,user_password from user where user_name = #{user_name} and user_password = #{user_password}")
    List<User> selectUser(@Param("user_name") String user_name, @Param("user_password") String user_password);

}