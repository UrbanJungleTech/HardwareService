package urbanjungletech.hardwareservice.config.cpu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.cpu.CpuSensorType;

import java.util.Map;
import java.util.function.Supplier;

@Configuration
public class CpuBeans {

    @Bean
    public SystemInfo systemInfo(){
        return new SystemInfo();
    }

    @Bean
    HardwareAbstractionLayer hardwareAbstractionLayer(SystemInfo systemInfo){
        return systemInfo.getHardware();
    }
    @Bean
    public CentralProcessor centralProcessor(HardwareAbstractionLayer hardwareAbstractionLayer){
        return hardwareAbstractionLayer.getProcessor();
    }

    @Bean
    public Map<CpuSensorType, Supplier<Double>> cpuSensorTypeMappings(HardwareAbstractionLayer hardwareAbstractionLayer){
        Map<CpuSensorType, Supplier<Double>> result = new java.util.HashMap<>();
        result.put(CpuSensorType.TEMPERATURE, () -> hardwareAbstractionLayer.getSensors().getCpuTemperature());
        result.put(CpuSensorType.LOAD, () -> hardwareAbstractionLayer.getProcessor().getSystemLoadAverage(1)[0]);
        result.put(CpuSensorType.CLOCK_SPEED, () -> Double.valueOf(hardwareAbstractionLayer.getProcessor().getProcessorIdentifier().getVendorFreq()));
        result.put(CpuSensorType.CORE_VOLTAGE, () -> hardwareAbstractionLayer.getSensors().getCpuVoltage());
        result.put(CpuSensorType.CORE_MULTIPLIER, () -> Double.valueOf(hardwareAbstractionLayer.getProcessor().getProcessorIdentifier().getIdentifier()));
        result.put(CpuSensorType.FAN_SPEED, () -> Double.valueOf(hardwareAbstractionLayer.getSensors().getFanSpeeds()[0]));
        return result;
    }
}
