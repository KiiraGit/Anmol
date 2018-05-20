package main;

import javax.swing.JOptionPane;

import step1.bussiness.step1;
import step2.bussiness.step2;

public class mainCtrl
{
	public static void main(String[] args)
	{
		step1	step1 = new step1();
				step1.selectOpenFile();
				step1.calculateStock();
				step1.selectSaveFile();
				
				JOptionPane.showMessageDialog(null, "Finalizado");
				
//		step2	step2 = new step2();
//				step2.selectFile();
	}
}
