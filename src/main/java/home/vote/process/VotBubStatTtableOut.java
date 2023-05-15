package home.vote.process;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class VotBubStatTtableOut {    
	
	// MAIN 생성용 OBJECT
	private JSONObject jObjRtn = new JSONObject();
	
	public VotBubStatTtableOut (Map<String, Object> param) throws IOException {
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
				
			// 투표은 LIST 형태로 조회되기 때문에 LIST 선언
			List<Map<String, Object>> rtnList = null;			
											
			rtnList = session.selectList("uni-home-mapping.selectVotBubStat",param);	
			
			JSONArray jarySub = new JSONArray();
			JSONObject jObjSub = new JSONObject();
									
									
			// 투표은 다건일수 있기에 selectList 로 호출하며 loop 를 수행하여 레코드별 JSON 형태로 만들어준다
			for (int i=0; i < rtnList.size() ;i++)
			{
				jObjSub.put("VOT_TOT", rtnList.get(i).get("VOT_TOT"));
				jObjSub.put("VOT_SEQ", rtnList.get(i).get("VOT_SEQ"));
				jObjSub.put("VOT_SUB_TOT", rtnList.get(i).get("VOT_SUB_TOT"));
				jObjSub.put("PRCT", rtnList.get(i).get("PRCT"));
								
				jarySub.add(jObjSub);
			}
					
			jObjRtn.put("RSLT_CD", "00");
			jObjRtn.put("VOT_BUB", jarySub);
			
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