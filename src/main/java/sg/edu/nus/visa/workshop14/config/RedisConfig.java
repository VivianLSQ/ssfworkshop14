package sg.edu.nus.visa.workshop14.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

@Value("${spring.redis.host}")
private String redisHost; 
@Value("${spring.redis.port}")
private int redisPort; 
@Value("${spring.redis.username}")
private String redisUser; 
@Value("${spring.redis.password}")
private String redisPassword; 
    
@Bean
@Scope("singleton")
public RedisTemplate<String,Object> getRedisTemplate(){
    final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();

    config.setHostName(redisHost);
    config.setPort(redisPort);

    if(!redisUser.isEmpty() && !redisPassword.isEmpty()){
        config.setUsername(redisUser); 
        config.setPassword(redisPassword);
    }

    config.setDatabase(0);

    final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();

    //setting up connection with redis database
   final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
   //initialize and validate connection factory
   jedisFac.afterPropertiesSet();
   final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>(); 

    //Associate to Redis connection 
    redisTemplate.setConnectionFactory(jedisFac);

    //set the List key (contact- (1)) and Hash key (contact ID-(3)) serialization type to string
    //redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());
    
    //Convert String to Object 
    //Enables redis to store Java object on the value column
    //Enabling the Java object as values in Redis 
    RedisSerializer<Object>objSerializer=new JdkSerializationRedisSerializer(getClass().getClassLoader()); 
    //redisTemplate.setValueSerializer(objSerializer); //value for list (2)
    redisTemplate.setHashValueSerializer(objSerializer); //value for hash (4)

    return redisTemplate; 


}
    
    
}
