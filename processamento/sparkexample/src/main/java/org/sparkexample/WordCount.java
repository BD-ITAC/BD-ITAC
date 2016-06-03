package org.sparkexample;

import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.sparkexample.model.pojo.CrisePojo;
import org.sparkexample.model.pojo.ResultPojo;
import org.sparkexample.model.pojo.UsuarioPojo;
import org.sparkexample.model.repository.CriseRepository;

import com.datastax.spark.connector.cql.CassandraConnector;
import com.datastax.spark.connector.japi.CassandraJavaUtil;

import scala.Tuple2;

public class WordCount {

	public static JavaSparkContext context;
	public static int idCount = 0;

	private static final PairFunction<String, String, Integer> WORDS_MAPPER = new PairFunction<String, String, Integer>() {

		public Tuple2<String, Integer> call(String s) throws Exception {
			return new Tuple2<String, Integer>(s, 1);
		}
	};

	private static final PairFunction<UsuarioPojo, String, ResultPojo> WORDS_MAPPER_CLASS = new PairFunction<UsuarioPojo, String, ResultPojo>() {

		public Tuple2<String, ResultPojo> call(UsuarioPojo u) throws Exception {
			return new Tuple2<String, ResultPojo>(u.getName(), new ResultPojo(idCount++, u.getName(), 1));
		}
	};

	private static final Function2<ResultPojo, ResultPojo, ResultPojo> WORDS_REDUCER_CLASS = new Function2<ResultPojo, ResultPojo, ResultPojo>() {

		public ResultPojo call(ResultPojo a, ResultPojo b) throws Exception {

			a.setValue( a.getValue() + b.getValue());
			return a;
		}
	};

	public static void main(String[] args) {

		//MSQL Configuration
		CriseRepository cr = new CriseRepository();
		List<CrisePojo> list = cr.find();
		
		//Spark Configuration
		SparkConf conf = new SparkConf().setAppName("org.sparkexample.WordCount").setMaster("local")
				.set("spark.cassandra.connection.host", "127.0.0.1");

		context = new JavaSparkContext(conf);

		//Cassandra connector
		CassandraConnector connector = CassandraConnector.apply(context.getConf());

		JavaRDD<UsuarioPojo> rdd = CassandraJavaUtil.javaFunctions(context).cassandraTable("ita_key", "usuario",
				CassandraJavaUtil.mapRowTo(UsuarioPojo.class));

		// Filtrar

		// Separa o texto em um map com chave
		JavaPairRDD<String, ResultPojo> pairs = rdd.mapToPair(WORDS_MAPPER_CLASS);

		// Junta todas as ocorrencias da mesma chave em uma linha
		JavaPairRDD<String, ResultPojo> counter = pairs.reduceByKey(WORDS_REDUCER_CLASS);

		// Salva o ResultPojoado em um arquivo
		// counter.saveAsTextFile(args[1]);

		// Obtem os objetos finais a serem salvos
		JavaRDD<ResultPojo> ResultRDD = counter.map(x -> x._2);

		// salva o ResultPojoado no banco de dados cassandra
		CassandraJavaUtil.javaFunctions(ResultRDD)
				.writerBuilder("ita_key", "result", CassandraJavaUtil.mapToRow(ResultPojo.class)).saveToCassandra();
	}
}