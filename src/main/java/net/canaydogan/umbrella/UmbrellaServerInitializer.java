package net.canaydogan.umbrella;

import net.canaydogan.umbrella.handler.HttpHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

class UmbrellaServerInitializer extends ChannelInitializer<SocketChannel>  {
	
	protected HttpHandler httpHandler;
	
	public UmbrellaServerInitializer(HttpHandler httpHandler) {
		this.httpHandler = httpHandler;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// Create a default pipeline implementation.
        ChannelPipeline p = ch.pipeline();

        // Uncomment the following line if you want HTTPS
        //SSLEngine engine = SecureChatSslContextFactory.getServerContext().createSSLEngine();
        //engine.setUseClientMode(false);
        //p.addLast("ssl", new SslHandler(engine));

        p.addLast("decoder", new HttpRequestDecoder());
        
        p.addLast("aggregator", new HttpObjectAggregator(65536));
        
        // Uncomment the following line if you don't want to handle HttpChunks.
        //p.addLast("aggregator", new HttpObjectAggregator(1048576));
        p.addLast("encoder", new HttpResponseEncoder());
        
        p.addLast("chunkedWriter", new ChunkedWriteHandler());
        
        // Remove the following line if you don't want automatic content compression.
        //p.addLast("deflater", new HttpContentCompressor());
        p.addLast("handler", new UmbrellaServerHandler(httpHandler));		
	}

}
