package cn.xiyou.works.work;

import java.util.Date;


public class Work {
	private int sum;
	private String wid;//��Ʒid
	private String address;//��Ʒ���洢��·��
	private Date time;//��Ʒ�ϴ�ʱ��
	private String picture;//��Ʒ����·��
	private String type;//��Ʒ����
	private int status;//��Ʒ״̬��1���ͨ����2δ���
	private int praise;//��Ʒ������
	private int stamp;//��Ʒ��������
	private String introduce;//��Ʒ���
	private String sname;//ѧ������
	private String sclass;//ѧ���༶
	private String number;//ѧ��
	private String wname;//��Ʒ����
	private String pid;//��Ʒ����id
	private String sid;//ѧ��id;
//	private String pictureName;//�ļ����б����ͼƬ�����ư�����׺
//	private String workName;//�ļ�����Ʒ���ư�����׺
	
	public String getSname() {
		return sname;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getSclass() {
		return sclass;
	}
	public void setSclass(String sclass) {
		this.sclass = sclass;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getWid() {
		return wid;
	}
	public void setWid(String wid) {
		this.wid = wid;
	}
	public String getWname() {
		return wname;
	}
	public void setWname(String wname) {
		this.wname = wname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date date) {
		this.time = date;
	}
	//	public String getTime() {
//		return time;
//	}
//	public void setTime(String time) {
//		this.time = time;
//	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPraise() {
		return praise;
	}
	public void setPraise(int praise) {
		this.praise = praise;
	}
	public int getStamp() {
		return stamp;
	}
	public void setStamp(int stamp) {
		this.stamp = stamp;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Work [sum=" + sum + ", wid=" + wid + ", address=" + address
				+ ", time=" + time + ", picture=" + picture + ", type=" + type
				+ ", status=" + status + ", praise=" + praise + ", stamp="
				+ stamp + ", introduce=" + introduce + ", sname=" + sname
				+ ", sclass=" + sclass + ", number=" + number + ", wname="
				+ wname + ", pid=" + pid + ", sid=" + sid + "]";
	}

	
}
