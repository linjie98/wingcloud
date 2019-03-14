package top.wingcloud.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * 利用mybatis查询mysql
 */
@Mapper
public interface ReportMapper {

    /**
     * 查询mysql中的总销售额数据
     * @return
     */
    @Select("select money from shop_money")
    Long selectMoney();


}
