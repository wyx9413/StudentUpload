package cn.xiyou.works.teacher.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cn.xiyou.works.teacher.dao.TeacherDao;
import cn.xiyou.works.work.Work;

public class TeacherService {
	TeacherDao td = new TeacherDao();
	
	/**
	 * ͨ��wid�鿴��Ʒ����
	 * @param wid
	 * @return
	 */
	public Work findById(String wid){
		try {
			return td.findById(wid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * ��ѯ����
	 * ��ʦ����δ�������˵��������в�ѯ
	 * @param condition
	 * @return
	 */
	@Test
	public List<Work> select(String condition,int pc,int ps){
		try {
				List<Work> list = new ArrayList<Work>();
				//�жϲ�ѯ��������ѯ����/�����ͨ��/δ���
				if(condition.equals("selectAll")){
					list = td.selectAll(pc,ps);
				}else if(condition.equals("selectPass")){
					list = td.selectPassOrUnPass(1,pc,ps);
				}else{
					list = td.selectPassOrUnPass(0,pc,ps);
				}
				//���ز�ѯ���
				return list;
				
			} catch (SQLException e) {
				throw new RuntimeException();
			}
	}
	/**
	 * ��¼����
	 * @param loginname
	 * @param password
	 * @return
	 */
	public int login(String loginname,String password){
		try {
			return td.login(loginname, password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * ͨ����˹���
	 * @param wid
	 */
	public void approved(String wid){
		try {
			td.approved(wid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public void delete(String wid){
		try {
			td.delete(wid);
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
			List<Work> pb  = td.findByPraise(ps);
			
			return pb;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
