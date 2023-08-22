package urbanjungletech.hardwareservice.model;

import java.util.List;

public class HardwareControllerGroup {
    private Long id;
    private String name;
    private List<HardwareController> hardwareControllers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<HardwareController> getHardwareControllers() {
        return hardwareControllers;
    }

    public void setHardwareControllers(List<HardwareController> hardwareControllers) {
        this.hardwareControllers = hardwareControllers;
    }
}
