/**
 * Juice
 * com.juice.orange.game.container
 * GameRoomImpl.java
 */
package com.payinfo.net.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.payinfo.net.util.OutputMessage;

/**
 * @author shaojieque 
 * 2013-3-22
 */
public class GameRoomImpl extends ScheduledThreadPoolExecutor implements
		GameRoom {
	private String roomId;
	private Map<String, GameSession> sessionMap;
	private Map<String, Object> roomMap;

	public GameRoomImpl(String roomId) {
		super(ROOM_POOL_SIZE);
		this.roomId = roomId;
		this.sessionMap = new HashMap<String, GameSession>();
		this.roomMap = new HashMap<String, Object>();
	}

	@Override
	public void addSession(GameSession session) {
		this.sessionMap.put(session.getSessionId(), session);
	}
	
	@Override
	public List<GameSession> getSessionList() {
		List<GameSession> sessionList = new ArrayList<GameSession>();
		for (Entry<String, GameSession> entry : sessionMap.entrySet()) {
			sessionList.add(entry.getValue());
		}
		return sessionList;
	}
	
	@Override
	public void sendMessage(short flag, OutputMessage message) {
		for (Entry<String, GameSession> entry : sessionMap.entrySet()) {
			entry.getValue().sendMessage(flag, message);
		}
	}
	
	@Override
	public void sendMessage(short flag, OutputMessage message, GameSession session) {
		for (Entry<String, GameSession> entry : sessionMap.entrySet()) {
			if (session==null || !entry.getValue().getSessionId().equals(session.getSessionId())){
				entry.getValue().sendMessage(flag, message);
			}
		}
	}

	@Override
	public void destory() {
		roomId = null;
		sessionMap.clear();
		this.terminated();
	}

	@Override
	public String getRoomId() {
		return roomId;
	}

	@Override
	public void put(String key, Object value) {
		roomMap.put(key, value);
	}

	@Override
	public Object getObject(String key) {
		return roomMap.get(key);
	}

	@Override
	public void removeSession(String sessionId) {
		sessionMap.remove(sessionId);
	}

	@Override
	public void remove(String key) {
		roomMap.remove(key);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ScheduledFuture scheduleWithFixedDelay(Runnable command, long initialDelay,
			long period, TimeUnit unit) {
		return super.scheduleWithFixedDelay(command, initialDelay, period, unit);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ScheduledFuture schedule(Runnable command, long delay,
			TimeUnit unit) {
		return super.schedule(command, delay, unit);
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public void cancelFuture(ScheduledFuture future) {
		future.cancel(true);
	}
}
