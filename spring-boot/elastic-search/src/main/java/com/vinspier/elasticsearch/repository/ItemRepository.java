package com.vinspier.elasticsearch.repository;

import com.vinspier.elasticsearch.pojo.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @ClassName: ItemRepository
 * @Description: 自定义操作方法接口 继承提供基础支持的Repository
 * @Author:
 * @Date: 2020/4/8 10:29
 * @Version V1.0
 **/
public interface ItemRepository extends ElasticsearchRepository<Item,Long>{

    /**
     * 根据价格区间查询
     * @param price1
     * @param price2
     * @return
     */
    List<Item> findByPriceBetween(double price1, double price2);

}
