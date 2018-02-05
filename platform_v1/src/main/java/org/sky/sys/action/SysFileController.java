package org.sky.sys.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.sky.sys.action.BaseController;
import org.sky.sys.exception.ServiceException;
import org.sky.sys.model.SysFile;
import org.sky.sys.model.SysFileExample;
import org.sky.sys.model.SysFileExample.Criteria;
import org.sky.sys.service.SysFileService;
import org.sky.sys.utils.BspUtils;
import org.sky.sys.utils.CommonUtils;
import org.sky.sys.utils.ConfUtils;
import org.sky.sys.utils.JsonUtils;
import org.sky.sys.utils.Page;
import org.sky.sys.utils.PageListData;
import org.sky.sys.utils.ResultData;
import org.sky.sys.utils.StringUtils;
import org.sky.sys.utils.ZipUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller
public class SysFileController extends BaseController{
	@Autowired
	private SysFileService sysfileService;
	
	public SysFileController() {
		// TODO Auto-generated constructor stub
	}
	/**
	*显示附件表列表页面
	**/
	@RequestMapping(value = "/sys/SysFile/initSysFileListPage", method = { RequestMethod.GET })
	public String initSysFileListPage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/file/listsysfile";
	}
	/**
	 * 附件上传
	 */
	@RequestMapping(value = "/sys/SysFile/initSysFileUploadPage", method = { RequestMethod.GET })
	public String initSysFileUploadPage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/file/uploadfile";
	}
	/**
	 * 附件表分页查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/SysFile/getSysFileByPage", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSysFileByPage(
			HttpServletRequest request, 
			HttpServletResponse response){
		String filter = request.getParameter("filter");
		Map filterMap = JsonUtils.json2map(filter);
		String sortfield=request.getParameter("sortfield");
		Page p= super.getPage(request);
		SysFileExample pote= new SysFileExample();
		if(null!=filterMap){
			pote.createCriteria();
			pote.integratedQuery(filterMap);
		}
		
		if(!StringUtils.isNull(sortfield)){
			pote.setOrderByClause(sortfield);
		}
		pote.setPage(p);
		PageListData pageData = sysfileService.getSysFileByPage(pote);
		return JsonUtils.obj2json(pageData);
	}
	/**
	*显示附件表新增页面
	**/
	@RequestMapping(value = "/sys/SysFile/initAddSysFilePage", method = { RequestMethod.GET })
	public String initAddSysFilePage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/file/editsysfile";
	}
	/**
	*显示附件表修改页面
	**/
	@RequestMapping(value = "/sys/SysFile/initEditSysFilePage", method = { RequestMethod.GET })
	public String initEditSysFilePage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/file/editsysfile";
	}
	/**
	*显示附件表详细页面
	**/
	@RequestMapping(value = "/sys/SysFile/initDetailSysFilePage", method = { RequestMethod.GET })
	public String initDetailSysFilePage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/file/detailsysfile";
	}
	/**
	*保存新增/修改附件表
	**/
	@RequestMapping(value = "/sys/SysFile/saveAddEditSysFile", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String saveAddEditSysFile(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			SysFile edit = (SysFile) getEntityBean(request,SysFile.class);
			sysfileService.saveAddEditSysFile(edit);
			rd.setCode(ResultData.code_success);
			rd.setName("保存成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rd.setCode(ResultData.code_error);
			rd.setName("保存失败<br>"+e.getMessage());
		}
		return JsonUtils.obj2json(rd);
	}
	/**
	*删除附件表
	**/
	@RequestMapping(value = "/sys/SysFile/delSysFile", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String delSysFile(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			String delRows=request.getParameter("delRows");
			List de=JsonUtils.json2list(delRows, SysFile.class);
			sysfileService.delSysFileById(de);
			rd.setCode(ResultData.code_success);
			rd.setName("删除成功");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rd.setCode(ResultData.code_error);
			rd.setName("删除失败<br>"+e.getMessage());
		}
		return JsonUtils.obj2json(rd);
	}
	/**
	*根据主键查询附件表
	**/
	@RequestMapping(value = "/sys/SysFile/getSysFileById", method =RequestMethod.GET,produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSysFileById(
			HttpServletRequest request, 
			HttpServletResponse response){
		String id = request.getParameter("id");
		SysFile bean = sysfileService.getSysFileById(id);
		return JsonUtils.obj2json(bean);
	}
		/**
	 * 下载zip格式的文件
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/SysFile/downexcelzip")
	public void downexcelzip(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream fos = null;
		InputStream fis = null;
		String filerealpath = request.getParameter("path");
		String filename1 = filerealpath.substring(filerealpath.indexOf("_") + 1, filerealpath.length());
		String filename = URLDecoder.decode(filename1, "utf-8");
		filerealpath = ConfUtils.getValue("temp_dir") + "\\excel\\"
				+ URLDecoder.decode(filerealpath, "utf-8");

		String zipfile = getFileNameNoEx(filename);
		String zipfileZip = zipfile + ".xlsx";
		// ZipUtils.zip(zipfile, "", filerealpath);
		File downFiles = new File(filerealpath);

		response.setCharacterEncoding("UTF-8");

		try {
			fis = new FileInputStream(downFiles);
			bis = new BufferedInputStream(fis);
			fos = response.getOutputStream();
			bos = new BufferedOutputStream(fos);
			setFileDownloadHeader(request, response, zipfileZip);
			int byteRead = 0;
			byte[] buffer = new byte[8192];
			while ((byteRead = bis.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, byteRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bos.flush();
			fis.close();
			bis.close();
			fos.close();
			bos.close();
			// 删除文件
			downFiles.delete();
			File f = new File(filerealpath);
			f.delete();
		}

	}

	/**
	 * 下载zip格式的文件
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/SysFile/downzip", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public void downZip(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream fos = null;
		InputStream fis = null;
		String filerealpath = request.getParameter("filerealpath");
		String zipfile = getFileNameNoEx(filerealpath) + ".zip";
		ZipUtils.zip(zipfile, "", filerealpath);
		File downFiles = new File(zipfile);

		try {
			fis = new FileInputStream(downFiles);
			bis = new BufferedInputStream(fis);
			fos = response.getOutputStream();
			bos = new BufferedOutputStream(fos);
			setFileDownloadHeader(request, response, downFiles.getName());
			int byteRead = 0;
			byte[] buffer = new byte[8192];
			while ((byteRead = bis.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, byteRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bos.flush();
			fis.close();
			bis.close();
			fos.close();
			bos.close();
			// 删除文件
			downFiles.delete();
			File f = new File(filerealpath);
			f.delete();
		}

	}

	/**
	 * 设置让浏览器弹出下载对话框的Header. 根据浏览器的不同设置不同的编码格式 防止中文乱码
	 * 
	 * @param fileName
	 *            下载后的文件名.
	 */
	public static void setFileDownloadHeader(HttpServletRequest request,
			HttpServletResponse response, String fileName) {
		try {
			// 中文文件名支持
			String encodedfileName = null;
			String agent = request.getHeader("USER-AGENT");
			if (null != agent && -1 != agent.indexOf("MSIE")) {// IE
				encodedfileName = java.net.URLEncoder.encode(fileName, "UTF-8");
			} else if (null != agent && -1 != agent.indexOf("Mozilla")) {
				encodedfileName = new String(fileName.getBytes("UTF-8"),
						"iso-8859-1");
			} else {
				encodedfileName = java.net.URLEncoder.encode(fileName, "UTF-8");
			}
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ encodedfileName + "\"");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取不带后缀的文件名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}
	@RequestMapping(value = "/sys/SysFile/upLoadFiles", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody
	ResultData upLoadFiles(HttpServletRequest request, HttpServletResponse response)
			throws IllegalStateException, IOException {
		ResultData rd = new ResultData();
		try {
			// 创建一个通用的多部分解析器
			CommonsMultipartResolver multipartResolver = (CommonsMultipartResolver)BspUtils.getBean("multipartResolver");
			// 判断 request 是否有文件上传,即多部分请求
			if (multipartResolver.isMultipart(request)) {
				// 转换成多部分request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)multipartResolver.resolveMultipart(request);
				Map<String, Object> paramMap = new HashMap();
				List<SysFile> list = new ArrayList<SysFile>();
				String params = multiRequest.getParameter("data");
				paramMap = JsonUtils.json2map(params);
				// 取得request中的所有文件名
				Iterator<String> iter = multiRequest.getFileNames();
				
				while (iter.hasNext()) {
					// 记录上传过程起始时的时间，用来计算上传时间
					int pre = (int) System.currentTimeMillis();
					// 取得上传文件
					MultipartFile attachfile = multiRequest.getFile(iter.next());
					if (attachfile != null) {
						// 取得当前上传文件的文件名称
						String fileName = attachfile.getOriginalFilename();
						// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
						if (fileName.trim() != "") {
							// 定义上传路径
							String path = ConfUtils.getValue("ATTACHMENT_DIR")
									+ paramMap.get("bizType") + File.separator
									+ paramMap.get("bizCode") + fileName;
							File localFile = new File(path);
							if (!localFile.getParentFile().exists()) {
								localFile.getParentFile().mkdirs();
							}
							attachfile.transferTo(localFile);
							SysFile att = new SysFile();
							att.setSize(new Long(attachfile.getSize()).intValue());
							att.setType(attachfile.getContentType());
							att.setSuffix(fileName.split("\\.")[fileName
									.split("\\.").length - 1]);
							att.setPath(path);
							att.setBizCode((String) paramMap.get("bizCode"));
							att.setBizType((String) paramMap.get("bizType"));
							att.setName(attachfile.getOriginalFilename());
							list.add(att);
						}
					}
					// 记录上传该文件后的时间
					int finaltime = (int) System.currentTimeMillis();
					System.out.println(finaltime - pre);
				}
				sysfileService.saveSysFile(list, null);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rd.setCode(ResultData.code_error);
			rd.setName("上传失败<br>" + e.getMessage());
			return rd;
		}
		rd.setCode(ResultData.code_success);
		rd.setName("上传成功");
		return rd;
	}
	
	@RequestMapping(value = "/sys/fileupload", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public void uploadFile(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("rm") String redirect) {
		ResultData rd = new ResultData();
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 记录上传过程起始时的时间，用来计算上传时间
				int pre = (int) System.currentTimeMillis();
				// 取得上传文件
				MultipartFile attachfile = multiRequest.getFile(iter.next());
				if (attachfile == null) {
					rd.setCode(ResultData.code_error);
					rd.setDesc("上传文件为空！");
				} else {
					String fileName = "";
					File tempDir = new File(
							System.getProperty("java.io.tmpdir") + "/dmsupload");
					if (!tempDir.exists()) {
						tempDir.mkdirs();
					}
					rd.setCode(ResultData.code_success);
					try {
						File tempFile = new File(tempDir.getPath()
								+ "/"
								+ CommonUtils.getCurrentDate("yyyyMMddHHmmss")
								+ CommonUtils.getUUID(6)
								+ "_"
								+ attachfile.getOriginalFilename().substring(
										attachfile.getOriginalFilename()
												.lastIndexOf("\\") + 1));
						OutputStream out = new FileOutputStream(tempFile);
						InputStream in = attachfile.getInputStream();
						StreamUtils.copy(in, out);
						out.close();
						in.close();
						fileName = tempFile.getPath();
					} catch (Exception e) {
						rd.setCode(ResultData.code_error);
						rd.setDesc(e.getMessage());
					}
					rd.setData(fileName);
				}
				if (!StringUtils.isNull(redirect)) {
					request.setAttribute("uploadRlt", rd);
					try {
						request.getRequestDispatcher(redirect).forward(request,
								response);
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("redirect" + redirect + "路径配置错误！");
						e.printStackTrace();
					}
					// return "redirect:" + redirect;
				} else {
					// return JsonUtils.obj2json(rd);
					response.setCharacterEncoding("UTF-8");
					PrintWriter out = null;
					try {
						out = response.getWriter();
						out.write(JsonUtils.obj2json(rd));
						out.flush();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						out.close();
					}
				}
				// 记录上传该文件后的时间
				int finaltime = (int) System.currentTimeMillis();
				System.out.println(finaltime - pre);
			}
		}

	}
	/**
	 * 下载格式的文件
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/SysFile/downfile", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public  @ResponseBody
	ResultData downfile(HttpServletRequest request, HttpServletResponse response)
			throws IllegalStateException, IOException {
		ResultData rd = new ResultData();
		try
		{
		String path = request.getParameter("filerealpath");
		File file = new File(path);// 	
	    String filename = file.getName();// 		 
	    InputStream fis = new BufferedInputStream(new FileInputStream(path));	    
	    byte[] buffer = new byte[fis.available()];
	    fis.read(buffer);	    
	    fis.close();
	    response.reset();
	    // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
	    response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.replaceAll(" ", "").getBytes("utf-8"),"iso8859-1"));	   
	    response.addHeader("Content-Length", "" + file.length());	 
	    OutputStream os = new BufferedOutputStream(response.getOutputStream());	     
	    response.setContentType("application/octet-stream");	    
	    os.write(buffer);// 输出文件	     
	    os.flush();
	    os.close();
	   
	}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rd.setCode(ResultData.code_error);
			rd.setName("下载失败<br>" + e.getMessage());
			return rd;
		}
		
		rd.setCode(ResultData.code_success);
		rd.setName("下载成功");		
		return rd;
	}
}