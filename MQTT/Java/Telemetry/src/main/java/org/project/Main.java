package org.project;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {


    public static Map<String , Apartment> global_map = new HashMap<>();


    public static void main(String[] args) throws MqttException {

        //INITIALIZATION
        final String address = "207.180.244.242";
        final String port = "1882";
        final String protocol = "tcp://";
        final String topic = "v1/devices/me/telemetry";
        String broker = protocol + address + ":" + port;
        String clientID = "test_client";
        MqttClient client1 = new MqttClient(broker,clientID);

        //connect to broker and set a topic
        client1.connect();
        System.out.println("connected to broker: "+broker);
        client1.subscribe(topic);
        System.out.println("subscribing to topic: "+ topic);


        //creating callback functions for client
        client1.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("Connetcion to broker lost!\nReason: "+cause.getMessage());
                cause.printStackTrace();
                System.exit(1);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String msg = new String(message.getPayload());
                //System.out.println(msg);

                ObjectMapper parser = new ObjectMapper();
                Map<String,Double> tmp = parser.readValue(msg, new TypeReference<Map<String, Double>>() { });

                //temp values
                String name = "";
                Double value = (double) 0;

                for(Map.Entry<String,Double> e : tmp.entrySet()){
                    //System.out.println(e.getKey()+" : "+e.getValue());
                    name = e.getKey();
                    value = e.getValue();

                }

                //spliting name into apt_No, and sensor_name
                int x = name.lastIndexOf('_');
                String apt_No = name.substring(x+1);
                String sensor_name = name.substring(0,x);
                //System.out.println(apt_No + " : " + sensor_name+" : "+value);

                //TODO
                //fixing some of the values to be in the right range
                //voltages should be devided by 1000(sensor name starts with VF....)
                //Appartment temps should be devided by 10(sensor name is "temp")
                //ex. temp=256 -> temp = 25.6
                //ex. VF1_AN2= 239300.0 -> VF1_AN2= 239.300


                if(!global_map.containsKey(apt_No)){
                    Apartment apt = new Apartment(apt_No);
                    apt.update_stat(sensor_name,value);
                    global_map.put(apt_No,apt);
                }
                else {
                    global_map.get(apt_No).update_stat(sensor_name,value);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        try (Scanner sc = new Scanner(System.in)){
            String name;
            while(true){
                name = sc.next();
                if(name.equals("exit"))
                    break;
                //the name is the name of the location we want telemetry for
                else{
                    if(name.equals("all"))
                        print_map(null);
                    else
                        print_map(name);
                }


            }

        }
        catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }


        //disconnecting
        client1.disconnect();

    }

    private static void print_map(String name) {
        if(name != null)
        {
            if(global_map.containsKey(name))
                System.out.println(global_map.get(name));
            else
                System.out.println("NEMA PODATAKA!");
        }
        else {
            for(Map.Entry<String ,Apartment> entry : global_map.entrySet()) {
                System.out.println(entry.getValue());
            }
        }
    }
}
