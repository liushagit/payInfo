package com.orange.platform.bill.view.service;

import java.util.concurrent.atomic.AtomicLong;

import com.orange.platform.bill.view.ActionAware;

public class PingService extends ActionAware{

	public static final String CREATED = "created";
	public static final String FINISHED = "finished";
	public static final String REFUND = "refund";
	
	private static AtomicLong id = new AtomicLong(1000000000l);
	public static long getNextOrderId(){
		if (id.longValue() == 1000000000l) {
			synchronized (id) {
				if (id.longValue() == 1000000000l) {
					long maxId = billAction.queryMaxPingOrderId();
					if (maxId == 0) {
						maxId = 1000000000l;
					}
					id.set(maxId);
				}
			}
		}
		return id.incrementAndGet();
	}
}
