package the.flash.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author huzhijian
 * @version 1.0
 * @Date 2019/11/1 9:37
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {
    public void  channelRead(ChannelHandlerContext ctx,Object msg){
        ByteBuf buf=(ByteBuf)msg;
        System.out.println(new Date()+"server read ->"+buf.toString(Charset.forName("utf-8")));
        System.out.println(new Date()+"server write ->");
        ByteBuf byteBuf=getBytebuf(ctx);
        ctx.channel().writeAndFlush(byteBuf);
    }
    private ByteBuf getBytebuf(ChannelHandlerContext ctx){
        byte[] bytes="hello,client".getBytes(Charset.forName("utf-8"));
        ByteBuf byteBuf=ctx.alloc().buffer();
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }
}
