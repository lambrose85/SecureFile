import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;


public class FileSystem {
	String UserName;
	String Password;
	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	JFrame frame;
	private JList<String> list;
	private DefaultListModel<String> listModel;
	JLabel lblNumber = new JLabel("");
	private String workingDirectory = "";
	private String lastDirectory = "";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (Exception e) {
	    	
	    }
		
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
	@SuppressWarnings("serial")
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 342, 363);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		
		
		
		JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 51, 198, 266);
		frame.getContentPane().add(scrollPane);
		
		
		lblNumber.setBounds(10, 320, 46, 14);
		frame.getContentPane().add(lblNumber);

		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		
		scrollPane.setViewportView(list);
		
		JButton btnAdd = new JButton("Add File");
		btnAdd.setToolTipText("100 MB Max");
		btnAdd.setBounds(218, 49, 105, 23);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(workingDirectory.equals("")) {
					message("Please select a Working Directory", "");
					return;
				}
				
				OSFile obj = new OSFile();
				String path = obj.open(lastDirectory);

				
				if(path.equals("")) {
					return;
				}
				
				lastDirectory = path;
				String[] p = (path).split("\\\\");
				String entry = p[p.length-1];
				
				entry = entry + ".enc";
				
				String iv = generateIV();
				
				//send workingDirectory, entry, and iv 
				
				
				//test file size
				File test = new File(path);
				int bytes = (int) test.length();
				if(bytes>1e+8) {
					message("File is " + String.format("%.2f",bytes/1e+6) + " MB, needs to be less than 100 MB", "File Too Large");
					return;
				}
				
				
				FileEncryption f = new FileEncryption(path,hex_to_char("6d79706f726e7069637475726573"),hex_to_char(iv));
				try {
					f.fileEncrypt(workingDirectory + "\\" + entry);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				if(!listModel.contains(entry)) {
					listModel.addElement(entry);
					updateDirecory();
				}
				
			}
		});
		frame.getContentPane().add(btnAdd);
		
		JButton btnRetreive = new JButton("Retreive File");
		btnRetreive.setBounds(218, 83, 105, 23);
		btnRetreive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(list.isSelectionEmpty()) {
					message("Please select an entry from the list.", "");
					return;
				}
				
				OSFile obj = new OSFile();
				
				String src = workingDirectory + "\\" + list.getSelectedValue();
				String des = obj.directory(lastDirectory);
				
				if(!des.equals("")) {
					lastDirectory = des;
				}
				
				FileEncryption f = new FileEncryption(src,hex_to_char("6d79706f726e7069637475726573"),hex_to_char("ffffffffffffffffffffffffffffffff"));
				String name = list.getSelectedValue();
				des = des + "\\" + name.substring(0, name.length()-4);

				try {
					f.fileDecrypt(des);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		frame.getContentPane().add(btnRetreive);
		
		JButton btnNewButton = new JButton("Remove File");
		btnNewButton.setBounds(218, 117, 105, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		frame.getContentPane().add(btnNewButton);
		
		JButton btnTitle = new JButton("Click to Change Working Dir.");
		btnTitle.setBounds(10, 11, 198, 34);
		btnTitle.setRolloverEnabled(false);
		btnTitle.setFocusable(false);
		btnTitle.setBorder(null);
		btnTitle.setFocusPainted(false);
		btnTitle.setBorderPainted(false);
		btnTitle.setBackground(SystemColor.menu);

		btnTitle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OSFile obj = new OSFile();
				String temp = obj.directory(workingDirectory);
				String[] path;
				
				if(!temp.equals("")) {
					workingDirectory = temp;
					path = workingDirectory.split("\\\\");
					updateDirecory();
					btnTitle.setText(path[path.length-1]);
					btnTitle.setToolTipText(workingDirectory);
				}


			}
		});
		btnTitle.setFont(new Font("Tahoma", Font.PLAIN, 13));
		frame.getContentPane().add(btnTitle);

		
	}

    private static void message(String message, String title){
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
	
	private static String generateIV() {
		Random r = new Random();
		String out = "";
		
		for(int i=0;i<32;i++) {
			out += String.format("%x", (r.nextInt(16)));
		}
		
		return out;
		
	}
    
	private void updateDirecory() {
		listModel.removeAllElements();
		ArrayList<String> files = getFiles(workingDirectory);
		int number = 0;
		
		for(int i=0;i<files.size();i++) {
			
			int index = files.get(i).lastIndexOf('.');
			String extension = files.get(i).substring(index+1);
			
			if(extension.equals("enc")) {
				listModel.addElement(files.get(i));
				number++;
			}

		}

		lblNumber.setText(number + " files");
		
	}

	private byte[] hex_to_char(String input){
		
		byte[] out = new byte[16];
		int index = 0;
		
		String temp = "";
		for(int i=0;i<input.length();i+=2) {
			temp += input.charAt(i) + "" + input.charAt(i+1);
			out[index] = (byte) Integer.parseInt(temp, 16);
			temp = "";
			index++;
		}
		
		return out;
	}
	
	private ArrayList<String> getFiles(String path) {
        File folder = new File(path);
        File[] files = folder.listFiles();
        ArrayList<String> temp = new ArrayList<String>();

        for (File file : files)
        {
            if (file.isFile())
            {
                temp.add(file.getName());
            }
        }
		return temp;
	}
}
