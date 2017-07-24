package com.flash.toolbar.redis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * 
 * <Description> redis访问操作类<br> 
 *  
 * @author ocean<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年1月13日 <br>
 */
@Component("redisOperation")
public class RedisOperation {
    /**
     * jedis集群对象
     */
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 构造函数
     * 
     * @param jedisCluster jedis集群
     */
    public RedisOperation(RedisTemplate jedisCluster) {
        super();
        this.redisTemplate = jedisCluster;
    }

    /**
     * 默认构造函数
     */
    public RedisOperation() {

    }

    /**
     * get jedisCluster
     * 
     * @return Returns the jedisCluster.<br>
     */
    public RedisTemplate getJedisCluster() {
        return redisTemplate;
    }

    /**
     * set jedisCluster
     * 
     * @param jedisCluster The jedisCluster to set. <br>
     */
    public void setJedisCluster(RedisTemplate jedisCluster) {
        this.redisTemplate = jedisCluster;
    }

    /**  
     * 新增 
     *<br>------------------------------<br> 
     * @param user 
     * @return 
     */  
    public boolean add(final String key,final String value) {  
        boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] keyT  = serializer.serialize(key);  
                byte[] name = serializer.serialize(value);  
                return connection.setNX(keyT, name);  
            }  
        });  
        return result;  
    }  
    
    /**
     * 设置过期时间
     * @param key
     * @param timeout
     * @param unit
     * @return
     */
    public boolean expire(String key,long timeout,TimeUnit unit){
    	Boolean rtn = redisTemplate.expire(key, timeout, unit);
    	return rtn;
    }
    /**  
     * 通过key获取 
     * <br>------------------------------<br> 
     * @param keyId 
     * @return 
     */  
    public String get(final String keyId) {  
        String result = (String) redisTemplate.execute(new RedisCallback<String>() {  
            public String doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] key = serializer.serialize(keyId);  
                byte[] value = connection.get(key);  
                if (value == null) {  
                    return null;  
                }  
                String name = serializer.deserialize(value);  
                return name;  
            }  
        });  
        return result;  
    }
    
    /**
     * 通过key删除缓存
     * @param key
     * @return
     */
    public void del(String... key){
        if(key!=null && key.length > 0){  
            if(key.length == 1){  
                redisTemplate.delete(key[0]);  
            }else{  
                redisTemplate.delete(CollectionUtils.arrayToList(key));               
            }  
        }  
    } 
    
    public boolean isSetsValue(final String keyId,final String val){
        boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] key = serializer.serialize(keyId);  
                byte[] value = serializer.serialize(val);  
                return connection.sIsMember(key, value);  
            }  
        }); 
        return result;
    }
    
    
   	public Set<String> SMEMBERS(final String key){
   		Set<String> result = null;
    	result = (Set<String>) redisTemplate.execute(new RedisCallback<Set<String>>(){
   			public Set<String> doInRedis(RedisConnection connection)
   					throws DataAccessException {
   				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] keyT  = serializer.serialize(key);
   				Set<byte[]> values = connection.sMembers(keyT);
   				Iterator iter = values.iterator();
   				Set<String> set = new HashSet<String>();
   				while(iter.hasNext()){
   					byte[] value = (byte[]) iter.next();
   					String str = serializer.deserialize(value);
   					set.add(str);
   				}
   				return set;
   			}    		
       	});
       	return result;
       }
   	
   	public boolean isExistKey(final String key){
        boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] keyT = serializer.serialize(key);  
                return connection.exists(keyT);  
            }  
        }); 
        return result;
   	}
}  

    

