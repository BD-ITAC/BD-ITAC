package br.ita.bditac.mqtt.model;

/**
 * 
 * Criteria to choose a Sensor
There are certain features which have to be considered when we choose a sensor. They are as given below:
1.     Accuracy
2.     Environmental condition - usually has limits for temperature/ humidity
3.     Range - Measurement limit of sensor
4.   Calibration - Essential for most of the measuring devices as the readings changes with time
5.     Resolution - Smallest increment detected by the sensor
6.     Cost
7.     Repeatability - The reading that varies is repeatedly measured under the same environment 
 

Classification of Sensors
The sensors are classified into the following criteria:
1.     Primary Input quantity (Measurand)
2.     Transduction principles (Using physical and chemical effects)
3.     Material and Technology
4.     Property
5.     MqttApplication
 
Transduction principle is the fundamental criteria which are followed for an efficient approach. Usually, material and technology criteria are chosen by the development engineering group.
 
Classification based on property is as given below:
·        Temperature - Thermistors, thermocouples, RTD’s, IC and many more.
·        Pressure - Fibre optic, vacuum, elastic liquid based manometers, LVDT, electronic.
·        Flow - Electromagnetic, differential pressure, positional displacement, thermal mass, etc.
·        Level Sensors - Differential pressure, ultrasonic radio frequency, radar, thermal displacement, etc.
·        Proximity and displacement - LVDT, photoelectric, capacitive, magnetic, ultrasonic.
·        Biosensors - Resonant mirror, electrochemical, surface Plasmon resonance, Light addressable potentio-metric.  
·        Image - Charge coupled devices, CMOS
·        Gas and chemical - Semiconductor, Infrared, Conductance, Electrochemical.
·        Acceleration - Gyroscopes,     Accelerometers.
·        Others - Moisture, humidity sensor, Speed sensor, mass, Tilt sensor, force, viscosity.
 
Surface Plasmon resonance and Light addressable potentio-metric from the Bio-sensors group are the new optical technology based sensors. CMOS Image sensors have low resolution as compared to charge coupled devices. CMOS has the advantages of small size, cheap, less power consumption and hence are better substitutes for Charge coupled devices. Accelerometers are independently grouped because of their vital role in future applications like aircraft, automobiles, etc and in fields of videogames, toys, etc. Magnetometers are those sensors which measure magnetic flux intensity B (in units of Tesla or As/m2).
 
Classification based on MqttApplication is as given below:
·        Industrial process control, measurement and automation
·        Non-industrial use – Aircraft, Medical products, Automobiles, Consumer electronics, other type of sensors.
 
Sensors can be classified based on power or energy supply requirement of the sensors:
·        Active Sensor - Sensors that require power supply are called as Active Sensors. Example: LiDAR (Light detection and ranging), photoconductive cell.
·        Passive Sensor - Sensors that do not require power supply are called as Passive Sensors. Example: Radiometers, film photography.
 
In the current and future applications, sensors can be classified into groups as follows:
·        Accelerometers - These are based on the Micro Electro Mechanical sensor technology. They are used for patient monitoring which includes pace makers and vehicle dynamic systems.
·        Biosensors - These are based on the electrochemical technology. They are used for food testing, medical care device, water testing, and biological warfare agent detection.
·        Image Sensors - These are based on the CMOS technology. They are used in consumer electronics, biometrics, traffic and security surveillance and PC imaging.
·        Motion Detectors - These are based on the Infra Red, Ultrasonic, and Microwave / radar technology. They are used in videogames and simulations, light activation and security detection. 
 *
 */
public enum SensorTipo {

	Desconhecido("Desconhecido"),
	AceleracaoVibracao("Aceleração/Vibração"),
	Acustico("Acústico"),
	QuimicoGas("Químico/Gás"),
	EletricoMagnetico("Elétrico/Magnético"),
	Fluxo("Fluxo"),
	ForcaCargaTorqueTensao("Força/Carga/Torque/Tensão"),
	HumidadeVapor("Humidade/Vapor"),
	Vazamento("Vazamento"),
	Otico("Ótico"),
	MovimentoVelocidadeDeslocamento("Movimento/Velocidade/Deslocamento"),
	PosicaoPresencaProximidade("Posição/Presença/Proximidade"),
	Pressao("Pressão"),
	Temperatura("Temperatura");
	
	private final String id;
	
	private SensorTipo(String id) {
		this.id = id;
	}
	
	public boolean equalsName(String id) {
		return (id == null) ? false : id.equals(id);
	}
	
	public String toString() {
		return this.id;
	}
	
}
