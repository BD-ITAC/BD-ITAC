package br.ita.bditac.model;


import java.util.Random;

import org.joda.time.DateTime;


public class IOTFPayload {

	public class IOTFData {
		
		public int tipo;
		
		public double lat;
		
		public double lon;
		
		public double alt;
		
		public double val;
		
	}
	
	IOTFData d = new IOTFData();
	
	long ts;
	
	public IOTFPayload(
			int tipo,
			double latitude,
			double longitude,
			double altitude,
			double valor
			) {
		
		this.d.tipo = tipo;
		this.d.lat = latitude;
		this.d.lon = longitude;
		this.d.alt = altitude;
		Random random = new Random(DateTime.now().getMillis());
		this.d.val = random.nextDouble(); 
		this.ts = DateTime.now().getMillis();
		
	}
	
	
	public IOTFData getD() {
		return d;
	}

	
	public long getTs() {
		return ts;
	}
	
	
}
