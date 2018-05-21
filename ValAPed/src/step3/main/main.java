package step3.main;

import javax.swing.JOptionPane;

import step3.bussiness.step3;

public class main
{
	public static void main()
	{
		step3 	step3 = new step3();
				step3.selectOpenFile();
				step3.selectSaveFile();
				
				JOptionPane.showMessageDialog(null, "Finalizado");
	}
}
