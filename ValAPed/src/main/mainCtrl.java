package main;

import javax.swing.JOptionPane;

import step1.bussiness.step1;
import step3.bussiness.step3;

public class mainCtrl
{
	public static void main(String[] args)
	{
		runStep1();
	}
	
	private static void runStep1()
	{
		step1	step1 = new step1();
				step1.selectOpenFile();
				step1.calculateStock();
				step1.selectSaveFile();
				
				JOptionPane.showMessageDialog(null, "Finalizado");
	}
	
	private static void runStep3()
	{
		step3 	step3 = new step3();
				step3.selectOpenFile();
				step3.selectSaveFile();
		
				JOptionPane.showMessageDialog(null, "Finalizado");
	}
}
