package cn.xiyou.works.student.web.servlet;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import cn.itcast.commons.CommonUtils;
import cn.xiyou.works.student.service.UploadService;
import cn.xiyou.works.work.Work;
import net.sf.json.JSONArray;
/**
 * ��Ʒ�ϴ�servlet
 * @author lenovo
 *
 *1�������ַ����룬��Ӧ��ʽ��
 *2����������
 *3����ʱ�ļ����壬�ж��ϴ���С
 *4�������ϴ�·����������
 *5�������ļ��ϴ���������
 *
 *
 *ͼƬ��ǰ
 *��Ʒ�ں�
 */
public class UploadServlet extends HttpServlet {  
	
	
	private UploadService us= new UploadService();//���ύҵ���߼�
	
	private String uploadPath;//�ļ������·��
	private String fileName;//�ļ�����
	private Work form = new Work();//���������
	private Map<String,String> path = new HashMap<String,String>();//�����ļ��洢·����map
	private Map<String,Object> map = new HashMap<String,Object>();//����ǰ��ҳ������ݵ�map
	private String pictureName;
	private Map<String,String> workName = new HashMap<String,String>();;
	/**
	 *  ��Ʒ�ϴ�����
	 */
    public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException { 
    	/**
    	 * 1�������ַ����룬��Ӧ��ʽ��
    	 */
        request.setCharacterEncoding("UTF-8");  //�����ַ�����
        response.setContentType("text/html;charset=UTF-8");//������Ӧ��ʽ  
        PrintWriter out = response.getWriter();  //��ȡ��Ӧ�����
        
        /**
         * 2����������
         */
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);//��ȡ�����Խ����ж�  
        if(!isMultipart){  
            throw new RuntimeException("�������ı���enctype���ԣ�ȷ����multipart/form-data");  
        }  
        /**
         * 3����ʱ�ļ����壬�ж��ϴ���С
         */
        DiskFileItemFactory dfif = new DiskFileItemFactory();  
        ServletFileUpload parser = new ServletFileUpload(dfif);  
        
        parser.setFileSizeMax(100*1024*1024);//���õ����ļ��ϴ��Ĵ�С  
        parser.setSizeMax(1000*1024*1024);//���ļ��ϴ�ʱ�ܴ�С����  
          
        //�� fileupload ���У� HTTP �����еĸ��ӱ�Ԫ�ض�������һ�� FileItem����
        List<FileItem> items = null;  
        try {  
            items = parser.parseRequest(request);//�����������ı������ϴ��ļ�  
        }catch(FileUploadBase.FileSizeLimitExceededException e) {  
            out.write("�ϴ��ļ�������100M");  
            return;  
        }catch(FileUploadBase.SizeLimitExceededException e){  
            out.write("���ļ�������1000M");  
            return;  
        }catch (FileUploadException e) {  
            e.printStackTrace();  
            throw new RuntimeException("�����ϴ�����ʧ�ܣ���������һ��");  
        }  
        /**
         * 4�������ϴ�·����������
         */
        
        PathAndForm(items);
        
        /**
         * 5�������ļ��ϴ��������� 
         */
        if(items!=null){  
            for(FileItem item:items){  
                if(item.isFormField()){  //�ж�����ͨ��
                }else{  //Ϊ�ϴ��ļ�
                    processUploadField(item);  
                }  
            }  
        }  
        //������
        us.saveForm(form);
        //���ͻ��˵���Ӧ   
          
