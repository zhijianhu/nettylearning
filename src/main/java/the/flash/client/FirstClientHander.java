package the.flash.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author huzhijian
 * @version 1.0
 * @Date 2019/10/31 20:14
 */
public class FirstClientHander extends ChannelInboundHandlerAdapter {
    public void channelActive(ChannelHandlerContext ctx){
        System.out.println(new Date()+":client say");
        ByteBuf byteBuf=getByteBuf(ctx);
        ctx.channel().writeAndFlush(byteBuf);
    }
    public ByteBuf getByteBuf(ChannelHandlerContext ctx){
        ByteBuf byteBuf=ctx.alloc().buffer();
        byte[] bytes="hello".getBytes(Charset.forName("utf-8"));
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        ByteBuf byteBuf=(ByteBuf)msg;
        System.out.println(new Date()+"client read ->"+byteBuf.toString(Charset.forName("utf-8")));
    }
}
