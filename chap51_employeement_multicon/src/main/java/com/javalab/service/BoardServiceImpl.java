package com.javalab.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javalab.dao.BoardDAO;
import com.javalab.vo.BoardVO;
import com.javalab.vo.Criteria;

import lombok.extern.slf4j.Slf4j;


@Service("boardService")
@Slf4j
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardDAO boardDAO;

	public BoardServiceImpl() {
	}

	// 페이징 처리 없이 게시물 목록 조회
//	@Override
//	public List<BoardVO> getBoardList(BoardVO vo) {
//		log.info(vo.toString());
//		List<BoardVO> boardList = boardDAO.getBoardList(vo);
//		log.info("boardList.size() : " + boardList.size());
//		return boardList;
//	}	
	
	// 페이징 처리를 위한 게시물 목록 조회
	@Override
	public List<BoardVO> getListPagingAndSearching(Criteria cri) {
		log.info(cri.toString());
		List<BoardVO> boardList = boardDAO.getListPagingAndSearching(cri);
		return boardList;
	}	
	
	@Override
	public BoardVO getBoardById(BoardVO vo) {
		return this.boardDAO.getBoardById(vo);
	}
	
	@Override
	public int insertBoard(BoardVO vo) {

		int result = 0;
		result = this.boardDAO.insertBoard(vo);
		return result;
	}

	@Override
	public void updateBoard(BoardVO vo) {
		this.boardDAO.updateBoard(vo);
	}

	@Override
	public void deleteBoard(BoardVO vo) {
		this.boardDAO.deleteBoard(vo);
	}
	
	@Override
	public void updateHit(BoardVO vo) {
		this.boardDAO.updateHit(vo);
	}
	@Override
	public int getTotalBoardCount(Criteria cri) {
		return this.boardDAO.getTotalBoardCount(cri);
	}

}
