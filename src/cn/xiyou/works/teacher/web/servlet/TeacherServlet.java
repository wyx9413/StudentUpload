package cn.xiyou.works.teacher.web.servlet;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import cn.xiyou.works.teacher.service.TeacherService;
import cn.xiyou.works.work.Work;

public class TeacherServlet extends HttpServlet {
	TeacherService ts = new TeacherService();
	
	
	public  void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String method = req.getParameter("method");
		System.out.println("�����÷���Ϊ"+method);
		if(method.equals("findById")){
			findById(req, resp);
		}else if(method.equals("select")){
			select(req, resp);
		}else if(method.equals("login")){
			login(req, resp);
		}else if(method.equals("approved")){
			approved(req, resp);
		}else if(method.equals("delete")){
			delete(req, resp);
		}else if(method.equals("findByPraise")){
			findByPraise(req, resp);
		}
	}
	/**
	 * ���ݵ�������ѯ
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findByPraise(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	/**
		 *1������service���в���
		 *2�����ݽ��з�װ������
		 */
		String page = req.getParameter("limit");
		int ps = Integer.parseInt(page);
		List<Work> list = ts.findByPraise(ps);
		JSONArray array = JSONArray.fromObject(list);
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().print(array);
	}            
	
	/**
	 * ͨ��wid�鿴��Ʒ����
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findById(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//��ȡ��ǰ��Ҫ��ѯ��Ʒ��id
		String wid = req.getParameter("wid");
		Work work = ts.findById(wid);//����service�����ҵ��������
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().print(JSONArray.fromObject(work));
		
	}
	/**
	 * ��ѯ��Ʒ
	 * ��ʦ�����������в�ѯ
	 * δ���/�����/ȫ��
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
//	@Test
	public void select(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * ��ȡǰ��json����������
		 * ��ȡ����ѯ������	""��Ϊ����ѯ��ǩ������
		 * δ��ˡ�����ˡ�ȫ�� ��ǩ��name��ȡֵΪselect
		 */
		String condition = req.getParameter("select");
		String p = req.getParameter("page");
		String page = req.getParameter("limit");
		int ps = Integer.parseInt(page);
		int pc = Integer.parseInt(p);
		/**
		 * ����service���ҵ��
		 */
		List<Work> list = ts.select(condition,pc,ps);
		/**
		 * ��ǰ�˷���json����
		 */
		
		
		JSONArray array = JSONArray.fromObject(list);
		resp.setContentType("text/text;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().print(array);
		
        
	}
	/**
	 * ��¼����
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public void login(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//��ȡ������
		String loginname = req.getParameter("loginname");
		String password = req.getParameter("password");
		//����service���ҵ��
		int teacher = ts.login(loginname, password);
		//�����жϣ�������
		Map<String, String> result = new HashMap<String,String>();
//		String result = "{'result':";
		if(teacher==1){//��¼�ɹ��������û���Ϣ������ת���ɹ�ҳ
			/**
			 * ���û������б���
			 */
			req.getSession().setAttribute("teacher", loginname);
//			result+="'success'}";
			result.put("result", "success");
		}else{//��¼ʧ��������ʾ��Ϣ������ת����¼ҳ��ʾ�����µ�¼
			req.setAttribute("msg", "�û������������");
//			result+="'fail'}";
			result.put("result", "fail");

		}
		resp.setContentType("text/text;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().print(JSONArray.fromObject(result));
	}
	/**
	 * ͨ����˹���
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
//	@Test
	public void approved(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1.��ȡ��Ҫͨ������Ʒ��Ӧ��id
		 * 2.����service���ҵ��
		 * 3.ˢ��ҳ�棨��ת����ǰҳ�棩
		 */
		//��ȡid��
		String wid = req.getParameter("wid");
		//����service
		ts.approved(wid);
		//����ҳ��
		Map<String,String> map = new HashMap<String,String>();
		map.put("result", "success");
		resp.setContentType("text/text;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().print(JSONArray.fromObject(map));

//		req.getRequestDispatcher("f:/teacher/success.jsp").forward(req, resp);//ҳ����ת
	}
	/**
	 * ɾ����Ʒ����
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
//	@Test
	public void delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//public void s(){
		/**
		 * ��ȡ��ɾ����Ʒ��id
		 * ����service���д���
		 * ˢ��ҳ��
		 */
		String wid = req.getParameter("wid");//request.getParameter��ȡ���ܹ��������ݣ�name
		//String wid = "3";
		ts.delete(wid);
		Map<String,String> map = new HashMap<String,String>();
		map.put("result", "success");
		resp.setContentType("text/text;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().print(JSONArray.fromObject(map));

//		req.getRequestDispatcher("f:/teacher/success.jsp").forward(req, resp);//ҳ����ת
	}

}
