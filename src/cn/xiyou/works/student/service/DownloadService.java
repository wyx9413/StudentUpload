package cn.xiyou.works.student.service;

import java.sql.SQLException;

import cn.xiyou.works.student.dao.DownloadDao;

public class DownloadService {
	private DownloadDao dd = new DownloadDao(); 
	
	public String findName(String wid){
		try {
			return dd.findName(wid);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	/**
	 * ������Ʒ�����ļ�λ��
	 * @param wid
	 * @return
	 */
	public String findAddress(String wid){
		try {
			return dd.findAddress(wid);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
}
