package org.spark;

import java.io.IOException;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.spark.model.pojo.CriseCount;
import org.spark.model.pojo.Facebook;
import org.spark.model.pojo.Twitter;

import com.datastax.spark.connector.cql.CassandraConnector;
import com.datastax.spark.connector.japi.CassandraJavaUtil;

public class Processamento {

	private static JavaSparkContext context;	
	private static PropertiesUtils propertiesUtils;	

	public static void main(String[] args) throws IOException {

		propertiesUtils = new PropertiesUtils();

		// Spark Configuration
		SparkConf conf;

		if (propertiesUtils.getCassandraUser() == null || propertiesUtils.getCassandraPass() == null)
			conf = new SparkConf().setAppName("org.sparkexample.WordCount").setMaster(propertiesUtils.getCassandraMaster())
					.set("spark.cassandra.connection.host", propertiesUtils.getCassandraIP());
		else
			conf = new SparkConf().setAppName("org.sparkexample.WordCount").setMaster(propertiesUtils.getCassandraMaster())
					.set("spark.cassandra.auth.username", propertiesUtils.getCassandraUser())
					.set("spark.cassandra.auth.password", propertiesUtils.getCassandraPass())
					.set("spark.cassandra.connection.host", propertiesUtils.getCassandraIP());

		context = new JavaSparkContext(conf);

		// Cassandra connector
		CassandraConnector connector = CassandraConnector.apply(context.getConf());

		twitterCount();
		facebookCount();
	}

	private static void twitterCount() {
		
		if(!propertiesUtils.getCassandraTwitter())
			return;
		
		JavaRDD<Twitter> rdd = CassandraJavaUtil.javaFunctions(context).cassandraTable(propertiesUtils.getCassandraKeyspace(),
				Twitter.class.getSimpleName().toLowerCase(), CassandraJavaUtil.mapRowTo(Twitter.class));

		// Filtrar

		// Separa o texto em um map com chave
		JavaPairRDD<Integer, CriseCount> pairs = rdd.mapToPair(SparkFunction.WORDS_MAPPER_TWITTER_CLASS);

		reduceAndSave(pairs);
	}
	
	private static void facebookCount() {
		
		if(!propertiesUtils.getCassandraFacebook())
			return;
		
		JavaRDD<Facebook> rdd = CassandraJavaUtil.javaFunctions(context).cassandraTable(propertiesUtils.getCassandraKeyspace(),
				Facebook.class.getSimpleName().toLowerCase(), CassandraJavaUtil.mapRowTo(Facebook.class));

		// Filtrar

		// Separa o texto em um map com chave
		JavaPairRDD<Integer, CriseCount> pairs = rdd.mapToPair(SparkFunction.WORDS_MAPPER_FACEBOOK_CLASS);

		reduceAndSave(pairs);
	}

	private static void reduceAndSave(JavaPairRDD<Integer, CriseCount> pairs) {
		// Junta todas as ocorrencias da mesma chave em uma linha
		JavaPairRDD<Integer, CriseCount> counter = pairs.reduceByKey(SparkFunction.WORDS_REDUCER_CLASS);

		// Salva o ResultPojoado em um arquivo
		// counter.saveAsTextFile(args[1]);

		// Obtem os objetos finais a serem salvos
		JavaRDD<CriseCount> ResultRDD = counter.map(x -> x._2);

		// salva o ResultPojoado no banco de dados cassandra
		CassandraJavaUtil.javaFunctions(ResultRDD)
				.writerBuilder(propertiesUtils.getCassandraKeyspace(), propertiesUtils.getCassandraTableOut(), CassandraJavaUtil.mapToRow(CriseCount.class))
				.saveToCassandra();
	}
}