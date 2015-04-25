/**
 * Juice
 * com.juice.orange.game.util.threadpool
 * JuicePoolManager.java
 */
package com.payinfo.net.util.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.payinfo.net.handler.IJuiceControl;

/**
 * @author shaojieque 
 * 2013-11-12
 */
public class JuicePoolManager {
	private static Random random = new Random();
	
	private static List<JuiceThreadPool> threadPools = new ArrayList<JuiceThreadPool>();
	
	public static void addThreadPool(JuiceThreadPool jtp) {
		threadPools.add(jtp);
	}
	
	public static void addRequest(IJuiceControl control) {
		if (threadPools.size() > 0) {
			JuiceThreadPool jtp = threadPools.get(random.nextInt(threadPools.size()));
			jtp.addJuiceNotify(control);
		}
	}
}
