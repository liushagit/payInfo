/**
 * 
 */
package com.payinfo.net.cached;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.apache.log4j.Logger;

import com.payinfo.net.log.LoggerFactory;

/**
 * @author queshaojie
 * 
 *         orangegame
 */
public class MemcachedResource {
	private static Logger logger = LoggerFactory.getLogger(MemcachedResource.class);
	//
	public static final int TIMEOUT = 24 * 60 * 60;
	//
	private static MemcachedClient client;
	
	public static void buildMemcached(String serverUrl) {
		if (client!=null)return;
		try {
			MemcachedClientBuilder builder = new XMemcachedClientBuilder(
					AddrUtil.getAddresses(serverUrl));
			builder.setCommandFactory(new BinaryCommandFactory());//use binary protocol
			client = builder.build(); 
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public static void save(String key, Object value) {
		save(key, value, TIMEOUT);
	}
	
	public static void save(String key, Object value, int timeout) {
		try {
			client.set(key, timeout, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public static Object get(String key) {
		Object object = null;
		try {
			object = client.get(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return object;
	}
	public static Object get(String key,long timeOut) {
		Object object = null;
		try {
			object = client.get(key,timeOut);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return object;
	}
	
	public static void remove(String key) {
		try {
			client.delete(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
