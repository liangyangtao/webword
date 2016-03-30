package com.unbank.plugin.aspect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.context.ServletContextAware;

import com.unbank.model.HighchartsFile;
import com.unbank.model.UnbankModel;

@Aspect
public class FileReceiver implements ServletContextAware {
	private String basePath;

	public FileReceiver() {
		System.out.println("File Receiver Init!");
	}

	@Override
	public void setServletContext(ServletContext sc) {
		Properties pro = new Properties();
		String realpath = sc.getRealPath("/WEB-INF/classes");
		// 读取配置文件
		try {
			FileInputStream in = new FileInputStream(realpath
					+ "/resources.properties");
			pro.load(in);
			this.basePath = pro.getProperty("nginxPic");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Around("execution(* com.unbank.plugin.service.PluginProxyService.*(..))")
	public Object watchPlugin(ProceedingJoinPoint joinpoint) throws Throwable {
		Object retVal = joinpoint.proceed();
		if (retVal instanceof UnbankModel) {
			UnbankModel um = (UnbankModel) retVal;
			if (um != null) {
				transfer(basePath, um);
				if (um.getTemplate() != null) {
					um.setTemplate(um.getTemplate().replaceAll(
							"(\r\n|\r|\n|\n\r|\t)", ""));
				}
			}
		}
		return retVal;
	}

	public static void transfer(String basePath, UnbankModel um) {
		if (basePath == null) {
			basePath = "d:\\testjson";
		}
		if (um != null && um.getFiles() != null) {
			for (HighchartsFile hf : um.getFiles()) {
				byte[] bytes = hf.getBytes();
				File file = new File(basePath, hf.getSuffix());
				file.getParentFile().mkdirs();
				FileOutputStream fos = null;
				try {
					try {
						fos = new FileOutputStream(file);
						fos.write(bytes);
						fos.flush();
					} finally {
						if (fos != null) {
							fos.close();
						}
					}
				} catch (FileNotFoundException e) {

				} catch (IOException e) {

				}
			}
		}
	}
}
