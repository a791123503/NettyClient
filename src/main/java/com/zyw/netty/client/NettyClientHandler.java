package com.zyw.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author zengyw
 * @version V1.0.0
 * @description
 * @date: 20:52 2020/6/25.
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Netty client register channel handler");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Netty client unregister channel handler");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String msg = "client was active";
        System.out.println("Netty client channel was active: " + msg);
        ctx.writeAndFlush(msg);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Netty client channel was inactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 强制转换为String类型对象
        String req = (String) msg;
        System.out.println("Netty client channel read the received message: " + req);
        ctx.channel().writeAndFlush(Unpooled.copiedBuffer(("Netty client response => " + req).getBytes()));
//        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Netty client channel read complete");
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // IdleStateHandler 是实现心跳的关键, 它会根据不同的 IO idle 类型来产生不同的 IdleStateEvent 事件
        // 而这个事件的捕获, 其实就是在 userEventTriggered 方法中实现的.
        System.out.println("Netty client userEventTriggered");
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        // channel写状态变化事件
        System.out.println("Netty client channelWritabilityChanged");
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Netty client exceptionCaught: " + cause.getMessage());
        super.exceptionCaught(ctx, cause);
    }

    @Override
    protected void ensureNotSharable() {
        // 保证该handler为非共享的，该方法为非共享handler的处理逻辑
        System.out.println("Netty client ensureNotSharable");
        super.ensureNotSharable();
    }

    @Override
    public boolean isSharable() {
        // 判断这个handler是否为共享的
        System.out.println("Netty client isSharable");
        return super.isSharable();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // 当前handler被加入pipeline时触发
        System.out.println("Netty client handlerAdded");
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 当前handler被移除pipeline时触发
        System.out.println("Netty client handlerRemoved");
        super.handlerRemoved(ctx);
    }
}
