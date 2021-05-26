package com.lyz.demo5.utils;

import com.lyz.demo5.model.Menu;
import com.lyz.demo5.model.VO.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;

@Component
public class RedisUtils {

    @Resource
    private JedisPool jedisPool;

    /**
     * 向Redis中存值，永久有效
     */
    public String set(String key, String value) {
        Jedis jedis = null;
        try {

            jedis = jedisPool.getResource();
            return jedis.set(key, value);
        } catch (Exception e) {
            return "NO";
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }
    }

    /**
     * 向Redis 中存值  设置有效时间 单位：秒
     * @param key
     * @param value
     * @param time
     * @return
     */
    public String set(String key, String value,int time) {
        Jedis jedis = null;
        try {

            jedis = jedisPool.getResource();
            boolean keyExist = jedis.exists(key);
            if(keyExist){
                jedis.del(key);
            }
            return jedis.setex(key, time,value);
        } catch (Exception e) {
            return "NO";
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }
    }
    public String setObj(byte[] key, Object value) {
        Jedis jedis = null;
        try {

            jedis = jedisPool.getResource();
            boolean keyExist = jedis.exists(key);
            if(keyExist){
                jedis.del(key);
            }
            byte[] bytes = serialize(value);
            return jedis.set(key,bytes);
        } catch (Exception e) {
            return "NO";
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }
    }
    public String setObjByBytes(byte[] key, byte[] value){
        Jedis jedis = null;
        try {

            jedis = jedisPool.getResource();
            boolean keyExist = jedis.exists(key);
            if(keyExist){
                jedis.del(key);
            }
            return jedis.set(key,value);
        } catch (Exception e) {
            return "NO";
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }
    }
    public byte[] getByBytes(byte[] key) {
        Jedis jedis = null;
        byte[] value = new byte[0];
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return value;
    }
    /**
     * 根据传入Key获取指定Value
     */
    public String get(String key) {
        Jedis jedis = null;
        String value;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            return "";
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return value;
    }
    public Object getObj(String key){
        Jedis jedis = null;
        byte[] result = new byte[0];
        Object obj = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.get(key.getBytes());
            obj = unserizlize(result);
        } catch (Exception e) {
            e.printStackTrace();
            return obj;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return obj;
    }
    public Boolean existsObj(byte[] key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            return false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    /**
     * 校验Key值是否存在
     */
    public Boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            return false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 删除指定Key-Value
     */
    public Long delete(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(key);
        } catch (Exception e) {
            return 0L;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }
    }

    // 以上为常用方法，更多方法自行百度

    /**
     * 序列化
     */
    public  byte[] serialize(Object object) {
        ObjectOutputStream objectOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            byte[] getByte = byteArrayOutputStream.toByteArray();
            return getByte;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反序列化
     * @param binaryByte
     * @return
     */
    public   Object unserizlize(byte[] binaryByte) {
        ObjectInputStream objectInputStream = null;
        ByteArrayInputStream byteArrayInputStream = null;
        byteArrayInputStream = new ByteArrayInputStream(binaryByte);
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object obj = objectInputStream.readObject();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        RedisUtils ru = new RedisUtils();
        Menu m = new Menu();
        m.setName("sb");
        byte[] bytes = ru.serialize(m);
        Menu m2 = (Menu) ru.unserizlize(bytes);
        System.out.println(m2.getName());
    }
}
