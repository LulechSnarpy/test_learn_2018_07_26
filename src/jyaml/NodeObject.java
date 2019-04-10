package jyaml;

public class NodeObject {
	private String standerName;
	private Integer count;
	private Double nums;
	private Long total;
	
	// 必须有无参构造函数
	public NodeObject() {
	}
	
	public NodeObject(String standerName, Integer count, Double nums, Long total) {
		super();
		this.standerName = standerName;
		this.count = count;
		this.nums = nums;
		this.total = total;
	}
	
	public String getStanderName() {
		return standerName;
	}
	public void setStanderName(String standerName) {
		this.standerName = standerName;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Double getNums() {
		return nums;
	}
	public void setNums(Double nums) {
		this.nums = nums;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{")
			.append("standerName").append(":").append(standerName).append(",")
			.append("count").append(":").append(count).append(",")
			.append("nums").append(":").append(nums).append(",")
			.append("total").append(":").append(total)
		.append("}");
		return sb.toString();
	}
}
