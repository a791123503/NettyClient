package com.zyw.netty;

import com.zyw.netty.client.NettyClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class NettyclientApplication {
    private NettyClient nettyClient = null;

    public static void main(String[] args) {
        SpringApplication.run(NettyclientApplication.class, args);
    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public String startBind(@RequestParam("host") String host, @RequestParam("port") int port) {
        System.out.println("Start client bind with 【" + host + "】==> 【"+ port + "】");
        nettyClient = new NettyClient(host,port);
        nettyClient.start();
        return "success";
    }

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String send(@RequestParam("msg") String msg) {
        // if 后期改用assert取代
        if (null != nettyClient) {
            nettyClient.send(msg);
        }
        return msg;
    }
}
