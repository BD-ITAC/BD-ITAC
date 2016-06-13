package org.sparkexample;

import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.sparkexample.model.pojo.CriseCount;
import org.sparkexample.model.pojo.CrisePojo;
import org.sparkexample.model.pojo.Facebook;
import org.sparkexample.model.pojo.Twitter;
import org.sparkexample.model.repository.CriseRepository;

import com.datastax.spark.connector.cql.CassandraConnector;
import com.datastax.spark.connector.japi.CassandraJavaUtil;

public class WordCount {

	private static JavaSparkContext context;

	private static final String cassandra_keyspace = "ita_key"; //?
	private static final String cassandra_ip = "127.0.0.1";//192.168.254.4
	private static final String cassandra_table_out = "crisecount";
	private static final String cassandra_user = null;//"bditacsenha";
	private static final String cassandra_pass = null;//"dbitac2016";
	

	public static void main(String[] args) {

		// MSQL Configuration
		CriseRepository cr = new CriseRepository();
		List<CrisePojo> list = cr.find();

		// Spark Configuration
		SparkConf conf;

		if (cassandra_user == null || cassandra_pass == null)
			conf = new SparkConf().setAppName("org.sparkexample.WordCount").setMaster("local")
					.set("spark.cassandra.connection.host", cassandra_ip);
		else
			conf = new SparkConf().setAppName("org.sparkexample.WordCount").setMaster("local")
					.set("spark.cassandra.auth.username", cassandra_user)
					.set("spark.cassandra.auth.password", cassandra_pass)
					.set("spark.cassandra.connection.host", cassandra_ip);

		context = new JavaSparkContext(conf);

		// Cassandra connector
		CassandraConnector connector = CassandraConnector.apply(context.getConf());

		twitterCount();
		facebookCount();
	}

	private static void twitterCount() {
		
		JavaRDD<Twitter> rdd = CassandraJavaUtil.javaFunctions(context).cassandraTable(cassandra_keyspace,
				Twitter.class.getSimpleName().toLowerCase(), CassandraJavaUtil.mapRowTo(Twitter.class));

		// Filtrar

		// Separa o texto em um map com chave
		JavaPairRDD<Integer, CriseCount> pairs = rdd.mapToPair(SparkFunction.WORDS_MAPPER_TWITTER_CLASS);

		// Junta todas as ocorrencias da mesma chave em uma linha
		JavaPairRDD<Integer, CriseCount> counter = pairs.reduceByKey(SparkFunction.WORDS_REDUCER_CLASS);

		// Salva o ResultPojoado em um arquivo
		// counter.saveAsTextFile(args[1]);

		// Obtem os objetos finais a serem salvos
		JavaRDD<CriseCount> ResultRDD = counter.map(x -> x._2);

		// salva o ResultPojoado no banco de dados cassandra
		CassandraJavaUtil.javaFunctions(ResultRDD)
				.writerBuilder(cassandra_keyspace, cassandra_table_out, CassandraJavaUtil.mapToRow(CriseCount.class))
				.saveToCassandra();
	}
	
	private static void facebookCount() {
		
		JavaRDD<Facebook> rdd = CassandraJavaUtil.javaFunctions(context).cassandraTable(cassandra_keyspace,
				Facebook.class.getSimpleName().toLowerCase(), CassandraJavaUtil.mapRowTo(Facebook.class));

		// Filtrar

		// Separa o texto em um map com chave
		JavaPairRDD<Integer, CriseCount> pairs = rdd.mapToPair(SparkFunction.WORDS_MAPPER_FACEBOOK_CLASS);

		// Junta todas as ocorrencias da mesma chave em uma linha
		JavaPairRDD<Integer, CriseCount> counter = pairs.reduceByKey(SparkFunction.WORDS_REDUCER_CLASS);

		// Salva o ResultPojoado em um arquivo
		// counter.saveAsTextFile(args[1]);

		// Obtem os objetos finais a serem salvos
		JavaRDD<CriseCount> ResultRDD = counter.map(x -> x._2);

		// salva o ResultPojoado no banco de dados cassandra
		CassandraJavaUtil.javaFunctions(ResultRDD)
				.writerBuilder(cassandra_keyspace, cassandra_table_out, CassandraJavaUtil.mapToRow(CriseCount.class))
				.saveToCassandra();
	}
}