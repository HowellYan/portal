package cn.com.bestpay.portal.req;

import java.io.Serializable;

/**
 * Created by Howell on 27/10/16.
 */
public class BaseRequest implements Serializable {

    private String WebKeep;

    private String MachineNetwork;

    private String MachineDisk;

    private String MachineCPU;

    public String getWebKeep() {
        return WebKeep;
    }

    public void setWebKeep(String webKeep) {
        WebKeep = webKeep;
    }

    public String getMachineNetwork() {
        return MachineNetwork;
    }

    public void setMachineNetwork(String machineNetwork) {
        MachineNetwork = machineNetwork;
    }

    public String getMachineDisk() {
        return MachineDisk;
    }

    public void setMachineDisk(String machineDisk) {
        MachineDisk = machineDisk;
    }

    public String getMachineCPU() {
        return MachineCPU;
    }

    public void setMachineCPU(String machineCPU) {
        MachineCPU = machineCPU;
    }
}
