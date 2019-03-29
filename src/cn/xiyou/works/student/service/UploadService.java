package cn.xiyou.works.student.service;

import java.sql.SQLException;

import cn.itcast.commons.CommonUtils;
import cn.xiyou.works.student.dao.UploadDao;
import cn.xiyou.works.work.Work;

public class UploadService {
	private UploadDao ud = new UploadDao();
	/**
	 * ���������
	 * @param form
	 */
	public void saveForm(Work form) {
		/**
		 * 1�����������
		 * 2��ͨ�������ѧ�Ų�sid
		 * 3��ͨ��������Ʒ���Ͳ�ѯpid
		 * 4����wid����Ʒ������sid,pid
		 */
		try {
			//1�����������
			System.out.println("service�еĵ�ֵַΪ:"+form.getAddress());
			ud.saveForm(form);
			//2��ͨ�������ѧ�Ų�sid
			String sid = ud.findSidByNumber(form.getNumber());
			if(sid==null){//δ�鵽�����ֶ�����������
				sid = CommonUtils.uuid();
				ud.addStudent(sid,form.getSname(),form.getSclass(),form.getNumber());
			}
			//3��ͨ��������Ʒ���Ͳ�ѯpid
			String pid = ud.findPidByType(form.getType());
			if(pid==null){//δ�鵽�����ֶ�����������
				pid = CommonUtils.uuid();
				ud.addType(pid,form.getType());
			}
			//4����wid����Ʒ������sid,pid
			ud.setPidAndSidByWid(form.getWid(), sid, pid);
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
