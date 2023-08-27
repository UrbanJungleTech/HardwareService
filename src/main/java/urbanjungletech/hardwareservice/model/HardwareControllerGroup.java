package urbanjungletech.hardwareservice.model;

import java.util.List;

public class HardwareControllerGroup {
    private Long id;
    private String name;
    private List<Long> hardwareControllers;

    public List<Long> getHardwareControllers() {
        return hardwareControllers;
    }

    public void setHardwareControllers(List<Long> hardwareControllers) {
        this.hardwareControllers = hardwareControllers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
