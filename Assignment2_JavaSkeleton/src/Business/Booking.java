package Business;

import java.time.LocalDate;

public class Booking {

	private int no = 0;
	private String customer;
	private String performance;
	private LocalDate performanceDate;
	private String agent;
	private String instructions;
	

	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getPerformance() {
		return performance;
	}
	public void setPerformance(String performance) {
		this.performance = performance;
	}

	public LocalDate getPerformanceDate() {
		return performanceDate;
	}
	public void setPerformanceDate(LocalDate performanceDate) {
		this.performanceDate = performanceDate;
	}

	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	
	public String toString()
	{
		return getCustomer();
	}
}
