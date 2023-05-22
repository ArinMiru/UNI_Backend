/*
 * 2023.05.09 김도원 <생성>
 * 
 * 2023.05.15 개발 담당 : 안재경
 * 
 * 2023.05.17 파일이름 수정 BudlistAdminTtableOut.java -> FreeAdmin
 * 
 * 2023.05.18 전체적으로 수정이 필요한부분 : 학교코드를 front단에서 입력받아와 해당 코드와 일치하는 정보만
 * 가져올수 있도록 조건식을 설정해야함 if문만 이용해도 충분할거같음.
 * 
 * 2023.05.23 김도원 실행 sql id 값 변경(selectFreeBubInfo)
 * 
 * 게시판모니터링 (자유게시판)
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

public class FreeAdmin {    
	
	private JSONArray jary = new JSONArray();
	private JSONObject jobjMain = new JSONObject();
	
	public FreeAdmin (Map<String, Object> param) throws IOException {
		
		String resource = "/mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		try {
			SqlSession session = sqlSessionFactory.openSession();
			
			List<Map<String, Object>> rtnList = null;

			rtnList = session.selectList("uni-account-mapping.selectFreeBubInfo",param);
			
			for (int i = 0;i < rtnList.size();i++) {
				
				jobjMain.put("NICK_NM", rtnList.get(i).get("NICK_NM"));
				jobjMain.put("MEMB_SC_CD", rtnList.get(i).get("MEMB_SC_CD"));
				jobjMain.put("MEMB_DEP_CD", rtnList.get(i).get("MEMB_DEP_CD"));
				jobjMain.put("TIT", rtnList.get(i).get("TIT"));
				jobjMain.put("CRE_DAT", rtnList.get(i).get("CRE_DAT"));
				jobjMain.put("CONT", rtnList.get(i).get("CONT"));
					
				
				
			}
			jary.add(rtnList);		
				
			
			
			
		
	    } catch(Exception e) {
			e.printStackTrace();
	    } finally {
	    	
	    }
	}
    
	public JSONObject getResult() {
		return jobjMain;
	}

}