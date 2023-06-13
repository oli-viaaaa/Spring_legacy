package com.javalab.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javalab.board.vo.BoardVo;

/**
 * 게시판 DAO 클래스 (데이터베이스 질의문 실행) - Data Access Object Layer 단 - @Component 의
 * 구현체인 @Reposiotory 어노테이션 적용되어 자동으로 빈으로 스캔(로딩)됨
 */

// @Repository : boardDao 라는 이름으로 빈으로 등록된다.
// @Repository("dao") : dao라는 이름으로 빈으로 등록된다.
// @Repository 주입받을 곳에서 boardDao라는 이름으로 사용
@Repository("boardDao") // 주입받을 곳에서 boardDao라는 이름으로 사용
public class BoardDao {

	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;

	@Autowired
	private DataSource dataSource;
	// org.apache.tomcat.jdbc.pool.DataSource

	// 게시물 목록 조회 메소드
	public ArrayList<BoardVo> selectBoardList() {

		ArrayList<BoardVo> boardList = new ArrayList<BoardVo>();
		try {
			con = dataSource.getConnection();
			String query = "select no, title, writer, hit, to_char(regdate,'yyyy-mm-dd') as regdate "
					+ " from simple_board order by regdate desc";
			System.out.println("SQL :  " + query);
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();

			BoardVo boardVo = null;
			while (rs.next()) {
				boardVo = new BoardVo();

				boardVo.setNo(rs.getInt("no"));
				boardVo.setTitle(rs.getString("title"));
				boardVo.setWriter(rs.getString("writer"));
				boardVo.setHit(rs.getInt("hit"));
				boardVo.setRegdate(rs.getDate("regdate"));

				boardList.add(boardVo);
			}

			rs.close();
			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boardList;
	}

	// 게시물 삭제 메소드
	public int deleteBoard(int no) {
		int result = 0;
		try {
			con = dataSource.getConnection();

			String query = "delete from simple_board where no=?";

			System.out.println("SQL :  " + query);

			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, no);

			result = pstmt.executeUpdate();

			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 한 명의 회원 조회 메소드
	public BoardVo getBoardById(int no) {
		BoardVo board = null;
		try {
			con = dataSource.getConnection();

			String query = "select no, title, content, writer, hit, regdate " + " from simple_board where no=?";

			System.out.println("SQL :  " + query);

			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();

			BoardVo boardVo = null;
			while (rs.next()) {
				boardVo = new BoardVo();

				boardVo.setNo(rs.getInt("no"));
				boardVo.setTitle(rs.getString("title"));
				boardVo.setContent(rs.getString("content"));
				boardVo.setWriter(rs.getString("writer"));
				boardVo.setHit(rs.getInt("hit"));
				boardVo.setRegdate(rs.getDate("regdate"));
			}
			rs.close();
			pstmt.close();
			con.close();

			return boardVo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 회원 수정 메소드
	public int modifyBoard(BoardVo boardVo) {
		int result = 0;
		try {
			con = dataSource.getConnection();

			int no = boardVo.getNo();
			String title = boardVo.getTitle();
			String content = boardVo.getContent();
			String writer = boardVo.getWriter();

			String query = "update simple_board set title=?, content=?, writer=?";
			query += " where no=?";

			System.out.println("SQL :  " + query);

			pstmt = con.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, writer);
			pstmt.setInt(4, no);

			result = pstmt.executeUpdate();

			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 게시물 저장 메소드
	public int insertBoard(BoardVo vo) {
		int result = 0;
		try {
			con = dataSource.getConnection();

			String title = vo.getTitle();
			String content = vo.getContent();
			String writer = vo.getWriter();

			String query = "insert into simple_board(no, title, content, writer) values(seq_simple_board.nextval, ?, ?, ?)";
			System.out.println("SQL :  " + query);

			pstmt = con.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, writer);

			result = pstmt.executeUpdate();

			pstmt.close();
			con.close();

			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 조회수 증가 메소드(게시물이 한 번 읽힐때마다 증가)
	public void updateHitCount(int no) {
		try {
			con = dataSource.getConnection();

			String query = "update simple_board set hit = hit + 1";
			query += " where no = ?";
			System.out.println("SQL :  " + query);

			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, no);

			pstmt.executeUpdate();

			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
