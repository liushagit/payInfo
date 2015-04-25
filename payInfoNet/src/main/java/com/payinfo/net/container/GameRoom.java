/**
 * Juice
 * com.juice.orange.game.container
 * GameRoom.java
 */
package com.payinfo.net.container;

import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.payinfo.net.util.OutputMessage;

/**
 * @author shaojieque 2013-3-22
 */
public interface GameRoom {
	//
	public static final int ROOM_POOL_SIZE = 2;
	
	/**
	 * get GameRoom id
	 */
	String getRoomId();

	/**
	 * add GameSession
	 */
	void addSession(GameSession session);

	/**
	 * remove GameSession
	 */
	void removeSession(String sessionId);

	/** get room sessions list*/
	List<GameSession> getSessionList();
	
	/**
	 * send a message to client, flag is identify what message type
	 */
	void sendMessage(short flag, OutputMessage message);
	
	/**
	 * send a message to client, flag is identify what message type
	 */
	void sendMessage(short flag, OutputMessage message, GameSession session);

	/**
	 * destroy GameRoom
	 */
	void destory();

	/**
	 * restore a object
	 */
	void put(String key, Object value);

	/**
	 * remove object
	 */
	void remove(String key);

	/**
	 * get object
	 */
	Object getObject(String key);

	/**
	 * add a schedule for GameRoom
	 */
	@SuppressWarnings("rawtypes")
	ScheduledFuture scheduleWithFixedDelay(Runnable runnable, long initialDelay, long period,
			TimeUnit unit);
	
	/**
	 * 定时器任务
	 */
	@SuppressWarnings("rawtypes")
	ScheduledFuture schedule(Runnable runnable, long delay, TimeUnit unit);
	
	/**
	 * 终止定时器任务
	 */
	@SuppressWarnings("rawtypes")
	void cancelFuture(ScheduledFuture future);
}
