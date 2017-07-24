package com.flash.toolbar.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
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
    
    /**
     * 添加set
     * @param key
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
	public Long SADD(final String key, final String value){
    	Long result = (Long) redisTemplate.execute(new RedisCallback<Long>(){
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] keyT  = serializer.serialize(key);
                byte[] valueT = serializer.serialize(value);
				return connection.sAdd(keyT, valueT);
			}    		
    	});
    	return result;
    }
    
    @SuppressWarnings("unchecked")
   	public Set<String> SMEMBERS(final String key){
    	Set<String> result = (Set<String>) redisTemplate.execute(new RedisCallback<Set<String>>(){
   			public Set<String> doInRedis(RedisConnection connection)
   					throws DataAccessException {
   				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] keyT  = serializer.serialize(key);
   				Set<byte[]> values = connection.sMembers(keyT);
   				Iterator<byte[]> iter = values.iterator();
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
    
    /**
     * 判断是否存在
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public Boolean exists(final String key){
    	Boolean result = (Boolean)redisTemplate.execute(new RedisCallback<Boolean>(){
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] keyT  = serializer.serialize(key);
				return connection.exists(keyT);
			}
    	});
    	return result;
    }
    
    @SuppressWarnings("unchecked")
    public Boolean rename(final String key, final String newkey){
    	Boolean result = (Boolean)redisTemplate.execute(new RedisCallback<Boolean>(){
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] keyT  = serializer.serialize(key);
                byte[] newKeyT = serializer.serialize(newkey);
				return connection.renameNX(keyT, newKeyT);
			}
    	});
    	return result;
    }
    
    /**
     * 从左边入队一个Lists集合
     * @param key
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
	public Long LPUSH(final String key, final String value){
    	Long result = (Long) redisTemplate.execute(new RedisCallback<Long>(){
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] keyT  = serializer.serialize(key);
                byte[] valueT = serializer.serialize(value);
				return connection.lPush(keyT, valueT);
			}    		
    	});
    	return result;
    }
    
    /**
     * 从List的右边出队一个List集合
     * @param key
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<String> RPOP(final String key){
    	List<String> result = (List<String>) redisTemplate.execute(new RedisCallback<List<String>>(){
   			public List<String> doInRedis(RedisConnection connection)
   					throws DataAccessException {
   				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] keyT  = serializer.serialize(key);
                long len = connection.lLen(keyT);                
   				List<String> list = new ArrayList<String>();
   				for(int i=0;i<len;i++){
   					byte[] value = (byte[]) connection.rPop(keyT);
   					String str = serializer.deserialize(value);
   					list.add(str);
   				}
   				return list;
   			}    		
       	});
       	return result;
    }
    
    /**
     * 添加一个有序集合ZSet
     * @param key
     * @param value
     * @param score
     * @return
     */
    @SuppressWarnings("unchecked")
    public Boolean ZAdd(final String key, final String value, final Double score){
    	Boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>(){
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] keyT  = serializer.serialize(key);
                byte[] valueT = serializer.serialize(value);
				return connection.zAdd(keyT, score, valueT);
			}    		
    	});
    	return result;
    }
    
    /**
     * 查询score
     * @param key
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public Double ZScore(final String key, final String value){
    	Double result = (Double) redisTemplate.execute(new RedisCallback<Double>(){
    		public Double doInRedis(RedisConnection connection)
    				throws DataAccessException {
    			RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
    			byte[] keyT  = serializer.serialize(key);
    			byte[] valueT = serializer.serialize(value);
    			return connection.zScore(keyT, valueT);
    		}    		
    	});
    	return result;
    }
    
    /**
     * 从指定集合中删除指定的元素
     * @param key
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public Long ZRem(final String key, final String value){
    	Long result = (Long) redisTemplate.execute(new RedisCallback<Long>(){
    		public Long doInRedis(RedisConnection connection)
    				throws DataAccessException {
    			RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
    			byte[] keyT  = serializer.serialize(key);
    			byte[] valueT = serializer.serialize(value);
    			return connection.zRem(keyT, valueT);
    		}    		
    	});
    	return result;
    }
    
    /**
     * 加锁
     * @param key
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public Boolean setNX(final String key, final String value){
    	Boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>(){
    		public Boolean doInRedis(RedisConnection connection)
    				throws DataAccessException {
    			RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
    			byte[] keyT  = serializer.serialize(key);
    			byte[] valueT = serializer.serialize(value);
    			return connection.setNX(keyT, valueT);
    		}    		
    	});
    	return result;
    }
    
    /**
     * 获取所有的ZSet
     * @param key
     * @param start 默认为0 第一个元素
     * @param stop 默认为-1 倒数第一个元素
     * @return
     */
    @SuppressWarnings("unchecked")
   	public List<String> ZRange(final String key, final long start, final long stop){
    	List<String> result = (List<String>) redisTemplate.execute(new RedisCallback<List<String>>(){
   			public List<String> doInRedis(RedisConnection connection)
   					throws DataAccessException {
   				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] keyT  = serializer.serialize(key);
   				Set<byte[]> values = connection.zRange(keyT, start, stop);
   				Iterator<byte[]> iter = values.iterator();
   				List<String> set = new ArrayList<String>();
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
    
    /**
     * 获取所有的ZSet 分数从高到低
     * @param key
     * @param start 默认为0 第一个元素
     * @param stop 默认为-1 倒数第一个元素
     * @return
     */
    @SuppressWarnings("unchecked")
   	public List<String> ZRevRange(final String key, final long start, final long stop){
    	List<String> result = (List<String>) redisTemplate.execute(new RedisCallback<List<String>>(){
   			public List<String> doInRedis(RedisConnection connection)
   					throws DataAccessException {
   				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] keyT  = serializer.serialize(key);
   				Set<byte[]> values = connection.zRevRange(keyT, start, stop);
   				Iterator<byte[]> iter = values.iterator();
   				List<String> set = new ArrayList<String>();
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
    
    /**
     * 获取所有的ZSet 分数从高到低
     * @param key
     * @param start 默认为0 第一个元素
     * @param stop 默认为-1 倒数第一个元素
     * @return
     */
    @SuppressWarnings("unchecked")
   	public Map<String, Double> ZRevRangeWithScores(final String key, final long start, final long stop){
    	Map<String, Double> result = (Map<String, Double>) redisTemplate.execute(new RedisCallback<Map<String, Double>>(){
   			public Map<String, Double> doInRedis(RedisConnection connection)
   					throws DataAccessException {
   				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] keyT  = serializer.serialize(key);
   				Set<Tuple> values = connection.zRevRangeWithScores(keyT, start, stop);
   				Iterator<Tuple> iter = values.iterator();
   				Map<String, Double> map = new HashMap<String, Double>();
   				while(iter.hasNext()){
   					Tuple value = (Tuple) iter.next();
   					String str = serializer.deserialize(value.getValue());
   					double score = value.getScore();
   					map.put(str, score);
   				}
   				return map;
   			}
       	});
       	return result;
    }
    
    /**
     * 获取一个排序集合的成员数量
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public Long ZCard(final String key){
    	Long result = (Long) redisTemplate.execute(new RedisCallback<Long>(){
    		public Long doInRedis(RedisConnection connection)
   					throws DataAccessException {
   				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] keyT  = serializer.serialize(key);
   				Long values = connection.zCard(keyT);   				
   				return values;
   			}
    	});
    	return result;
    }
    
    /**
     * 返回有序集key中成员member的排名 从大到小排列
     * @param key
     * @param member
     * @return
     */
    @SuppressWarnings("unchecked")
    public Long ZRevRank(final String key, final String member){
    	Long result = (Long) redisTemplate.execute(new RedisCallback<Long>(){
    		public Long doInRedis(RedisConnection connection)
   					throws DataAccessException {
   				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                byte[] keyT  = serializer.serialize(key);
                byte[] valueT = serializer.serialize(member);
   				Long values = connection.zRevRank(keyT, valueT);
   				return values;
   			}
    	});
    	return result;
    }
}  

    

