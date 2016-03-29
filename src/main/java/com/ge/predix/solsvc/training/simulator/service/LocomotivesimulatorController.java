package com.ge.predix.solsvc.training.simulator.service;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.math.util.MathUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ge.predix.solsvc.training.simulator.model.LocationData;
import com.ge.predix.solsvc.training.simulator.model.Locomotive;
import com.ge.predix.solsvc.training.simulator.model.LocomotiveGatewayType;
import com.ge.predix.solsvc.training.simulator.model.RPMData;
import com.ge.predix.solsvc.training.simulator.model.TorqueData;

@RestController
public class LocomotivesimulatorController implements EnvironmentAware {
	
	private static final Log logger = LogFactory.getLog(LocomotivesimulatorController.class);
	
	@Value("${timeseriesZone}")
	String timeseriesZone;
		
	@Value("${timeseriesUrl}")
	String timeseriesUrl;
	
	@Value("${clientId}")
	String clientId;
		
	private static boolean runSimulation = false;
	
	LocomotiveGatewayType locomotiveGatewayType;
	LocationData locationData = new LocationData();
	RPMData rpmData =  new RPMData();
	TorqueData torqueData = new TorqueData();
	
	@RequestMapping(
		    value = "/simulator/start",
			method = RequestMethod.POST,
		    consumes = "application/json")
	public String startSimulation() throws Exception {
		runSimulation = true;
//		generateSimulatorData();
		return Boolean.toString(runSimulation);

	}
	
	@RequestMapping(
			value = "/simulator/stop",
			method = RequestMethod.POST,
			consumes = "application/json")
	public String stopSimulation() throws Exception {
		runSimulation = false;
		return Boolean.toString(runSimulation);
	}
	
	@Scheduled(fixedRate=2000)
	public void generateSimulatorData() {
		
		logger.info(" LocomotivesimulatorController :: generateSimulatorData  " );
		
		// the simulator is not supposed to run if the runSimulation flag is set to false and so, return w/o pushing data to TimeSeries
		if (!runSimulation) {
			return;
		}
			
		locomotiveGatewayType = new LocomotiveGatewayType();
		for (Locomotive locomotive : Locomotive.values()) {
			
			locationData.setName(locomotive.name() + "_location");
			locationData.setLatitude(String.valueOf(locomotive.getLatitude() + generateRandomUsageValue(0.55, 0.75)));
			locationData.setLongitude(String.valueOf(locomotive.getLongitude() + generateRandomUsageValue(0.10, 0.56)));
			
			rpmData.setName(locomotive.name() + "_rpm");
			double rpm = MathUtils.round(1000 + generateRandomUsageValue(1, 10), 0, BigDecimal.ROUND_HALF_DOWN);				
			rpmData.setRpm(rpm);
			
			torqueData.setName(locomotive.name() + "_torque");
			int horsePower = locomotive.getHorsePower();
			//Torque = HP * 5252.11 / RPM
			double torque = MathUtils.round((horsePower * 5252.11)/rpm, 0, BigDecimal.ROUND_HALF_DOWN);
			torqueData.setTorque(torque);
			
			locomotiveGatewayType.setCurrentTime(System.currentTimeMillis());
			locomotiveGatewayType.setLocdata(locationData);
			locomotiveGatewayType.setRpmData(rpmData);
			locomotiveGatewayType.setTorquedata(torqueData);
							
			RestTemplate restTemplate = new RestTemplate();
			final String uri = this.timeseriesUrl; //"http://locomotive-dataingestion-service.run.aws-usw02-pr.ice.predix.io/SaveTimeSeriesData";
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = null;
			try {
				jsonInString = mapper.writeValueAsString(locomotiveGatewayType);
			} catch (JsonProcessingException e) {
				logger.error("Error occurred while parsing Json", e);
				e.printStackTrace();
			}
			
			logger.info(" LocomotivesimulatorController :: generateSimulatorData - json result: " + jsonInString);
			
			MultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();
		    mvm.add("clientId", this.clientId);
		    //mvm.add("tenantId", "34d2ece8-5faa-40ac-ae89-3a614aa00b6e");
		    mvm.add("tenantId", this.timeseriesZone);
		    mvm.add("content", jsonInString);
			String result = restTemplate.postForObject(uri, mvm, String.class);
			
			logger.info(" LocomotivesimulatorController :: generateSimulatorData - result: " + result);

		}
				
	}
	
	@Override
	public void setEnvironment(Environment env) {
		this.timeseriesZone = env.getProperty("timeseriesZone");
		this.timeseriesUrl = env.getProperty("timeseriesUrl");		
		this.clientId = env.getProperty("clientId");
	}
	
    private static double generateRandomUsageValue(double low, double high) {
    	return low + Math.random() * (high - low);
    }

}
