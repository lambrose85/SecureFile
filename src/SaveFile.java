import java.io.File;
import javax.swing.JFileChooser;


public class SaveFile {

	
	JFileChooser f = new JFileChooser();
	String path;
	
	public void pick() {
		
		
		if(f.showSaveDialog(null)==JFileChooser.APPROVE_OPTION) {
		
			File file = f.getSelectedFile();
			path = file.getAbsolutePath();
		
		}
		else {
			path = "";
		}
		
		
	}

}