package org.linlinjava.litemall.db.dao;

import org.linlinjava.litemall.db.domain.LitemallGoodsProduct;
import org.linlinjava.litemall.db.mapper.GoodsProductMapper;
import org.linlinjava.litemall.db.mapper.LitemallGoodsProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * @program: litemall
 * @description:
 * @author: 2-3
 * @create: 2020-03-30 13:46
 **/
@Repository
public class LitemallGoodsProductDAO {
    @Autowired
    private LitemallGoodsProductMapper litemallGoodsProductMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    public int reduceStock(Integer id,Short num){
        String key=id.toString();
        Integer Value=(Integer)redisTemplate.opsForValue().get(key);
        System.out.println("updateBefore:"+Value);
        if(Value==null){
            Value=litemallGoodsProductMapper.selectByPrimaryKey(id).getNumber();
            redisTemplate.opsForValue().set(key, Value);
            if(Value<num){
                return -1;
            }
            else{
                System.out.println("updateSuccessful:"+(Value-num));
                redisTemplate.opsForValue().set(key, Value-num);
                return 1;
            }
        }
        else{
            if(Value<num){
                return -1;
            }
            else{
                System.out.println("updateSuccessful:"+(Value-num));
                redisTemplate.opsForValue().set(key, Value-num);
                return 1;
            }
        }
    }
    public LitemallGoodsProduct selectByPrimaryKey(Integer id){
        String key=id.toString();

        if(redisTemplate.opsForValue().get(key)==null){
            Integer Value=litemallGoodsProductMapper.selectByPrimaryKey(id).getNumber();
            redisTemplate.opsForValue().set(key,Value);
            return litemallGoodsProductMapper.selectByPrimaryKey(id);
        }
        else {
            System.out.println(redisTemplate.opsForValue().get(key));
            return litemallGoodsProductMapper.selectByPrimaryKey(id);
        }
    }

    @Scheduled(cron = "0 0/1 * * * ?")//TODO：为了方便看到结果 现在定时一分钟写回一次数据库，同时清空redis
    public void timerToWriteSock(){
        System.out.println("now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"检查redis是否有数据需要写回");
        //获得所有key
        Set<String> keys = redisTemplate.keys("*");
        //创建集合
        HashMap<Object, Object> map = new HashMap();
        //循环
        System.out.println(keys.size());
        for (String key: keys)//获得key对应的value
            {   LitemallGoodsProduct litemallGoodsProduct=new LitemallGoodsProduct();
                System.out.println("now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"执行redis写回数据库成功");
                 Object value = redisTemplate.opsForValue().get(key);
                litemallGoodsProduct.setNumber((Integer)value);
                litemallGoodsProduct.setId(Integer.parseInt(key));
                litemallGoodsProduct.setUpdateTime(LocalDateTime.now());
                litemallGoodsProductMapper.updateByPrimaryKeySelective(litemallGoodsProduct);
            }
            redisTemplate.delete(keys);//执行完后清空当日redis
        }
    }
