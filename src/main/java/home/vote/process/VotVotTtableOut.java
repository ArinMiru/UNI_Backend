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

public class VotVotTtableOut {    
	
	// MAIN 생성용 OBJECT
	private JSONObject jObjRtn = new JSONObject();
	
	public VotVotTtableOut (JSONObject jobj) throws IOException {
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
			param.put("CRE_SEQ", jobj.get("CRE_SEQ"));
			
			String votSeq = jobj.getString("VOT_SEQ");
			String[] listVot = votSeq.split(",");
			//List<String> vot_list = new ArrayList<String>();
			
			for(int i=0;i<listVot.length;i++)
			{
				if (listVot[i] != "-1")
				{
				  param.put("VOT_SEQ", listVot[i]);
				  // 투표 통계 등록
				  session.insert("uni-home-mapping.insertVotVotInfo",param);
				}
			}
			
			//param.put("VOT_SEQ", listVot);

			// 투표 통계 등록
			//session.insert("uni-home-mapping.insertVotVotInfo",param);
					
			jObjRtn.put("RSLT_CD", "00");
			
			// db 저장 확정
			session.commit();

		} catch(Exception e) {
			e.printStackTrace();
			
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