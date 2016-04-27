package br.ita.bditac.mqtt.support;


public interface MQTTConstants {

	public static final String MQTT_DEFAULT_HOST = "tcp://iot.eclipse.org:1883";
	
	public static final String MQTT_BACKUP_HOST = "tcp://iot.eclipse.org:1883";
	
	public static final String MQTT_GENERAL_TOPIC = "bditac/#";
	
	public static final int MQTT_CHANNEL_ADAPTER_COMPLETION_TIMEOUT = 5000;
	
	public interface MQTTQoS {
		
		public static final int MQTT_QOS_AT_MOST_ONCE = 0;
		
		public static final int MQTT_QOS_AT_LEAST_ONCE = 1;
		
		public static final int MQTT_QOS_EXACTLY_ONCE = 2;
		
	}

}
