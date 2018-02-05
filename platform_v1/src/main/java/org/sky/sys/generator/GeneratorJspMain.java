package org.sky.sys.generator;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.sky.sys.generator.consts.GeneratorConsts;
import org.sky.sys.generator.db.AnalysisDB;
import org.sky.sys.generator.model.TableMeta;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * @author
 */
public class GeneratorJspMain {

	public static void main(String[] args) throws IOException,
			TemplateException {
		List<TableMeta> tableList;
		Writer out = null;
		String targetDir = "src/main/webapp/"+GeneratorConsts.TARGET_DIR;

		tableList = AnalysisDB.readDB();
		AnalysisDB.readTables(tableList);
		// 输出到文件
		File dir = new File(targetDir);
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}
		//generatorJspAdd(tableList,targetDir);
		//generatorJsAdd(tableList,targetDir);
		generatorJspList(tableList,targetDir);
		generatorJsList(tableList,targetDir);
		generatorJspEdit(tableList,targetDir);
		generatorJsEdit(tableList,targetDir);
		generatorJspDetail(tableList,targetDir);
		generatorJsDetail(tableList,targetDir);
		generatorController(tableList,GeneratorConsts.BASE_FOLDER+GeneratorConsts.BASE_PACKAGE.replace(".","/")+"/action");
		generatorService(tableList,GeneratorConsts.BASE_FOLDER+GeneratorConsts.BASE_PACKAGE.replace(".","/")+"/service");
	}
	/**
	 * 创建add.jsp
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	private static void generatorJspAdd(List<TableMeta> tableList,String targetDir) 
			throws IOException, TemplateException{
		Writer out = null;
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(GeneratorConsts.BASE_FOLDER+"/org/sky/sys/generator"));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		Template tpl = cfg.getTemplate("templates/addjsp.ftl");
		if (tableList != null) {
			for (TableMeta tm : tableList) {
				if (StringUtils.isBlank(tm.getClassName()))
					continue;
				out = new FileWriter(new File(targetDir + "add"+tm.getClassName().toLowerCase()+".jsp"));
				tpl.process(tm, out);
				System.out.println("===文件 " + "add"+tm.getClassName().toLowerCase()+".jsp"	+ " 生成成功===");
			}
		}

		out.flush();
		out.close();
	}
	private static void generatorJsAdd(List<TableMeta> tableList,String targetDir) 
			throws IOException, TemplateException{
		Writer out = null;
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(GeneratorConsts.BASE_FOLDER+"/org/sky/sys/generator"));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		Template tpl = cfg.getTemplate("templates/addjs.ftl");
		if (tableList != null) {
			for (TableMeta tm : tableList) {
				if (StringUtils.isBlank(tm.getClassName()))
					continue;
				out = new FileWriter(new File(targetDir +"add"+ tm.getClassName().toLowerCase()+".js"));
				tpl.process(tm, out);
				System.out.println("===文件 " +"add"+ tm.getClassName().toLowerCase()+".js"	+ " 生成成功===");
			}
		}

		out.flush();
		out.close();
	}
	/**
	 * 创建list.jsp
	 * @param tableList
	 * @param targetDir
	 * @throws IOException
	 * @throws TemplateException
	 */
	private static void generatorJspList(List<TableMeta> tableList,String targetDir)
			throws IOException, TemplateException{
		Writer out = null;
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(GeneratorConsts.BASE_FOLDER+"/org/sky/sys/generator"));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		Template tpl = cfg.getTemplate("templates/listjsp.ftl");
		if (tableList != null) {
			for (TableMeta tm : tableList) {
				if (StringUtils.isBlank(tm.getClassName()))
					continue;
				out = new FileWriter(new File(targetDir +"list"+tm.getClassName().toLowerCase()+".jsp"));
				tpl.process(tm, out);
				System.out.println("===文件 "+"list"+tm.getClassName().toLowerCase()+".jsp 生成成功===");
			}
		}

		out.flush();
		out.close();
	}
	private static void generatorJsList(List<TableMeta> tableList,String targetDir)
			throws IOException, TemplateException{
		Writer out = null;
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(GeneratorConsts.BASE_FOLDER+"/org/sky/sys/generator"));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		Template tpl = cfg.getTemplate("templates/listjs.ftl");
		if (tableList != null) {
			for (TableMeta tm : tableList) {
				if (StringUtils.isBlank(tm.getClassName()))
					continue;
				out = new FileWriter(new File(targetDir +"list"+tm.getClassName().toLowerCase()+".js"));
				tpl.process(tm, out);
				System.out.println("===文件 "+"list"+tm.getClassName().toLowerCase()+".js 生成成功===");
			}
		}

		out.flush();
		out.close();
	}
	/**
	 * 创建edit.jsp
	 * @param tableList
	 * @param targetDir
	 * @throws IOException
	 * @throws TemplateException
	 */
	private static void generatorJspEdit(List<TableMeta> tableList,String targetDir)
			throws IOException, TemplateException{
		Writer out = null;
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(GeneratorConsts.BASE_FOLDER+"/org/sky/sys/generator"));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		Template tpl = cfg.getTemplate("templates/editjsp.ftl");
		if (tableList != null) {
			for (TableMeta tm : tableList) {
				if (StringUtils.isBlank(tm.getClassName()))
					continue;
				out = new FileWriter(new File(targetDir +"edit"+tm.getClassName().toLowerCase()+".jsp"));
				tpl.process(tm, out);
				System.out.println("===文件 "+"edit"+tm.getClassName().toLowerCase()+".jsp 生成成功===");
			}
		}

		out.flush();
		out.close();
	}
	private static void generatorJsEdit(List<TableMeta> tableList,String targetDir)
			throws IOException, TemplateException{
		Writer out = null;
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(GeneratorConsts.BASE_FOLDER+"/org/sky/sys/generator"));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		Template tpl = cfg.getTemplate("templates/editjs.ftl");
		if (tableList != null) {
			for (TableMeta tm : tableList) {
				if (StringUtils.isBlank(tm.getClassName()))
					continue;
				out = new FileWriter(new File(targetDir + "edit"+tm.getClassName().toLowerCase()+".js"));
				tpl.process(tm, out);
				System.out.println("===文件 "+"edit"+tm.getClassName().toLowerCase()+".js 生成成功===");
			}
		}

		out.flush();
		out.close();
	}
	/**
	 * 创建view.jsp
	 * @param tableList
	 * @param targetDir
	 * @throws IOException
	 * @throws TemplateException
	 */
	private static void generatorJspDetail(List<TableMeta> tableList,String targetDir)
			throws IOException, TemplateException{
		Writer out = null;
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(GeneratorConsts.BASE_FOLDER+"/org/sky/sys/generator"));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		Template tpl = cfg.getTemplate("templates/detailjsp.ftl");
		if (tableList != null) {
			for (TableMeta tm : tableList) {
				if (StringUtils.isBlank(tm.getClassName()))
					continue;
				out = new FileWriter(new File(targetDir + "detail"+tm.getClassName().toLowerCase()+".jsp"));
				tpl.process(tm, out);
				System.out.println("===文件"+"detail"+tm.getClassName().toLowerCase()+".jsp 生成成功===");
			}
		}

		out.flush();
		out.close();
	}
	private static void generatorJsDetail(List<TableMeta> tableList,String targetDir)
			throws IOException, TemplateException{
		Writer out = null;
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(GeneratorConsts.BASE_FOLDER+"/org/sky/sys/generator"));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		Template tpl = cfg.getTemplate("templates/detailjs.ftl");
		if (tableList != null) {
			for (TableMeta tm : tableList) {
				if (StringUtils.isBlank(tm.getClassName()))
					continue;
				out = new FileWriter(new File(targetDir +"detail"+ tm.getClassName().toLowerCase()+".js"));
				tpl.process(tm, out);
				System.out.println("===文件"+"detail"+tm.getClassName().toLowerCase()+".js 生成成功===");
			}
		}

		out.flush();
		out.close();
	}
	
	private static void generatorController(List<TableMeta> tableList,String targetDir) 
			throws IOException, TemplateException{
		Writer out = null;
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(GeneratorConsts.BASE_FOLDER+"/org/sky/sys/generator"));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		Template tpl = cfg.getTemplate("templates/java.controller.ftl");
		if (tableList != null) {
			for (TableMeta tm : tableList) {
				if (StringUtils.isBlank(tm.getClassName()))
					continue;
				out = new FileWriter(new File(targetDir+"/"+tm.getClassName()+"Controller.java"));
				tpl.process(tm, out);
				System.out.println("===文件 " +tm.getClassName()+"Controller.java 生成成功===");
			}
		}

		out.flush();
		out.close();
	}
	private static void generatorService(List<TableMeta> tableList,String targetDir) 
			throws IOException, TemplateException{
		Writer out = null;
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(GeneratorConsts.BASE_FOLDER+"/org/sky/sys/generator"));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		Template tpl = cfg.getTemplate("templates/java.service.ftl");
		if (tableList != null) {
			for (TableMeta tm : tableList) {
				if (StringUtils.isBlank(tm.getClassName()))
					continue;
				out = new FileWriter(new File(targetDir+"/"+tm.getClassName()+"Service.java"));
				tpl.process(tm, out);
				System.out.println("===文件 " +tm.getClassName()+"Service.java 生成成功===");
			}
		}

		out.flush();
		out.close();
	}
	
}