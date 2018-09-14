package com.unzen.web.message.web.controller.desk;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.unzen.base.context.Global;
import com.unzen.base.context.UnzenConsts;
import com.unzen.base.lang.Consts;
import com.unzen.base.utils.DateFormatUtils;
import com.unzen.base.utils.FileUtils;
import com.unzen.base.utils.model.DataModel;
import com.unzen.common.core.data.AccountProfile;
import com.unzen.common.core.persist.entity.PostPO;
import com.unzen.common.service.PostService;
import com.unzen.web.message.web.controller.BaseController;

@Controller
@RequestMapping(value="/mytest")
public class UploadFileController extends BaseController{
	
	/*
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PostService poService;
	
	@RequestMapping(value ="/tomytest")
	public String tomytest(){
		return "mytest/upload";
	}
	
	@ResponseBody
	@RequestMapping(method={RequestMethod.POST}, value="uploads")
	public String uploads (int fileCount,int requests,HttpServletRequest request,Model model, MultipartFile[] file){
		AccountProfile up = getSubject().getProfile();
		Long userId = up.getId();
//		List<String> list = Consts.PATHS.get(userId);
		try{
			//文件夹地址
			String fileDir = Consts.FILEPATH;
			File f = new File(fileDir);
			//如果文件夹不存在
			if(!f.exists()){
				f.mkdirs();
			}
			String fileName = null;
			for (int i = 0; i < file.length; i++) {
				fileName = upload(fileDir,file[i]);
				/*if(list==null){//第一次
					list = new ArrayList<String>();
				}
				list.add(fileName);
				Consts.PATHS.put(userId, list);*/
			}
			return fileName;
		}catch(Exception e){
			return null;
		}
	}
	
	@RequestMapping(method={RequestMethod.POST}, value="uploads1")
	@ResponseBody
	public DataModel uploads1(HttpServletRequest request, HttpServletResponse response){
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
		if(!multipartResolver.isMultipart(request)){
			return DataModel.error("请发送Multipart请求");
		}
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
        MultipartFile file  = multiRequest.getFile("file");
        if (null == file) {
        	return DataModel.error("请选择文件");
        }
        //重命名上传后的文件名  
    	String fileExt = StringUtils.getFilenameExtension(file.getOriginalFilename());
    	String fileName = DateFormatUtils.getDate("yyyyMMddHHmmssms") + RandomStringUtils.randomNumeric(10) + "." + fileExt;
    	//定义上传路径  
    	String path = Consts.FILEPATH ;//文件上传路径  + "temp/" + fileName;
    	logger.info("===>>>>创建文件目录：" + path);
        try {
        	if (!FileUtils.createFile(path)) {
        		return DataModel.error("创建文件失败");
        	}
			file.transferTo(new File(path));
			 return DataModel.successMap().add("fileName", fileName);
		} catch (Exception e) {
			return DataModel.error("保存文件失败");
		}  
	}
	
	/**
	 * 单文件上传
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(method={RequestMethod.POST}, value="/upload")
	@ResponseBody
	public String upload(String fileDir,MultipartFile file){
		try {
			String path = Consts.FILEPATH ;//文件上传路径 
			String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			//文件名称
			String fileName = UUID.randomUUID()+suffix;
			//保存文件对象
			File serverFile = new File(path+fileName);
			file.transferTo(serverFile);
			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 批量分片上传文件
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	/*@RequestMapping(method={RequestMethod.POST},value={"/upload"})
	@ResponseBody
	public void upload(HttpServletRequest request, HttpServletResponse response) throws Exception{
//		try {
			String path = request.getParameter("path");
			path = path!=null? java.net.URLDecoder.decode(path, "utf-8") : "";
			// 判断enctype属性是否为multipart/form-data    
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if(isMultipart){
				//创建文件工厂
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload //处理上传的文件的数据，优先保存在缓冲区，如果数据超过了缓冲区大小，
				//则保存到硬盘上，存储在DiskFileItemFactory指定目录下的临时文件。数据都接收完后，
				//它再在从临时文件中将数据写入到上传文件目录下的指定文件中，并删除临时文件。
				ServletFileUpload upload = new ServletFileUpload(factory);
				
				// 得到所有的表单域，它们目前都被当作FileItem 
				List<FileItem> fileForms =upload.parseRequest(request);
				
				StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
				 
				Iterator<String> iterator = req.getFileNames();
				String fileName = "";
				FileItem tempFileItem = null;
				while (iterator.hasNext()) {
//					fileName = file.getOriginalFilename();
					MultipartFile file = req.getFile(iterator.next());
					CommonsMultipartFile cFile = (CommonsMultipartFile) file;
			        DiskFileItem fileItem = (DiskFileItem) cFile.getFileItem();
			        fileName =  fileItem.getFieldName();
			        System.out.println("fileName="+fileName);
				}
				
				
				String id="";
				
				int chunks = 1;// 如果大于1说明是分片处理 
				int chunk = 0;//分片索引，下标从0开始
//				FileItem tempFileItem = null;
				for (FileItem fileItem : fileForms) {
					if(fileItem.getFieldName().equals("id")){
						id = fileItem.getString();
					}else if(fileItem.getFieldName().equals("name")){
						fileName = new String((fileItem.getString()).getBytes("ISO-8859-1"),"UTF-8");
					}else if(fileItem.getFieldName().equals("chunks")){
						chunks = Integer.valueOf(fileItem.getString());
					}else if (fileItem.getFieldName().equals("chunk")) {  
                        chunk = Integer.valueOf(fileItem.getString());  
                    }else if(fileItem.getFieldName().equals("file")){
						tempFileItem = fileItem;
					}
				}
				System.out.println("id="+id+"  filename="+fileName+"  chunks="+chunks+" chunk="+chunk+"  size="+tempFileItem.getSize());
				 String filePath = "F:\\word";//文件上传路径 
				 String tempFileDir = filePath+File.separator+id;
						 //filePath;//+id;
				 //临时目录用来存放所有分片文件
				 File parentFileDir = new File(tempFileDir);
				 if(!parentFileDir.exists()){//如果文件夹不存在
					 parentFileDir.mkdirs();//建立多级文件夹
				 }
				 //分片处理时，前台会多次调用上传接口，每次都会上传文件的一部分到后台
				 File tempParatFile = new File(parentFileDir,fileName+"_"+chunk+".part");
				 FileUtils.copyInputStreamToFile(tempFileItem.getInputStream(), tempParatFile);
				 // 是否全部上传完成
				 // 所有分片都存在才说明整个文件上传完成
				 boolean uploadDone = true;
				 for (int i = 0; i < chunk; i++) {
					 //判断文件是否断片全部存在
					File partFile = new File(tempFileDir,fileName+"_"+chunk+".part");
					if(!partFile.exists()){
						uploadDone = false;
					}
				}
				//所有分片文件都上传完成,将所有分片文件合并到一个文件中 	
				 if(uploadDone){
					 // 得到 destTempFile 就是最终的文件 
					 File destTempFile = new File(filePath,fileName);
					 for (int i = 0; i < chunks; i++) {
						File pathFile = new File(parentFileDir,fileName + "_" + i + ".part");
						FileOutputStream destTempfos = new FileOutputStream(destTempFile,true);
						//遍历"所有分片文件"到"最终文件"中 
						FileUtils.copyFile(pathFile, destTempfos);
						destTempfos.close();
					}
					 String imgPath = destTempFile.getPath();
					 Consts.filepath.add(imgPath);
					 System.out.println("文件路径:"+imgPath);
					 System.out.println("list:"+Consts.filepath);
					 // 删除临时目录中的分片文件 
					 FileUtils.deleteDirectory(parentFileDir);
				 }else{
					// 临时文件创建失败 
					 if(chunk ==chunks-1){
						 FileUtils.deleteDirectory(parentFileDir); 
					 }
				 }
			}
//		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
//		}
	}*/

	@RequestMapping(value="/saveContents")
	@ResponseBody
	public int saveContents(String jsonobj,Model model){
		AccountProfile up = getSubject().getProfile();
		String newJson = jsonobj.replaceAll("&quot;","\"");
		Map<String, Object> map = JSON.parseObject(newJson);
		PostPO po = new PostPO();
		po.setAuthorId(up.getId());
		po.setChannelId((int)map.get("channelId"));
		//内容(摘要不要了)
		po.setSummary(map.get("contents").toString());
		po.setCreated(new Date());
		po.setPicturesPath(map.get("picturesPath").toString());
		int size = poService.save(po);
		return size;
	}
	
	
	private HttpServletRequest getRequest() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@RequestMapping(value="/msg")
	@ResponseBody
	public int messageProcess(){
		return 0;
	}
}
