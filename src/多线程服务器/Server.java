package 多线程服务器;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server{
	//和本线程相关的Socket
    Socket socket = null;
    /*public Server(Socket socket){
        this.socket = socket;
    }
    */
    private ArrayList<WorkThread> listThread;
    
    public  void start(){
    	listThread = new ArrayList<WorkThread>();
    	ServerSocket serverSocket = null;
        try { 
        	//1、创建一个服务器端Socket,即ServerSocket, 指定绑定的端口，并监听此端口
        	serverSocket = new ServerSocket(3333);
            //Socket socket = null;
            //记录客户端的数量
            int count = 0;
            System.out.println("***服务器即将启动，等待客户端的链接***");
            //循环监听等待客户端的链接
            while (true){
                //调用accept()方法开始监听，等待客户端的链接
            	Socket socket = serverSocket.accept();
                WorkThread newThread = new WorkThread(socket, listThread.size());
				newThread.start();
              /* //创建一个新的线程
                Server server = new Server();
                //启动线程
                server.start();*/
 
                count++; //统计客户端的数量
                System.out.println("客户端的数量: " + count);
                InetAddress address = socket.getInetAddress();
                System.out.println("当前客户端的IP ： " + address.getHostAddress());
                
                listThread.add(newThread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
    
    private class WorkThread extends Thread {
		private Socket socket;
		private int id;

		public WorkThread(Socket socket, int id) {
			this.socket = socket;
			this.id = id;
		}
		//线程执行的操作，响应客户端的请求
	    public void run(){
	        try {
	            //获取一个输入流，并读取客户端的信息
	        	InputStream is = socket.getInputStream();
	            InputStreamReader isr = new InputStreamReader(is); //将字节流转化为字符流
	            BufferedReader br = new BufferedReader(isr); //添加缓冲
	        	PrintStream ps = new PrintStream(socket.getOutputStream());
	            //循环读取数据
	            while(true){
	            	String info = br.readLine();
	            	if(info == null){
	            		try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						continue;
	            	}
	            	System.out.println("客户端 "+id+" 提交: " +info);
	                String reString=reverse(info);
	                ps.println(reString);
	            }
	 
	            /*socket.shutdownInput(); //关闭输入流
	 
	            //获取输出流，响应客户端的请求
	            os = socket.getOutputStream();
	            pw = new PrintWriter(os); //包装为打印流
	            pw.write("欢迎你");
	            pw.flush();  //将缓存输出
	 */
	 
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        }
		
    private String reverse(String str) {
		int length = str.length();
		char[] result = new char[length];
		for (int i = 0; i < length; i++) {
			result[i] = str.charAt(length - i - 1);
		}
		String re = new String(result);
		return re;
	}
    }
}