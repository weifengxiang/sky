package org.sky.sys.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
/**
 * ����걨ģ��
 * @author Administrator
 *
 */
public class DocumentHandler {
	private Configuration configuration = null;

	public DocumentHandler() {
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
	}

	public boolean createDoc(Object obj, String tmpname,String filepath) {
		// Ҫ����ģ��������ļ�
		// ����ģ��װ�÷�����·��,FreeMarker֧�ֶ���ģ��װ�ط�����������servlet��classpath����ݿ�װ�أ�
		// �������ǵ�ģ���Ƿ���com.havenliu.document.template������
		configuration.setClassForTemplateLoading(this.getClass(), "template");
		Template t = null;
		try {
			// test.ftlΪҪװ�ص�ģ��
			t = configuration.getTemplate(tmpname);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// ����ĵ�·�������
		File outFile = new File(filepath);
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
			t.process(obj, out);
			out.flush();
			out.close();
			return true;
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}