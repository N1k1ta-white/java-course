package bg.sofia.uni.fmi.mjt.intelligenthome.device;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class IoTDeviceBase implements IoTDevice {
    protected static int uniqueNumberDevice = 0;
    protected DeviceType type;
    protected String name;
    protected double powerConsumption;
    protected LocalDateTime installationDateTime;
    protected LocalDateTime registration;

    protected String id;

    public IoTDeviceBase(String name, double powerConsumption, LocalDateTime installationDateTime) {
        this.name = name;
        this.powerConsumption = powerConsumption;
        this.installationDateTime = installationDateTime;
    }

    @Override
    public abstract String getId();

    //	public void setDeviceID(int uniqueNumber) {
    //		deviceID = deviceID + uniqueNumber;
    //	}

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPowerConsumption() {
        return powerConsumption;
    }

    @Override
    public LocalDateTime getInstallationDateTime() {
        return installationDateTime;
    }

    @Override
    public abstract DeviceType getType();

    @Override
    public long getRegistration() {
        return Duration.between(registration, LocalDateTime.now()).toSeconds();
    }

    @Override
    public void setRegistration(LocalDateTime registration) {
        this.registration = registration;
    }

    @Override
    public long getPowerConsumptionKWhPerMinute() {
        long duration = Duration.between(getInstallationDateTime(), LocalDateTime.now()).toMinutes();
        return (long)(duration *  powerConsumption);
    }

    @Override
    public long getPowerConsumptionKWh() {
        long duration = Duration.between(getInstallationDateTime(), LocalDateTime.now()).toHours();
        return (long)(duration *  powerConsumption);
    }
}
