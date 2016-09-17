package 多线程服务器;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
 
public class Client extends JFrame{
	private InputStreamReader isr;
	private BufferedReader br;
	private PrintStream ps;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	public Client(int count) {
		setBounds(200, 200, 450, 142);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setBounds(10, 10, 300, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnSubmit = new JButton("提交");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				click();
			}
		});
		btnSubmit.setBounds(325, 10, 90, 20);
		contentPane.add(btnSubmit);

		JLabel lblReceive = new JLabel("接收:");
		lblReceive.setBounds(20, 40, 55, 15);
		contentPane.add(lblReceive);

		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(10, 65, 406, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		setTitle(count + "");

        try {
            //1、创建客户端Socket，指定服务器端口号和地址
            Socket socket = new Socket("127.0.0.1",3333);
            /*//2、获取输出流,向服务器发送信息
            OutputStream os = socket.getOutputStream(); //字节输出流
            PrintWriter pw  = new PrintWriter(os); //将输出流包装为打印流
            pw.write("用户名:tom; 密码：456");
            pw.flush();
            socket.shutdownOutput(); //关闭输出流*/
 
            InputStream is = socket.getInputStream();
             isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            ps = new PrintStream(socket.getOutputStream());
        } catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        }
	
	private void click() {
		try {
			String text = textField.getText();
			System.out.println(text);
			ps.println(text);
			textField_1.setText(br.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
