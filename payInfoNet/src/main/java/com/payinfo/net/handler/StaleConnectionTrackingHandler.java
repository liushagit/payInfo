/**
 * Juice
 * com.juice.orange.game.handler
 * StaleConnectionTrackingHandler.java
 */
package com.payinfo.net.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.payinfo.net.channel.ChannelTimeTrack;
import com.payinfo.net.container.Container;
import com.payinfo.net.log.LoggerFactory;

/**
 * Keeps track of all connections and automatically closes the ones that are stale.
 * @author shaojieque 
 * 2013-3-21
 */
public class StaleConnectionTrackingHandler extends SimpleChannelHandler {
	private static Logger logger = LoggerFactory.getLogger(StaleConnectionTrackingHandler.class);
	//
	private static Map<Integer, ChannelTimeTrack> stamps = new HashMap<Integer, ChannelTimeTrack>();
	private static List<Integer> tempList = new ArrayList<Integer>();
	private long timeout;

	public StaleConnectionTrackingHandler(long timeout) {
		this.timeout = timeout;
	}

	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		stamp(e.getChannel());
		super.channelOpen(ctx, e);
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		stamp(e.getChannel());
		super.messageReceived(ctx, e);
	}

	private void stamp(Channel channel) {
		try {
			int channelId = channel.getId();
			ChannelTimeTrack channelTrack = stamps.get(channelId);
			if (channelTrack == null) {
				channelTrack = new ChannelTimeTrack(channel, System.currentTimeMillis());
			} else {
				channelTrack.setTimestamp(System.currentTimeMillis());
			}
			stamps.put(channelTrack.getId(), channelTrack);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.info("start stamps size:" + stamps.size());
			closeStaleConnections();
			logger.info("end stamps size:" + stamps.size());
		}
	}

	public void closeStaleConnections() {
		//
		tempList.clear();
		try {
			for(Entry<Integer, ChannelTimeTrack>entry:stamps.entrySet()){
				ChannelTimeTrack channelTrack = entry.getValue();
				if (!channelTrack.getChannel().isConnected() || isStale(channelTrack.getTimestamp())) {
					tempList.add(entry.getKey());
				}
			}
			//
			for(Integer i : tempList) {
				ChannelTimeTrack track = stamps.get(i);
				Container.removeChannelSession(track.getChannel().getId());
				track.getChannel().close();
				stamps.remove(i);
			}
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		tempList.clear();
	}

	/**
	 * Stops tracking this channel for staleness. This happens for WebSockets
	 * and EventSource connections.
	 * 
	 * @param channel
	 */
	public static void stopTracking(Channel channel) {
		stamps.remove(channel.getId());
	}

	private boolean isStale(Long timeStamp) {
		return System.currentTimeMillis() - timeStamp > timeout;
	}
}
