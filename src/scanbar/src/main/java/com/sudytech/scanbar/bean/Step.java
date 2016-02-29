package com.sudytech.scanbar.bean;



public class Step{

	/**作业，端末加工，弯曲，组立*/
	public static String[] steps = new String[]{"作业","端末加工","弯曲","组立"};
	
	public static int step1 = 0;
	
	public static int step2 = 1;
	
	public static int step3 = 2;
	
	public static int step4 = 3;
	
	private int step;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}
	
}
