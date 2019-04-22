import java.io.File;
import javax.swing.JFileChooser;

public class OSFile {

	
	JFileChooser f = new JFileChooser();
	String path;
	
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

	public String save() {
		
		String path="";
		
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
	
	
}
