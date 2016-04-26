package br.ita.bditac.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.joda.time.DateTime;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.ita.bditac.app.Application.BDITACGateway;


@Component
public class SensorDAO implements ApplicationContextAware {

	private static Map<String, Sensor> _sensores = new HashMap<String, Sensor>();

    private static Map<Integer, Mensagem> _messages = new HashMap<Integer, Mensagem>();

    private static ApplicationContext context;
    
    static {
        _messages.put(1, new Mensagem(1, Mensagem.Type.INFO, "001", "Ok", "Everything is all right!"));
        _messages.put(2, new Mensagem(2, Mensagem.Type.ERROR, "999", "Error", "Something went very badly"));
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }    
	
	public static String adicionarSensor(SensorTipo sensorTipo) {
		Sensor sensor = new Sensor(sensorTipo);
		
		_sensores.put(sensor.getUuid(), sensor);
		
		return sensor.getUuid();
	}
	
	public static String adicionarSensor(Sensor sensor) {
		_sensores.put(sensor.getUuid(), sensor);
		
		return sensor.getUuid();
	}
	
	
	public static Sensor obterSensor(String uuid) {
		return _sensores.get(uuid);
	}

	
    public static Mensagem obterMessage(int id) {
        Mensagem mensagem = _messages.get(id);

        return mensagem;
    }

    public static Mensagem obterMessageInfo(int id, String info) {
        Mensagem mensagem = _messages.get(id);
        Mensagem resourceMessage = new Mensagem(mensagem.getId(), mensagem.getType(), mensagem.getStatus(), mensagem.getDescription(), info);

        return resourceMessage;
    }

    public static List<Mensagem> obterMessages() {
        return new ArrayList<Mensagem>(_messages.values());
    }
    
    @Scheduled(fixedRate = 1000)
	private static void runSensors() {
		for(Sensor sensor : _sensores.values()) {
			for(String topico : sensor.getTopicos()) {
				try {
					Random random = new Random(DateTime.now().getMillis());
					IOTFPayload payload = new IOTFPayload(
							SensorTipo.Fluxo.ordinal(),
							random.nextDouble(),
							random.nextDouble(),
							random.nextDouble(),
							random.nextDouble());
					ObjectMapper mapper = new ObjectMapper();
					String payloadJson = mapper.writeValueAsString(payload);
					BDITACGateway gateway = context.getBean(BDITACGateway.class);
					gateway.sendToMqtt(payloadJson, topico);
				}
				catch(Exception ex) {
					throw new RuntimeException(ex);
				}
			}
		}
	}
	
}
