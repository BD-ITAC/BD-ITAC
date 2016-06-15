package org.spark;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {
	
	private static final String filePath = "/config.properties";
	private static Properties properties;
	
	public PropertiesUtils() throws IOException {
		Properties props = new Properties();
		FileInputStream file = new FileInputStream(filePath);
		props.load(file);
		properties = props;
	}
	
	public String get(String property){
		return properties.getProperty(property);
	}
	
	public String getCassandraKeyspace(){
		return get("cassandra.keyspace");
	}
	
	public String getCassandraIP(){
		return get("cassandra.ip");
	}
	
	public String getCassandraTableOut(){
		return get("cassandra.table.out");
	}
	
	public String getCassandraUser(){
		return get("cassandra.user");
	}
	
	public String getCassandraPass(){
		return get("cassandra.pass");
	}

	public boolean getCassandraFacebook() {
		return Boolean.parseBoolean(get("cassandra.facebook"));
	}

	public boolean getCassandraTwitter() {
		return Boolean.parseBoolean(get("cassandra.twitter"));
	}

	public String getCassandraMaster() {
		return get("spark.master");
	}
}
