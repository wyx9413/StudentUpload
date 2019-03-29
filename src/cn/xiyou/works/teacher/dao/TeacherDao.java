package cn.xiyou.works.teacher.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.jdbc.TxQueryRunner;
import cn.xiyou.works.pager.Expression;
import cn.xiyou.works.pager.PageConstants;
import cn.xiyou.works.work.Work;

public class TeacherDao {
	QueryRunner qr = new TxQueryRunner();
	
	/**
	 * ͨ��wid������Ʒ���鿴��Ʒ����
	 * @param wid
	 * @return
	 * @throws SQLException
	 */
	public Work findById(String wid) throws SQLException{
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
	 * ��¼,ͨ���û���������в���
	 * @param loginname
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public int login(String loginname,String password) throws SQLException{
		String sql = "select count(*) from t_teacher where loginname=? and password =?";
		Number n = (Number) qr.query(sql, new ScalarHandler(),loginname,password);
		return n.intValue();
	}
	/**
	 * ͨ����˹���
	 * ͨ��id������Ʒ���޸���״ֵ̬��1Ϊ���ͨ����2Ϊδͨ��
	 * @param wid
	 * @throws SQLException 
	 */
	public void approved(String wid) throws SQLException{
		String sql = "update t_works set status=1 where wid=?";
		qr.update(sql,wid);
	}
	/**
	 * ɾ����Ʒ
	 * @param wid
	 * @throws SQLException
	 */
	public void delete(String wid) throws SQLException{
		String sql = "delete from t_works where wid=?";
		qr.update(sql,wid);
	}
	/**
	 * ��ѯ������Ʒ
	 * @return
	 * @throws SQLException
	 */
	public List<Work> selectAll(int pc,int ps) throws SQLException{
		/*String sql = "select wid,wname,sid,address,picture,pid,praise,stamp,introduce from t_works";
		return qr.query(sql, new BeanListHandler<Work>(Work.class));*/
		
		List<Expression> exprList = new ArrayList<Expression>();
//		exprList.add(new Expression("status","=","1"));
//		int ps = PageConstants.WORK_PAGE_SIZE;  //ps,ÿҳ����
		List<Work> list = findByCriteria(exprList,pc,ps);
		return list;

	}
	/**
	 * ������Ʒ״̬���в��ң�δ���/�����
	 * @param status
	 * @return
	 * @throws SQLException
	 */
	public List<Work> selectPassOrUnPass(int status,int pc,int ps) throws SQLException{
		/*String sql = "select wid,wname,sid,address,picture,pid,praise,stamp,introduce from t_works where status=?";
		return qr.query(sql, new BeanListHandler<Work>(Work.class),status);*/
		
		
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("status","=",status+""));
//		int ps = PageConstants.WORK_PAGE_SIZE;  //ps,ÿҳ����
		List<Work> list = findByCriteria(exprList,pc,ps);
		return list;

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
}
