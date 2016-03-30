/*
 * <p>Title: 知识自动化平台</p>
 * <p>Description: DocMain.java</p>
 * <p>Copyright: Copyright (c) 2015 北京银联信投资顾问有限责任公司，版权所有. </p>
 * <p>Company: 北京银联信投资顾问有限责任公司</p>
 * @author knight
 * @date 2015-5-18
 * @version 1.0
 */
package com.web.homePage.util;

import org.apache.log4j.Logger;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * <p>Title: DocMain</p>
 * <p>Description: TODO</p>
 * @author knight
 * @date 2015-5-18
 */
public class DocMain {

	private Dispatch doc;

    private ActiveXComponent word;

    private Dispatch documents;

    private Dispatch selection;

    private boolean saveOnExit = true;
    
    private static final Logger logger = Logger.getLogger(DocMain.class);
    
    public DocMain(boolean visible) {
        if (word == null) {
            word = new ActiveXComponent("Word.Application");
            word.setProperty("Visible", new Variant(visible));
        }
        if (documents == null)
            documents = word.getProperty("Documents").toDispatch();
    }
    
    public void setSaveOnExit(boolean saveOnExit) {
        this.saveOnExit = saveOnExit;
    }
    
    public void openDocument(String docPath) {
    	doc = Dispatch.invoke(
    			documents,
				"Open",
				Dispatch.Method,
				new Object[] { docPath, new Variant(false),
						new Variant(true) }, new int[1]).toDispatch();
        selection = Dispatch.get(word, "Selection").toDispatch();
    }
    
    public void saveAs(String savePath) {
    	try {
    		 Dispatch.call(
                     doc,
                     "SaveAs", savePath, new Variant(10));
		} catch (Exception e) {
			logger.error("提取目录异常....", e);
		}
    	
    }
    
    public void close() {
        closeDocument();
        if (word != null) {
            Dispatch.call(word, "Quit");
            word = null;
        }
        selection = null;
        documents = null;
    }
    
    public void closeDocument() {
		Dispatch.call(doc, "Close", new Variant(false));
    }
}
