package com.springclient.springclient;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableApolloConfig
//@EnableDiscoveryClient
//@RefreshScope
public class SpringclientApplication extends SpringBootServletInitializer {

    Logger logger = LoggerFactory.getLogger(SpringclientApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(SpringclientApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder springApplicationBuilder){
        return springApplicationBuilder.sources(SpringclientApplication.class);
    }
    @Value("${server.port}")
    String port;

//    @Value("${foo}")
//    String foo;

    @RequestMapping("/hi")
    public String home(@RequestParam(value = "name", defaultValue = "forezp") String name) {
//        return "hi " + name + " ,i am from port:" + port + foo;
        logger.info("hi " + name + " ,i am from port:" + port);
        return "hi " + name + " ,i am from port:" + port ;
    }

    @RequestMapping("/info")
    public String info(){
        logger.info("calling trace service-hi ");

        return "i'm service-hi";

    }

    @RequestMapping("/setStatus")
    public String setStatus(String status){
        System.out.println("calling trace service-hi ");
        serviceRegistry.setStatus(registration, status);
        return "i'm service-hi";

    }

    @Value("${server.servlet.context-path}")
    String path;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Resource
    Registration registration;
    @Resource
    ServiceRegistry serviceRegistry;

    @PostConstruct
    public void down()  {
        System.out.println("-------------------down-------------");
//        System.out.println(JSON.toJSONString(discoveryClient.getServices()));
//        DiscoveryManager.getInstance().shutdownComponent();

        System.out.println("--------------------------------");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(5000L);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                String url = "http://localhost:"+port+path+"/actuator/service-registry";
//                System.out.println(url);
////        String url = "http://localhost:8089/springclient/actuator/service-registry";
//                JSONObject params = new JSONObject();
//                try {
//                    params.put("status", "DOWN");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                HashMap<String,String> header = new HashMap<>();
//                header.put("Content-Type","application/vnd.spring-boot.actuator.v2+json;charset=UTF-8");
//                String res = HttpKit.post(url,null,params.toString(),header);
//                System.out.println(res);
//            }
//        }).start();

    }

//    public static void main(String args[]) throws JSONException {
//        String url = "http://localhost:8089/springclient/actuator/service-registry";
//        JSONObject params = new JSONObject();
//        params.put("status", "DOWN");
//        HashMap<String,String> header = new HashMap<>();
//        header.put("Content-Type","application/vnd.spring-boot.actuator.v2+json;charset=UTF-8");
//        String res = com.jfinal.kit.HttpKit.post(url,null,params.toString(),header);
//        System.out.println(res);
//    }


}
