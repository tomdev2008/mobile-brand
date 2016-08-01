package com.xiu.common.web.thread.syncTopicFilter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
* @Description: TODO(执行访问接口的线程池) 
* @author haidong.luo@xiu.com
* @date 2015年7月16日 下午6:07:21 
*
 */
public class SyncTopicFilterThreadTool {
   //创建一个可重用固定线程数的线程池
  private  final static  Integer  toolMaxNum = 4;
  //创建一个可重用固定线程数的线程池
  private static  ExecutorService pool = null;
  private SyncTopicFilterThreadTool() {}  
  private static SyncTopicFilterThreadTool single=null;  
  
  public static synchronized SyncTopicFilterThreadTool getInstance() {  
       if (single == null) {    
           single = new SyncTopicFilterThreadTool(); 
           pool=Executors.newFixedThreadPool(toolMaxNum);
       }    
      return single;  
  }  
  
  /**
   * 将线程放入池中进行执行
   * @param thread
   */
  public void execute(Thread thread){
	  pool.execute(thread);
  }
  
  
}
