package ���̷߳�����;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server{
	//�ͱ��߳���ص�Socket
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
        	//1������һ����������Socket,��ServerSocket, ָ���󶨵Ķ˿ڣ��������˶˿�
        	serverSocket = new ServerSocket(3333);
            //Socket socket = null;
            //��¼�ͻ��˵�����
            int count = 0;
            System.out.println("***�����������������ȴ��ͻ��˵�����***");
            //ѭ�������ȴ��ͻ��˵�����
            while (true){
                //����accept()������ʼ�������ȴ��ͻ��˵�����
            	Socket socket = serverSocket.accept();
                WorkThread newThread = new WorkThread(socket, listThread.size());
				newThread.start();
              /* //����һ���µ��߳�
                Server server = new Server();
                //�����߳�
                server.start();*/
 
                count++; //ͳ�ƿͻ��˵�����
                System.out.println("�ͻ��˵�����: " + count);
                InetAddress address = socket.getInetAddress();
                System.out.println("��ǰ�ͻ��˵�IP �� " + address.getHostAddress());
                
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
		//�߳�ִ�еĲ�������Ӧ�ͻ��˵�����
	    public void run(){
	        try {
	            //��ȡһ��������������ȡ�ͻ��˵���Ϣ
	        	InputStream is = socket.getInputStream();
	            InputStreamReader isr = new InputStreamReader(is); //���ֽ���ת��Ϊ�ַ���
	            BufferedReader br = new BufferedReader(isr); //��ӻ���
	        	PrintStream ps = new PrintStream(socket.getOutputStream());
	            //ѭ����ȡ����
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
	            	System.out.println("�ͻ��� "+id+" �ύ: " +info);
	                String reString=reverse(info);
	                ps.println(reString);
	            }
	 
	            /*socket.shutdownInput(); //�ر�������
	 
	            //��ȡ���������Ӧ�ͻ��˵�����
	            os = socket.getOutputStream();
	            pw = new PrintWriter(os); //��װΪ��ӡ��
	            pw.write("��ӭ��");
	            pw.flush();  //���������
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