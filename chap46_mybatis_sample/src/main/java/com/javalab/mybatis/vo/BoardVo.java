package com.javalab.mybatis.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 게시판 vo 클래스
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardVo {
	
	private int no;
	private String title;
	private String content;
	private String writer;
	private int hit;
	private Date regdate;
	
}