     /*   Map<String,String> map = new HashMap<String, String>();
		map.put("result", "OK");
		//���ؽ��
		response.setContentType("text/text;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(JSONArray.fromObject(map));
		  */
//        out.write("OK");
        response.sendRedirect("upload.html");
    }
    
    
    /**
     * 
     * �����ϴ�·����������
     * @param item
     */
    private void PathAndForm(List<FileItem> items) {  
        //��ǰ��ҳ������ݱ�����
    	if(items!=null){  
            for(FileItem item:items)
            {  
                if(item.isFormField()){  //�ж�����ͨ��
                	processUploadForm(item);
                	
                }else{  //Ϊ�ϴ��ļ�
                	processUploadPath(item);//�����ļ��洢·��         
                }  
            }  
        }
    	//��ǰ��ҳ�����е�ֵ����work�У���װ��bean�б��ڱ��������ݿ���
    	form = CommonUtils.toBean(map, Work.class);
    	//���������ݲ���
    	
    	form.setWid(getID());
//    	form.setWid(CommonUtils.uuid());//������ƷΨһId
    	form.setTime(new Date());
    	form.setStatus(0);//���õ�ǰ��Ʒ״̬Ϊδ����
    	form.setPraise(0);//���õ�ǰ��Ʒ������Ϊ0
    	form.setStamp(0);//���õ�ǰ��Ʒ����Ϊ0
    	System.out.println("form�б���ĵ�ֵַΪ��"+path.get(fileName));
    	form.setAddress(path.get(fileName));//������Ʒ�����ַ
    	if(workName.get("picture")==null||workName.get("picture").equals("")){//���δ�ϴ���Ƭ��������Ĭ�Ϸ���
    		form.setPicture("/picture/surf.jpg");
    	}else
    		form.setPicture(workName.get("picture"));//���÷��汣���ַ
    	
          
      } 
    private String getID(){
    	int i=0;
    	for(int j=0;j<100;j++){
    		i+=(int)((Math.random()*9+1)*100000);
    	}
    	String id = String.valueOf(i);
    	return id;
    }
    /**
     * �����ļ��洢·��
     */
    private void processUploadPath(FileItem item){
    	//�ļ�����
       fileName = item.getFieldName();  
       if(item.getName()==null||item.getName().equals("")){
    	  path.put(fileName, null);
    	  return ;
       }
          /**
         * �����ϴ�·�� 
         */
        
        //�û�ѡ���ϴ��ļ�ʱ  
        if(item.getName()!=null&&!item.getName().equals("")){ 
           //���ݱ��е�name��ȡѧ����ѧ��Ϊ���ļ��������ļ�����
           
        	String childDirectory = "files/"+(String) map.get("number");
//        	String childDirectory = "E:\\files\\"+(String) map.get("number");
        	
            //�����ļ��洢·�� �Ӹ�·����ʼ
            uploadPath = getServletContext().getRealPath("/"+childDirectory);  
//        	uploadPath = childDirectory; 
        	System.out.println("�ϴ�·������Ϊ��"+uploadPath);
            File storeDirectory = new File(uploadPath); //�������ļ�·��Ϊ��������·�� 
            if(!storeDirectory.exists()){  //����ļ��Ƿ��Ѿ�����
                storeDirectory.mkdirs();  
            } 
            uploadPath=uploadPath+"/"+item.getName();
            path.put(fileName, uploadPath);//������·��������map��
            uploadPath=childDirectory+"/"+item.getName();
            workName.put(fileName, uploadPath);
        }
    }
    /**
     * ���ñ�
     * @param item
     */
    private void processUploadForm(FileItem item){
    	/**
    	 * 1����ȡ��ǰ���������ֶμ��ֶ�ֵ
    	 * 2����ֵ������map��
    	 * 3��ʹ��CommonUtils.toBean(),��ֵ��װ��Work�б��ڴ������ݿ�
    	 * 4���ֶ���������ֵ
    	 */
    	String fieldName = item.getFieldName();//�ֶ���  
        String fieldValue;//��Ӧ���ֶ�ֵ  
        try {  
            fieldValue = item.getString("UTF-8");  //��ȡ�˴��������ֶε�ֵ
            map.put(fieldName, fieldValue);//��ֵ������map��
        } catch (UnsupportedEncodingException e) {  
            throw new RuntimeException("��֧��UTF-8����");  
         }  
    }
    
    /**
     * 5�ϴ��ļ�
     */
    private void processUploadField(FileItem item) { 
    	/**
    	 * �ϴ��ļ��������֣���Ʒ�ļ��ͷ���ͼƬ
    	 * 1����ȡ��ǰ�ϴ������ͣ�work/picture(����name)
    	 * 2����ȡ��ǰ���ļ���(�ϴ�ʱ������)
    	 * 3����ȡ��ǰ��·��(���õ�·��
    	 * 4���ϴ�
    	 */
        try {  
        		//1����ȡ�ϴ�����
        		String type = item.getFieldName();//�ϴ��������Ʒ�����
        		String name = item.getName();//��Ʒ����
        		String wpath = path.get(type);//��ȡ��ǰ�ļ��Ĵ洢·��
        		item.write(new File(wpath));//�����ļ���ɾ����ʱ�ļ�  
        } catch (Exception e) {  
            throw new RuntimeException("�ϴ�ʧ��,������");  
        }  
          
    }  
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        doGet(request, response);  
    }  
  
}




