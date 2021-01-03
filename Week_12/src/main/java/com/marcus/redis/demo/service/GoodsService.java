package com.marcus.redis.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * @author: MarcusJiang1306
 */

@Service
public class GoodsService {

    @Autowired
    private Jedis jedis;


    /**
     * 很粗暴的假设传进来的一定是商品id
     * @param goodsID
     * @return
     */
    public boolean decrGoods(String goodsID) {
        Long count = jedis.decr(goodsID);
        System.out.println(count);
        return count >= 0;
    }
}
