/*
 * LonginTtableOut : 로그인_공통
 * 
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

import net.sf.json.JSONObject;

public class TokenOut {    
	
	// MAIN 생성용 OBJECT
	private JSONObject jObjMain = new JSONObject();
	
	public TokenOut (Map<String, Object> param) throws IOException {
	//public LonginTtableOut (String userId,String userType,String userPasswrd) throws IOException {
		
		// SQL 접속장버
		String resource = "/mybatis-config.xml";
		// database.properties 읽기
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// maria db 접속하여 db 세션 획득
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		// mybatis-config.xml 을 이용하여 db 커넥션 생성
		SqlSession session = sqlSessionFactory.openSession();
		
		try {
		
			int upd=0;
			// sql 호출 결과를 단건으로 받아오기 위한 map 선언 (조회용 key,value )
			Map<String, Object> rtn = null;
			
			System.out.println("param :"+param.toString());
			
			// 로그인 검증 SQL 호출
	/*		if (param.get("TYPE").equals("C"))
			{
				rtn = session.selectOne("uni-account-mapping.tokenCheckId",param);
				if (rtn == null) {
					rtn = new HashMap<String, Object>();
					rtn.put("RSLT_CD", "01");
				}
			} 
	*/
			if (param.get("TYPE").equals("D"))
				// 로그아웃 했을때 토큰 만료
			{
				upd = session.update("uni-account-mapping.tokenIdExp",param);
				if (upd == 0) {
					rtn = new HashMap<String, Object>();
					rtn.put("RSLT_CD", "01");
				} else {
					rtn = new HashMap<String, Object>();
					rtn.put("RSLT_CD", "00");
				}
			} 
			
			// 로그인결과코드 JSON MAIN 항목추가
			jObjMain.put("RSLT_CD", rtn.get("RSLT_CD"));
		//	jObjMain.put("MEMB_ID", rtn.get("LOGIN_ID"));
			
	    } catch(Exception e) {
			e.printStackTrace();
			jObjMain.put("RSLT_CD", "99");
	    } finally {
	    	// 사용다한 세션 닫아주기
	    	if (session != null) session.close();  
	    }
	}
    
	public JSONObject getResult() {
		return jObjMain;
	}

}