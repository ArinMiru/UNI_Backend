/*
 * 2023.05.09 김도원 수정 (주석 제거 및 API 명세서 기반으로 변경)
 * MembPassFnd : 비밀번호 찾기
 * 
 */

/*
 * 결과코드 : RSLT_CD
 * 인증일련번호 : CERT_SEQ
 */

package account.process;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import mail.process.GmailSend;
import net.sf.json.JSONObject;

public class MembPassFnd {    
	
	// MAIN 생성용 OBJECT
	private JSONObject jObjMain = new JSONObject();
	
	public MembPassFnd (Map<String, Object> param) throws IOException {
	//public LonginTtableOut (String userId,String userType,String userPasswrd) throws IOException {
		
		// SQL 접속장버
		String resource = "/mybatis-config.xml";
		// database.properties 읽기
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// maria db 접속하여 db 세션 획득
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			Map<String, Object> rtn = null;
			rtn = session.selectOne("uni-account-mapping.selectCheckEmail",param);
			
			// 로그인결과코드 JSON MAIN 항목추가
			if (rtn == null) {
				jObjMain.put("RSLT_CD","99");
			} 
			
			jObjMain.put("RSLT_CD",rtn.get("RSLT_CD"));
			
			if ("00".equals(rtn.get("RSLT_CD").toString()))
			{
			
				UUID uuid = UUID.randomUUID();
				String certNum = uuid + "";
			
				Random random = new Random();
				StringBuffer sb = new StringBuffer();
				for(int i=0; i<6; i++) {
					sb.append(random.nextInt(10));
				}
			
				param.put("CERT_NUM", certNum);
				param.put("CERT_DIV_CD", "02");
				param.put("CHK_NUM", sb.toString());
			
				System.out.println(param.toString());
			
			
				GmailSend gmailSend = new GmailSend();
				String toMail = param.get("MEMB_EM").toString();
				String toTitle = "[UNI] 인증코드번호 전송";
				String content = "인증번호 : "+sb.toString()+" 코드입력 화면에 입력해주세요.";
			
				System.out.println(toMail);
				System.out.println(toTitle);
				System.out.println(content);
			
				if (toMail.isEmpty()) 
				{ 
					jObjMain.put("RSLT_CD","04");
				} else {
				
					gmailSend.GmailSet(toMail, toTitle, content);
					long rtnCnt = session.insert("uni-account-mapping.insertCertInfo",param);
					if (rtnCnt == 0) {
						jObjMain.put("RSLT_CD","99");
					} else {
						jObjMain.put("RSLT_CD","00");
					}
				}
			
				jObjMain.put("CERT_SEQ",certNum);

				session.commit();
			}
			
	    } catch(Exception e) {
			e.printStackTrace();
			jObjMain.put("RSLT_CD","99");
	    } finally {
	    	// 사용다한 세션 닫아주기
	    	if (session != null) session.close();
	    }
	}
    
	public JSONObject getResult() {
		return jObjMain;
	}

}