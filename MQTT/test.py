import json
import time
import paho.mqtt.client as mqtt

global_names = set()
global_map = dict()


def on_recieve(client,userdata,message):
    print("Message recieved:")
    niska = str(message.payload)

    new = niska[1:].strip("\'")
    data = json.loads(new)

    mapa = dict(data)
    for k,v in mapa.items():
        if k not in global_names:
            global_names.add(k)
        
        global_map[k] = v


            
        
       # print("{} : {}".format(k,v))

    #print(new)
    #print(data)
    print()

def on_connect(client, userdata, flags, rc):
    if rc != 0:
        print("connection failed")

def napravi_par(kljuc,vrednost):
    #TODO
    pass

def main():

    address = "207.180.244.242"
    client1 = mqtt.Client("CL1")
    client1.on_message = on_recieve
    client1.on_connect = on_connect
    client1.connect(address,port = 1882)

    client1.subscribe("v1/devices/me/telemetry")
    client1.loop_start()
    #client1.loop_forever()
    
    while True:
        x = int(input("unesi: "))
        if x == 0:
            break
        if x==1:
            print(global_names)
            print()
            for k,v in global_map.items():
               print("{} : {}".format(k,v)) 

    client1.loop_stop()
    #if int(input("unesi:")) == 0:
    #    client1.loop_stop()
    



if __name__ == "__main__":
    main()