/*
 * 2023.05.09 김도원 수정 (주석 제거 및 API 명세서 기반으로 변경)
 * MembInfoUpd : 프로파일 이미지변경
 * 
 * 2023.05.10 김도원 수정 (uni-account-mapping.xml query 작성 및 try{} 코드 수정)
 * updateMembInfoUpd : 프로파일 이미지변경
 */

/*
 * 로그인결과코드 : RSLT_CD
 * LIKE_CNT : 좋아요 갯수
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

public class MembLikeMinusUpd {    
	
	// MAIN 생성용 OBJECT
	private JSONObject jObjMain = new JSONObject();
	
	public MembLikeMinusUpd (JSONObject jobj) throws IOException {
		String resource = "/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		
        try {
        	Map<String, Object> param = new HashMap<String, Object>();
        	
        	System.out.println("jobj 데이터: " + jobj);
        	
        	param.put("MEMB_ID", jobj.get("LOGIN_ID"));
			param.put("PROC_TYPE", jobj.get("PROC_TYPE"));
			param.put("CRE_SEQ", jobj.get("CRE_SEQ"));
			param.put("MEMB_SC_CD", jobj.get("MEMB_SC_CD"));
			param.put("MEMB_DEP_CD", jobj.get("MEMB_DEP_CD"));
			param.put("TIT_CD", jobj.get("TIT_CD"));

			System.out.println("param 맵 데이터: " + param);
			
            int updatedRows = 0;
            Map<String, Object> likeCnt = new HashMap<String, Object>();
            
            // LIKE_CNT 업데이트 및 조회 처리
            switch (jobj.getString("PROC_TYPE")) {
                case "01":
                    updatedRows = session.update("uni-account-mapping.updateOpenLikeMinusUpd", param);
                    break;
                case "02":
                    updatedRows = session.update("uni-account-mapping.updateFreeLikeUpd", param);
                    break;
                case "03":
                    updatedRows = session.update("uni-account-mapping.updateQuesLikeUpd", param);
                    break;
                case "04":
                    updatedRows = session.update("uni-account-mapping.updateSugLikeUpd", param);
                    break;
                case "05":
                    updatedRows = session.update("uni-account-mapping.updateGallLikeUpd", param);
                    break;
                case "06":
                    updatedRows = session.update("uni-account-mapping.updateMarkLikeUpd", param);
                    break;
            }
            
            if (updatedRows > 0) {
                likeCnt = session.selectOne("uni-account-mapping.selectOpenLikeCnt", param);
                System.out.println("LIKE_CNT 값: " + likeCnt);
            }
            
            int unLikeRows =0;
            unLikeRows= session.update("uni-account-mapping.insertUnLikeBas", param);

            Map<String, Object> rtn = new HashMap<String, Object>();

            if (updatedRows > 0 && likeCnt != null && unLikeRows > 0) {
                rtn.put("RSLT_CD", "00"); // 00: 정상 처리
                rtn.put("LIKE_CNT", likeCnt); // LIKE_CNT 값 추가
            } else {
                rtn.put("RSLT_CD", "99"); // 99: 오류 처리
            }

            jObjMain.putAll(rtn); // JSON 객체에 결과 맵을 추가
            
            session.commit();
			
	    } catch(Exception e) {
			e.printStackTrace();
			jObjMain.put("RSLT_CD", "99");
			System.out.println("예외 발생: " + e.getMessage());
	    } finally {
	    	if (session != null) session.close();
	    }
	}
    
	public JSONObject getResult() {
		return jObjMain;
	}
}
