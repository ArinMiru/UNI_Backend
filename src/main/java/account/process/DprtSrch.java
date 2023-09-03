/*
 * DprtSrch : 학과 검색
 * 2023.09.03 김도원 생성	 
 */

package account.process;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import net.sf.json.JSONObject;

public class DprtSrch {    
	
	// MAIN 생성용 OBJECT
	private JSONObject jObjMain = new JSONObject();
	
	public DprtSrch (Map<String, Object> param) throws IOException {
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
		    Map<String, Object> rtn = null;
		    rtn = session.selectOne("uni-account-mapping.selectSchlAndDprtSrch", param);
		    
		    if (rtn == null) {
		        jObjMain.put("RSLT_CD", "01");
		    } else {
		        if ("00".equals(rtn.get("RSLT_CD"))) {
		            jObjMain.put("SCH_CD", ((Integer) rtn.get("SCH_CD")).intValue());  // Integer로 캐스팅
		            jObjMain.put("SCH_NM", rtn.get("SCH_NM"));
		            jObjMain.put("DPRT_CD", rtn.get("DPRT_CD"));
		            jObjMain.put("DPRT_NM", rtn.get("DPRT_NM"));
		            jObjMain.put("RSLT_CD", rtn.get("RSLT_CD"));
		        } else {
		            jObjMain.put("RSLT_CD", "01");
		        }
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    if (session != null) {
		        session.close();
		    }
		}
	}
	public JSONObject getResult() {
		return jObjMain;
	}
}