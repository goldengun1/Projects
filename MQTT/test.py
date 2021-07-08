import json
import time
import paho.mqtt.client as mqtt

def on_recieve(client,userdata,message):
    print("Message recieved:")
    data = str(message.payload)

    new = data[1:].strip("\'")
    data = json.loads(new)
    print(new)
    print(data)
    print()
    #print(data)
    #print(new)
    #print(message.payload)

def on_connect(client, userdata, flags, rc):
    if rc == 0:
        print("connection success")
    else:
        print("connection failed")

def main():

    address = "207.180.244.242"
    client1 = mqtt.Client("CL1")
    client1.on_message = on_recieve
    client1.on_connect = on_connect
    client1.connect(address,port = 1882)

    #client1.loop_start()
    client1.subscribe("v1/devices/me/telemetry")
    #time.sleep(5)

    client1.loop_forever()
    #client1.loop_end()



if __name__ == "__main__":
    main()