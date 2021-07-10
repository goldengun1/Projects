import json
import os
import paho.mqtt.client as mqtt

global_map = dict()


def on_recieve(client,userdata,message):
    print("Message recieved:")
    niska = str(message.payload)

    new = niska[1:].strip("\'")
    data = json.loads(new)
    mapa = dict(data)
    key = None
    param = None

    for topic,value in mapa.items():
        (key,param) = make_pair(topic)
        #print("{} {}".format(key,param))

    del mapa

    #ubacujemo informacije u globalnu mapu ako postoji kljuc key
    if key in global_map:
        global_map[key][param] = value
    else:
    #pravimo mapu koju dodeljujemo kljucu key
        tmp = dict()
        tmp[param] = value
        global_map[key] = tmp

def on_connect(client, userdata, flags, rc):
    if rc != 0:
        print("connection failed")


def make_pair(topic):
    key = topic[topic.rindex("_")+1:]
    param = topic[:topic.rindex("_")]

    return(key,param)

def print_map(apts):
    for key,map in global_map.items():
        print("{} :".format(key))
        for param,value in map.items():
            print("\t{} : {}".format(param,value))
        print()

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
        x = int(input())
        if x == 0:
            break
        if x==1:
            os.system("clear")
            print_map(global_map)

    client1.loop_stop()
    #if int(input("unesi:")) == 0:
    #    client1.loop_stop()
    



if __name__ == "__main__":
    main()