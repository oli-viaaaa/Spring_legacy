package com.javalab.board.vo;

import java.sql.Date;

/**
 * 게시판 vo 클래스
 * @since 2020.02.05
 * @author javalab
 */
public class BoardVo {
	
	/** 번호 */
	private int no;
	/** 제목 */
	private String title;
	/** 내용 */
	private String content;
	/** 작성자 */
	private String writer;
	/** 조회수 */
	private int hit;
	/** 등록 일시 */
	private Date regdate;
	
	
	public BoardVo() {
		super();
	}

	public BoardVo(String title, String content, String writer) {
		this.title = title;
		this.content = content;
		this.writer = writer;
	}

	public int getNo() {
		return no;
	}



	public void setNo(int no) {
		this.no = no;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public String getWriter() {
		return writer;
	}



	public void setWriter(String writer) {
		this.writer = writer;
	}



	public int getHit() {
		return hit;
	}



	public void setHit(int hit) {
		this.hit = hit;
	}




	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", writer=" + writer + ", hit=" + hit
				+ ", regdate=" + regdate + "]";
	}


	
	
}
