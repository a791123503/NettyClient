package com.zyw.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledDirectByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author zengyw
 * @version V1.0.0
 * @description
 * @date: 21:41 2020/6/16.
 */
public class NettyClient extends Thread {
    private String host;
    private Integer port;

    private Bootstrap bootstrap = new Bootstrap();
//    private EventLoopGroup group = new EpollEventLoopGroup();
    private EventLoopGroup group = new NioEventLoopGroup();
    private ChannelFuture future = null;

    public NettyClient(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        startClient();
    }

    private void startClient() {
        try {
            bootstrap.group(group)
                // 使用NioSocketChannel来作为连接用的channel类
                .channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());
            //发起异步连接请求，绑定连接端口和host信息
            future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void stopClient() {
        // 优雅退出
        group.shutdownGracefully();
    }

    public void send(String msg) {
        System.out.println("Netty client send message: " + msg);
        byte[] bytes = msg.getBytes();
        future.channel().writeAndFlush(bytes);
    }
}
