package org.sparkexample;

import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.sparkexample.model.pojo.CrisePojo;
import org.sparkexample.model.pojo.CriseCount;
import org.sparkexample.model.pojo.UsuarioPojo;
import org.sparkexample.model.repository.CriseRepository;

import com.datastax.spark.connector.cql.CassandraConnector;
import com.datastax.spark.connector.japi.CassandraJavaUtil;

import scala.Tuple2;

public class WordCount {

	private static JavaSparkContext context;

	private static final String cassandra_keyspace = "ita_key"; //?
	private static final String cassandra_ip = "127.0.0.1";//192.168.254.4
	private static final String cassandra_table_in = "usuario";//?
	private static final String cassandra_table_out = "crisecount";
	private static final String cassandra_user = null;//"bditacsenha";
	private static final String cassandra_pass = null;//"dbitac2016";

	private static final PairFunction<UsuarioPojo, String, CriseCount> WORDS_MAPPER_CLASS = new PairFunction<UsuarioPojo, String, CriseCount>() {

		public Tuple2<String, CriseCount> call(UsuarioPojo u) throws Exception {
			//alterar para usar id da crise
			return new Tuple2<String, CriseCount>(u.getName(), new CriseCount(1, 1));
		}
	};

	private static final Function2<CriseCount, CriseCount, CriseCount> WORDS_REDUCER_CLASS = new Function2<CriseCount, CriseCount, CriseCount>() {

		public CriseCount call(CriseCount a, CriseCount b) throws Exception {

			a.setCount(a.getCount() + b.getCount());
			return a;
		}
	};

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

		JavaRDD<UsuarioPojo> rdd = CassandraJavaUtil.javaFunctions(context).cassandraTable(cassandra_keyspace,
				cassandra_table_in, CassandraJavaUtil.mapRowTo(UsuarioPojo.class));

		// Filtrar

		// Separa o texto em um map com chave
		JavaPairRDD<String, CriseCount> pairs = rdd.mapToPair(WORDS_MAPPER_CLASS);

		// Junta todas as ocorrencias da mesma chave em uma linha
		JavaPairRDD<String, CriseCount> counter = pairs.reduceByKey(WORDS_REDUCER_CLASS);

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