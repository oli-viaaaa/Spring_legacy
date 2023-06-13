package com.javalab.vo;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Vo 클래스
 */
@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardVO {

	private long no;
	private String title;
	private String content;
	private String id;
	private int hit;
	private Date regDate;
	
	/** 파일 업로드 멤버 변수 추가 **/
	private MultipartFile uploadFile;
	
}
