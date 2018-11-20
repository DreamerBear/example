package com.good.xuchao.example.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.oio.OioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * All rights Reserved, Designed By www.maihaoche.com
 *
 * @Package com.good.xuchao.example.netty
 * @author: 绪超（xuchao@maihaoche.com）
 * @date: 2018/10/22 上午10:10
 * @Copyright: 2017-2020 www.maihaoche.com Inc. All rights reserved.
 * 注意：本内容仅限于卖好车内部传阅，禁止外泄以及用于其他的商业目
 */
@Slf4j
public class ServerDemo {

    private final static int CORE_SIZE = Runtime.getRuntime().availableProcessors();

    private final static ThreadGroup THREAD_GROUP = new ThreadGroup("NETTY_PROCESS_GROUP");

    private final static AtomicInteger THREAD_COUNTER = new AtomicInteger(1);

    private final static Pattern COMMAND_PATTERN = Pattern.compile("^(\\w+?)\\s(.)*$");

    private final static ThreadPoolExecutor PROCESS_POOL = new ThreadPoolExecutor(CORE_SIZE, CORE_SIZE, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<>(), new ThreadFactory() {
        @Override
        public Thread newThread(@NotNull Runnable r) {
            return new Thread(THREAD_GROUP, r, "NETTY_PROCESS_THREAD_" + THREAD_COUNTER.getAndIncrement());
        }
    }, new ThreadPoolExecutor.CallerRunsPolicy());

    public void serverInBIO(int port) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                final Socket clientSocket = serverSocket.accept();
                System.out.println("[BIO]accepted connection from " + clientSocket);

                PROCESS_POOL.execute(() -> {
                    try (OutputStream out = clientSocket.getOutputStream(); InputStream in = clientSocket.getInputStream()) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(in));
                        String inputLine;
                        while ((inputLine = br.readLine()) != null) {
                            Matcher m = COMMAND_PATTERN.matcher(inputLine);
                            if (m.find()) {
                                StringBuilder sb = new StringBuilder("hello, you said:");
                                sb.append(" command:").append(m.group(1));
                                sb.append(" word:").append(m.group(2));
                                out.write(sb.toString().getBytes());
                                out.flush();
                                if (m.group(1).equals("exit") || m.group(1).equals("quit")) {
                                    break;
                                }
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    public void serverInNIO(int port) throws IOException {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(port));
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            final ByteBuffer readBuffer = ByteBuffer.allocate(1);
            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel client = server.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                        System.out.println("[NIO]accepted connection from " + client);
                    }
                    if (selectionKey.isReadable()) {
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        StringBuilder sb = new StringBuilder("you said ");
                        while (client.read(readBuffer) > 0) {
                            readBuffer.flip();
                            sb.append(Charset.defaultCharset().decode(readBuffer).toString());
                            readBuffer.clear();
                        }
                        client.register(selector, SelectionKey.OP_WRITE, sb);
                    }
                    if (selectionKey.isWritable()) {
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        StringBuilder sb = (StringBuilder) selectionKey.attachment();
                        if (sb != null) {
                            ByteBuffer buffer = ByteBuffer.wrap(sb.toString().getBytes());
                            while (buffer.hasRemaining()) {
                                if (client.write(buffer) == 0) {
                                    break;
                                }
                            }
                        }
                        client.close();
                    }
                }
            }
        }
    }

    public void serverInNetty(int port) throws Exception {
        EventLoopGroup group = new OioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(OioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<io.netty.channel.socket.SocketChannel>() {
                        @Override
                        public void initChannel(io.netty.channel.socket.SocketChannel ch) {
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) {
                                    ctx.writeAndFlush(Unpooled.unreleasableBuffer(
                                            Unpooled.copiedBuffer("你好是!\r\n", Charset.forName("UTF-8")))).addListener(ChannelFutureListener.CLOSE);
                                }
                            });
                        }
                    });
            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }

    }

    public static void main(String[] args) throws Exception {
        new ServerDemo().serverInNetty(8888);
    }
}
