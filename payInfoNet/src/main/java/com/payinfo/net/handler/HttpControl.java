/**
 * Juice
 * com.juice.orange.game.handler
 * HttpControl.java
 */
package com.payinfo.net.handler;


/**
 * @author shaojieque
 * 2013-3-19
 */
public interface HttpControl extends IJuiceControl{
    void nextHandler(HttpRequest request, HttpResponse response);

    void nextHandler(HttpRequest request, HttpResponse response, HttpControl control);
}
