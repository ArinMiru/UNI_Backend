/*
 * 2023.05.09 김도원 <생성>
 * 
 * 2023.05.15 개발 담당 : 최서은
 * userInfo : 사용자 정보
 * 
 * 2023.05.18 16:06 최서은
 * 사용자 정보를 배열로 선언
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

public class UserInfoAdminTtableOut {    
	
	private JSONObject jObjMain = new JSONObject();

	public UserInfoAdminTtableOut (Map<String, Object> param) throws IOException {
		
		String resource = "/mybatis-config.xml"; // 변경 예정
		
		InputStream inputStream = Resources.getResourceAsStream(resource);
		
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			
			JSONArray jarySub = new JSONArray();
			JSONObject jObjSub = new JSONObject();
			
			List<Map<String, Object>> rtnList = null;	
			
			rtnList = session.selectList("uni-account-mapping.**",param);
			
			for(int i=0; i < rtnList.size()	;i++) 
			{
				jObjSub.put("MEMB_NM", rtnList.get(i).get("MEMB_NM"));
				jObjSub.put("MEMB_SC_CD", rtnList.get(i).get("MEMB_SC_CD"));
				jObjSub.put("MEMB_DEP_CD", rtnList.get(i).get("MEMB_DEP_CD"));
				jObjSub.put("MEMB_SC_NM", rtnList.get(i).get("MEMB_SC_NM"));
				jObjSub.put("MEMB_DEP_NM", rtnList.get(i).get("MEMB_DEP_NM"));
				jObjSub.put("TIT_CD", rtnList.get(i).get("TIT_CD"));
				jObjSub.put("TIT_NM", rtnList.get(i).get("TIT_NM"));
				jObjSub.put("NICK_NM", rtnList.get(i).get("NICK_NM"));
				jObjSub.put("MEMB_GRA", rtnList.get(i).get("MEMB_GRA"));
				jObjSub.put("MEMB_EM", rtnList.get(i).get("MEMB_EM"));
				jObjSub.put("PROF_IMG_PATH", rtnList.get(i).get("PROF_IMG_PATH"));
			}
			
			
			jarySub.add(jObjSub);
			
			jObjMain.put("USER_LIST", jarySub);

	    } catch(Exception e) {
			e.printStackTrace();
			jObjMain.put("RSLT_CD", "99");
	    } finally {
	    	if (session != null) session.close();
	    }
	}
    
	public JSONObject getResult() {
		return jObjMain;
	}

}