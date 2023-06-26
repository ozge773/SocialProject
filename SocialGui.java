package social;

import java.io.Serial;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;


public class SocialGui extends JFrame {
	@Serial
	private static final long serialVersionUID = 1L;

	// The following components are declared public
	// in order to allow testing the user interface
	
	/**
	 * The code of the person to log in
	 */
	public JTextField id ;
	
	/**
	 * The button to perform login
	 */
	public JButton login ;
	
	/**
	 * The label that shall contain the info
	 * of the logged in person 
	 */
	public JLabel name ;
	
	/**
	 * The list of friends of the person
	 * that is logged in
	 */
	public JList<String> friends ;
	

	public SocialGui(Social m){
		
	}
	//


}
