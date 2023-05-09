/*
 * 2023.05.09 김도원 수정 (주석 제거 및 API 명세서 기반으로 변경)\
 * MembReglns : 회원가입
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

public class MembReglns {    
	
	// MAIN 생성용 OBJECT
	private JSONObject jObjMain = new JSONObject();
	
	public MembReglns (Map<String, Object> param) throws IOException {
		
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
					
					// 로그인 검증 SQL 호출
					rtn = session.selectOne("uni-account-mapping.**",param);
					
					// 로그인결과코드 JSON MAIN 항목추가
					if (rtn == null) {
						rtn = new HashMap<String, Object>();
						rtn.put("RSLT_CD", "99"); // 99 : 기타오류
					} 
					
					jObjMain.put("RSLT_CD", rtn.get("RSLT_CD"));
					// RSLT_CD : 로그인결과코드
					
					//---------------------------------------------------------
					
					if ("00".equals(rtn.get("RSLT_CD"))) {	
						// 00 : 정상
						jObjMain.put("RSLT_CD", rtn.get("RSLT_CD"));
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