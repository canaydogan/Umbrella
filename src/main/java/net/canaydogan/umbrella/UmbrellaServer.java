package net.canaydogan.umbrella;

import net.canaydogan.umbrella.handler.HttpHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class UmbrellaServer {

	protected int port;
	
	protected HttpHandler httpHandler;
	
	public UmbrellaServer run() throws InterruptedException {
        // Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .childHandler(new UmbrellaServerInitializer(getHttpHandler()));

            Channel ch = b.bind(getPort()).sync().channel();
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
        
		return this;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public HttpHandler getHttpHandler() {
		return httpHandler;
	}

	public void setHttpHandler(HttpHandler httpHandler) {
		this.httpHandler = httpHandler;
	}
	
}
