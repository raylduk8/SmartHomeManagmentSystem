# SmartHomeManagmentSystem

This project represents the management of a smart home system. There are 4 lamps, 2 cameras and 4 heaters available. 
The task offers the execution of several commands for managing these devices, and also implies checking the input data.

# Available commands 

- DisplayAllStatus
- TurnOn {deviceName} {deviceId}
- TurnOff {deviceName} {deviceId}
- StartCharging {deviceName} {deviceId}
- StopCharging {deviceName} {deviceId}
- SetTemperature {deviceName} {deviceId} {temperature}
- SetBrightness {deviceName} {deviceId} {brightnessLevel}
- SetColor {deviceName} {deviceId} {color}
- SetAngle {deviceName} {deviceId} {angle}
- StartRecording {deviceName} {deviceId}
- StopRecording {deviceName} {deviceId}
- end

# How to use

Each device has two states: ON and OFF.

- Lights: The color of light might be changed to WHITE or YELLOW. The brightness can be changed to LOW, MEDIUM and HIGH. Lights might be charging or not. 
- Cameras: The angle of camera might be changed in range of integer values from -60 to 60. Cameras can record or not. Cameras might be charging or not.
- Heaters: The temperature might be changed in range of integer values from 15 to 30. 

Initial state:

- Lights: Status: ON, Color: YELLOW, Brightness: LOW, not charging
- Cameras: Status ON, Angle: 45, not recording, not charging
- Heaters: Status ON, Temperature: 20
  
# Rules of usage

- When any device is turned OFF, adjustments to any attributes are restricted except the charging status attribute for Lights and Cameras.
- The lights can only be set to either YELLOW or WHITE colors.
- Brightness levels can be categorized as LOW, MEDIUM, or HIGH.
- Each camera's angle can be adjusted independently within a vert

Device initializing:

- Lights have ID's from 0 to 3
- Cameras have ID's 4 and 5
- Heaters have ID's from 6 to 9
