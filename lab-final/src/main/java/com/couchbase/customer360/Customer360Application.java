package com.couchbase.customer360;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Customer360Application {

	public static void main(String[] args) {
		SpringApplication.run(Customer360Application.class, args);
	}

	@Bean
	public Cluster cluster(@Value("${couchbase.clusterHost}") String hostname, @Value("${couchbase.username}") String username,
						   @Value("${couchbase.password}") String password) {
		return Cluster.connect(hostname,username,password);
	}

	@Bean
	public Bucket customerBucket(Cluster cluster, @Value("${couchbase.bucket}") String bucketName) {
		return cluster.bucket(bucketName);
	}
}
