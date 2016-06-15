package org.spark;

import java.sql.Timestamp;

import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.spark.model.pojo.CriseCount;
import org.spark.model.pojo.CriseType;
import org.spark.model.pojo.Facebook;
import org.spark.model.pojo.Twitter;

import scala.Tuple2;

public class SparkFunction<T> {
	
	private static final Timestamp now = new Timestamp(System.currentTimeMillis());
	
	public static final PairFunction<Twitter, Integer, CriseCount> WORDS_MAPPER_TWITTER_CLASS = new PairFunction<Twitter, Integer, CriseCount>() {

		public Tuple2<Integer, CriseCount> call(Twitter t) throws Exception {
			//alterar para usar id da crise
			return new Tuple2<Integer, CriseCount>(t.getCri_id(), new CriseCount(t.getCri_id(), 1, now, CriseType.TWITTER));
		}
	};
	
	public static final PairFunction<Facebook, Integer, CriseCount> WORDS_MAPPER_FACEBOOK_CLASS = new PairFunction<Facebook, Integer, CriseCount>() {

		public Tuple2<Integer, CriseCount> call(Facebook t) throws Exception {
			//alterar para usar id da crise
			return new Tuple2<Integer, CriseCount>(t.getFcb_id(), new CriseCount(t.getCri_id(), 1, now, CriseType.FACEBOOK));
		}
	};

	public static final Function2<CriseCount, CriseCount, CriseCount> WORDS_REDUCER_CLASS = new Function2<CriseCount, CriseCount, CriseCount>() {

		public CriseCount call(CriseCount a, CriseCount b) throws Exception {

			a.setCount(a.getCount() + b.getCount());
			return a;
		}
	};
}
