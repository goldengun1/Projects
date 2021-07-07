import json
import time
import paho.mqtt.client as mqtt

def on_recieve(client,userdata,message):
    print("poruka stigla:")
    print(message.payload)

address = "207.180.244.242"
client1 = mqtt.Client("CL1")
client1.on_message = on_recieve
client1.connect(address,port = 1882)

client1.loop_start()
client1.subscribe("v1/devices/me/telemetry")
time.sleep(5)

client1.loop_end()

