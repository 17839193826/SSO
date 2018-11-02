package sso.mapper;

import core.vo.UserStatus;
import org.apache.ibatis.annotations.*;
import sso.domain.User;

import java.util.List;

public interface UserMapper {

    //查询用户列表
    @Select("select id,username,password,phone from t_user")
    @ResultType(User.class)
    List<User> queryPage(@Param("index") int index, @Param("count") int count);

    //登录查询
    @Select("select id,username,password,phone from t_user where username=#{username} or phone=#{username}")
    @ResultType(User.class)
    User queryBy(String username);

    //查询登陆状态
    @Select("select * from userstatus where token=#{token}")
    @ResultType(UserStatus.class)
    UserStatus queryStatusByToken(String token);

    //查询登陆状态 token
    @Select("select * from userstatus where uid=#{uid}")
    @ResultType(UserStatus.class)
    UserStatus queryStatusByUid(int uid);
    //查询token uid
    @Select("select * from userstatus where uid=#{uid}")
    @ResultType(UserStatus.class)
    UserStatus queryStatusTkByUid(int uid);
    //查询token uid
    @Select("select * from userstatus where uid=#{uid}")
    @ResultType(UserStatus.class)
    List<UserStatus> queryStatusListByUid(int uid);
    //添加登陆状态
    @Insert("insert into userstatus(id,islogin,token,uid) values(null,#{islogin},#{token},#{uid})")
    int addStatus(UserStatus userStatus);

    //更新登陆状态 token
    @Update("update userstatus set islogin=#{islogin} where token=#{token}")
    int updateStatus(@Param("islogin") int islogin, @Param("token") String token);

    //更新登陆状态
    @Update("update userstatus set islogin=#{islogin},token=#{token} where uid=#{uid}")
    int updateAllStatus(UserStatus u);


    //更新登陆状态 id
    @Update("update userstatus set islogin=#{islogin} where uid=#{uid}")
    void updateStatusById(@Param("islogin") int islogin,@Param("uid") int uid);
}
