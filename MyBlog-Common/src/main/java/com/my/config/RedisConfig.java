package com.my.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@PropertySource("classpath:/properties/redis.properties")
@Configuration
@Data
public class RedisConfig {
	@Value("${redis.node}")
	private String redisNode;
	
	private String ipAddr;
	
	private Integer port;
	
	@Value("${redis.sentinel}")
	private String redisSentinel;
	
	@Value("${redis.cluster}")
	private String redisCluster;
	
	private List<String> clusterIps;
	
	private List<Integer> clusterPorts;
	
	@PostConstruct
	public void doRedisCluster() {
		String[] nodeArr = redisCluster.split(";");
		clusterIps = new ArrayList<>();
		clusterPorts = new ArrayList<>();
		for (String string : nodeArr) {
			clusterIps.add(string.split(":")[0]);
			clusterPorts.add(Integer.parseInt(string.split(":")[1]));
		}
	}
	
	@PostConstruct
	public void doRedisConfig() {
		String[] nodeArray = redisNode.split(":");
		ipAddr = nodeArray[0];
		port = Integer.valueOf(nodeArray[1]);
	}
	
	
}
