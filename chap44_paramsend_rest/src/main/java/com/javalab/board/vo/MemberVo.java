package com.javalab.board.vo;

public class MemberVo {

	private String name;
	private int grade;

	public MemberVo(String name, int grade) {
		super();
		this.name = name;
		this.grade = grade;
	}

	public MemberVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "MemberVo [name=" + name + ", grade=" + grade + "]";
	}

}