package club.iskyc.lulech.gomoku.constant;


public enum GradeEnum {
	GRADE_AAAAA("aaaaa",1000),
	GRADE_ACAAA("acaaa",1000),
	GRADE_AACAA("aacaa",1000),
	GRADE_CAAAAC("caaaac",1000),
	GRADE_BAAAAC("baaaac",1000),
	GRADE_BAAAAB("baaaab",1000),
	GRADE_CAAAC("caaac",1000),
	GRADE_BAAAB("baaab",1000),
	GRADE_BAAACC("baaacc",1000),
	GRADE_BAAACB("baaacb",1000),
	GRADE_CAACAC("caacac",1000),
	GRADE_BAACAC("baacac",1000),
	GRADE_BACAAC("bacaac",1000),
	GRADE_BAACAB("baacab",1000),
	GRADE_ACCAA("accaa",1000),
	GRADE_CAACC("caacc",1000),
	GRADE_BAAB("baab",1000),
	GRADE_BAACB("baacb",1000),
	GRADE_BAACCB("baaccb",1000),
	GRADE_BAACCC("baaccc",1000),
	GRADE_BCAACB("bcaacb",1000),
	GRADE_CACAC("cacac",1000),
	GRADE_BACAB("bacab",1000),
	GRADE_BACACC("bacacc",1000),
	GRADE_BACACB("bacacb",1000),
	GRADE_CACCAC("caccac",1000),
	GRADE_BACCAC("baccac",1000),
	GRADE_BACCAB("baccab",1000),
	GRADE_ACCCA("accca",1000),
	GRADE_CACCC("caccc",1000),
	GRADE_CCACC("ccacc",1000),
	GRADE_BACCCC("bacccc",1000),
	GRADE_BACCCB("bacccb",1000),
	GRADE_BACCB("baccb",1000),
	GRADE_BACB("bacb",1000),
	GRADE_BAB("bab",1000),
	GRADE_BCACCC("bcaccc",1000),
	GRADE_BCACCB("bcaccb",1000),
	GRADE_BCACB("bcacb",1000),
	;
	private String name;
	private Integer value;
	private GradeEnum(String name,Integer value){
		this.name = name;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
}
