package bg.sofia.uni.fmi.mjt.intelligenthome.center;

import bg.sofia.uni.fmi.mjt.intelligenthome.device.AmazonAlexa;
import bg.sofia.uni.fmi.mjt.intelligenthome.device.DeviceType;
import bg.sofia.uni.fmi.mjt.intelligenthome.device.IoTDevice;
import bg.sofia.uni.fmi.mjt.intelligenthome.device.RgbBulb;
import bg.sofia.uni.fmi.mjt.intelligenthome.center.exceptions.DeviceAlreadyRegisteredException;
import bg.sofia.uni.fmi.mjt.intelligenthome.center.exceptions.DeviceNotFoundException;
import bg.sofia.uni.fmi.mjt.intelligenthome.device.WiFiThermostat;
import bg.sofia.uni.fmi.mjt.intelligenthome.storage.MapDeviceStorage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IntelligentHomeCenterTest {
    @Test
    void testRegisterNullArg() {
        IntelligentHomeCenter test = new IntelligentHomeCenter(new MapDeviceStorage());
        assertThrows(IllegalArgumentException.class, () -> test.register(null));
    }

    @Test
    void testRegisterIsAlreadyRegistered() throws DeviceAlreadyRegisteredException {
        IntelligentHomeCenter test = new IntelligentHomeCenter(new MapDeviceStorage());
        IoTDevice testDevice = new AmazonAlexa("Alexa", 1.1, LocalDateTime.now());
        test.register(testDevice);
        assertThrows(DeviceAlreadyRegisteredException.class, () -> test.register(testDevice));
    }

    @Test
    void testRegisterIsWorking() throws DeviceAlreadyRegisteredException, DeviceNotFoundException {
        IntelligentHomeCenter test = new IntelligentHomeCenter(new MapDeviceStorage());
        IoTDevice testDevice = new AmazonAlexa("Alexa", 1.1, LocalDateTime.now());
        test.register(testDevice);
        assertNotEquals(null, test.getDeviceById(testDevice.getId()));
    }

    @Test
    void testUnregisterIsWorking() throws DeviceAlreadyRegisteredException, DeviceNotFoundException {
        IntelligentHomeCenter test = new IntelligentHomeCenter(new MapDeviceStorage());
        IoTDevice testDevice = new AmazonAlexa("Alexa", 1.1, LocalDateTime.now());
        test.register(testDevice);
        test.unregister(testDevice);
        assertThrows(DeviceNotFoundException.class, () -> test.getDeviceById(testDevice.getId()));
    }

    @Test
    void testUnregisterNullArg() {
        IntelligentHomeCenter test = new IntelligentHomeCenter(new MapDeviceStorage());
        assertThrows(IllegalArgumentException.class, () -> test.unregister(null));
    }

    @Test
    void testUnregisterNotFound() {
        IntelligentHomeCenter test = new IntelligentHomeCenter(new MapDeviceStorage());
        assertThrows(DeviceNotFoundException.class, () ->
                test.unregister(new AmazonAlexa("Alexa", 1.11, LocalDateTime.now())));
    }

    @Test
    void testGetDeviceByIdNull() {
        IntelligentHomeCenter test = new IntelligentHomeCenter(new MapDeviceStorage());
        assertThrows(IllegalArgumentException.class, () -> test.getDeviceById(null));
    }

    @Test
    void testGetDeviceByIdEmpty() {
        IntelligentHomeCenter test = new IntelligentHomeCenter(new MapDeviceStorage());
        assertThrows(IllegalArgumentException.class, () -> test.getDeviceById(""));
    }

    @Test
    void testGetDeviceByIdIsWorking() throws DeviceAlreadyRegisteredException, DeviceNotFoundException {
        IntelligentHomeCenter test = new IntelligentHomeCenter(new MapDeviceStorage());
        IoTDevice testDevice = new AmazonAlexa("Alexa", 1.1, LocalDateTime.now());
        test.register(testDevice);
        assertEquals(testDevice, test.getDeviceById(testDevice.getId()));
    }

    @Test
    void testGetDeviceQuantityPerTypeNull() {
        IntelligentHomeCenter test = new IntelligentHomeCenter(new MapDeviceStorage());
        assertThrows(IllegalArgumentException.class, () -> test.getDeviceQuantityPerType(null));
    }

    @Test
    void testGetDeviceQuantityPerTypeZeroDevices() {
        IntelligentHomeCenter test = new IntelligentHomeCenter(new MapDeviceStorage());
        assertEquals(0, test.getDeviceQuantityPerType(DeviceType.BULB));
        assertEquals(0, test.getDeviceQuantityPerType(DeviceType.SMART_SPEAKER));
        assertEquals(0, test.getDeviceQuantityPerType(DeviceType.THERMOSTAT));
    }

    @Test
    void testGetDeviceQuantityPerTypeIsWorking() throws DeviceAlreadyRegisteredException {
        IntelligentHomeCenter test = new IntelligentHomeCenter(new MapDeviceStorage());
        test.register(new AmazonAlexa("Alexa", 1.1, LocalDateTime.now()));
        test.register(new AmazonAlexa("Alexa", 1.1, LocalDateTime.now()));
        assertEquals(2, test.getDeviceQuantityPerType(DeviceType.SMART_SPEAKER));
    }

    @Test
    void testGetTopNDevicesByPowerConsumptionNegativeArgument() {
        IntelligentHomeCenter test = new IntelligentHomeCenter(new MapDeviceStorage());
        assertThrows(IllegalArgumentException.class, () -> test.getTopNDevicesByPowerConsumption(-1));
    }

    @Test
    void testGetTopNDevicesByPowerConsumptionZeroArgument() {
        IntelligentHomeCenter test = new IntelligentHomeCenter(new MapDeviceStorage());
        assertIterableEquals(List.of(), test.getTopNDevicesByPowerConsumption(0));
    }

    @Test
    void testGetTopNDevicesByPowerConsumptionIsWorking() throws DeviceAlreadyRegisteredException {
        IntelligentHomeCenter test = new IntelligentHomeCenter(new MapDeviceStorage());
        IoTDevice dev1 = new RgbBulb("Bulb", 1.0, LocalDateTime.now().minusDays(1));
        IoTDevice dev2 = new AmazonAlexa("Alexa", 20.1, LocalDateTime.now().minusHours(1));
        test.register(dev2);
        test.register(dev1);
        assertIterableEquals(List.of(dev1.getId(), dev2.getId()), test.getTopNDevicesByPowerConsumption(2));
    }

    @Test
    void testGetTopNDevicesByPowerConsumptionNIsExceedsTotalNumber() throws DeviceAlreadyRegisteredException {
        IntelligentHomeCenter test = new IntelligentHomeCenter(new MapDeviceStorage());
        IoTDevice dev1 = new RgbBulb("Bulb", 1.0, LocalDateTime.now().minusDays(1));
        IoTDevice dev2 = new AmazonAlexa("Alexa", 20.1, LocalDateTime.now().minusHours(1));
        test.register(dev2);
        test.register(dev1);
        assertIterableEquals(List.of(dev1.getId(), dev2.getId()), test.getTopNDevicesByPowerConsumption(Integer.MAX_VALUE));
    }

    @Test
    @Disabled
    void testGetTopNDevicesByPowerConsumptionEqualsConsumption() throws DeviceAlreadyRegisteredException {
        IntelligentHomeCenter test = new IntelligentHomeCenter(new MapDeviceStorage());
        IoTDevice dev1 = new RgbBulb("Bulb", 0, LocalDateTime.now().minusDays(1));
        IoTDevice dev2 = new AmazonAlexa("Alexa", 0, LocalDateTime.now().minusHours(1));
        IoTDevice dev4 = new WiFiThermostat("CLexa2", 0, LocalDateTime.now().minusHours(1));
        test.register(dev1);
        test.register(dev2);
        test.register(dev4);
        assertIterableEquals(List.of(dev2.getId(), dev1.getId(), dev4.getId()), test.getTopNDevicesByPowerConsumption(4));
    }

    @Test
    void testGetTopNDevicesByPowerConsumptionNIsLowerThanTotalNumber() throws DeviceAlreadyRegisteredException {
        IntelligentHomeCenter test = new IntelligentHomeCenter(new MapDeviceStorage());
        IoTDevice dev1 = new RgbBulb("Bulb", 1.0, LocalDateTime.now().minusDays(1));
        IoTDevice dev2 = new AmazonAlexa("Alexa", 20.1, LocalDateTime.now().minusHours(1));
        IoTDevice dev3 = new AmazonAlexa("Lexa", 1.1, LocalDateTime.now().minusHours(1));
        IoTDevice dev4 = new WiFiThermostat("Lexa2", 19.1, LocalDateTime.now().minusHours(1));
        IoTDevice dev5 = new AmazonAlexa("Lexa4", 1.1, LocalDateTime.now().minusDays(2));
        IoTDevice dev6 = new AmazonAlexa("Lexa3", 1.1, LocalDateTime.now().minusHours(0));
        test.register(dev2);
        test.register(dev1);
        test.register(dev3);
        test.register(dev6);
        test.register(dev4);
        test.register(dev5);
        assertIterableEquals(List.of(dev5.getId() ,dev1.getId(), dev2.getId(), dev4.getId()), test.getTopNDevicesByPowerConsumption(4));
    }

    @Test
    void testGetFirstNDevicesByRegistrationNIsLowerThanTotalNumber() throws DeviceAlreadyRegisteredException, InterruptedException {
        IntelligentHomeCenter test = new IntelligentHomeCenter(new MapDeviceStorage());
        IoTDevice dev1 = new RgbBulb("Bulb", 1.0, LocalDateTime.now().minusDays(1));
        IoTDevice dev2 = new AmazonAlexa("Alexa", 20.1, LocalDateTime.now().minusHours(1));
        IoTDevice dev3 = new AmazonAlexa("Lexa", 1.1, LocalDateTime.now().minusHours(1));
        test.register(dev2);
        Thread.sleep(1000);
        test.register(dev1);
        Thread.sleep(1000);
        test.register(dev3);
        assertIterableEquals(List.of(dev2), test.getFirstNDevicesByRegistration(1));
    }

    @Test
    void testGetFirstNDevicesByRegistrationNegativeArgument() {
        IntelligentHomeCenter test = new IntelligentHomeCenter(new MapDeviceStorage());
        assertThrows(IllegalArgumentException.class, () -> test.getFirstNDevicesByRegistration(-1));
    }

    @Test
    void testGetFirstNDevicesByRegistrationZeroArgument() {
        IntelligentHomeCenter test = new IntelligentHomeCenter(new MapDeviceStorage());
        assertIterableEquals(List.of(), test.getFirstNDevicesByRegistration(0));
    }

    @Test
    void testGetFirstNDevicesByRegistrationIsWorking() throws DeviceAlreadyRegisteredException {
        IntelligentHomeCenter test = new IntelligentHomeCenter(new MapDeviceStorage());
        IoTDevice dev1 = new RgbBulb("Bulb", 1.0, LocalDateTime.now().minusDays(1));
        IoTDevice dev2 = new AmazonAlexa("Alexa", 20.1, LocalDateTime.now().minusHours(1));
        test.register(dev2);
        test.register(dev1);
        assertIterableEquals(List.of(dev2, dev1), test.getFirstNDevicesByRegistration(2));
    }
}
