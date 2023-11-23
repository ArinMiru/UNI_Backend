package home.schedule.process;

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

public class YMDSchdBubListTtableOut {    
	
	private JSONObject jobj = new JSONObject();
	
	public YMDSchdBubListTtableOut (Map<String, Object> param) throws IOException {
	//public LonginTtableOut (String userId,String userType,String userPasswrd) throws IOException {
		
		// SQL
		String resource = "/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		// mybatis-config.xml 을 이용하여 db 커넥션 생성
		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			
			// 스케쥴은 LIST 형태로 조회되기 때문에 LIST 선언
			List<Map<String, Object>> rtnList = null;			
								
			rtnList = session.selectList("uni-home-mapping.selectYMDSchdBubInfo",param);	
									
			JSONArray jarySub = new JSONArray();
			JSONObject jObjSub = new JSONObject();		
			JSONObject jObjHead = new JSONObject();		
			JSONObject jObjTail = new JSONObject();		
									
			// 스케쥴은 다건일수 있기에 selectList 로 호출하며 loop 를 수행하여 레코드별 JSON 형태로 만들어준다
			for (int i=0; i < rtnList.size() ;i++)
			{
				
				jobj.put("YEAR", rtnList.get(i).get("YEAR"));
				jobj.put("MONTH", rtnList.get(i).get("MONTH"));
				
				int yoil = (int) rtnList.get(0).get("YOIL");
				
				// 처음에 전월 일자 구간 부분
				if (i == 0 && yoil > 1) 
				{
					
					for (int j=1;j<yoil;j++)
					{
						if (j == 1) jObjHead.put("DAY_DESC", "일");
						if (j == 2) jObjHead.put("DAY_DESC", "월");
						if (j == 3) jObjHead.put("DAY_DESC", "화");
						if (j == 4) jObjHead.put("DAY_DESC", "수");
						if (j == 5) jObjHead.put("DAY_DESC", "목");
						if (j == 6) jObjHead.put("DAY_DESC", "금");
						if (j == 7) jObjHead.put("DAY_DESC", "토");
						jObjHead.put("THIS_MON_YN", "N");
						//jObjHead.put("STAT", "02");
						
						jarySub.add(jObjHead);
					}
					
				}
				/*
				jObjSub.put("DAY", rtnList.get(i).get("DAY"));
				jObjSub.put("DAY_DESC", rtnList.get(i).get("DAY_DESC"));
				jObjSub.put("CRE_SEQ", rtnList.get(i).get("CRE_SEQ"));
				jObjSub.put("TIT", rtnList.get(i).get("TIT"));
				jObjSub.put("CONT", rtnList.get(i).get("CONT"));
				jObjSub.put("STRT_SCHD_YMD", rtnList.get(i).get("STRT_SCHD_YMD"));
				jObjSub.put("END_SCHD_YMD", rtnList.get(i).get("END_SCHD_YMD"));
				jObjSub.put("THIS_MON_YN", "Y");
				jObjSub.put("CRE_DAT", rtnList.get(i).get("CRE_DAT"));
				*/
				jObjSub.put("CNT", rtnList.get(i).get("CNT"));
				jObjSub.put("THIS_MON_YN", "Y");
				jObjSub.put("DAY", rtnList.get(i).get("DAY"));
				jObjSub.put("DAY_DESC", rtnList.get(i).get("DAY_DESC"));
		
				/*
				if (rtnList.get(i).get("STRT_SCHD_YMD") == null)
				{
					jObjSub.put("STAT", "02");
				}
				else {
					jObjSub.put("STAT", "01");
				}
				*/
				
								
				jarySub.add(jObjSub);
				
				// 마지막 일자이거 주중에 말일이 끝나면
				if (i == rtnList.size()-1 && yoil < 7) 
				{
					int yoile = (int) rtnList.get(i).get("YOIL");
					
					for (int j=yoile+1;j<=7;j++)
					{
						if (j == 1) jObjTail.put("DAY_DESC", "일");
						if (j == 2) jObjTail.put("DAY_DESC", "월");
						if (j == 3) jObjTail.put("DAY_DESC", "화");
						if (j == 4) jObjTail.put("DAY_DESC", "수");
						if (j == 5) jObjTail.put("DAY_DESC", "목");
						if (j == 6) jObjTail.put("DAY_DESC", "금");
						if (j == 7) jObjTail.put("DAY_DESC", "토");
						jObjTail.put("THIS_MON_YN", "N");
						//jObjTail.put("STAT", "02");
						
						jarySub.add(jObjTail);
					}
				}
			}
								
			jobj.put("RSLT_CD", "00");
			jobj.put("SCHD_BUB", jarySub);

	    } catch(Exception e) {
			e.printStackTrace();
			jobj.put("RSLT_CD", "99");
	    } finally {
	    	// 사용다한 세션 닫아주기
	    	if (session != null) session.close();
	    }
	}
    
	public JSONObject getResult() {
		return jobj;
	}

}