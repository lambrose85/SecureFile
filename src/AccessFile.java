
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import java.io.File;

public class AccessFile  {


	public static void main(String[] args) {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int returnValue = jfc.showOpenDialog(null);
		
		if(returnValue == JFileChooser.APPROVE_OPTION){
			File selectedFile = jfc.getSelectedFile();
			System.out.println(selectedFile.getAbsolutePath());
		}
	}


}
