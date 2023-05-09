/*
 * 2023.05.09 김도원 수정 (주석 제거 및 API 명세서 기반으로 변경)\
 * MembUniCertUpd : 대학인증 회원수정
 * 
 */

/*	
 * 회원ID	 : MEMB_ID
 * 학교코드 : MEMB_SC_CD
 * 학과코드 : MEMB_DEP_CD
 * 학번 : MEMB_NUM
 * 이메일 : MEMB_EM
 */
package account.process;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MembUniCertUpd {    
	
	// MAIN 생성용 OBJECT
	private JSONObject jObjMain = new JSONObject();
	
	public MembUniCertUpd (Map<String, Object> param) throws IOException {
	//public LonginTtableOut (String userId,String userType,String userPasswrd) throws IOException {
		
		// SQL 접속장버
		String resource = "/mybatis-config.xml";
		// database.properties 읽기
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// maria db 접속하여 db 세션 획득
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		try {
			// mybatis-config.xml 을 이용하여 db 커넥션 생성
			SqlSession session = sqlSessionFactory.openSession();
			
			// sql 호출 결과를 단건으로 받아오기 위한 map 선언 (조회용 key,value )
			Map<String, Object> rtn = null;
			
			System.out.println("param :"+param.toString());
			
			// 로그인 검증 SQL 호출
			rtn = session.selectOne("uni-account-mapping.**",param);
			
			// 대학 인증 코드 JSON MAIN 항목추가
			if (rtn == null) {
				rtn = new HashMap<String, Object>();
				rtn.put("RSLT_CD", "99"); // 99 : 기타오류
			} 
			
			// 대학 인증 결과코드 JSON MAIN 항목추가
			jObjMain.put("RSLT_CD", rtn.get("RSLT_CD"));
			
			// 로그인 결과가 정상인경우 회원정보 조회 (00 : 정상)
			if ("00".equals(rtn.get("RSLT_CD"))) {
				rtn = session.selectOne("uni-account-mapping.",param);				
				jObjMain.put("RSLT_CD", rtn.get("RSLT_CD")); // 로그인결과코드 : RSLT_CD
				jObjMain.put("CERT_SEQ", rtn.get("CERT_SEQ")); // 인증일련번호 : CERT_SEQ
				}
			
	    } catch(Exception e) {
			e.printStackTrace();
	    } finally {
	    	
	    }
	}
    
	public JSONObject getResult() {
		return jObjMain;
	}

}