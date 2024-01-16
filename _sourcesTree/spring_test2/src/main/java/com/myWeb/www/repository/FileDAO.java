package com.myWeb.www.repository;

import java.util.List;

import com.myWeb.www.domain.BoardDTO;
import com.myWeb.www.domain.FileVO;

public interface FileDAO {

	int registerFile(FileVO fvo);

	List<FileVO> fileList(int bno);

	void fileModify(BoardDTO bdto);

	int remove(String uuid);

	void fileDelete(int bno);

	List<FileVO> selectList();

}