/*    *//**
     * �����ϴ��ļ�����
     * �����ϴ��ļ������洢·��
     * @param item
     *//*
    private void processUploadField(FileItem item) {  
        try {  
        	//�ļ���
            String fileName = item.getName();  
              
            *//**
             * 4�������ϴ�·�� 
             *//*
            
            //�û�ѡ���ϴ��ļ�ʱ  
            if(fileName!=null&&!fileName.equals("")){ 
            	//�����ļ���Ϊ����ַ����ͱ�����ļ���
                fileName = UUID.randomUUID().toString()+"_"+FilenameUtils.getName(fileName);  
                  
                //��չ��  ȡԭ�ļ���չ��
                String extension = FilenameUtils.getExtension(fileName);  
                //MIME����  
                String contentType = item.getContentType();  
                
                //�����ļ�����hashCode����洢Ŀ¼  
                String childDirectory = makeChildDirectory(getServletContext().getRealPath("/WEB-INF/files/"),fileName);  
                //�����ļ��洢·�� �Ӹ�·����ʼ
                uploadPath = getServletContext().getRealPath("/WEB-INF/files/"+childDirectory);  
                File storeDirectory = new File(uploadPath); //�������ļ�·��Ϊ��������·�� 
                if(!storeDirectory.exists()){  //����ļ��Ƿ��Ѿ�����
                    storeDirectory.mkdirs();  
                }  
                System.out.println(fileName+"..."+uploadPath);  
                *//**
                 * 5�ϴ�
                 *//*
                item.write(new File(uploadPath+File.separator+fileName));//�����ļ���ɾ����ʱ�ļ�  
                  
            }  
        } catch (Exception e) {  
            throw new RuntimeException("�ϴ�ʧ��,������");  
        }  
          
    }  
    //�����ŵ���Ŀ¼  
    private String makeChildDirectory(String realPath, String fileName) {  
        int hashCode = fileName.hashCode();  
        int dir1 = hashCode&0xf;// ȡ1~4λ  
        int dir2 = (hashCode&0xf0)>>4;//ȡ5~8λ  
          
        String directory = ""+dir1+File.separator+dir2;  
        File file = new File(realPath,directory);  
        if(!file.exists())  
            file.mkdirs();  
          
        return directory;  
    }  
    *//**
     * ������ͨ������
     * @param request
     * @param item
     *//*
    private void processFormField(HttpServletRequest request,FileItem item) {  
     	Map<String,Object> map =new HashMap<String,Object>(); 
     	
    	String fieldName = item.getFieldName();//�ֶ���  
        String fieldValue;  
        try {  
            fieldValue = item.getString("UTF-8");  
        } catch (UnsupportedEncodingException e) {  
            throw new RuntimeException("��֧��UTF-8����");  
         }  
         System.out.println(fieldName+"="+fieldValue);  
   }  
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        doGet(request, response);  
    }  
  
}*/