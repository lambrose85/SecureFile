import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class FileSystem {

	private JFrame frame;
	private JTextField textFieldSource;
	private JTextField textFieldDes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileSystem window = new FileSystem();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FileSystem() {
		initialize();
	}

	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textFieldSource = new JTextField();
		textFieldSource.setEditable(false);
		textFieldSource.setToolTipText("Type in the source path");
		textFieldSource.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Source:");
		
		JLabel lblNewLabel_1 = new JLabel("Destination:");
		
		textFieldDes = new JTextField();
		textFieldDes.setEditable(false);
		textFieldDes.setColumns(10);
		
		JButton btnSource = new JButton("Select");
		btnSource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				OpenFile obj = new OpenFile();
				
				try {
					obj.pick();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
				
				textFieldSource.setText(obj.path);
				
				
			}
		});
		
		JButton btnDes = new JButton("Select");
		btnDes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				SaveFile obj = new SaveFile();
				
				try {
					obj.pick();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
				
				textFieldDes.setText(obj.path);
				
				
			}
		});
		
		JButton btnEnc = new JButton("Encrypt");
		btnEnc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(textFieldDes.getText().equals("")&&textFieldSource.getText().equals("")) {
					return;
				}
				
				byte[] key = {12,12,12,34,43,3,43,23,23,23,54,34,23,23,23,12};
				
				FileEncryption f = new FileEncryption(textFieldSource.getText(), key);
				try {
					f.fileEncrypt(textFieldDes.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				System.out.println("done");
				
			}
		});
		
		
		
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addContainerGap(387, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_1)
							.addContainerGap(366, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(textFieldDes)
								.addComponent(textFieldSource, GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnDes, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
								.addComponent(btnEnc, Alignment.TRAILING)
								.addComponent(btnSource, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))
							.addContainerGap())))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldSource, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSource))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldDes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDes))
					.addPreferredGap(ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
					.addComponent(btnEnc)
					.addContainerGap())
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}