package com.marcus.redis.demo.service;

import com.marcus.redis.demo.lock.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author: MarcusJiang1306
 */

@Service
public class DemoService {

    @Autowired
    private RedisLock redisLock;


    public String doSomething() {
        String result;
        String lockKey = "lock1";
        boolean lock = redisLock.tryLock(lockKey, 10);
        if (lock) {
            try {
                // do something
                TimeUnit.SECONDS.sleep(5);
                result = "ok";
            } catch (InterruptedException e) {
                e.printStackTrace();
                return "failed";
            } finally {
                redisLock.unLock(lockKey);
            }
        } else {
            result = "failed";
        }
        return result;
    }

}
