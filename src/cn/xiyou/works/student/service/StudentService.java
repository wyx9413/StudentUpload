package cn.xiyou.works.student.service;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import cn.xiyou.works.pager.PageBean;
import cn.xiyou.works.student.dao.StudentDao;
import cn.xiyou.works.work.Work;

public class StudentService {
	StudentDao sd = new StudentDao();
	/**
	 * �������ݿ�������Ŀ��
	 */
	public Long findSum(){
		try {
			return sd.findSum();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * ����������ͨ����˵���Ʒ
	 */
	public List<Work> findAll(int pc,int ps){
		try {
			return sd.findAll(pc,ps);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * ͨ��ID������Ʒ
	 * @param wid
	 * @return
	 */
	public Work finById(String wid){
		try {
			return sd.findByid(wid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * ������Ʒ���ͽ��в���
	 * @param type
	 * @return
	 */
	public List<Work> findByType(String type,int pc,int ps){
		try {
			return sd.findByType(type,pc,ps);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * ����
	 * @param wid
	 * @throws SQLException 
	 */
	public void praise(String wid) {
		 try {
			sd.praise(wid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * ��
	 * @param wid
	 */
	public void stamp(String wid){
		try {
			sd.stamp(wid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * ͨ����Ʒ����ģ����ѯ
	 * @param name
	 * @return
	 */
	public List<Work> findByName(String name,int pc,int ps) {
//	@Test
//	public void a(){	
	try {
			List<Work> pb =  sd.findByName(name,pc,ps);
			return pb;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * ���յ���������
	 * @return
	 */
	public List<Work> findByPraise(int ps) {
//	@Test
//	public void a (){
		try {
			List<Work> pb  = sd.findByPraise(ps);
			
			return pb;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Work> findByCompose(String work, String student, String type, int pc,int ps) {
		try {
			List<Work> pb = sd.findByCompose(work,student,type,pc,ps);
			return pb;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	
		
	}
	
}
