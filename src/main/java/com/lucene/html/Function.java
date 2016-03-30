package com.lucene.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 
 * @author Administrator
 * 
 */
public class Function {

	protected static Logger logger = Logger.getLogger(Function.class);

	/**
	 * 将所有br分段
	 * 
	 * @param html
	 * @return
	 */
	public static String replaceBR(String html) {
		Pattern p = Pattern.compile("<br />", Pattern.UNICODE_CASE
				+ Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(html);
		return m.replaceAll("</p><p>");
	}

	/**
	 * 去除所有HTML标签
	 * 
	 * @param html
	 * @return
	 */
	public static String removeHTMLTag(String html) {
		Pattern p = Pattern.compile("<([^>]*)>");
		Matcher m = p.matcher(html);
		return m.replaceAll("");
	}

	/**
	 * 去除所有HTML转义字符
	 * 
	 * @param html
	 * @return
	 */
	public static String removeCharacterEntity(String html) {
		Pattern p = Pattern.compile("&([^;]*);");
		Matcher m = p.matcher(html);
		return m.replaceAll("");
	}

	/**
	 * 去除所有换行和回车
	 * 
	 * @return
	 */
	public static String removeRN(String html) {
		Pattern p = Pattern.compile("(\r+|\n+)");
		Matcher m = p.matcher(html);
		return m.replaceAll("");
	}

	/**
	 * 去除所有&nbsp;
	 * 
	 * @param html
	 * @return
	 */
	public static String removeNBSP(String html) {
		Pattern p = Pattern.compile("&nbsp;|　| ");
		Matcher m = p.matcher(html);
		return m.replaceAll("");
	}

	/**
	 * 
	 * @param html
	 * @return
	 */
	public static String removeNoneP(String html) {
		return html.replaceAll("<p>\\s*</p>", "");
	}

	/**
	 * 去除 JS
	 * 
	 * @param html
	 * @return
	 */
	public static String removeJS(String html) {
		Pattern p = Pattern.compile("(?is)<script[^>]*?>.*?<\\/script>");
		Matcher m = p.matcher(html);
		return m.replaceAll("");
	}

	public static String optimizeContent(String content) {
		try {

			// 替换换行
			Pattern p = Pattern.compile("<p([^>]*)>|</p>|<br\\s*>|<br\\s*/>",
					Pattern.UNICODE_CASE + Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(content);
			String nc = m.replaceAll("\n");

			// 替换之前版本？
			// p = Pattern.compile("\\?", Pattern.UNICODE_CASE +
			// Pattern.CASE_INSENSITIVE);
			// m = p.matcher(nc);
			// nc = m.replaceAll("&mdash;");

			// 替换所有标签，优化内容html
			p = Pattern.compile("<([^>]*)>|&nbsp;|　| ", Pattern.UNICODE_CASE
					+ Pattern.CASE_INSENSITIVE);
			m = p.matcher(nc);
			StringBuffer sb = new StringBuffer();
			int intable = 0;
			while (m.find()) {
				String f = m.group().toLowerCase();
				// 对于表格、图片、粗体不做替换
				if (f.contains("<table")) {
					m.appendReplacement(sb, "<table border=\"1\">\n");
					intable++;
				} else if (f.contains("/table")) {
					m.appendReplacement(sb, "</table>\n");
					intable--;
				} else if (f.contains("<tr")) {
					m.appendReplacement(sb, "<tr>");
				} else if (f.contains("<td")) {
					// 取得colspan
					StringBuffer td = new StringBuffer();
					td.append("<td");

					Pattern pc = Pattern.compile("colspan(\\D+)(\\d+)",
							Pattern.UNICODE_CASE + Pattern.CASE_INSENSITIVE);
					Matcher mc = pc.matcher(f);
					if (mc.find()) {
						td.append(" colspan=\"").append(mc.group(2))
								.append('"');
					}

					// 取得rowspan
					Pattern pr = Pattern.compile("rowspan(\\D+)(\\d+)",
							Pattern.UNICODE_CASE + Pattern.CASE_INSENSITIVE);
					Matcher mr = pr.matcher(f);
					if (mr.find()) {
						td.append(" rowspan=\"").append(mr.group(2))
								.append('"');
					}

					// 取得align
					Pattern pa = Pattern.compile("align(\\W+)(\\w+)",
							Pattern.UNICODE_CASE + Pattern.CASE_INSENSITIVE);
					Matcher ma = pa.matcher(f);
					if (ma.find()) {
						td.append(" align=\"").append(ma.group(2)).append('"');
					}
					td.append('>');

					m.appendReplacement(sb, td.toString());
				} else if (f.contains("<img") || f.contains("<strong")
						|| f.contains("/td") || f.contains("/tr")
						|| f.contains("/th") || f.contains("/strong")) {

					// if(f.contains("$")){
					// 如果有$符号会出现问题, alt中有正文的会有$符号
					f = f.replaceAll(
							"alt|title[\\s]*?=[\\s]*?['\"]([^'\"]*)['\"]", "");
					// System.out.println(f);
					// }
					m.appendReplacement(sb, f);
				} else {
					m.appendReplacement(sb, "");
				}
			}
			m.appendTail(sb);
			nc = sb.toString();

			if (nc.isEmpty())
				return "";

			// 重新处理段落
			StringBuffer sb1 = new StringBuffer(nc.length());
			sb1.append("<p>");
			boolean begin = true;
			boolean blank = true;
			boolean intag = false;
			intable = 0;
			for (int i = 0; i < nc.length(); i++) {
				char c = nc.charAt(i);
				if (c == '<') {
					String temp = nc.substring(i);
					if (temp.indexOf("table") == 1)
						intable++;
					else if (temp.indexOf("/table") == 1)
						intable--;
					intag = true;
					sb1.append(c);
				} else if (c == '>') {
					intag = false;
					sb1.append(c);
				} else if (intag || (intable > 0)) {
					if (c != '\n')
						sb1.append(c);
				} else {
					if (c == '\n') {
						if (!blank) {
							sb1.append("</p>");
							blank = true;
							begin = false;
						}
					} else {
						if (!begin) {
							sb1.append("<p>");
							begin = true;
						}
						blank = false;
						sb1.append(c);
					}
				}
			}

			if (!blank) {
				sb1.append("</p>");
			}

			return Function.removeNoneP(sb1.toString());
		} catch (Exception e) {
			logger.error(content);
			logger.error("optimizeContent error!", e);

			throw new RuntimeException(e);
		}

	}

	/**
	 * 去除style
	 * 
	 * @param 原始内容
	 * @return 过滤后内容
	 */
	public static String trimStyle(String content) {
		String regEx = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";

		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(content.toLowerCase());
		String result = content;
		if (m.find()) {
			result = m.replaceAll("");
		}
		return result;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * String regex = "</?[^/?(img)|(p)|(table)|(tr)|(td)][^><]*>"; String s
		 * =
		 * "<td width='113' nowrap='' style='border-top-style: double; border-top-color: windowtext; border-top-width: 6px; border-left-style: none; border-bottom-color: windowtext; border-bottom-width: 1px; border-right-color: windowtext; border-right-width: 1px; background-color: rgb(84, 141, 212); padding: 0px 7px; ' height='18'><p style='text-align:center'><span style='font-family: 仿宋_GB2312'>银行名称</span></p></td>"
		 * ; // String rr = trimStyle(s); String rr = s.replaceAll(regex, "");
		 * System.err.println(rr); String r = Function.removeNoneP(s); r =
		 * r.replaceAll("<p>\\s*</p>", ""); System.out.println(r);
		 * 
		 * String aaaa = "  地价 房";
		 * System.out.println(Function.removeNBSP(aaaa));
		 */

		String sss2 = "<div style=text-align:left;>  <p></p> <p>奢主义30款一见倾心婚戒 每个新娘都能找到Right款</p> <p></p> <p style=\"text-align:center;\"><img src=\"http://10.0.2.35:8080/unbankImage/images/20140620/55fb781198ad321f2d9a24612ac989db.jpg\" title=\"奢主义30款一见倾心婚戒 每个新娘都能找到Right款\" alt=\"奢主义30款一见倾心婚戒 每个新娘都能找到Right款\" /></p> <p></p> <p>六月是喜结良缘的好时候，准新娘们都想办一场一生最难以忘记的婚礼，而一款见证幸福的套环却是她们婚礼当天最好的见证人。编辑今天就为你推荐30款最好的设计作品，Cartier, David Webb和Monique Pan等等不同设计元素的钻戒，每个风格不同的女孩都能在其中找到她们的Ms.Right，快来跟随我们的脚步取取经吧。</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"30款最美婚戒满足每个新娘\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/905219dce8add456ffb412742482f773.jpg\" /></p> <p></p> <p>30款最美婚戒满足每个新娘</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Cartier Solitaire1895钻戒 $4,225\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/5ad0d4e7451392a484027fd213944826.jpg\" /></p> <p></p> <p>Cartier Solitaire1895钻戒 $4,225</p> <p>Cartier Solitaire1895系列，诞生于1895年，是长盛不衰的永恒钻戒。外形做工精致平衡，雅致和谐的4爪镶座让人产生钻石凭空镶嵌在戒指上的错觉。一般卡地亚30分的高品质钻石就要在3万以上，2克拉的比较稀有，按国际报价要在23万左右，不过这只是市场上的裸钻价格，因为卡地亚是顶级品牌，包含的附加值是相当高昂的。</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"David Webb黄色钻石宝石戒指 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/198077c468f38f9b06acb79c4e9482e2.jpg\" /></p> <p></p> <p>David Webb黄色钻石宝石戒指 未定价</p> <p>令人惊叹的这款来自于美国品牌David Webb的翡翠蓝宝石和18k黄色白金钻戒全部由工匠用手锤打造而成，而且可以很方便的调整项圈的大小。</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Fred Leighton 爱德华七世宝石切割钻戒 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/1828941a9b51e94c729030ebc803a701.jpg\" /></p> <p></p> <p>Fred Leighton 爱德华七世宝石切割钻戒 未定价</p> <p>Fred Leighton珠宝以18、19和20世纪的经典设计为主打，以精妙的手工工艺，和精心挑选的上等宝石 (包括从克什米尔蓝宝石到粉红宝石，各种绚丽焕彩的彩色宝石)，令你的项上指尖闪烁着高贵的光辉。无论是大牌秀场的看秀嘉宾、还是出席奥斯卡颁奖礼的明星，总是可以看到各路明星名流们佩戴着Fred Leighton华丽珠宝亮相。</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Monique Pan trapezoid白色宝石钻戒 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/cf76b8622b4cd391df24948727d5b725.jpg\" /></p> <p></p> <p>Monique Pan trapezoid白色宝石钻戒 未定价</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Van Cleef  Arpels 钻戒 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/c4b5f3c229bd51f941ebf6b0920c0fb9.jpg\" /></p> <p></p> <p>Van Cleef Arpels 钻戒 未定价</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Verdura 宝石钻戒 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/4e149b7b95b7cc0d8308c7d3c23f34fa.jpg\" /></p> <p></p> <p>Verdura 宝石钻戒 未定价</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Dior Fine Jewelry黄色钻戒 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/d51ffa6cc6471bc8183eabf4530097d1.jpg\" /></p> <p></p> <p>Dior Fine Jewelry黄色钻戒 未定价</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Graff fancy蓝色宝石钻戒 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/931d643175d255033064c71975b2e779.jpg\" /></p> <p></p> <p>Graff fancy蓝色宝石钻戒 未定价</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Leviev fancy深蓝粉黄蓝钻戒 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/eb8094eaf5d431557bb57bc8ae4ccbd0.jpg\" /></p> <p></p> <p>Leviev fancy深蓝粉黄蓝钻戒 未定价</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Repossi Serti sur Vide黄金钻戒 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/1ab41d935db16a9590d00cb506f7b18e.jpg\" /></p> <p></p> <p>Repossi Serti sur Vide黄金钻戒 未定价</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"S.J. Phillips七种宝石钻戒 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/55db8fc257abb4d2d34dffd26d1cfe5a.jpg\" /></p> <p></p> <p>S.J. Phillips七种宝石钻戒 未定价</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Tiffany  Co.蓝钻 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/c7fdd7103afd983791ada734af02e23d.jpg\" /></p> <p></p> <p>Tiffany Co.蓝钻 未定价</p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Asprey emerald绿宝石钻戒 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/e6b06fbac3f84ac4994bd0b55472644c.jpg\" /></p> <p></p> <p>Asprey emerald绿宝石钻戒 未定价</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Bulgari白钻 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/41ea97cb5c6406cbc20bb44a3a780ef0.jpg\" /></p> <p></p> <p>Bulgari白钻 未定价</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Cartier Dclaration d’Amour Solitaire白钻\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/95b3aafbd3729b0b6fbafdadb0344673.jpg\" /></p> <p></p> <p>Cartier Dclaration d’Amour Solitaire白钻</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Munnu金银钻戒 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/10349ee03425d8e1e67b7fb1f5be99e4.jpg\" /></p> <p></p> <p>Munnu金银钻戒 未定价</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Ralph Lauren Fine Jewelry玫瑰金白金钻戒 $4,200\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/4d2d8f14fe4312311538cdd52626fe2d.jpg\" /></p> <p></p> <p>Ralph Lauren Fine Jewelry玫瑰金白金钻戒 $4,200</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Repossi Serti sur Vide三颗梨形宝石钻戒 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/3af397703153644babb187d93fbcbd01.jpg\" /></p> <p></p> <p>Repossi Serti sur Vide三颗梨形宝石钻戒 未定价</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Cartier Trinity Ruban solitaire钻戒 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/5ea2c8863c3a7c30ae5c2ed8367e6eaa.jpg\" /></p> <p></p> <p>Cartier Trinity Ruban solitaire钻戒 未定价</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Chanel Fine Jewelry白金钻戒 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/620fbb58f5247771decd2ca3e238ed62.jpg\" /></p> <p></p> <p>Chanel Fine Jewelry白金钻戒 未定价</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"David Webb蓝宝石钻戒 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/528d9b1f455dcc68521302a0dc1c934c.jpg\" /></p> <p></p> <p>David Webb蓝宝石钻戒 未定价</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Hemmerle rectangular切割宝石钻戒 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/799be5eec2b582d27a8ec3cbba587123.jpg\" /></p> <p></p> <p>Hemmerle rectangular切割宝石钻戒 未定价</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Leviev emeral橘黄宝石钻戒 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/7fd98970575c17d80baa174a720f1da6.jpg\" /></p> <p></p> <p>Leviev emeral橘黄宝石钻戒 未定价</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Repossi Serti sur Vid两颗梨形宝石钻戒 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/6871eac4793929697bed42942c28f370.jpg\" /></p> <p></p> <p>Repossi Serti sur Vid两颗梨形宝石钻戒 未定价</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Fiona Druckenmiller Edwardian梨形宝石钻戒 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/d535b85577d2ee52c7f912b473165869.jpg\" /></p> <p></p> <p>Fiona Druckenmiller Edwardian梨形宝石钻戒 未定价</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Fred Leighto蓝宝石钻戒 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/7ddadce41f9a8082ac10e93cd41fc55f.jpg\" /></p> <p></p> <p>Fred Leighto蓝宝石钻戒 未定价</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Graff cushion蓝宝石钻戒 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/9c4b6f53f1c636d1c467e4c294417467.jpg\" /></p> <p></p> <p>Graff cushion蓝宝石钻戒 未定价</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Ralph Lauren Fine Jewelry现代感钻戒 $4,500\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/77aee2403404fc22f1663a1f156edda0.jpg\" /></p> <p></p> <p>Ralph Lauren Fine Jewelry现代感钻戒 $4,500</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Robert Procop 皇室翡翠绿钻戒 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/981a425c2a28fd2af51f3a9e79f48552.jpg\" /></p> <p></p> <p>Robert Procop 皇室翡翠绿钻戒 未定价</p> <p></p> <p style=\"text-align:center;\"><img style=\"BORDER-BOTTOM-COLOR: black; BORDER-TOP-COLOR: black; BORDER-RIGHT-COLOR: black; BORDER-LEFT-COLOR: black\" title=\"Van Cleef  Arpels绿宝石钻戒 未定价\" src=\"http://10.0.2.35:8080/unbankImage/images/20140620/d8009463942fcbd158a505e5e03c8b4b.jpg\" /></p> <p></p> <p>Van Cleef Arpels绿宝石钻戒 未定价</p> <p></p> <p style=\"text-align:center;\"><img src=\"http://10.0.2.35:8080/unbankImage/images/20140620/3a3d4e2c426140e2e7268041d81a5772.jpg\" /></p> <p></p> <p></p> <p style=\"text-align:center;\"><img src=\"http://10.0.2.35:8080/unbankImage/images/20140620/4d680ff836ba48627ebb63768aa7e104.jpg\" title=\"微头条\" alt=\"微头条\" /></p> <p></p> <p>微信原文微信文章为作者独立观点，不代表微头条立场</p> <p></p> <p></p></div>";
		System.out.println(sss2);
		String rs = optimizeContent(sss2);
		System.out.println(rs);

		// String rrr = Function.removeRN(s);
		// System.out.println(rrr);
	}

}
