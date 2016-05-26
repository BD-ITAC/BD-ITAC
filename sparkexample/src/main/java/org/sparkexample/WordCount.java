package org.sparkexample;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import com.datastax.spark.connector.cql.CassandraConnector;
import com.datastax.spark.connector.japi.CassandraJavaUtil;
import com.datastax.spark.connector.writer.RowWriterFactory;

import scala.Tuple2;

public class WordCount {

	public static JavaSparkContext context;
	public static int idCount = 2;
	

   private static final PairFunction<String, String, Integer> WORDS_MAPPER =
      new PairFunction<String, String, Integer>() {
 
        public Tuple2<String, Integer> call(String s) throws Exception {
          return new Tuple2<String, Integer>(s, 1);
        }
   };
      
   private static final PairFunction<Usuario, String, Result> WORDS_MAPPER_CLASS =
      new PairFunction<Usuario, String, Result>() {
 
        public Tuple2<String, Result> call(Usuario u) throws Exception {
          return new Tuple2<String, Result>(u.name, new Result(idCount++, u.name, 1));
	    }
   };

    private static final Function2<Result, Result, Result> WORDS_REDUCER_CLASS =
      new Function2<Result, Result, Result>() {
   
        public Result call(Result a, Result b) throws Exception {
         
          a.value += b.value; 
          return a;
        }
      };    

    public static void main(String[] args) {
	    if (args.length < 1) {
	      System.err.println("Please provide the input file full path as argument");
	      System.exit(0);
	    }
	
	    SparkConf conf = new SparkConf().setAppName("org.sparkexample.WordCount")
	    		.setMaster("local")
	    		.set("spark.cassandra.connection.host", "127.0.0.1");
	    
	    context = new JavaSparkContext(conf);

	    CassandraConnector connector = CassandraConnector.apply(context.getConf());

	    JavaRDD<Usuario> rdd = CassandraJavaUtil.javaFunctions(context).cassandraTable("ita_key", "usuario", CassandraJavaUtil.mapRowTo(Usuario.class));
	      
	    //Filtrar 
	    
	    //Separa o texto em um map com chave
	    JavaPairRDD<String, Result> pairs = rdd.mapToPair(WORDS_MAPPER_CLASS);
	    
	    //Junta todas as ocorrencias da mesma chave em uma linha
		JavaPairRDD<String, Result> counter = pairs.reduceByKey(WORDS_REDUCER_CLASS); 
		  
		//Salva o resultado em um arquivo
		//counter.saveAsTextFile(args[1]);
		
		//Obtem os objetos finais a serem salvos
		JavaRDD<Result> resultRDD = counter.map(x -> x._2);
		
		
		//salva o resultado no banco de dados cassandra
		CassandraJavaUtil.javaFunctions(resultRDD)
			.writerBuilder("ita_key", "result", CassandraJavaUtil.mapToRow(Result.class))
			.saveToCassandra();
	}

	public static class Usuario implements Serializable {
	    private Integer id;
	    private String name;
	   	
	    public Usuario() { }
	
	    public Usuario(Integer id, String name) {
	        this.id = id;
	        this.name = name;
	    }
	
	    public Integer getId() { return id; }
	    public void setId(Integer id) { this.id = id; }
	
	    public String getName() { return name; }
	    public void setName(String name) { this.name = name; }
	
	    @Override
	    public String toString() {
	        return MessageFormat.format("Tweet'{'id={0}, name=''{1}'''}'", id, name);
	    }
	}
	
	public static class Result implements Serializable {
	    private Integer id;
	    private String name;
	    private Integer value;
	   	
	    public Result() { }
	
	    public Result(Integer id,String name, Integer value) {
	        this.id = id;
	        this.name = name;
	        this.value = value;
	    }
	
	    public Integer getId() { return id; }
	    public void setId(Integer id) { this.id = id; }
	
	    public String getName() { return name; }
	    public void setName(String name) { this.name = name; }
	    
	    public Integer getValue() { return value; }
	    public void setValue(Integer value) { this.value = value; }
	
	    @Override
	    public String toString() {
	        return MessageFormat.format("Tweet'{'id={0}, name=''{1}'''}'", id, name);
	    }
	}
	
}