package cn.xiyou.works.student.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import cn.xiyou.works.student.service.StudentService;
import cn.xiyou.works.work.Work;


public class StudentServlet extends HttpServlet {
	StudentService ss = new StudentService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String method = req.getParameter("method");
		System.out.println("�����÷���Ϊ"+method);
		if(method.equals("findByPraise")){
			findByPraise(req, resp);
		}else if(method.equals("findAll")){
			findAll(req, resp);
		}
		else if(method.equals("findByName")){
			findByName(req, resp);
		}else if(method.equals("praise")){
			praise(req, resp);
		}else if(method.equals("stamp")){
			stamp(req, resp);
		}else if(method.equals("findByCompose")){
			findByCompose(req, resp);
		}else if(method.equals("findById")){
			findById(req, resp);
		}else if(method.equals("findByType")){
			findByType(req, resp);
		}
		else if(method.equals("findSum")){
			findSum(req, resp);
		}
		
	}
	
	/**
	 * �������ݿ�����Ʒ����
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findSum(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Long count = ss.findSum();
		
		Map<String,Long> map = new HashMap<String, Long>();
		map.put("count", count);
		System.out.println(JSONArray.fromObject(map));
		//���ؽ��
		resp.setContentType("text/text;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().print(JSONArray.fromObject(map));
		  
	}

	/**
	 * ��ȡ��ǰҳ��
	 * @param req
	 * @return
	 */
	private int getPc(HttpServletRequest req){
		int pc=1;
		String param = req.getParameter("page");
		if(param!=null && !param.trim().isEmpty()){
			try{
				pc = Integer.parseInt(param);
		
			}catch(RuntimeException e){}
		}
		return pc;
	}
	
	/**
	 * ��ȡurl��ҳ���з�ҳ��������Ҫʹ������Ϊ�����ӵ�Ŀ��
	 * @param req
	 * @return
	 */
	private String getUrl(HttpServletRequest req){
		String url=req.getRequestURI()+"?"+req.getQueryString();
		/**
		 * ���url�д���pc��������ȡ���������ù�
		 */
		int index = url.lastIndexOf("&pc=");
		if(index!=-1){
			url = url.substring(0,index);
		}
		return url;
	}
	/**
	 * ��������ϲ�ѯ
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findByCompose(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/**
		 * 1����ȡ�����ݣ���Ʒ���ƣ�ѧ����������Ʒ���ͣ�
		 * 2������service���ҵ��
		 * 3����������
		 */
		//��ҳ
//		String url = getUrl(req);//��ȡurl
		int pc = getPc(req);//��ȡ��ǰҳ��
		
		String work = req.getParameter("wname");
		String student = req.getParameter("sname");
		String type = req.getParameter("tname");
		String page = req.getParameter("limit");
		int ps = Integer.parseInt(page);
		List<Work> list = ss.findByCompose(work,student,type,pc,ps);
		
		//Long sum = ss.findSum();
		
//		list.setUrl(url);
		//���ؽ��
		resp.setContentType("text/text;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().print(JSONArray.fromObject(list));
		
	}
	/**
	 * ͨ�����������Ҳ�����������Ʒ
	 * @param req
	 * @param resp
	 * @return
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
//		String url = getUrl(req);
		List<Work> list = ss.findByPraise(ps);
//		list.setUrl(url);
//		JSONObject jo = JSONObject.fromObject(list);
//		System.out.println(jo);
		JSONArray array = JSONArray.fromObject(list);
		resp.setContentType("text/text;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().print(array);
	}                                                     
	
	/**
	 * ͨ��ID������Ʒ
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findById(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("����findById");
		//��ȡ��Ʒid
		String wid = req.getParameter("wid");
		//����ID���в���
		Work work = ss.finById(wid);
		System.out.println("�˳�findById");
		System.out.println(JSONArray.fromObject(work));
//		����json������
		resp.setContentType("text/text;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().print(JSONArray.fromObject(work));
	}
	/**
	 * ������Ʒ���ͽ��в���
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findByType(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=UTF-8");
		//��ȡ��ǰҳ��
		int pc = getPc(req);
		//��ȡ��ǰUrl
//		String url = getUrl(req);
		//��ȡ��Ҫ���ҵ���Ʒ����
		String type = req.getParameter("type");
		String page = req.getParameter("limit");
		int ps = Integer.parseInt(page);
		//����service���ҵ��
		List<Work> list = ss.findByType(type,pc,ps);
//		list.setUrl(url);
		System.out.println(JSONArray.fromObject(list));
		resp.setContentType("text/text;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().print(JSONArray.fromObject(list));
	}	
	/**
	 * ����
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void praise(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		System.out.println("������޷���");
		//��ȡ��ƷId
		String wid = req.getParameter("wid");
		System.out.println(wid);
		//����ҵ����
		 ss.praise(wid);
		//���سɹ���ʾ
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"success\"").append(":").append("success");
		sb.append("}");
		System.out.println("�˳����޷���");
		resp.setContentType("text/text;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().print(JSONArray.fromObject(sb));
	}
	/**
	 * ��
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void stamp(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		//��ȡ��ƷId
		String wid = req.getParameter("wid");
		//����ҵ����
		ss.stamp(wid);
		//���سɹ���ʾ
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"success\"").append(":").append("success");
		sb.append("}");
		resp.setContentType("text/text;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().print(JSONArray.fromObject(sb));
	}
	/**
	 * ͨ����Ʒ���ƽ��в�ѯ
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findByName(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		int pc = getPc(req);//��ȡ��ǰҳ��
		//��ȡ��ѯ����
		String name = req.getParameter("wname");
		String page = req.getParameter("limit");
		int ps = Integer.parseInt(page);
		System.out.println("���յ�����Ʒ����Ϊ��"+name);
		//���в�ѯ����
		List<Work> list = ss.findByName(name,pc,ps);
		//���ؽ��
		resp.setContentType("text/text;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().print(JSONArray.fromObject(list));
	}
	/**
	 * �������������
	 * @param req��Ʒ
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		int pc = getPc(req);
		String page = req.getParameter("limit");
		int ps = Integer.parseInt(page);
		
//		findSum(req, resp);
		
		List<Work> list = ss.findAll(pc,ps);
		JSONArray array = JSONArray.fromObject(list);
		resp.setContentType("text/text;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.getWriter().print(array);
	}
}
