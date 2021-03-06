package cn.xiyou.works.pager;

import java.util.List;
/**
 * 鍒嗛〉bean
 * @author lenovo
 *
 * @param <T>
 */
public class PageBean<T> {
	private int pc;//当前页码 pageCode
	private int tr;//总记录数 totalRecord
	private int ps;//每页记录数 pageSize
	private String url;//请求路径和参数
	private List<T> beanList; 
	
	//记录总页数 totalPage
	public int getTp(){
		int tp = tr / ps;
		return tr % ps == 0 ? tp : tp+1;
	}
	public int getPc() {
		return pc;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	public int getTr() {
		return tr;
	}
	public void setTr(int tr) {
		this.tr = tr;
	}
	public int getPs() {
		return ps;
	}
	public void setPs(int ps) {
		this.ps = ps;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<T> getBeanList() {
		return beanList;
	}
	public void setBeanList(List<T> beanList) {
		this.beanList = beanList;
	}
	
	
}
