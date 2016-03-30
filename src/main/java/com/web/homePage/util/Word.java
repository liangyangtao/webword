/*
 * <p>Title: 知识自动化平台</p>
 * <p>Description: Word.java</p>
 * <p>Copyright: Copyright (c) 2015 北京银联信投资顾问有限责任公司，版权所有. </p>
 * <p>Company: 北京银联信投资顾问有限责任公司</p>
 * @author knight
 * @date 2015-5-8
 * @version 1.0
 */
package com.web.homePage.util;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * <p>Title: Word</p>
 * <p>Description: TODO</p>
 * @author knight
 * @date 2015-5-8
 */
public class Word {

	//尾页内容
		static  String  text = "本报告采用公开、合法的信息，由北京银联信信息咨询中心（简称银联信）的研究人员运用相应的研究方法，"
				+ "对所研究的对象做出相应的评判，代表银联信观点，仅供用户参考，并不构成任何投资建议。投资者须根据情况自行判断，"
				+ "银联信对投资者的投资行为不负任何责任。\n银联信力求信息的完整和准确，但是并不保证信息的完整性和准确性；"
				+ "报告中提供的包括但不限于数据、观点、文字等信息不构成任何法律证据。如果报告中的研究对象发生变化，我们将不另行通知。"
				+ "\n未获得银联信的书面授权，任何人不得对本报告进行任何形式的发布、复制。如引用、刊发，需注明出处为“北京银联信信息咨询中心”，"
				+ "且不得对本报告进行有悖原意的删节和修改。";
		
		private static Logger logger = Logger.getLogger(Word.class);
//		public  boolean flag = true;
		
		/**
		 * word中需要替换成图片的地方
		 */
		public static final String TEXT = "{reportChart}";

