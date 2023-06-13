package com.javalab.vo;

import java.sql.Date;
import lombok.Data;

/**
 * Vo 클래스
 * @Data : Getter/Setter/ToString/기본생성자 자동 생성
 */
@Data
public class UserVo {
	private String id;
	private String pwd;
	private String name;
	private String email;
	private String role;	
	private Date joinDate;	
}
