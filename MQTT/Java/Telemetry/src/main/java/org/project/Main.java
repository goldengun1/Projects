package org.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {


    public static Map<String , Map> global_map = new HashMap<>();

    public static void main(String[] args) throws MqttException {

        //INITIALIZATION
        String address = "207.180.244.242";
        String port = "1882";
        String protocol = "tcp://";
        String broker = protocol + address + ":" + port;
        String topic = "v1/devices/me/telemetry";
        String clientID = "test_client";
        System.out.println(broker);
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
                System.out.println(msg);

                ObjectMapper parser = new ObjectMapper();
                Map<String,Double> tmp = parser.readValue(msg,Map.class);

                //temp values
                //TODO

                for(Map.Entry<String,Double> e : tmp.entrySet()){
                    System.out.println(e.getKey()+" : "+e.getValue());
                    //System.out.println(e.getKey().getClass());
                    //System.out.println(e.getValue());
                }

                //TODO
                //spliting name into apt_No, and sensor_name
                //int x = name.lastIndexOf('_');
                //String apt_No = name.substring(x);
                //String sensor_name = name.substring(0,x-1);
                //System.out.println(apt_No + " : " + sensor_name);


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


            }

        }
        catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }

        //try {
        //    client1.wait(5);
        //}
        //catch (IllegalMonitorStateException | InterruptedException e){
        //    System.out.println(e.getMessage());
        //}

        //disconnecting
        client1.disconnect();

    }
}
