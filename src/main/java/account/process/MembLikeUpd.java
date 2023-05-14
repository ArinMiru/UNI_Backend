/*
 * 2023.05.09 김도원 수정 (주석 제거 및 API 명세서 기반으로 변경)
 * MembInfoUpd : 프로파일 이미지변경
 * 
 * 2023.05.10 김도원 수정 (uni-account-mapping.xml query 작성 및 try{} 코드 수정)
 * updateMembInfoUpd : 프로파일 이미지변경
 */

/*
 * 로그인결과코드 : RSLT_CD
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

public class MembLikeUpd {    
	
	// MAIN 생성용 OBJECT
	private JSONObject jObjMain = new JSONObject();
	
	public MembLikeUpd (JSONObject jobj) throws IOException {
	//public LonginTtableOut (String userId,String userType,String userPasswrd) throws IOException {
		
		// SQL 접속장버
		String resource = "/mybatis-config.xml";
		// database.properties 읽기
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// maria db 접속하여 db 세션 획득
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		SqlSession session = sqlSessionFactory.openSession();
		
        try {
            			
			Map<String, Object> param = null;
			
			param.put("LOGIN_ID", jobj.getString("LOGIN_ID"));
			param.put("PROC_TYPE", jobj.getString("PROC_TYPE"));
			param.put("CRE_SEQ", jobj.getInt("CRE_SEQ"));
			param.put("MEMB_SC_CD", jobj.getString("MEMB_SC_CD"));
			param.put("MEMB_DEP_CD", jobj.getString("MEMB_DEP_CD"));
			param.put("TIT_CD", jobj.getString("TIT_CD"));
			
            // 수정된 부분: update 메소드를 사용하도록 변경
            int updatedRows = 0;
            
            if (jobj.getString("PROC_TYPE").equals("01")) updatedRows = session.update("uni-account-mapping.updateOpenLikeUpd",param);
            if (jobj.getString("PROC_TYPE").equals("02")) updatedRows = session.update("uni-account-mapping.updateFreeLikeUpd",param);
            if (jobj.getString("PROC_TYPE").equals("03")) updatedRows = session.update("uni-account-mapping.updateQuesLikeUpd",param);
            if (jobj.getString("PROC_TYPE").equals("04")) updatedRows = session.update("uni-account-mapping.updateSugLikeUpd",param);
            if (jobj.getString("PROC_TYPE").equals("05")) updatedRows = session.update("uni-account-mapping.updateGallLikeUpd",param);
            if (jobj.getString("PROC_TYPE").equals("06")) updatedRows = session.update("uni-account-mapping.updateMarkLikeUpd",param);
            
            
            Map<String, Object> rtn = new HashMap<String, Object>();

            if (updatedRows > 0) {
                rtn.put("RSLT_CD", "00"); // 00: 정상
            } else {
                rtn.put("RSLT_CD", "99"); // 99: 기타 오류
            }

            jObjMain.put("RSLT_CD", rtn.get("RSLT_CD"));
            
            session.commit();
			
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