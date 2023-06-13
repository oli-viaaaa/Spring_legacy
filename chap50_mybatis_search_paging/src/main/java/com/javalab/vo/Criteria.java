package com.javalab.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * [페이징 + 검색 정보 보관 클래스]
 * - 요청된 페이지 정보와 한페이지에 몇개의 게시물을 보여줄지 정보 저장 
 * - 검색 키워드 정보 저장
 */
@Getter
@Setter
@ToString
public class Criteria {
	private int pageNum = 1;	// 요청 페이지 번호
	private int amount = 10; 		// 한페이지에 보여줄 게시물수
	private String searchText = "" ; 	// 검색 키워드
	
	public Criteria() {	// 기본적으로 요청 페이지는 1로 하드코딩
		this(1, 10);	// 기본적으로 한 페이지에 10개 출력되도록 하드코딩
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}

	public Criteria(int pageNum, int amount, String searchText) {
		super();
		this.pageNum = pageNum;
		this.amount = amount;
		this.searchText = searchText;
	}

	public Criteria(String searchText) {
		super();
		this.searchText = searchText;
	}	
}
