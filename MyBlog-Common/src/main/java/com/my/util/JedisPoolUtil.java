package com.my.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.my.config.RedisConfig;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;



@Configuration
public class JedisPoolUtil {

	public static volatile JedisCluster jedisCluster= null;

	@Autowired
	private RedisConfig redisConfig;


	public JedisCluster getJedisCluster() {
		if(null == jedisCluster) {
			synchronized(JedisPoolUtil.class) {
				if(null == jedisCluster) {
					Set<HostAndPort> node = new HashSet<>();
					List<String> ips = redisConfig.getClusterIps();
					List<Integer> ports = redisConfig.getClusterPorts();
					for (int i = 0; i < ips.size(); i++) {
						node.add(new HostAndPort(ips.get(i), ports.get(i)));
					}
					jedisCluster = new JedisCluster(node);
				}
			}
			
		}
		return jedisCluster;
	}


	public static byte[] serialize(Serializable object) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(object);
		return baos.toByteArray();
	}

	public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = new ObjectInputStream(bais);
		return ois.readObject();
	}
}

