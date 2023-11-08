package home.schedule.process;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import net.sf.json.JSONObject;

public class SchdBubTtableOut {    
	
	// MAIN 생성용 OBJECT
	private JSONObject jObjRtn = new JSONObject();
	
	public SchdBubTtableOut (JSONObject jobj) throws IOException {
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
				
			// MAIN 데이터 MAP 먼저등록
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("MEMB_ID", jobj.get("LOGIN_ID"));
			param.put("MEMB_SC_CD", jobj.get("MEMB_SC_CD"));
			param.put("MEMB_DEP_CD", jobj.get("MEMB_DEP_CD"));
			param.put("TIT_CD", jobj.get("TIT_CD"));
			param.put("STRT_SCHD_YMD", jobj.get("STRT_SCHD_YMD"));
			param.put("END_SCHD_YMD", jobj.get("END_SCHD_YMD"));			
			param.put("TIT", jobj.get("TIT"));
			param.put("CONT", jobj.get("CONT"));
			
			// 01 등록
			if ("01".equals(jobj.getString("PROC_TYPE")))
			{
				// 스케쥴 게시판 생성일련번호 채번
				long creSeq = session.selectOne("uni-home-mapping.selectSchdBubSeq");
				param.put("CRE_SEQ", creSeq);
				
				// 스케쥴게시판 등록
				session.insert("uni-home-mapping.insertSchdBubInfo",param);
								
			}
			
			// 02 수정
			if ("02".equals(jobj.getString("PROC_TYPE")))
			{
				// 스케쥴게시판 수정
				param.put("CRE_SEQ", jobj.get("CRE_SEQ"));
				session.update("uni-home-mapping.updateSchdBubInfo",param);
			}
			
			// 03 삭제
			if ("03".equals(jobj.getString("PROC_TYPE"))) {
		        // 스케쥴게시판 삭제
		        param.put("CRE_SEQ", jobj.get("CRE_SEQ"));
		        int deleteResult = session.delete("uni-home-mapping.deleteSchdBubInfo", param);
		        
		        // 삭제 결과에 따른 검증
		        if (deleteResult > 0) {
		            System.out.println("삭제 성공: " + deleteResult + " 개의 행이 삭제되었습니다.");
		        } else {
		            System.out.println("삭제 실패: 삭제할 데이터가 없거나 이미 삭제되었습니다.");
		        }
		    }

		    // 결과 코드 설정
		    jObjRtn.put("RSLT_CD", "00");

		    // 데이터베이스 작업 커밋
		    session.commit();

		} catch (Exception e) {
		    e.printStackTrace();

		    // 에러 발생시 결과 코드 설정
		    jObjRtn.put("RSLT_CD", "99");
		    System.out.println("데이터베이스 작업 중 예외 발생: " + e.getMessage());
		} finally {
		    // 세션 닫기
		    if (session != null) {
		        session.close();
		        System.out.println("세션을 닫았습니다.");
		    }
		}
	}    
	public JSONObject getResult() {
		return jObjRtn;
	}

}