package com.javalab.vo;

import lombok.Getter;
import lombok.ToString;

/**
 * [페이징 정보와 한 페이지에 보여줄 구체적인 정보(시작페이지번호, 끝페이지번호)보관용 클래스]
 * - Criteria : 요청된 페이지 정보와 한페이지에 보여줄 게시물수 정보, 검색 키워드 정보 
 */
@Getter
@ToString
public class PageDto {

	private int startPage;
	private int endPage;
	private boolean prev, next;

	private int total;
	private Criteria cri;

	// 오버로딩 생성자
	public PageDto(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;

		// 끝 페이지 번호
		this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10;
		// 시작 페이지 번호
		this.startPage = this.endPage - 9;
		/*
		 * 실제 끝 페이지 번호(1페이지에 20개 게시물 출력으로 가정)
		 *  - 전체 게시물이 201개라고 가정하면 201/10하면 20.1이 되야하는데 자바는 나눗셈에서 몫만 취해서
		 *    20이됨. 그래서 total을 실수로 바꾸고 나눗셈 하면 결과가 20.1이 되고 Math.ceil하면 21이 
		 *    되어서 원하는 21이 나옴. 즉 곱하기 0.1은 소숫점을 보존하기 위해서 정수를 실수로 변환하는 용도
		 */
		int realEnd = (int) (Math.ceil((total * 1.0) / cri.getAmount()));
		// 끝 페이지번호 다시 계산
		if (realEnd <= this.endPage) { // 만약 구해둔 endPage가 실제끝번호보다 큰경우
			this.endPage = realEnd; // 실제 끝페이지번호가 끝페이지번호가 됨.
		}
		// 이전(<)은 시작페이지번호가 1보다 크면 존재
		this.prev = this.startPage > 1;
		// 다음(>)은 구해둔 끝페이지번호가 실제 페이지번호보다 작으면 존재
		this.next = this.endPage < realEnd; //
	}

}
