package the.flash.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author huzhijian
 * @version 1.0
 * @Date 2019/10/31 16:48
 */
public class NettyClient {
    private static int MAX_Retry=5;
    public static void main(String[] args) {
        NioEventLoopGroup workerGroup=new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new FirstClientHander());
                    }
                });
        connect(bootstrap,"127.0.0.1",8000,MAX_Retry);
    }
    private static void connect(Bootstrap bootstrap, final String host, final int port, final int retry){
        bootstrap.connect(host,port).addListener(future->{
            if(future.isSuccess())
                System.out.println("connect"+host+port);
            else if(retry==0){
                System.err.println("connect fail");
            }
            else{
                int order=(MAX_Retry-retry)+1;
                int delay=1<<order;
                System.err.println(new Date()+": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit
                        .SECONDS);
            }
        });
    }

}
