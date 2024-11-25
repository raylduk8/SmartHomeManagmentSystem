import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class SmartHomeManagementSystem {
    /* Constants to create instances of each class */
    static final int LIGHTS = 4;
    static final int CAMERAS = 6;
    static final int HEATERS = 10;
    static final int INITIAL_ANGLE = 45;
    static final int INITIAL_TEMP = 20;

    public static void main(String[] args) {
        /* A map of all devices and their indices */
        Map<Integer, SmartDevice> devices = new HashMap<>();

        /* Creating lights, cameras, and heaters */
        for (int i = 0; i < LIGHTS; i++) {
            Light light = new Light(i, Status.ON, "Light", false, BrightnessLevel.LOW, LightColor.YELLOW);
            devices.put(i, light);
        }
        for (int i = LIGHTS; i < CAMERAS; i++) {
            Camera camera = new Camera(i, Status.ON, "Camera", false, false, INITIAL_ANGLE);
            devices.put(i, camera);
        }
        for (int i = CAMERAS; i < HEATERS; i++) {
            Heater heater = new Heater(i, Status.ON, "Heater", INITIAL_TEMP);
            devices.put(i, heater);
        }

        /* Reading commands */
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();

            if (input.equals("end")) {
                break;
            }
            commands(input, devices);
        }
    }

    public void displayAllStatus(Map<Integer, SmartDevice> devices) {
        for (Map.Entry<Integer, SmartDevice> entry : devices.entrySet()) {
            SmartDevice device = entry.getValue();

            if (device instanceof Light) {
                System.out.println(device.displayStatus());
            } else if (device instanceof Camera) {
                System.out.println(device.displayStatus());
            } else if (device instanceof Heater) {
                System.out.println(device.displayStatus());
            }
        }
    }

    public void turnOn(Map<Integer, SmartDevice> devices, String deviceName, int deviceId) {
        SmartDevice device = devices.get(deviceId);

        if (!(devices.containsKey(deviceId) && deviceName.equals(device.getDeviceName()))) {
            try {
                throw new Errors.DeviceNotFound();
            } catch (Errors.DeviceNotFound e) {
                System.out.println(e.getMessage());
            }
        } else if (device.checkStatusAccess()) {
            try {
                throw new Errors.IsAlreadyOnException(deviceName, deviceId);
            } catch (Errors.IsAlreadyOnException e) {
                System.out.println(e.getMessage());
            }
        } else {
            device.turnOn();
            System.out.println(deviceName + " " + deviceId + " is on");
        }
    }

    public void turnOff(Map<Integer, SmartDevice> devices, String deviceName, int deviceId) {
        SmartDevice device = devices.get(deviceId);
        if (!(devices.containsKey(deviceId) && deviceName.equals(device.getDeviceName()))) {
            try {
                throw new Errors.DeviceNotFound();
            } catch (Errors.DeviceNotFound e) {
                System.out.println(e.getMessage());
            }
        } else if (!device.checkStatusAccess()) {
            try {
                throw new Errors.IsAlreadyOffException(deviceName, deviceId);
            } catch (Errors.IsAlreadyOffException e) {
                System.out.println(e.getMessage());
            }
        } else {
            device.turnOff();
            System.out.println(deviceName + " " + deviceId + " is off");
        }
    }

    public void startCharging(Map<Integer, SmartDevice> devices, String deviceName, int deviceId) {
        SmartDevice device = devices.get(deviceId);

        if (!(devices.containsKey(deviceId) && deviceName.equals(device.getDeviceName()))) {
            try {
                throw new Errors.DeviceNotFound();
            } catch (Errors.DeviceNotFound e) {
                System.out.println(e.getMessage());
            }
        } else if (device instanceof Heater) {
            try {
                throw new Errors.DeviceIsNotChargeable(deviceName, deviceId);
            } catch (Errors.DeviceIsNotChargeable e) {
                System.out.println(e.getMessage());
            }
        } else if (device instanceof Light) {
            if (((Light) device).isCharging()) {
                try {
                    throw new Errors.IsAlreadyChargingException(deviceName, deviceId);
                } catch (Errors.IsAlreadyChargingException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                ((Light) device).startCharging();
                System.out.println(deviceName + " " + deviceId + " is charging");
            }

        } else if (device instanceof Camera) {
            if (((Camera) device).isCharging()) {
                try {
                    throw new Errors.IsAlreadyChargingException(deviceName, deviceId);
                } catch (Errors.IsAlreadyChargingException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                ((Camera) device).startCharging();
                System.out.println(deviceName + " " + deviceId + " is charging");
            }
        }
    }

    public void stopCharging(Map<Integer, SmartDevice> devices, String deviceName, int deviceId) {
        SmartDevice device = devices.get(deviceId);

        if (!(devices.containsKey(deviceId) && deviceName.equals(device.getDeviceName()))) {
            try {
                throw new Errors.DeviceNotFound();
            } catch (Errors.DeviceNotFound e) {
                System.out.println(e.getMessage());
            }
        } else if (device instanceof Heater) {
            try {
                throw new Errors.DeviceIsNotChargeable(deviceName, deviceId);
            } catch (Errors.DeviceIsNotChargeable e) {
                System.out.println(e.getMessage());
            }
        } else if (device instanceof Light) {
            if (!((Light) device).isCharging()) {
                try {
                    throw new Errors.IsNotChargingException(deviceName, deviceId);
                } catch (Errors.IsNotChargingException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                ((Light) device).stopCharging();
                System.out.println(deviceName + " " + deviceId + " stopped charging");
            }
        } else if (device instanceof Camera) {
            if (!((Camera) device).isCharging()) {
                try {
                    throw new Errors.IsNotChargingException(deviceName, deviceId);
                } catch (Errors.IsNotChargingException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                ((Camera) device).stopCharging();
                System.out.println(deviceName + " " + deviceId + " stopped charging");
            }
        }
    }

    public void setTemperature(Map<Integer, SmartDevice> devices, String deviceName, int deviceId, int temperature) {
        SmartDevice device = devices.get(deviceId);

        if (!(devices.containsKey(deviceId) && deviceName.equals(device.getDeviceName()))) {
            try {
                throw new Errors.DeviceNotFound();
            } catch (Errors.DeviceNotFound e) {
                System.out.println(e.getMessage());
            }
        } else if (!device.checkStatusAccess()) {
            try {
                throw new Errors.ChangingOffDevice(deviceName, deviceId);
            } catch (Errors.ChangingOffDevice e) {
                System.out.println(e.getMessage());
            }
        } else if (!(device instanceof Heater)) {
            try {
                throw new Errors.NonHeaterDeviceException(deviceName, deviceId);
            } catch (Errors.NonHeaterDeviceException e) {
                System.out.println(e.getMessage());
            }
        } else if (!((Heater) device).setTemperature(temperature)) {
            try {
                throw new Errors.TemperatureOutOfRange(deviceId);
            } catch (Errors.TemperatureOutOfRange e) {
                System.out.println(e.getMessage());
            }
        } else {
            ((Heater) device).setTemperature(temperature);
            System.out.println(deviceName + " " + deviceId + " temperature is set to " + temperature);
        }
    }

    public void setBrightness(Map<Integer, SmartDevice> devices, String deviceName,
                              int deviceId, String brightnessString) {
        SmartDevice device = devices.get(deviceId);

        if (!(devices.containsKey(deviceId) && deviceName.equals(device.getDeviceName()))) {
            try {
                throw new Errors.DeviceNotFound();
            } catch (Errors.DeviceNotFound e) {
                System.out.println(e.getMessage());
            }
        } else if (!device.checkStatusAccess()) {
            try {
                throw new Errors.ChangingOffDevice(deviceName, deviceId);
            } catch (Errors.ChangingOffDevice e) {
                System.out.println(e.getMessage());
            }
        } else if (!(device instanceof Light)) {
            try {
                throw new Errors.NonLightDeviceException(deviceName, deviceId);
            } catch (Errors.NonLightDeviceException e) {
                System.out.println(e.getMessage());
            }
        } else if (!(brightnessString.equals("LOW") || brightnessString.equals("MEDIUM")
                || brightnessString.equals("HIGH"))) {
            try {
                throw new Errors.BrightnessOutOfScope();
            } catch (Errors.BrightnessOutOfScope e) {
                System.out.println(e.getMessage());
            }
        } else {
            Light lightDevice = (Light) device;
            lightDevice.setBrightnessLevel(BrightnessLevel.valueOf(brightnessString));
            System.out.println(deviceName + " " + deviceId + " brightness level is set to " + brightnessString);
        }
    }

    public void setColor(Map<Integer, SmartDevice> devices, String deviceName, int deviceId, String colorString) {
        SmartDevice device = devices.get(deviceId);

        if (!(devices.containsKey(deviceId) && deviceName.equals(device.getDeviceName()))) {
            try {
                throw new Errors.DeviceNotFound();
            } catch (Errors.DeviceNotFound e) {
                System.out.println(e.getMessage());
            }
        } else if (!device.checkStatusAccess()) {
            try {
                throw new Errors.ChangingOffDevice(deviceName, deviceId);
            } catch (Errors.ChangingOffDevice e) {
                System.out.println(e.getMessage());
            }
        } else if (!(device instanceof Light)) {
            try {
                throw new Errors.NonLightDeviceException(deviceName, deviceId);
            } catch (Errors.NonLightDeviceException e) {
                System.out.println(e.getMessage());
            }
        } else if (!(colorString.equals("YELLOW") || colorString.equals("WHITE"))) {
            try {
                throw new Errors.ColorOutOfScope();
            } catch (Errors.ColorOutOfScope e) {
                System.out.println(e.getMessage());
            }
        } else {
            Light lightDevice = (Light) device;
            lightDevice.setLightColor(LightColor.valueOf(colorString));
            System.out.println(deviceName + " " + deviceId + " color is set to " + colorString);
        }
    }

    public void setAngle(Map<Integer, SmartDevice> devices, String deviceName, int deviceId, int angle) {
        SmartDevice device = devices.get(deviceId);

        if (!(devices.containsKey(deviceId) && deviceName.equals(device.getDeviceName()))) {
            try {
                throw new Errors.DeviceNotFound();
            } catch (Errors.DeviceNotFound e) {
                System.out.println(e.getMessage());
            }
        } else if (!device.checkStatusAccess()) {
            try {
                throw new Errors.ChangingOffDevice(deviceName, deviceId);
            } catch (Errors.ChangingOffDevice e) {
                System.out.println(e.getMessage());
            }
        } else if (!(device instanceof Camera)) {
            try {
                throw new Errors.NonCameraDeviceException(deviceName, deviceId);
            } catch (Errors.NonCameraDeviceException e) {
                System.out.println(e.getMessage());
            }
        } else if (!((Camera) device).setCameraAngle(angle)) {
            try {
                throw new Errors.AngleOutOfRange(deviceId);
            } catch (Errors.AngleOutOfRange e) {
                System.out.println(e.getMessage());
            }
        } else {
            ((Camera) device).setCameraAngle(angle);
            System.out.println(deviceName + " " + deviceId + " angle is set to " + angle);
        }
    }

    public void startRecording(Map<Integer, SmartDevice> devices, String deviceName, int deviceId) {
        SmartDevice device = devices.get(deviceId);

        if (!(devices.containsKey(deviceId) && deviceName.equals(device.getDeviceName()))) {
            try {
                throw new Errors.DeviceNotFound();
            } catch (Errors.DeviceNotFound e) {
                System.out.println(e.getMessage());
            }
        } else if (!device.checkStatusAccess()) {
            try {
                throw new Errors.ChangingOffDevice(deviceName, deviceId);
            } catch (Errors.ChangingOffDevice e) {
                System.out.println(e.getMessage());
            }
        } else if (!(device instanceof Camera)) {
            try {
                throw new Errors.NonCameraDeviceException(deviceName, deviceId);
            } catch (Errors.NonCameraDeviceException e) {
                System.out.println(e.getMessage());
            }
        } else if (((Camera) device).isRecording()) {
            try {
                throw new Errors.IsAlreadyRecordingExceptions(deviceName, deviceId);
            } catch (Errors.IsAlreadyRecordingExceptions e) {
                System.out.println(e.getMessage());
            }
        } else {
            ((Camera) device).startRecording();
            System.out.println(deviceName + " " + deviceId + " started recording");
        }
    }

    public void stopRecording(Map<Integer, SmartDevice> devices, String deviceName, int deviceId) {
        SmartDevice device = devices.get(deviceId);

        if (!(devices.containsKey(deviceId) && deviceName.equals(device.getDeviceName()))) {
            try {
                throw new Errors.DeviceNotFound();
            } catch (Errors.DeviceNotFound e) {
                System.out.println(e.getMessage());
            }
        } else if (!device.checkStatusAccess()) {
            try {
                throw new Errors.ChangingOffDevice(deviceName, deviceId);
            } catch (Errors.ChangingOffDevice e) {
                System.out.println(e.getMessage());
            }
        } else if (!(device instanceof Camera)) {
            try {
                throw new Errors.NonCameraDeviceException(deviceName, deviceId);
            } catch (Errors.NonCameraDeviceException e) {
                System.out.println(e.getMessage());
            }
        } else if (!((Camera) device).isRecording()) {
            try {
                throw new Errors.IsNotRecordingException(deviceName, deviceId);
            } catch (Errors.IsNotRecordingException e) {
                System.out.println(e.getMessage());
            }
        } else {
            ((Camera) device).stopRecording();
            System.out.println(deviceName + " " + deviceId + " stopped recording");
        }
    }

    public static void commands(String input, Map<Integer, SmartDevice> devices) {
        String[] parts = input.split(" ");
        String commandName = parts[0];

        switch (commandName) {
            case "TurnOn": {
                try {
                    /* The number of fields is incorrect */
                    if (parts.length != 3) {
                        throw new Errors.InvalidCommandException();
                    }
                    /* Device ID is not a number */
                    for (int i = 0; i < parts[2].length(); i++) {
                        if (parts[2].charAt(i) != '-' && (parts[2].charAt(i) < '0' || parts[2].charAt(i) > '9')) {
                            throw new Errors.InvalidCommandException();
                        }
                    }
                } catch (Errors.InvalidCommandException e) {
                    System.out.println(e.getMessage());
                    break;
                }
                String deviceName = parts[1];
                int deviceId = Integer.parseInt(parts[2]);
                SmartHomeManagementSystem command = new SmartHomeManagementSystem();
                command.turnOn(devices, deviceName, deviceId);
                break;
            }
            case "TurnOff": {
                try {
                    /* The number of fields is incorrect */
                    if (parts.length != 3) {
                        throw new Errors.InvalidCommandException();
                    }
                    /* Device ID is not a number */
                    for (int i = 0; i < parts[2].length(); i++) {
                        if (parts[2].charAt(i) != '-' && (parts[2].charAt(i) < '0' || parts[2].charAt(i) > '9')) {
                            throw new Errors.InvalidCommandException();
                        }
                    }
                } catch (Errors.InvalidCommandException e) {
                    System.out.println(e.getMessage());
                    break;
                }
                String deviceName = parts[1];
                int deviceId = Integer.parseInt(parts[2]);
                SmartHomeManagementSystem command = new SmartHomeManagementSystem();
                command.turnOff(devices, deviceName, deviceId);
                break;
            }
            case "StartCharging": {
                try {
                    /* The number of fields is incorrect */
                    if (parts.length != 3) {
                        throw new Errors.InvalidCommandException();
                    }
                    /* Device ID is not a number */
                    for (int i = 0; i < parts[2].length(); i++) {
                        if (parts[2].charAt(i) != '-' && (parts[2].charAt(i) < '0' || parts[2].charAt(i) > '9')) {
                            throw new Errors.InvalidCommandException();
                        }
                    }
                } catch (Errors.InvalidCommandException e) {
                    System.out.println(e.getMessage());
                    break;
                }
                String deviceName = parts[1];
                int deviceId = Integer.parseInt(parts[2]);
                SmartHomeManagementSystem command = new SmartHomeManagementSystem();
                command.startCharging(devices, deviceName, deviceId);
                break;
            }
            case "StopCharging": {
                try {
                    /* The number of fields is incorrect */
                    if (parts.length != 3) {
                        throw new Errors.InvalidCommandException();
                    }
                    /* Device ID is not a number */
                    for (int i = 0; i < parts[2].length(); i++) {
                        if (parts[2].charAt(i) != '-' && (parts[2].charAt(i) < '0' || parts[2].charAt(i) > '9')) {
                            throw new Errors.InvalidCommandException();
                        }
                    }
                } catch (Errors.InvalidCommandException e) {
                    System.out.println(e.getMessage());
                    break;
                }
                String deviceName = parts[1];
                int deviceId = Integer.parseInt(parts[2]);
                SmartHomeManagementSystem command = new SmartHomeManagementSystem();
                command.stopCharging(devices, deviceName, deviceId);
                break;
            }
            case "SetTemperature": {
                try {
                    /* The number of fields is incorrect */
                    if (parts.length != 4) {
                        throw new Errors.InvalidCommandException();
                    }
                    /* Device ID is not a number */
                    for (int i = 0; i < parts[2].length(); i++) {
                        if (parts[2].charAt(i) != '-' && (parts[2].charAt(i) < '0' || parts[2].charAt(i) > '9')) {
                            throw new Errors.InvalidCommandException();
                        }
                    }
                } catch (Errors.InvalidCommandException e) {
                    System.out.println(e.getMessage());
                    break;
                }
                String deviceName = parts[1];
                int deviceId = Integer.parseInt(parts[2]);
                int temperature = Integer.parseInt(parts[3]);
                SmartHomeManagementSystem command = new SmartHomeManagementSystem();
                command.setTemperature(devices, deviceName, deviceId, temperature);
                break;
            }
            case "SetBrightness": {
                try {
                    /* The number of fields is incorrect */
                    if (parts.length != 4) {
                        throw new Errors.InvalidCommandException();
                    }
                    /* Device ID is not a number */
                    for (int i = 0; i < parts[2].length(); i++) {
                        if (parts[2].charAt(i) != '-' && (parts[2].charAt(i) < '0' || parts[2].charAt(i) > '9')) {
                            throw new Errors.InvalidCommandException();
                        }
                    }
                } catch (Errors.InvalidCommandException e) {
                    System.out.println(e.getMessage());
                    break;
                }
                String deviceName = parts[1];
                int deviceId = Integer.parseInt(parts[2]);
                String brightnessString = parts[3];
                SmartHomeManagementSystem command = new SmartHomeManagementSystem();
                command.setBrightness(devices, deviceName, deviceId, brightnessString);
                break;
            }
            case "SetColor": {
                try {
                    /* The number of fields is incorrect */
                    if (parts.length != 4) {
                        throw new Errors.InvalidCommandException();
                    }
                    /* Device ID is not a number */
                    for (int i = 0; i < parts[2].length(); i++) {
                        if (parts[2].charAt(i) != '-' && (parts[2].charAt(i) < '0' || parts[2].charAt(i) > '9')) {
                            throw new Errors.InvalidCommandException();
                        }
                    }
                } catch (Errors.InvalidCommandException e) {
                    System.out.println(e.getMessage());
                    break;
                }
                String deviceName = parts[1];
                int deviceId = Integer.parseInt(parts[2]);
                String colorString = parts[3];
                SmartHomeManagementSystem command = new SmartHomeManagementSystem();
                command.setColor(devices, deviceName, deviceId, colorString);
                break;
            }
            case "SetAngle": {
                try {
                    /* The number of fields is incorrect */
                    if (parts.length != 4) {
                        throw new Errors.InvalidCommandException();
                    }
                    /* Device ID is not a number */
                    for (int i = 0; i < parts[2].length(); i++) {
                        if (parts[2].charAt(i) != '-' && (parts[2].charAt(i) < '0' || parts[2].charAt(i) > '9')) {
                            throw new Errors.InvalidCommandException();
                        }
                    }
                } catch (Errors.InvalidCommandException e) {
                    System.out.println(e.getMessage());
                    break;
                }
                String deviceName = parts[1];
                int deviceId = Integer.parseInt(parts[2]);
                int angle = Integer.parseInt(parts[3]);
                SmartHomeManagementSystem command = new SmartHomeManagementSystem();
                command.setAngle(devices, deviceName, deviceId, angle);
                break;
            }
            case "StartRecording": {
                try {
                    /* The number of fields is incorrect */
                    if (parts.length != 3) {
                        throw new Errors.InvalidCommandException();
                    }
                    /* Device ID is not a number */
                    for (int i = 0; i < parts[2].length(); i++) {
                        if (parts[2].charAt(i) != '-' && (parts[2].charAt(i) < '0' || parts[2].charAt(i) > '9')) {
                            throw new Errors.InvalidCommandException();
                        }
                    }
                } catch (Errors.InvalidCommandException e) {
                    System.out.println(e.getMessage());
                    break;
                }
                String deviceName = parts[1];
                int deviceId = Integer.parseInt(parts[2]);
                SmartHomeManagementSystem command = new SmartHomeManagementSystem();
                command.startRecording(devices, deviceName, deviceId);
                break;
            }
            case "StopRecording": {
                try {
                    /* The number of fields is incorrect */
                    if (parts.length != 3) {
                        throw new Errors.InvalidCommandException();
                    }
                    /* Device ID is not a number */
                    for (int i = 0; i < parts[2].length(); i++) {
                        if (parts[2].charAt(i) != '-' && (parts[2].charAt(i) < '0' || parts[2].charAt(i) > '9')) {
                            throw new Errors.InvalidCommandException();
                        }
                    }
                } catch (Errors.InvalidCommandException e) {
                    System.out.println(e.getMessage());
                    break;
                }
                String deviceName = parts[1];
                int deviceId = Integer.parseInt(parts[2]);
                SmartHomeManagementSystem command = new SmartHomeManagementSystem();
                command.stopRecording(devices, deviceName, deviceId);
                break;
            }
            case "DisplayAllStatus": {
                try {
                    if (parts.length != 1) {
                        throw new Errors.InvalidCommandException();
                    }
                } catch (Errors.InvalidCommandException e) {
                    System.out.println(e.getMessage());
                    break;
                }
                SmartHomeManagementSystem command = new SmartHomeManagementSystem();
                command.displayAllStatus(devices);
                break;
            }
            default:
                System.out.println("Invalid command");
        }
    }
}

abstract class SmartDevice implements Controllable {
    private int deviceId;
    private int numberOfDevices = 10;
    private Status status;
    private String deviceName;

    public SmartDevice() {
        this.status = Status.ON;
    }

    public SmartDevice(int deviceId, Status status, String deviceName) {
        this.deviceId = deviceId;
        this.status = status;
        this.deviceName = deviceName;
    }

    public abstract String displayStatus();

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean turnOff() {
        if (status == Status.ON) {
            setStatus(Status.OFF);
            return true;
        }
        return false;
    }

    public boolean turnOn() {
        if (status == Status.ON) {
            return false;
        }
        setStatus(Status.ON);
        return true;
    }

    public boolean isOn() {
        return status == Status.ON;
    }

    public boolean checkStatusAccess() {
        return isOn();
    }
}

class Light extends SmartDevice implements Chargeable {
    private boolean charging;
    private BrightnessLevel brightnessLevel;
    private LightColor lightColor;

    public Light(int deviceId, Status status, String deviceName, boolean charging,
                 BrightnessLevel brightnessLevel, LightColor lightColor) {
        super(deviceId, status, deviceName);
        this.charging = charging;
        this.brightnessLevel = brightnessLevel;
        this.lightColor = lightColor;
    }

    public LightColor getLightColor() {
        return lightColor;
    }

    public void setLightColor(LightColor lightColor) {
        this.lightColor = lightColor;
    }

    public BrightnessLevel getBrightnessLevel() {
        return brightnessLevel;
    }

    public void setBrightnessLevel(BrightnessLevel brightnessLevel) {
        this.brightnessLevel = brightnessLevel;
    }

    @Override
    public boolean startCharging() {
        if (!charging) {
            charging = true;
        }
        return true;
    }

    @Override
    public boolean stopCharging() {
        if (charging) {
            charging = false;
        }
        return false;
    }

    @Override
    public boolean isCharging() {
        return charging;
    }

    @Override
    public String displayStatus() {
        return "Light " + getDeviceId() + " is " + getStatus() + ", the color is " + getLightColor()
                + ", the charging status is " + isCharging() + ", and the brightness level is " + getBrightnessLevel() + ".";
    }
}

class Camera extends SmartDevice implements Chargeable {
    static final int MIN_CAMERA_ANGLE = -60;
    static final int MAX_CAMERA_ANGLE = 60;

    private boolean charging;
    private boolean recording;
    private int angle;

    public Camera(int deviceId, Status status, String deviceName, boolean charging, boolean recording, int angle) {
        super(deviceId, status, deviceName);
        this.charging = charging;
        this.recording = recording;
        this.angle = angle;
    }

    public int getAngle() {
        return angle;
    }

    public boolean setCameraAngle(int angle) {
        if (angle < MIN_CAMERA_ANGLE || angle > MAX_CAMERA_ANGLE) {
            return false;
        } else {
            this.angle = angle;
            return true;
        }
    }

    public boolean startRecording() {
        if (!recording) {
            recording = true;
        }
        return true;
    }

    public boolean stopRecording() {
        if (recording) {
            recording = false;
        }
        return false;
    }

    public boolean isRecording() {
        return recording;
    }

    @Override
    public boolean startCharging() {
        if (!charging) {
            charging = true;
        }
        return true;
    }

    @Override
    public boolean stopCharging() {
        if (charging) {
            charging = false;
        }
        return false;
    }

    @Override
    public boolean isCharging() {
        return charging;
    }

    @Override
    public String displayStatus() {
        return "Camera " + getDeviceId() + " is " + getStatus() + ", the angle is " + getAngle()
                + ", the charging status is " + isCharging() + ", and  the recording status is " + isRecording() + ".";
    }
}

class Heater extends SmartDevice     {
    static final int MAX_TEMP = 30;
    static final int MIN_TEMP = 15;

    private int temperature;

    public Heater(int deviceId, Status status, String deviceName, int temperature) {
        super(deviceId, status, deviceName);
        this.temperature = temperature;
    }

    public int getTemperature() {
        return temperature;
    }

    public boolean setTemperature(int temperature) {
        if (temperature < MIN_TEMP || temperature > MAX_TEMP) {
            return false;
        }
        this.temperature = temperature;
        return true;
    }

    @Override
    public String displayStatus() {
        return "Heater " + getDeviceId() + " is " + getStatus()
                + " and the temperature is " + getTemperature() + ".";
    }
}

class Errors {

    public static class InvalidCommandException extends Exception {
        public InvalidCommandException() {
            super("Invalid command");
        }
    }

    public static class DeviceNotFound extends Exception {
        public DeviceNotFound() {
            super("The smart device was not found");
        }
    }

    public static class DeviceIsNotChargeable extends Exception {
        public DeviceIsNotChargeable(String deviceName, int deviceId) {
            super(deviceName + " " + deviceId + " is not chargeable");
        }
    }

    public static class NonHeaterDeviceException extends Exception {
        public NonHeaterDeviceException(String deviceName, int deviceId) {
            super(deviceName + " " + deviceId + " is not a heater");
        }
    }

    public static class BrightnessOutOfScope extends Exception {
        public BrightnessOutOfScope() {
            super("The brightness can only be one of \"LOW\", \"MEDIUM\", or \"HIGH\"");
        }
    }

    public static class NonLightDeviceException extends Exception {
        public NonLightDeviceException(String deviceName, int deviceId) {
            super(deviceName + " " + deviceId + " is not a lighter");
        }
    }

    public static class ColorOutOfScope extends Exception {
        public ColorOutOfScope() {
            super("The light color can only be \"YELLOW\" or \"WHITE\"");
        }
    }

    public static class NonCameraDeviceException extends Exception {
        public NonCameraDeviceException(String deviceName, int deviceId) {
            super(deviceName + " " + deviceId + " is not a camera");
        }
    }

    public static class IsAlreadyOffException extends Exception {
        public IsAlreadyOffException(String deviceName, int deviceId) {
            super(deviceName + " " + deviceId + " is already off");
        }
    }

    public static class IsAlreadyOnException extends Exception {
        public IsAlreadyOnException(String deviceName, int deviceId) {
            super(deviceName + " " + deviceId + " is already on");
        }
    }

    public static class ChangingOffDevice extends Exception {
        public ChangingOffDevice(String deviceName, int deviceId) {
            super("You can't change the status of the " + deviceName + " " + deviceId + " while it is off");
        }
    }

    public static class AngleOutOfRange extends Exception {
        public AngleOutOfRange(int deviceId) {
            super("Camera " + deviceId + " angle should be in the range [-60, 60]");
        }
    }

    public static class IsAlreadyRecordingExceptions extends Exception {
        public IsAlreadyRecordingExceptions(String deviceName, int deviceId) {
            super(deviceName + " " + deviceId + " is already recording");
        }
    }

    public static class IsNotRecordingException extends Exception {
        public IsNotRecordingException(String deviceName, int deviceId) {
            super(deviceName + " " + deviceId + " is not recording");
        }
    }

    public static class IsAlreadyChargingException extends Exception {
        public IsAlreadyChargingException(String deviceName, int deviceId) {
            super(deviceName + " " + deviceId + " is already charging");
        }
    }

    public static class IsNotChargingException extends Exception {
        public IsNotChargingException(String deviceName, int deviceId) {
            super(deviceName + " " + deviceId + " is not charging");
        }
    }

    public static class TemperatureOutOfRange extends Exception {
        public TemperatureOutOfRange(int deviceId) {
            super("Heater " + deviceId + " temperature should be in the range [15, 30]");
        }
    }
}

enum BrightnessLevel {
    HIGH,
    MEDIUM,
    LOW
}

interface Chargeable {
    boolean isCharging();
    boolean startCharging();
    boolean stopCharging();
}

interface Controllable {
    boolean turnOff();
    boolean turnOn();
    boolean isOn();
}

enum LightColor {
    WHITE,
    YELLOW;
}

enum Status {
    ON,
    OFF
}