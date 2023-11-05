package home.vote.process;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import net.sf.json.JSONObject;

public class VotBubTtableOut {    
	
	// MAIN 생성용 OBJECT
	private JSONObject jObjRtn = new JSONObject();
	
	public VotBubTtableOut (JSONObject jobj) throws IOException {
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
			param.put("VOT_TITLE", jobj.get("VOT_TITLE"));
			param.put("VOT_TYPE_CD", jobj.get("VOT_TYPE_CD"));
			param.put("VOT_CRE_DATE", jobj.get("VOT_CRE_DATE"));
			param.put("VOT_EXPR_DATE", jobj.get("VOT_EXPR_DATE"));
			param.put("VOT_DESC", jobj.get("VOT_DESC"));
			
			// 01 등록
			if ("01".equals(jobj.getString("PROC_TYPE")))
			{
				// 투표 생성일련번호 채번
				long creSeq = session.selectOne("uni-home-mapping.selectVotBubSeq");
				
				String votInfo = jobj.getString("VOT_INFO");
				String[] listVot = votInfo.split(","); 	
				param.put("VOT_INFO", listVot);
				param.put("CRE_SEQ", creSeq);
				
				// 투표 등록
				session.insert("uni-home-mapping.insertVotBubInfo",param);
				// 보기정보 등록
				
				
				for(int i=0;i<listVot.length;i++)
				{
					if (listVot[i] != "-1")
					{
					  param.put("VOT_SEQ", i);
					  param.put("VOT_DESC", listVot[i]);
					  // 투표 통계 등록
					  session.insert("uni-home-mapping.insertVotDtlInfo",param);
					}
				}
								
			}
			
			// 02 수정
			if ("02".equals(jobj.getString("PROC_TYPE")))
			{
				String votInfo = jobj.getString("VOT_INFO");
				String[] listVot = votInfo.split(","); 	
				param.put("VOT_INFO", listVot);
				param.put("CRE_SEQ", jobj.get("CRE_SEQ"));
				// 투표 수정
				session.update("uni-home-mapping.updateVotBubInfo",param);
				
				// 보기정보 삭제 후 등록
				session.delete("uni-home-mapping.deleteVotDtlInfo",param);
				//session.insert("uni-home-mapping.insertVotDtlInfo",param);
				for(int i=0;i<listVot.length;i++)
				{
					if (listVot[i] != "-1")
					{
						param.put("VOT_SEQ", i);
						param.put("VOT_DESC", listVot[i]);
					  // 투표 통계 등록
					  session.insert("uni-home-mapping.insertVotDtlInfo",param);
					}
				}
				
				// 통계 삭제
				session.delete("uni-home-mapping.deleteVotStatInfo",param);
			}
			
			// 03 삭제
			if ("03".equals(jobj.getString("PROC_TYPE")))
			{
				param.put("CRE_SEQ", jobj.get("CRE_SEQ"));
				// 투표 삭제
				session.delete("uni-home-mapping.deleteVotBubInfo",param);	
				// 투표 답변 삭제
				session.delete("uni-home-mapping.deleteVotDtlInfo",param);
				// 통계 삭제
				session.delete("uni-home-mapping.deleteVotStatInfo",param);
			}
					
			jObjRtn.put("RSLT_CD", "00");
			
			// db 저장 확정
			session.commit();

		} catch(Exception e) {
			e.printStackTrace();
			
			// 등록,수정 실패시 첨부파일 위치에서 삭제
			
			jObjRtn.put("RSLT_CD", "99");
		} finally {
			// 사용다한 세션 닫아주기
			if (session != null) session.close();   	
		}
	}
    
	public JSONObject getResult() {
		return jObjRtn;
	}

}