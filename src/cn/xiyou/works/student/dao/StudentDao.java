package cn.xiyou.works.student.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cn.xiyou.works.pager.Expression;
import cn.xiyou.works.pager.PageConstants;
import cn.xiyou.works.work.Work;

public class StudentDao {
	QueryRunner qr = new TxQueryRunner();
	
	/**
	 * ����ȫ�����ͨ������Ʒ
	 * @return
	 * @throws SQLException 
	 */
	public List<Work> findAll(int pc,int ps) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("status","=","1"));
//		int ps = PageConstants.WORK_PAGE_SIZE;  //ps,ÿҳ����
		List<Work> list = findByCriteria(exprList,pc,ps);
		return list;
	}
	/**
	 * ��ҳ��ѯ
	 */
	/**
	 * ������ģ����ѯ
	 * @param bname
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	
	public List<Work> findByName(String wname,int pc,int ps) throws SQLException{
		/**
		 * pc��ǰҳ��
		 * wname����
		 */
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("status","=","1"));
		exprList.add(new Expression("wname","like","%"+wname+"%"));
//		int ps = PageConstants.WORK_PAGE_SIZE;
		return findByCriteria(exprList,pc,ps);
	}
	/**
	 * ͨ�ò�ѯ����
	 * @param exprList
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	private List<Work> findByCriteria(List<Expression> exprList,int pc,int ps) throws SQLException{
		/**
		 * 1.�õ�ps  pageSize ��ǰҳ����
		 * 2.�õ�tr	totalRocode ����
		 * 3.�õ�beanList 
		 * 4.����PageBean,����
		 */
		//1.�õ�ps
		   //��ǰҳ������Ϊ��ͨ��Ʒչʾҳ����������Ʒҳ��,�����ɶ�Ӧ���Ҵ��ݹ���
		//2.�õ�tr
		//ͨ��exprList����where�Ӿ�
		StringBuilder whereSql = new StringBuilder(" where 1=1");
		List<Object> params = new ArrayList<Object>();//sql����е��ʺ�ֵ
		
		for(Expression expr : exprList){
		/**
		 * �������
		 * 1.��and��ͷ
		 * 2.����������
		 * 3.�������������
		 * 4.�����������is null ��׷���ʺţ�����params�����һЩ�ʺŶ�Ӧ��ֵ	
		 */
		whereSql.append(" and ").append(expr.getName()).append(" ")
			.append(expr.getOperator()).append(" ");
		
		if(!expr.getOperator().equals("is null")){
			whereSql.append("?");
			params.add(expr.getValue());
		}
		}
		//��ѯ���ݿ�õ�tr
		String sql = "select count(*) from t_works"+whereSql;
		Number n = (Number) qr.query(sql, new ScalarHandler(),params.toArray());
		int tr = n.intValue();//�õ����ܼ�¼��
		//3.�õ�BeanList
