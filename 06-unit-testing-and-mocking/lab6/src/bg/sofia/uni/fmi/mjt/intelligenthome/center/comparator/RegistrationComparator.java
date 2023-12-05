package bg.sofia.uni.fmi.mjt.intelligenthome.center.comparator;

import bg.sofia.uni.fmi.mjt.intelligenthome.device.IoTDevice;

import java.util.Comparator;

public class RegistrationComparator implements Comparator<IoTDevice> {

    @Override
    public int compare(IoTDevice firstDevice, IoTDevice secondDevice) {
        return (int)(secondDevice.getRegistration() - firstDevice.getRegistration());
//        if (res == 0) {
//            return firstDevice.getName().compareTo(secondDevice.getName());
//        }
//        return (res > 0 ? 1 : -1);
    }

}
