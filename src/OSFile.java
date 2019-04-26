import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class OSFile {

	private JFrame frame = new JFrame();
	JFileChooser f = new JFileChooser();
	String path;
	
	public OSFile() {
		frame.add(f);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public String open() {
		
		String path="";
		
		if(f.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
		
			File file = f.getSelectedFile();
			path = file.getAbsolutePath();
		
		}
		else {
			path = "";
		}
		
		return path;
		
	}
	
	public String open(String p) {
		
		String path="";
		f.setCurrentDirectory(new File(p));
		if(f.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
		
			File file = f.getSelectedFile();
			path = file.getAbsolutePath();
		
		}
		else {
			path = "";
		}
		
		return path;
		
	}
	
	public String save() {
		
		String path = "";
		
		if(f.showSaveDialog(null)==JFileChooser.APPROVE_OPTION) {
			
			File file = f.getSelectedFile();
			path = file.getAbsolutePath();
		
		}
		else {
			path = "";
		}
		
		return path;
		
	}
	
	public String directory() {
		
		String path="";
		f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		f.setDialogTitle("Choose Destination");
		if(f.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
			
			File file = f.getSelectedFile();
			path = file.getAbsolutePath();
		
		}
		else {
			path = "";
		}
		
		return path;
		
	}

	public String directory(String workingDirectory) {
		String path="";
		f.setCurrentDirectory(new File(workingDirectory));
		f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		f.setDialogTitle("Choose a Directory");
		
		if(f.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
		
			File file = f.getSelectedFile();
			path = file.getAbsolutePath();
		
		}
		else {
			path = "";
		}
		
		
		return path;
		
	}

	
	
}