//		sql = "select * from t_works"+whereSql+" limit ?,?";
		sql = "select wid,wname,sid,address,picture,pid,praise,stamp,introduce from t_works"+whereSql+" order by praise desc limit ?,?";
		params.add((pc-1)*ps);//��һ���ʺŵ�ǰҳ�׺���¼���±�
		if(tr<pc*ps){
			params.add(tr);
		}else{
			params.add(ps);//һ����ѯps����¼
		}
		
		
		List<Work> beanList = qr.query(sql,new BeanListHandler<Work>(Work.class),params.toArray());
		
		/*
		
		//����PageBean�����ò���
		PageBean<Work> pb = new PageBean<Work>();
		//����pagebeanû��url�����������servlet���
		pb.setBeanList(beanList);
		pb.setPc(pc);       
		pb.setPs(ps);
		pb.setTr(tr);*/
		return beanList;
	}
	/**
	 * ͨ��id������Ʒ
	 * @param wid
	 * @return
	 * @throws SQLException
	 */
	public Work findByid(String wid) throws SQLException{
		String sql = "select wid,wname,sid,address,picture,pid,praise,stamp,introduce from t_works where wid=?";
		Work work = qr.query(sql, new BeanHandler<Work>(Work.class),wid);
		sql = "select pname from t_type where pid =?";
		String type = (String) qr.query(sql, new ScalarHandler(),work.getPid());//ͨ��pid�ҵ���Ӧ��������
		sql = "select sclass from t_student where sid =?";
		String sclass = (String) qr.query(sql, new ScalarHandler(),work.getSid());
		sql = "select sname from t_student where sid =?";
		String sname = (String) qr.query(sql, new ScalarHandler(),work.getSid());
		work.setSclass(sclass);
		work.setSname(sname);
		work.setType(type);
		return work;
	}
	/**
	 * ����Ʒ�������ͽ��в���
	 * @param type
	 * @return
	 * @throws SQLException 
	 */
	public List<Work> findByType(String type,int pc,int ps) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		//ͨ��t_type���ѯ��������������Ӧ��pid
		String pid = findPid(type);
		exprList.add(new Expression("status","=","1"));
		exprList.add(new Expression("pid","=",pid));
//		int ps = PageConstants.WORK_PAGE_SIZE;
		return findByCriteria(exprList,pc,ps);
	}
	/**
	 * ͨ���������Ͳ������Ӧ��pid
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	private String findPid(String type) throws SQLException{
		String sql = "select pid from t_type where pname=?";
		String i= (String) qr.query(sql, new ScalarHandler(),type);
		
		return i;
	}
	private String findSid(String student) throws SQLException{
		String sql = "select sid from t_student where sname=?";
		return (String) qr.query(sql, new ScalarHandler(),student);
	}
	/**
	 * ���޹���
	 * @param wid
	 * @throws SQLException 
	 */
	public void praise(String wid) throws SQLException{
		//��ѯ��wid����Ӧ��Ʒ�ĵ�ǰ������
		String sql1 = "select praise from t_works where wid=?";
		Number n = (Number) qr.query(sql1, new ScalarHandler(),wid);
		//��������һ
		int m = n.intValue()+1;
//		Number m = n.intValue()+1;
		//���µ�ǰ������
		String sql2 = "update t_works set praise=? where wid=?";
		qr.update(sql2,m,wid);
	}
	/**
	 * �ȵĹ���
	 * ��ǰ������һ
	 * @param wid
	 * @throws SQLException
	 */
	public void stamp(String wid) throws SQLException{
			//��ѯ��wid����Ӧ��Ʒ�ĵ�ǰ������
			String sql1 = "select stamp from t_works where wid=?";
			Number n = (Number) qr.query(sql1, new ScalarHandler(),wid);
			//��������һ
			int m = n.intValue()+1;
			//���µ�ǰ������
			String sql2 = "update t_works set stamp=? where wid=?";
			qr.update(sql2,m,wid);
		}
	/**
	 * ���յ��������в���
	 * @return
	 * @throws SQLException
	 */
	public List<Work> findByPraise(int ps) throws SQLException {
		
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("status","=","1"));
//		int ps = PageConstants.POPULARITY_PAGE_SIZE;
		return findByCriteria(exprList,1,ps);

	}
	public List<Work> findByCompose(String work, String student,
			String type, int pc,int ps) throws SQLException {
		List<Expression> exprList = new ArrayList<Expression>();
		String pid = findPid(type);
		String sid = findSid(student);
		exprList.add(new Expression("status","=","1"));
//		exprList.add(new Expression("wname","like","%"+work+"%"));
		exprList.add(new Expression("pid","=",pid));
		exprList.add(new Expression("sid","=",sid));
//		int ps = PageConstants.WORK_PAGE_SIZE;
		return findByCriteria(exprList,pc,ps);
	}
	public Long findSum() throws SQLException{
		String sql = "select count(*) from t_works";
		Long c = (Long) qr.query(sql, new ScalarHandler());
		return c;
		
	}
	
}
