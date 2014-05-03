package view;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;


public class International {

	private static International instance;
	private Locale locale;
	private ResourceBundle bundleMessages;
	private ResourceBundle bundleButtons;
	private ResourceBundle bundleLabels;
	
	public static International getInstance ( ){
		
		if (instance == null){
			
			instance = new International();
		} else {
			
			// Nothing here.
		}
		
		return instance;
	}
	
	private International ( ){
		String[] languageOptions = {"I'd like to see SisRES in English!",
		"Eu quero ver o SisRES em português!"};

		final int selectedOption = JOptionPane.showOptionDialog(null, 
			"Select the language in which SisRES must be displayed",
			"Language", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
			null, languageOptions, languageOptions[0]);
		selectLocale(selectedOption);
	}
	
	public ResourceBundle getLabels ( ) {

		if (this.bundleLabels == null){
			
			this.bundleLabels = ResourceBundle.getBundle("i18n.labels", this.locale);
		} else {
			
			// Nothing here.
		}
		
		return this.bundleLabels;
	}

	public ResourceBundle getButtons ( ) {

		if (bundleButtons == null){
			
			bundleButtons = ResourceBundle.getBundle("i18n.buttons", this.locale);
		} else {
			
			// Nothing here.
		}
		return bundleButtons;
		
	}

	public ResourceBundle getMessages ( ) {
		
		if (this.bundleMessages == null){
			this.bundleMessages = ResourceBundle.getBundle("i18n.messages", this.locale);
		} else {
			
			// Nothing here.
		}
		return this.bundleMessages;
		
	}
	
	private void selectLocale(int selectedOption){
		
		if(selectedOption == JOptionPane.NO_OPTION){
		
			this.locale = new Locale("pt","BR");
		} else {
			
			this.locale = new Locale("en","US");
		}
	}
}
