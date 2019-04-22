import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.JList;
import javax.swing.DefaultListModel;


public class FileSystem {

	private JFrame frame;
	private JList<String> list;
	private DefaultListModel<String> listModel;
	private String workingDirectory = "";
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
		frame.setBounds(100, 100, 342, 338);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setLocation(10, 14);
		scrollPane.setSize(198, 283);
		frame.getContentPane().add(scrollPane);
		
		//Start process to obtain files and display on the screen


		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		
		scrollPane.setViewportView(list);
	
		frame.getContentPane().setLayout(null);
		
		JButton btnAdd = new JButton("Add File");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				OSFile obj = new OSFile();
				String path = obj.open();
				
				if(path.equals("")) {
					return;
				}
				
				String[] p = (path).split("\\\\");
				String entry = p[p.length-1];
				
				entry = entry + ".enc";
				
				FileEncryption f = new FileEncryption(path,hex_to_char("6d79706f726e7069637475726573"),hex_to_char("ffffffffffffffffffffffffffffffff"));
				try {
					f.fileEncrypt(workingDirectory + "\\" + entry);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				if(!listModel.contains(entry)) {
					listModel.addElement(entry);
				}
				
			}
		});
		btnAdd.setBounds(218, 12, 105, 23);
		frame.getContentPane().add(btnAdd);
		
		JButton btnRetreive = new JButton("Retreive File");
		btnRetreive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(list.isSelectionEmpty()) {
					return;
				}
				
				OSFile obj = new OSFile();
				
				String src = workingDirectory + "\\" + list.getSelectedValue();
				String des = obj.directory();
				
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
		btnRetreive.setBounds(218, 46, 105, 23);
		frame.getContentPane().add(btnRetreive);
		
		JButton btnNewButton = new JButton("Remove File");
		btnNewButton.setBounds(218, 79, 105, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Change WD");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OSFile obj = new OSFile();
				
				String temp = obj.directory(workingDirectory);
				
				if(!temp.equals("")) {
					workingDirectory = temp;
					updateDirecory();
				}

				
			}
		});
		btnNewButton_1.setBounds(220, 265, 103, 25);
		frame.getContentPane().add(btnNewButton_1);
		
		
	}

	private void updateDirecory() {
		ArrayList<String> files = getFiles(workingDirectory);
		
		for(int i=0;i<files.size();i++) {
			listModel.addElement(files.get(i));
		}
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
            else if (file.isDirectory())
            {
            	temp.addAll(getFiles(file.getAbsolutePath()));
            }
        }
		return temp;
	}
}
