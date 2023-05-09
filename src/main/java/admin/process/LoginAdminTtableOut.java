/*
 * 2023.05.09 김도원 수정
 * <개발 완료>
 */

/*
 * 로그인결과코드 : RSLT_CD
 * 회원명 : MEMB_NM
 * 학교코드 : MEMB_SC_CD
 * 학과코드 : MEMB_DEP_CD
 * 학번 : MEMB_NUM
 * 학교명 : MEMB_SC_NM
 * 학과명 : MEMB_DEP_NM
 * 직함코드 : TIT_CD
 * 직함명 : TIT_NM
 * 닉네임 : NICK_NM
 * 학년 : MEMB_GRA
 * 이메일 : MEMB_EM
 * 프로필이미지경로 : PROF_IMG_PATH
 * 대학인증여부 : COLL_CERT_IND
 */

package admin.process;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import net.sf.json.JSONObject;

public class LoginAdminTtableOut {    
	
	// MAIN 생성용 OBJECT
	private JSONObject jObjMain = new JSONObject();
	
	public LoginAdminTtableOut (Map<String, Object> param) throws IOException {
		
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
					rtn = session.selectOne("uni-account-mapping.selectCheckId",param);
					
					// 로그인결과코드 JSON MAIN 항목추가
					if (rtn == null) {
						rtn = new HashMap<String, Object>();
						rtn.put("RSLT_CD", "01");
					} 
					
					jObjMain.put("RSLT_CD", rtn.get("RSLT_CD"));
					
					//---------------------------------------------------------
					
					Map<String, Object> rtn1 = null;
					// 로그인 결과가 정상인경우 회원정보 조회
					if ("00".equals(rtn.get("RSLT_CD"))) {
						rtn1 = session.selectOne("uni-account-mapping.selectMembInfo",param);
						// 회원정보를 JSON MAIN 항목추가				
						jObjMain.put("LOGIN_ID", rtn1.get("LOGIN_ID"));
						jObjMain.put("MEMB_NM", rtn1.get("MEMB_NM"));
						jObjMain.put("MEMB_SC_CD", rtn1.get("MEMB_SC_CD"));
						jObjMain.put("MEMB_DEP_CD", rtn1.get("MEMB_DEP_CD"));
						jObjMain.put("MEMB_NUM", rtn1.get("MEMB_NUM"));
						jObjMain.put("MEMB_SC_NM", rtn1.get("MEMB_SC_NM"));
						jObjMain.put("MEMB_DEP_NM", rtn1.get("MEMB_DEP_NM"));	
						jObjMain.put("TIT_CD", rtn1.get("TIT_CD"));
						jObjMain.put("TIT_NM", rtn1.get("TIT_NM"));
						jObjMain.put("NICK_NM", rtn1.get("NICK_NM"));
						jObjMain.put("MEMB_GRA", rtn1.get("MEMB_GRA"));
						jObjMain.put("MEMB_EM", rtn1.get("MEMB_EM"));
						jObjMain.put("PROF_IMG_PATH", rtn1.get("PROF_IMG_PATH"));
						jObjMain.put("COLL_CERT_IND", rtn1.get("COLL_CERT_IND"));
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