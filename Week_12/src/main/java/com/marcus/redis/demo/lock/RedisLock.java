package com.marcus.redis.demo.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

/**
 * @author: MarcusJiang1306
 */

@Component
public class RedisLock {

    @Autowired
    private Jedis jedis;

    public boolean tryLock(String lock, int secondsToExpire) {
        SetParams setParams = new SetParams();
        setParams.ex(secondsToExpire);
        setParams.nx();
        String response = jedis.set(lock, lock, setParams);
        System.out.println(response);
        return "ok".equalsIgnoreCase(response);
    }

    public void unLock(String lock) {
        jedis.del(lock);
    }


}
