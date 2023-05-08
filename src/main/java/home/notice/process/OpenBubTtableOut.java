package home.notice.process;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class OpenBubTtableOut {    
	
	// MAIN 생성용 OBJECT
	private JSONObject jObjMain = new JSONObject();
	private JSONArray jaryMain = new JSONArray();
	
	public OpenBubTtableOut (JSONObject jobj,HttpServletRequest request) throws IOException {
	//public LonginTtableOut (String userId,String userType,String userPasswrd) throws IOException {
		
		// SQL 접속장버
		String resource = "/mybatis-config.xml";
		// database.properties 읽기
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// maria db 접속하여 db 세션 획득
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
				
		try {
			// mybatis-config.xml 을 이용하여 db 커넥션 생성
			SqlSession session = sqlSessionFactory.openSession();
					
			// sql 호출 결과를 단건으로 받아오기 위한 map 선언 (조회용 key,value )
			//Map<String, Object> rtn = null;

			// 공지사항은 LIST 형태로 조회되기 때문에 LIST 선언
			//List<Map<String, Object>> rtnList = null;
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("LOGIN_ID", jobj.get("LOGIN_ID"));
			param.put("PROC_TYPE", jobj.get("PROC_TYPE"));
			param.put("CRE_SEQ", jobj.get("CRE_SEQ"));
			param.put("MEMB_SC_CD", jobj.get("MEMB_SC_CD"));
			param.put("MEMB_DEP_CD", jobj.get("MEMB_DEP_CD"));
			param.put("TIT", jobj.get("TIT"));
			param.put("CONT", jobj.get("CONT"));
			
			jaryMain = (JSONArray) jobj.get("IMAGE_INFO");

			// 01 등록
			if ("01".equals(jobj.get("PROC_TYPE").toString()))
			{
				// 공지사항 등록
				session.insert("uni-home-mapping.insertOpenBubInfo",param);
				
				for(int i=0;i <= jaryMain.size();i++)
				{
					JSONObject jObjFile = (JSONObject) jaryMain.get(i);
					Map<String, Object> paramFile = new HashMap<String, Object>();
					
					Collection<Part> parts =  request.getParts();
					
					//for(Part part: parts)
					//{
					//	InputStream inputStream1 = part.getInputStream();
						
					//	StringUtils.
						
						
					//	if (StreamUtils.hasText(part.getSubmittedFileName())) 
					//	{
							
					//	}
					//}
					
					// 이미지 파일 등록
					session.insert("uni-home-mapping.insertOpenBubInfo",param);
				}
				
				
			}

			
						
					
			
					
			jObjMain.put("RSLT_CD", "00");

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			    	
		}
	}
    
	public JSONObject getResult() {
		return jObjMain;
	}

}