		/**
		 * Html to Word
		 * 
		 * @param html
		 * @param docFile
		 */
		public synchronized static boolean htmlToWord(File html, File docFile,String picPath, String  title) {
			boolean flag = true;
			logger.info("html to word, open word!");
//			logger.info("---37---picPath---"+picPath);
			System.out.println("title: " + title);
			if (docFile.exists()) {
				docFile.delete();
			}

			ActiveXComponent app = new ActiveXComponent("Word.Application");
			Dispatch doc= null;
			try {
				app.setProperty("Visible", new Variant(false));
				Dispatch docs = app.getProperty("Documents").toDispatch();

				doc = Dispatch.invoke(
						docs,
						"Open",
						Dispatch.Method,
						new Object[] { html.getAbsolutePath(), new Variant(false),
								new Variant(true) }, new int[1]).toDispatch();
				//设置页边距
//				Dispatch pageSetup = Dispatch.get(doc, "PageSetup").toDispatch();
//				Dispatch.put(pageSetup, "LeftMargin", "0");      
//		        Dispatch.put(pageSetup, "RightMargin", "0");      
//		        Dispatch.put(pageSetup, "TopMargin", "0");      
//		        Dispatch.put(pageSetup, "BottomMargin", "0");
				
				/*Dispatch startRange = Dispatch.call(doc, "Range", new Variant(0), new Variant(0)).
			             toDispatch();
				Dispatch selection = Dispatch.get(app.getObject(), "Selection").toDispatch();
				
				Dispatch toc = Dispatch.get(doc,"TablesOfContents").toDispatch();//目录
				Dispatch tocIndex = Dispatch.call(toc,"Add",startRange,new Variant(true),new Variant(1), new Variant(3),new Variant(true)).toDispatch();
				
				Dispatch font = Dispatch.get(selection, "Font").toDispatch();
			      Dispatch.put(font,"Color",new Variant(16711680));
			      Dispatch.put(font,"Bold",new Variant(true));
			      Dispatch.put(font,"Size",new Variant(20));
			      Dispatch.call(selection, "TypeText", "目录");
			      Dispatch.call(selection,  "InsertBreak" ,  new Variant(9) );*/
				
			      generateIndexBlock(app,doc);
			      
		        //word中插入图片
		        if(picPath != null){
		        	insertPicture(doc,picPath);
					
//					Dispatch.call(startRange,  "InsertBreak" ,  new Variant(7) );//分页符
		        	 
		        	/*Dispatch adjustments = Dispatch.call(doc,"Adjustments").toDispatch();
		        	Dispatch parent = Dispatch.get(adjustments,"Parent").toDispatch();
		        	Dispatch.put(parent,"Text","这是一个封面标题"); //插入内容
		        	Dispatch.call(selection, "HomeKey", new Variant(6));*/
			        /*Dispatch picture = Dispatch.call(Dispatch.get(selection,"InLineShapes").toDispatch(),"AddPicture", picPath).toDispatch(); // 添加图片
			        Dispatch.call(picture, "Select"); // 选中图片
			        Dispatch.put(picture, "Width", new Variant(595)); // 图片的宽度
			        Dispatch.put(picture, "Height", new Variant(842)); // 图片的高度
			        Dispatch ShapeRange = Dispatch.call(picture, "ConvertToShape").toDispatch(); // 取得图片区域
			        Dispatch WrapFormat = Dispatch.get(ShapeRange, "WrapFormat").toDispatch(); // 取得图片的格式对象
			        Dispatch.put(WrapFormat, "Type", 5); // 设置环绕格式（0 - 7）下面是参数说明
			        //图片相对页面的位置
			        Dispatch.call(ShapeRange, "Select"); // 选中图片
			        Dispatch.put(ShapeRange,"RelativeHorizontalPosition",1);
			        Dispatch.put(ShapeRange,"Left",0);
			        Dispatch.put(ShapeRange,"RelativeVerticalPosition",1);
			        Dispatch.put(ShapeRange,"Top",0);*/
		        }		    
				//添加尾页
		        /*
		        Dispatch  selection = insertBreak(moveEnd(app));
		        setParagraphsProperties(selection, 1, 0, 22, 3, 0);
		        insertText(selection, "免责声明");
		        find(app, selection, "免责声明");
		        setFont(selection, true, false, false, null, "18", "宋体");
		        selection = moveEnd(app);
		        enter(selection);
		        setParagraphsProperties(selection, 0, 1, 1, 1, 2);
		        setFont(selection, false, false, false, null, "12", "宋体");
		        insertText(selection, text);
		        */
				//页眉，页脚处理////////////////////////
		        //LL添加
		        boolean  bz = true;
		        logger.info("进入页眉页脚设置：");
		        try{
			        setHeader(app, "C:\\1.png", "北京银联信科技股份有限公司 ", title, "", "");
			    	setFooter(app, doc, "http：//www.unbank.info", "服务电话：（010）63368810", false);
		    	}catch(Exception  e){
		    		logger.error(e.getMessage());
		    		bz = false;
		    	}
		        logger.info("页眉页脚设置完毕！结果："+bz);
		        /*ll注销
				  Dispatch ActiveWindow = app.getProperty("ActiveWindow")
				    .toDispatch();
				  
				  Dispatch ActivePane = Dispatch.get(ActiveWindow, "ActivePane")
				    .toDispatch();
				  Dispatch selection = Dispatch.get(app, "Selection").toDispatch();
				  Dispatch View = Dispatch.get(ActivePane, "View").toDispatch();
				  Dispatch align = Dispatch.get(selection, "ParagraphFormat").toDispatch();

				  Dispatch.put(View, "SeekView", "9"); //9是设置页眉
				  Dispatch.put(align, "Alignment", "3"); // 置中
				  Dispatch.put(selection, "Text", "北京银联信投资顾问有限责任公司"); // 初始化时间
//				  Dispatch.put(View, "SeekView", "10"); // 10是设置页脚
//				  Dispatch.put(align, "Alignment", "2"); // 靠左
//				  Dispatch.put(selection, "Text", "电话：010-63368810"); // 初始化从1开始
//				  Dispatch.call(selection, "MoveLeft");
//				  Dispatch align2 = Dispatch.get(selection, "ParagraphFormat").toDispatch();
//				  Dispatch.put(align2, "Alignment", "2"); // 靠右
//				  Dispatch.put(selection, "Text", "这里是页脚"); // 初始化从1开始
				  
				  Dispatch section = Dispatch.call(doc,"Sections",new Variant(1)).getDispatch();
			      Dispatch footer = Dispatch.call(section,"Footers",new Variant(1)).getDispatch();
			      Dispatch pageNumbers = Dispatch.get(footer,"PageNumbers").toDispatch();
			      Dispatch.put(pageNumbers,"RestartNumberingAtSection",new Variant(true));
//			      Dispatch.call(pageNumbers,"Add",new Variant(1), new Variant(false));
			      Dispatch.put(pageNumbers,"StartingNumber",new Variant(0));
			      Dispatch.call(pageNumbers,"Add",new Variant(1), new Variant(false));

				  */
				  /*Dispatch sk = Dispatch.get(View, "SeekView").toDispatch();
				  Dispatch pn = Dispatch.get(sk,"PageNumber").toDispatch();
				  Dispatch.put(pn,"StartingNumber",new Variant(10));*/

//				Dispatch wdTocFormat = Dispatch.get(tocIndex,"WdTocFormat").toDispatch();
				
//				Dispatch.put(toc,"Format",new Variant(4));
				
//				Dispatch.put(toc,"UseOutlineLevels",new Variant(true));
//				generateIndexBlock(doc);//插入目录
				
		        //设置视图类型
		        setView(app, 3);
		        
		        Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] {
						docFile.getAbsolutePath(), new Variant(0) }, new int[1]);
				Dispatch.call(doc, "Close", new Variant(false));
			} catch (Exception e) {
				logger.error("html to word error!", e);
				flag = false;
				
			} finally {
				logger.info("quit word!");
				if(!flag){
					Dispatch.call(doc, "Close", new Variant(false));
				}
				app.invoke("Quit", new Variant[] {});
				ComThread.Release();
				logger.info("Release word!");
				
			}
			return flag;
		}
		
		//设置视图类型
		//@param word:word运行程序对象
		//@param wdViewType:int类型：3--页面视图
	    public  static  void  setView(ActiveXComponent  word, int  wdViewType){
	    	Dispatch  activeWindow = word.getProperty("ActiveWindow").toDispatch();
	    	Dispatch  view = Dispatch.get(activeWindow, "View").toDispatch();
	    	Dispatch.put(view, "Type", new  Variant(wdViewType));
	    }
		
		//把插入点移到文件尾
	    public  static  Dispatch  moveEnd(ActiveXComponent  word){
	    	Dispatch  selection = Dispatch.get(word, "Selection").toDispatch();
	    	Dispatch.call(selection, "EndKey", new Variant(6));
	    	return   selection;
	    }
	    
	    //换行
	    public  static  void  enter(Dispatch  selection) {
	    	Dispatch.call(selection, "TypeParagraph"); 
	    }
	    
	    //插入分页符不分节
	    public  static  Dispatch  insertBreak(Dispatch  selection){
	    	if (selection == null){
	    		System.out.println("插入点为空！");
	    		logger.info("插入点为空！");
	    	}
	    	Dispatch.call(selection, "InsertBreak", new Variant(7));
	    	return   selection;
	    }
	    
	    /**
	     * 在当前插入点插入字符串
	     * @param newText 要插入的新字符串
	     */
	    public  static  void  insertText(Dispatch  selection, String  newText) {
	    	if (selection != null)
		        Dispatch.put(selection, "Text", newText); 
	    }
	    
	    //设置段落格式
	    public  static  void  setParagraphsProperties(Dispatch  selection, int alignment, int lineSpaceingRule, int lineUnitBefore, 
	    		int lineUnitAfter, int characterUnitFirstLineIndent){
		    Dispatch paragraphs = Dispatch.get(selection, "Paragraphs").toDispatch();
		    Dispatch.put(paragraphs, "Alignment", new Variant(alignment)); //对齐方式
		    Dispatch.put(paragraphs, "LineSpacingRule", new Variant(lineSpaceingRule)); //行距
		    Dispatch.put(paragraphs, "LineUnitBefore", new Variant(lineUnitBefore)); //段前
		    Dispatch.put(paragraphs, "LineUnitAfter", new Variant(lineUnitAfter)); //段后
		    Dispatch.put(paragraphs, "CharacterUnitFirstLineIndent",new Variant(characterUnitFirstLineIndent)); //首行缩进字符数
		}
	    
	    //设置字体
	    public  static  void  setFont(Dispatch  selection, boolean  bold, boolean  italic, boolean  underLine, 
	    		String colorSize, String size, String name) { 
	    	//if (selection == null)
	          //  selection = Dispatch.get(word, "Selection").toDispatch();
	        Dispatch font = Dispatch.get(selection, "Font").toDispatch();  
	        Dispatch.put(font, "Name", new Variant(name));  //字体名称 
	        Dispatch.put(font, "Bold", new Variant(bold));  //粗体
	        Dispatch.put(font, "Italic", new Variant(italic));  //斜体
	        Dispatch.put(font, "Underline", new Variant(underLine));  //下划线 
	        Dispatch.put(font, "Color", colorSize);  //字体颜色
	        Dispatch.put(font, "Size", size);  //字体大小
	    }
	    
		//统计空格:!不精确!仅限于中英文、ASCII(含扩展)码，页脚页眉默认大小(9号)
		static  int  countSpace(String  str, int  ws){
	    	int  numOfPlace = 0;//word中字符所占空间
	    	for(int  i=0;  i<str.length();  i++){
	    		if(str.charAt(i) > 255)
	    			numOfPlace += 2;
	    		else
	    			numOfPlace++;
	    	}
	    	return  ws-numOfPlace;
	    }
		//统计空格:!不精确!仅限于中英文、ASCII(含扩展)码，页脚页眉默认大小(9号)
		static  int  addSpace(String  str, int  ws){
	    	float  numOfPlace = 0;//word中字符所占空间
	    	for(int  i=0;  i<str.length();  i++){
	    		if(str.charAt(i) > 255)
	    			numOfPlace += 2.08;
	    		else
	    			numOfPlace++;
	    	}
	    	System.out.println("numOfPlace="+numOfPlace);
	    	return  (int) (ws-numOfPlace);
	    }
	    
	    public  static  void  setAlignment(ActiveXComponent word, Dispatch  selection, int  align){
	    	if (selection == null)
	            selection = Dispatch.get(word, "Selection").toDispatch();
	    	Dispatch  Paras = Dispatch.get(selection, "Paragraphs").toDispatch();
	    	Dispatch.put(Paras, "Alignment", new Variant(align));
	    }
	    
	  //在当前插入点插入图片并设置图片大小--简版
	    public  static  void  insertImageAndSet(Dispatch  selection, String  imagePath, int  width, int  height){
	    	Dispatch  picture = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
	    			"AddPicture", imagePath).toDispatch(); //添加图片
	    	//Dispatch.call(picture, "Select"); //选中图片
	    	Dispatch.put(picture, "Width", new Variant(width)); //图片的宽度
	    	Dispatch.put(picture, "Height", new Variant(height)); //图片的高度
	    }
	    
	  //从选定内容或插入点开始查找文本
	    public  static  boolean  find(ActiveXComponent word, Dispatch  selection, String  toFindText){
	    	if (toFindText == null || toFindText.equals(""))  
	    		return false;
	    	//从selection所在位置开始查询
	    	@SuppressWarnings("static-access")
			Dispatch  find = word.call(selection, "Find").toDispatch();
	    	//设置要查找的内容
	    	Dispatch.put(find, "Text", toFindText);
	    	//向前查找(在文本中是向后:在选定范围中是从前往后或从插入点开始往后)
	    	Dispatch.put(find, "Forward", "True");
	    	//设置格式
	    	Dispatch.put(find, "Format", "True");
	    	//大小写匹配
	    	Dispatch.put(find, "MatchCase", "True");
	    	//全字匹配
	    	Dispatch.put(find, "MatchWholeWord", "True");
	    	//查找并选中
	    	return  Dispatch.call(find, "Execute").getBoolean();
	    }
	    
	  //设置字体
	    public  static  void  setFont(ActiveXComponent word, Dispatch  selection, boolean  bold, boolean  italic, boolean  underLine, 
	    		String colorSize, String size, String name) { 
	    	if (selection == null)
	            selection = Dispatch.get(word, "Selection").toDispatch();
	        Dispatch font = Dispatch.get(selection, "Font").toDispatch();  
	        Dispatch.put(font, "Name", new Variant(name));  //字体名称 
	        Dispatch.put(font, "Bold", new Variant(bold));  //粗体
	        Dispatch.put(font, "Italic", new Variant(italic));  //斜体
	        Dispatch.put(font, "Underline", new Variant(underLine));  //下划线 
	        Dispatch.put(font, "Color", colorSize);  //字体颜色
	        Dispatch.put(font, "Size", size);  //字体大小
	    }
	    
	    //设置页眉 @param str3、str4 用于设置字体的子串 str2:页眉 右边字段
	    public  static  void  setHeader(ActiveXComponent  word,  String  imagePath, String  str1, String  str2, String  str3, String  str4){
	    	//取得活动窗体对象
	        Dispatch  activeWindow = word.getProperty("ActiveWindow").toDispatch();
	        //取得活动窗格对象
	        Dispatch  activePane = Dispatch.get(activeWindow, "ActivePane").toDispatch();
	        //取得视窗对象
	        Dispatch  view = Dispatch.get(activePane, "View").toDispatch();
	        
	        Dispatch.put(view, "SeekView", "9");//9-代表页眉
	        //当前页眉位置
	        Dispatch  selection = word.getProperty("Selection").toDispatch();
	        
	        //清除原页眉内容
	        if(imagePath != null || str1 != null || str2 != null){
		        Dispatch  headerFooter = Dispatch.get(selection, "HeaderFooter").toDispatch(); 
		        Dispatch  ymrange = Dispatch.get(headerFooter, "Range").toDispatch();
		        Dispatch.put(ymrange, "Text", "");
	        }
	        setAlignment(word, selection, 0); //设置对齐格式为居左
	        StringBuilder  sbd = new  StringBuilder(0);
	        //插入logo
	        if(imagePath != null && !imagePath.equals("")){
	        	insertImageAndSet(selection, imagePath, 18, 18);
	        	//sbd.append(" ");
	        }
	        //拼接字符串
	        int  numOfSpace; //需要添加的空格数
	        if(str1 != null && !str1.equals("")){
	        	sbd.append(str1);
	        	numOfSpace = countSpace(str1, 34);
	        	for(int  i=0;  i<numOfSpace;  i++)
	            	sbd.append(" ");
	        }
	        if(str2 != null && !str2.equals("")){
	        	numOfSpace = addSpace(str2, 53);
	        	for(int  i=0;  i<numOfSpace;  i++)
	            	sbd.append(" ");
	        	sbd.append(str2);
	        }
	        //插入字符串
	        if(sbd.length() > 0)
	        	Dispatch.put(selection, "Text", sbd.toString());
	        
	        //给子串设置格式
	        if(str3 != null && !str3.equals("")){
	        	find(word, selection, str3);
	        	setFont(word, selection, true, false, false, null, "9", null);
	        }
	        if(str4 != null && !str4.equals("")){
	        	find(word, selection, str4);
	        	setFont(word, selection, true, false, false, "1,0,0,0", "9", null);
	        }
	        	
	        //恢复视图
	        Dispatch.put(view, "SeekView", new Variant(0));
	    }
	    
	    //设置页脚及页码
	    //@param allP 是否显示总页数
	    public  static  void  setFooter(ActiveXComponent  word, Dispatch  doc, String  str1, String  str2, boolean  allP){
	    	//取得活动窗体对象
	        Dispatch  activeWindow = word.getProperty("ActiveWindow").toDispatch();
	        //取得活动窗格对象
	        Dispatch  activePane = Dispatch.get(activeWindow, "ActivePane").toDispatch();
	        //取得视窗对象
	        Dispatch  view = Dispatch.get(activePane, "View").toDispatch();
	        //10是设置页脚
	        Dispatch.put(view, "SeekView", "10");
	        
	        final  Dispatch  sections = Dispatch.get(doc, "Sections").toDispatch();
	        final  Dispatch  item = Dispatch.call(sections, "Item", new Variant(1)).toDispatch();
	        final  Dispatch  footer = Dispatch.get(item, "Footers").toDispatch();
	        final  Dispatch  fi = Dispatch.call(footer, "Item", new Variant(1)).toDispatch();
	        final  Dispatch  range = Dispatch.get(fi, "Range").toDispatch();
	        final  Dispatch  fields = Dispatch.get(range, "Fields").toDispatch();
	        
	        Dispatch  selection = word.getProperty("Selection").toDispatch();
	        
	        Dispatch  paragraphFormat = (Dispatch)Dispatch.get(selection, "ParagraphFormat").getDispatch();
	        
	        if(str1 != null || str2 != null){
	        	//清除原页脚内容
	        	Dispatch.put(range, "Text", "");
	        	Dispatch.put(paragraphFormat, "Alignment", 1); //设置对齐格式为居中
	        }
	        
	        StringBuilder  sbd = new  StringBuilder(0);
	        int  numOfSpace; //需要添加的空格数
	        
	        if(str1 != null && !str1.equals("")){
	        	sbd.append(str1);
	        	numOfSpace = countSpace(str1, 45);
	        	for(int  i = 0;  i < numOfSpace;  i++)
	        		sbd.append(" ");
	        	Dispatch.call(selection, "TypeText", sbd.toString());
	        }
	        Dispatch.call(fields, "Add", Dispatch.get(selection, "Range").toDispatch(), new Variant(-1),
	        		"Page", true).toDispatch();
	        if(allP){
		        Dispatch.call(selection, "TypeText", "/");
		        Dispatch.call(fields, "Add", Dispatch.get(selection, "Range").toDispatch(), new Variant(-1),
		        		"NumPages", true).toDispatch();
	        }
	        if(sbd.length() > 0)
	        	sbd.setLength(0);
	        if(str2 != null && !str2.equals("")){
	        	numOfSpace = countSpace(str2, 45);
	        	for(int  i = 0;  i < numOfSpace;  i++)
	        		sbd.append(" ");
	        	sbd.append(str2);
	        	Dispatch.call(selection, "TypeText", sbd.toString());
	        }
	        
	        //恢复视图
	        Dispatch.put(view, "SeekView", new Variant(0));
	    }

		/**
		 * 报表中插入相关图表
		 * 
		 * @param docFile
		 *            doc文件
		 * @param imgPath
		 *            图表目录
		 */
		public synchronized static void insertChart(File docFile, File imgPath) {
			ActiveXComponent objWord = new ActiveXComponent("Word.Application");
			Dispatch document = null;
			try {
				// Assign a local word object
				Dispatch wordObject = objWord.getObject();

				// Create a Dispatch Parameter to hide the document that is opened
				Dispatch.put(wordObject, "Visible", new Variant(false));
				 

				// Instantiate the Documents Property
				Dispatch documents = objWord.getProperty("Documents").toDispatch();

				// Open a word document, Current Active Document
				document = Dispatch.call(documents, "Open",
						docFile.getAbsolutePath()).toDispatch();
				
				// selection
				Dispatch selection = Dispatch.get(wordObject, "Selection")
						.toDispatch();

				// search condition
				Dispatch find = Dispatch.get(selection, "Find").toDispatch();
				Dispatch.put(find, "Text", TEXT);

				//
				Dispatch chart = Dispatch.get(selection, "InLineShapes")
						.toDispatch();

				// loop
				String img = imgPath.getAbsoluteFile() + File.separator;
				String suffix = ".jpg";
				for (int i = 0; i < imgPath.listFiles().length; i++) {
					if (Dispatch.call(find, "Execute").getBoolean()) {
						Dispatch.call(chart, "AddPicture", img + i + suffix);
					}
				}
				

				 
				Dispatch.call(document, "Save");
				Dispatch.call(document, "Close");
				
			} catch (Exception e) {
				logger.error("insertChart error!",e);
//				flag = false;
				
			} finally {
//				if(document != null){
//					Dispatch.call(document, "Close");
//				}
				objWord.invoke("Quit", new Variant[] {});
				ComThread.Release();
			}
		}

		public synchronized static void insertChart(File docFile, List<File> lists) {
			ActiveXComponent objWord = new ActiveXComponent("Word.Application");
			Dispatch document = null;
			try {
				// Assign a local word object
				Dispatch wordObject = objWord.getObject();

				// Create a Dispatch Parameter to hide the document that is opened
				Dispatch.put(wordObject, "Visible", new Variant(false));

				// Instantiate the Documents Property
				Dispatch documents = objWord.getProperty("Documents").toDispatch();

				// Open a word document, Current Active Document
				document = Dispatch.call(documents, "Open",
						docFile.getAbsolutePath()).toDispatch();

				// selection
				Dispatch selection = Dispatch.get(wordObject, "Selection")
						.toDispatch();

				// search condition
				Dispatch find = Dispatch.get(selection, "Find").toDispatch();
				Dispatch.put(find, "Text", TEXT);

				//
				Dispatch chart = Dispatch.get(selection, "InLineShapes")
						.toDispatch();

				for (int i = 0; i < lists.size(); i++) {
					if (Dispatch.call(find, "Execute").getBoolean()) {
						if (lists.get(i).exists()) {
							Dispatch.call(chart, "AddPicture", lists.get(i)
									.getAbsolutePath());
						}
						// System.out.println(lists.get(i).getAbsolutePath());
					}
				}
				//更新页码
				Dispatch toc = Dispatch.call(document, "TablesOfContents", new Variant(1)).toDispatch();
		    	Dispatch.call(toc, "UpdatePageNumbers");

				Dispatch.call(document, "Save");
				Dispatch.call(document, "Close");
			} catch (Exception e) {
				logger.error("insertChart ERROR!", e);
//				flag = false;
				
			} finally {
//				if(document != null){
//					Dispatch.call(document, "Close");
//				}
				objWord.invoke("Quit", new Variant[] {});
				ComThread.Release();
			}
		}
		
		//编写封面参数
		public synchronized static void createHomePage(File docFile,Map<String,Object> map) {
			ActiveXComponent objWord = new ActiveXComponent("Word.Application");
			Dispatch document = null;
			try {
				// Assign a local word object
				Dispatch wordObject = objWord.getObject();

				// Create a Dispatch Parameter to hide the document that is opened
				Dispatch.put(wordObject, "Visible", new Variant(false));

				// Instantiate the Documents Property
				Dispatch documents = objWord.getProperty("Documents").toDispatch();

				// Open a word document, Current Active Document
				document = Dispatch.call(documents, "Open",
						docFile.getAbsolutePath()).toDispatch();

				// selection
				Dispatch selection = Dispatch.get(wordObject, "Selection")
						.toDispatch();
				//主标题
				addTextFrameOverCover(document,objWord,selection,map.get("mainX"),map.get("mainY"),map.get("mainTitleFontSize"),map.get("mainTitle"),map.get("mainW"),map.get("mainH"));
				//副标题
				addTextFrameOverCover(document,objWord,selection,map.get("subX"),map.get("subY"),map.get("subTitleFontSize"),map.get("subTitle"),map.get("subW"),map.get("subH"));
				//区域
				addTextFrameOverCover(document,objWord,selection,map.get("areaX"),map.get("areaY"),map.get("areaNameFontSize"),map.get("areaName"),map.get("areaW"),map.get("areaH"));
				
				Dispatch.call(document, "Save");
				Dispatch.call(document, "Close");
				
			} catch (Exception e) {
//				logger.error("createHomePage ERROR!", e);
//				flag = false;
			}finally{
//				if(!flag){
//					Dispatch.call(document, "Close");
//				}
				objWord.invoke("Quit", new Variant[] {});
				ComThread.Release();
			}
//			return flag;
		}
		   
		   //插入目录
		   private static void generateIndexBlock(ActiveXComponent word,Dispatch doc) throws Exception {
//		        Dispatch range = Dispatch.get(selection, "Range").toDispatch();
//		        Dispatch.call(selection,"Collapse",new Variant(1));
//		        Dispatch template = Dispatch.get(doc,"AttachedTemplate").toDispatch();
//		        Dispatch blockEntries = Dispatch.get(template,"BuildingBlockEntries").toDispatch();
//		        Dispatch.call(blockEntries,"Add","Index",new Variant(14),"Index",range,new Variant("Index"),new Variant(2));
		        Dispatch startRange = Dispatch.call(doc, "Range", new Variant(0), new Variant(0)).
		                toDispatch();
		        Dispatch.call(startRange,  "InsertBreak" ,  new Variant(7) );
		        Dispatch toc = Dispatch.get(doc,"TablesOfContents").toDispatch();
		        /*Dispatch tocIndex =  Dispatch.call(toc,"Add",Dispatch.call(doc,"Range",new Variant(0),new Variant(0)).
		                toDispatch(),new Variant(true),new Variant(1), new Variant(3),new Variant(true))
		                .toDispatch();*/
		        Dispatch tocIndex = Dispatch.call(toc,"Add",
		        		Dispatch.call(doc,"Range",new Variant(0),new Variant(0)).toDispatch(),   //目录出现的区域。如果该区域未折叠，目录将替换该区域。
		        		new Variant(true),   //如果为 True，则使用内置标题样式创建目录。默认值为 True。
		        		new Variant(1),      //目录的起始标题级别。对应于使用 \o 开关的目录 (TOC) 域的起始值。默认值为 1。
		        		new Variant(3),      //目录的结束标题级别。对应于使用 \o 开关的目录 (TOC) 域的结束值。默认值为 9。
		        		new Variant(true),   //如果目录项 (TC) 域用于创建目录，则为 True。可用 MarkEntry 方法标记要包括在目录中的各项。默认值为 False。
		        		new Variant("A"),    //用于从 TC 域创建目录的单字母标识符。对应于目录 (TOC) 域中的 \f 开关。例如，“T”表示使用表格标识符 T 从 TC 域建立目录。如果省略此参数，则不使用 TC 域。
		        		new Variant(true),   //如果目录中的页码与右边距对齐，则为 True。默认值为 True。
		        		new Variant(true),   //如果为 True，则在目录中包含页码。默认值为 True。
		        		new Variant(""),     //用来编译目录的其他样式的字符串名称（标题 1 – 标题 9 样式之外的样式）。可用 HeadingStyles 对象的 Add 方法创建新的标题样式。
		        		new Variant(true),   //如果在文档被发布到网站上时，目录中的各项应转为超链接格式，则为 True。默认值为 True。
		        		new Variant(true),   //如果在文档被发布到网站上时，目录中的页码应隐藏，则为 True。默认值为 True。
		        		new Variant(true)    //如果为 True，则使用大纲级别创建目录。默认值为 False。
		         ).toDispatch();

		        startRange = Dispatch.call(doc, "Range", new Variant(0), new Variant(0)).
		                toDispatch();
//		        Dispatch.call(startRange,  "InsertBreak" ,  new Variant(7) );
//		      Dispatch selectHome = Dispatch.call(startRange,"Select").toDispatch();
		        Dispatch  selection = Dispatch.get(word, "Selection").toDispatch();
		        Dispatch.call(selection, "HomeKey", new Variant(6));
		      Dispatch font = Dispatch.get(selection, "Font").toDispatch();
//		      Dispatch.put(font,"Color",new Variant(16711680));
		      Dispatch alignment = Dispatch.get(selection,  "ParagraphFormat" )
		              .toDispatch();
		      Dispatch.put(alignment,  "Alignment" , new Variant(1));
//		      Dispatch.call(selection, "TypeText", "目  录");
		      Dispatch.put(font,"Bold",new Variant(true));
		      Dispatch.put(font,"Size",new Variant(20));
		      Dispatch.call(selection, "TypeText", "目  录");
		      Dispatch.call(selection,  "InsertBreak" ,  new Variant(9) );

//		        Dispatch headingStyles = Dispatch.get(tocIndex,"HeadingStyles").toDispatch();
//		        Dispatch.call(headingStyles,"Add","Title",new Variant(2));
		    }
		
		   //插入图片
		   private static void insertPicture(Dispatch doc,String picpath) throws Exception {
		    	Dispatch homeRange = Dispatch.call(doc, "Range", new Variant(0), new Variant(0)).
		                toDispatch();
		        Dispatch.call(homeRange,  "InsertBreak" ,  new Variant(7) );
		        Dispatch startRange = Dispatch.call(doc,"Range",new Variant(0),new Variant(0)).
		                toDispatch();
		        Dispatch picture = Dispatch.call(Dispatch.get(startRange,"InLineShapes").toDispatch(),"AddPicture", picpath).toDispatch(); // 娣诲姞鍥剧墖
		        Dispatch.call(picture, "Select"); // 选中图片
		        Dispatch.put(picture, "Width", new Variant(595)); // 设置图片宽度
		        Dispatch.put(picture, "Height", new Variant(842)); // 设置图片高度
		        Dispatch shapeRange = Dispatch.call(picture, "ConvertToShape").toDispatch(); //取得图片区域
		        Dispatch WrapFormat = Dispatch.get(shapeRange, "WrapFormat").toDispatch(); // 取得图片格式
		        Dispatch.put(WrapFormat, "Type", 1); // 文字环绕方式
		        Dispatch.call(shapeRange, "Select"); // 选择图片
		        Dispatch.put(shapeRange,"RelativeHorizontalPosition",1);
		        Dispatch.put(shapeRange,"Left",0);
		        Dispatch.put(shapeRange,"RelativeVerticalPosition",1);
		        Dispatch.put(shapeRange,"Top",0);

//		        wdWrapInline 7 将形状嵌入到文字中。 
//		        wdWrapNone 3 将形状放在文字前面。请参阅  wdWrapFront 。 
//		        wdWrapSquare 0 使文字环绕形状。行在形状的另一侧延续。 
//		        wdWrapThrough 2 使文字环绕形状。 
//		        wdWrapTight 1 使文字紧密地环绕形状。 
//		        wdWrapTopBottom 4 将文字放在形状的上方和下方。 
//		        wdWrapBehind 5 将形状放在文字后面。 
//		        wdWrapFront 6 将形状放在文字前面。 
		    }
		   
		   //编辑封面
		   private static void addTextFrameOverCover(Dispatch doc,ActiveXComponent word,
				   Dispatch  selection,Object X,Object Y,Object fontSize,Object text,
				   Object w,Object h) throws Exception {		   
		    	moveStart(selection,word);
		    	Dispatch shapes = Dispatch.get(doc, "Shapes").toDispatch();
		    	Dispatch textBox = Dispatch.call(shapes, "AddTextbox",new Variant(1),new Variant(X),
		    			new Variant(Y),new Variant(w), new Variant(h)).toDispatch();
		    	Dispatch textFrame = Dispatch.get(textBox,"TextFrame").toDispatch();
		    	Dispatch fillObject = Dispatch.get(textBox,"Fill").toDispatch();
		    	Dispatch.put(fillObject,"Transparency",1.0);
		    	Dispatch textRange = Dispatch.get(textFrame,"TextRange").toDispatch();
		    	Dispatch font = Dispatch.get(textRange,"Font").toDispatch();
		    	Dispatch.put(font,"Size",fontSize);
		    	Dispatch.put(font,"Name","黑体");
		    	Dispatch.put(font,"Bold",true);
		    	Dispatch.put(textFrame,"TextRange",text);
		    	Dispatch lineObject = Dispatch.get(textBox,"Line").toDispatch();
		    	Dispatch.put(lineObject,"Transparency",1.0);
		    }
		   
		   //光标移动到第一页
		    private static void moveStart(Dispatch selection,ActiveXComponent word) throws Exception {
		        if (selection == null)
		            selection = Dispatch.get(word, "Selection").toDispatch();
		        Dispatch.call(selection, "HomeKey", new Variant(6));
		    }

		/**
		 * 
		 * @param args
		 */
		public static void main(String[] args) {
			// String source =
			// "E:\\Quzile\\MyWorkspace\\MyEclipse9\\.metadata\\.me_tcat\\webapps\\ubkdata\\2437.jsp";
			String source = "E:\\bankwork\\8795.jsp";
//			htmlToWord(new File(source), new File("E:\\n.doc"));

			// String imgPath =
			// "E:\\Quzile\\MyWorkspace\\MyEclipse9\\.metadata\\.me_tcat\\webapps\\ubkdata\\qReports\\temp\\2011\\3\\2437\\";
			// File file = new File(imgPath);
			// insertChart(new File("E:\\MyTest.doc"), file);
		}
}
