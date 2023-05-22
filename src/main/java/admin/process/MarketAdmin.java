/*
 * 2023.05.17 안재경 <생성>
 * 
 * 게시판 모니터링 (장터 계시판)
 * 
 * 2023.05.19 안재경 <수정>
 * 2차원 배열을 이용 게시물마다 관련정보를 불러오고 여러장으로 등록될수있는 이미지 또한 select
 * 
 * 2023.05.23 김도원 실행 sql id 값 변경(selectMarkBubInfo)
 */

package admin.process;

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

public class MarketAdmin {    
	
	private JSONArray jary = new JSONArray();
	private JSONObject jobjMain = new JSONObject();
	
	public MarketAdmin (Map<String, Object> param) throws IOException {
		
		String resource = "/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		try {
			SqlSession session = sqlSessionFactory.openSession();
			
			List<Map<String, Object>> rtnList = null;

			rtnList = session.selectList("uni-account-mapping.selectMarkBubInfo",param);
			
			for (int i = 0;i < rtnList.size();i++) {
				jobjMain.put("NICK_NM", rtnList.get(i).get("NICK_NM"));
				jobjMain.put("MEMB_SC_CD", rtnList.get(i).get("MEMB_SC_CD"));
				jobjMain.put("MEMB_DEP_CD", rtnList.get(i).get("MEMB_DEP_CD"));
				jobjMain.put("TIT", rtnList.get(i).get("TIT"));
				jobjMain.put("CRE_DAT", rtnList.get(i).get("CRE_DAT"));
				jobjMain.put("CONT", rtnList.get(i).get("CONT"));
				//필요한 정보 select후에 배열 만들기
				jary.add(rtnList);
				
				JSONArray jarrySub = new JSONArray();
				JSONObject jobjSub = new JSONObject();
				List<Map<String, Object>> rtnListSub = null;
				
				
				rtnListSub = session.selectList("uni-community-mapping.selectMarkImgInfo",param);
				//추가적으로 한 게시물당 여러장으로 등록될수 있는 이미지를 불러오기위한 2차원배열
				for (int j=0; j < rtnListSub.size() ;j++)
				{
					jobjSub.put("FILE_PATH", rtnListSub.get(j).get("FILE_PATH"));
					jobjSub.put("IMG_SEQ", rtnListSub.get(j).get("IMG_SEQ"));
								
					jarrySub.add(jobjSub);
				}
							
				jobjSub.put("IMAGE_INFO", jarrySub);
				}
			
			
	
	    } catch(Exception e) {
			e.printStackTrace();
	    } finally {
	    	
	    }
	}
    
	public JSONObject getResult() {
		return jobjMain;
	}

}