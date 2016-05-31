package br.ita.bditac.mqtt.support;


public interface MQTTConstants {

	public static final String MQTT_DEFAULT_HOST = "tcp://iot.eclipse.org:1883";
	
	public static final String MQTT_BACKUP_HOST = "tcp://iot.eclipse.org:1883";
	
	public static final String MQTT_DEFAULT_TOPIC = "bditac/default";
	
	public interface MQTTQoS {
		
		public static final int MQTT_QOS_AT_MOST_ONCE = 0;
		
		public static final int MQTT_QOS_AT_LEAST_ONCE = 1;
		
		public static final int MQTT_QOS_EXACTLY_ONCE = 2;
		
	}

}
