package kr.dcos.common.sql.sqlpicker;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;



public class InvalidXmlTagTest {
	
	
	

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	private String removeInvalidTag(String xml){
		char questionMark = (char)65533;
		char questionMark2 = (char)63;
		
		String range = ""+questionMark+questionMark2+"";
		//String regex = "\\<[^>\\/]*\\?\\>.*?\\<\\/[^>\\/]*\\?\\>";
		String regex = "\\<[^>\\/]*[\\"+range+"]+.*?\\>.*?\\<\\/[^>\\/]*\\"+range+"+.*?\\>";
		Pattern pattern = Pattern.compile(regex,Pattern.UNICODE_CASE);
	    Matcher matcher = pattern.matcher(xml);

	    //System.out.println("s:["+xml+"]");
	    
	    StringBuilder sb = new StringBuilder();
	    int beginIndex = 0;
	    while (matcher.find()) {
	    	sb.append(xml.substring(beginIndex,matcher.start()));
	    	beginIndex = matcher.end();
	    	System.out.println("invalid XML tagName:"+matcher.group());
//	    	System.out.print("Start index: " + matcher.start());
//	        System.out.print(" End index: " + matcher.end());
//	        System.out.println(" Found: " + matcher.group());
	    }
	    if(beginIndex < xml.length()){
	    	sb.append(xml.substring(beginIndex,xml.length()));
	    }
	    return sb.toString();
	}	
	@Test
	public void testRemoveInvalidTag() {
		String s = "111<매A?>abc</매A?> <font size=\"5\"><p>some text</p> <p>another text</p><매?></매?></font><우리는>내내랜랴</우리는>222";
		s = "<산업코드n>25911</산업코드n>\n"
	+"<History>Clear</History>\n"
	+"<N평점->0.4</N평점->\n"
	+"<매A"+new Character((char) (65533))+">abc</매A"+new Character((char) (65533))+">\n"
	+"<매출_실적></매출_실적>\n"
	+"<생산방식_외주가공>20</생산방식_외주가공>\n"
	+"<매출_수출비중>30</매출_수출비중>\n"
	+"<조瑛퓐岷胎"+new Character((char) (65533))+"value>&apos;공공기관&apos;=</조瑛퓐岷胎"+new Character((char) (65533))+"value>\n"
	+"<대표자_최종발생일></대표자_최종발생일>\n"
	+"<등급기준>\n";
		System.out.println("-----------------------------");
		String newXml = removeInvalidTag(s);
		System.out.println(newXml);
		System.out.println("-----------------------------");
	}
	/**
	 * XML String에서 잘못된 tagName으로 이루어진 부분을 제거한다.
	 * <매A?>abc</매A?> 또는 <출?></출?>
	 * @param xml String
	 * @return 잘못된 부분을 제거한 XML 형태의 문자열
	 */
	private String removeInvalidTag2(String xml){
		String regex = "\\<[^>\\/]*\\?\\>.*?\\<\\/[^>\\/]*\\?\\>";
		Pattern pattern = Pattern.compile(regex,Pattern.UNICODE_CASE);
	    Matcher matcher = pattern.matcher(xml);

	    //System.out.println("s:["+xml+"]");
	    
	    StringBuilder sb = new StringBuilder();
	    int beginIndex = 0;
	    while (matcher.find()) {
	    	sb.append(xml.substring(beginIndex,matcher.start()));
	    	beginIndex = matcher.end();
//	    	System.out.print("Start index: " + matcher.start());
//	        System.out.print(" End index: " + matcher.end());
//	        System.out.println(" Found: " + matcher.group());
	    }
	    if(beginIndex < xml.length()){
	    	sb.append(xml.substring(beginIndex,xml.length()));
	    }
	    return sb.toString();
	}
	@Test
	public void test() {
		String input = "<매A?>abc</매A?><font size=\"5\"><p>some text</p> <p>another text</p><매?></매?></font><우리는>내내랜랴</우리는>";
		
		String stripped = input.replaceAll("(?s)\\<[^>]*\\?\\>","");
		System.out.println(stripped);
		//<매?></매?>
		String s = "111<매A?>abc</매A?> <font size=\"5\"><p>some text</p> <p>another text</p><매?></매?></font><우리는>내내랜랴</우리는>222";
		s = "<산업코드n>25911</산업코드n>\n"
	+"<History>Clear</History>\n"
	+"<N평점->0.4</N평점->\n"
	+"<매?></매?>\n"
	+"<매출_실적></매출_실적>\n"
	+"<생산방식_외주가공>20</생산방식_외주가공>\n"
	+"<매출_수출비중>30</매출_수출비중>\n"
	+"<grd_ret>\n"
	+"<대표자_최종발생일></대표자_최종발생일>\n"
	+"<등급기준>\n";
		String newXml = removeInvalidTag(s);
	    
		String ss = s.replaceAll("\\<매\\?\\>\\<\\/매\\?\\>","");
		 System.out.println("ss:["+ss+"]");
	    System.out.println("result:["+newXml+"]");
	}
	//111<font size="5"><p>some text</p> <p>another text</p></font><우리는>내내랜랴</우리는>222
	//111<font size="5"><p>some text</p> <p>another text</p></font><우리는>내내랜랴</우리는>222
//<font size="5"><p>some text</p> <p>another text</p>
	private boolean isValidEmailAddress(String emailAddress){
		String regex ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(emailAddress).matches();
		
	}
	@Test
	public void eMailAddressCheck(){
		assertTrue(isValidEmailAddress("dykim@kalpa.co.kr"));
	}
}
