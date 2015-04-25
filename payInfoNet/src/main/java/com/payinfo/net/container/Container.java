/**
 * Juice
 * com.juice.orange.game.container
 * Container.java
 */
package com.payinfo.net.container;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.payinfo.net.log.LoggerFactory;
import com.payinfo.net.rmi.JuiceInvocationHandler;
import com.payinfo.net.rmi.RemoteConfig;
import com.payinfo.net.util.ClassUtils;

/**
 * @author shaojieque 
 * 2013-3-22
 */
public class Container {
	private static final Logger log = LoggerFactory.getLogger(Container.class);
	/** 管道和session关联 */
	private static Map<Integer, String> channelSessionMap = new HashMap<Integer, String>();
	/** session容器 */
	private static Map<String, GameSession> sessionMap = new HashMap<String, GameSession>();
	/** server映射 */
	private static Map<String, Object> serverMap = new HashMap<String, Object>();
	/** 协议号映射对应的server方法 */
	private static Map<String, String> pathMap = new HashMap<String, String>();
	/** 注册相应事件 */
	private static List<NotificationListener> listeners = new ArrayList<NotificationListener>();
	/** 远程服务器 */
	private static Map<String, RemoteConfig> remoteConfigMap = new HashMap<String, RemoteConfig>();

	public static void addChannelSession(int channelId, String sessionId) {
		channelSessionMap.put(channelId, sessionId);
	}

	public static String getChannelSession(int channelId) {
		return channelSessionMap.get(channelId);
	}

	public static void removeChannelSession(int channelId) {
		channelSessionMap.remove(channelId);
	}

	public static void addSession(GameSession session) {
		sessionMap.put(session.getSessionId(), session);
		try {
			sessionMap.put(session.getObject("otherSessionId").toString(), session);
		} catch (Exception e) {
		}
		try {
			log.info("Containersize|" + sessionMap.size() + "|"
					+ channelSessionMap.size() + "|" + serverMap.size() + "|"
					+ pathMap.size() + "|" + listeners.size() + "|"
					+ remoteConfigMap.size());
		} catch (Exception e) {
		}
	}

	public static void removeSession(String sessionId) {
		sessionMap.remove(sessionId);
	}

	public static GameSession getSessionById(String sessionId) {
		return sessionMap.get(sessionId);
	}

	public static GameRoom createGameRoom(String roomId) {
		GameRoom gameRoom = new GameRoomImpl(roomId);
		return gameRoom;
	}

	public static void registerServer(String name, Object server) {
		serverMap.put(name, server);
	}

	public static Object getServer(String name) {
		return serverMap.get(name);
	}

	public static void registerServerPath(String name, String path) {
		pathMap.put(name, path);
	}

	public static String getServerPath(String name) {
		return pathMap.get(name);
	}

	public static List<GameSession> getSessions() {
		List<GameSession> list = new ArrayList<GameSession>();
		for (Entry<String, GameSession> entry : sessionMap.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}

	public static void addNotificationListener(NotificationListener listener) {
		listeners.add(listener);
	}

	public static void notifyListener(Object obj) {
		if (listeners == null || listeners.size() == 0)
			return;
		for (NotificationListener listener : listeners) {
			listener.handler(obj);
		}
	}

	public static void addConfig(RemoteConfig config) {
		remoteConfigMap.put(config.getName(), config);
	}

	public static Map<String, RemoteConfig> getConfigs() {
		return remoteConfigMap;
	}

	public static Object createRemoteService(Class<?> clazz, String prefix) {
		if (clazz == null)
			throw new NullPointerException(
					"clazz must not be null for RemoteProxy.create()");
		/*
		 * JuiceInvocationHandler handler = new
		 * JuiceInvocationHandler(JuiceRemoteManager.getChannel(prefix),
		 * clazz.getName());
		 */
		JuiceInvocationHandler handler = new JuiceInvocationHandler(prefix,
				clazz.getName());
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		return Proxy
				.newProxyInstance(loader, new Class<?>[] { clazz }, handler);
	}

	public static Object createLocalService(Class<?> clazz) {
		if (clazz == null)
			throw new NullPointerException(
					"clazz must not be null for RemoteProxy.create()");
		String clazzName = clazz.getName();
		Object obj = ClassUtils.getObject(clazzName);
		return obj;
	}
}
