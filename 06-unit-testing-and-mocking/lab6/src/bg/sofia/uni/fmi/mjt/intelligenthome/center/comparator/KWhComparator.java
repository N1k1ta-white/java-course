package bg.sofia.uni.fmi.mjt.intelligenthome.center.comparator;

import bg.sofia.uni.fmi.mjt.intelligenthome.device.IoTDevice;

import java.util.Comparator;

public class KWhComparator implements Comparator<IoTDevice> {

    @Override
    public int compare(IoTDevice firstDevice, IoTDevice secondDevice) {
        return (int)(firstDevice.getPowerConsumptionKWhPerMinute() - secondDevice.getPowerConsumptionKWhPerMinute());
//        if (res == 0) {
//            RegistrationComparator comparator = new RegistrationComparator();
//            return comparator.compare(firstDevice, secondDevice);
//        }
//        return res > 0 ? 1 : -1;
    }

}