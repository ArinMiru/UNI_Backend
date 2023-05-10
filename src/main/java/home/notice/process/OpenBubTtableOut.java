package home.notice.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.apache.tomcat.util.codec.binary.Base64;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class OpenBubTtableOut {    
	
	// MAIN 생성용 OBJECT
	private JSONObject jObjRtn = new JSONObject();
	private JSONObject jObjMain = new JSONObject();
	private JSONArray jaryMain = new JSONArray();
	
	public OpenBubTtableOut (JSONObject jobj) throws IOException {
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
			// 아래 if 내는 등록,수정인 경우만 수행
			if ("01".equals(jobj.getString("PROC_TYPE")) || "02".equals(jobj.getString("PROC_TYPE")))
			{
				// JSON 의 이미지 부분을 먼저 처리
				jaryMain = (JSONArray) jobj.get("IMAGE_INFO");
				// 등록 파일명 보관
				ArrayList<String> fileAddList = new ArrayList<String>();
				// 삭제 SEQ 보관
				ArrayList<Integer> fileDelList = new ArrayList<Integer>();
			
				for(int i=0;i<jaryMain.size();i++)
				{
				
					// 첫 배열
					jObjMain = (JSONObject) jaryMain.get(i);
				
					// BASE64 문자열 읽기
					String fileBase = (jObjMain.get("FILE_BASE64") == null) ? "" : jObjMain.getString("FILE_BASE64");
					// 파일명 읽기
					String fileNm = (jObjMain.get("FILE_NM") == null) ? "" : jObjMain.getString("FILE_NM");
				
					// uuid 고유식별자를 채번해서 파일명이 중복되지 않게 변경용
					UUID uuid = UUID.randomUUID();
					// 파일명 새로 구성
					// 추후 파일 저장 경로 변경 예정
					String saveFileNm = "C:\\UNI\\" + uuid + "_" + fileNm;
				
					// 등록,수정
					if (0 == jObjMain.getInt("IMG_SEQ")) 
					{
						// 등록 요청 파일명 배열에 보관
						fileAddList.add(saveFileNm);
					}
					// 삭제
					if (0 > jObjMain.getInt("IMG_SEQ")) 
					{
						// 삭제요청 seq 저장
						fileDelList.add(jObjMain.getInt("IMG_SEQ") * -1);
					}
				
					// base 64 정보가 없으면 다움건으로 skip
					if (fileBase.isEmpty()) continue;
				
					// 이미지 BASE64 디코딩해서 파일로 생성
					byte decode[] = Base64.decodeBase64(fileBase);
					FileOutputStream fos;
					File target = new File(saveFileNm);
					target.createNewFile();
					fos = new FileOutputStream(target);
					fos.write(decode);
					fos.close();
				}
			
				// MAIN 데이터 MAP 먼저등록
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("MEMB_ID", jobj.get("LOGIN_ID"));
				param.put("MEMB_SC_CD", jobj.get("MEMB_SC_CD"));
				param.put("MEMB_DEP_CD", jobj.get("MEMB_DEP_CD"));
				param.put("TIT", jobj.get("TIT"));
				param.put("CONT", jobj.get("CONT"));
			
			
				// 01 등록
				if ("01".equals(jobj.getString("PROC_TYPE")))
				{
					// 공지 게시판 생성일련번호 채번
					long creSeq = session.selectOne("uni-home-mapping.selectOpenBubSeq");
					param.put("CRE_SEQ", creSeq);
				
					// 공지사항 등록
					session.insert("uni-home-mapping.insertOpenBubInfo",param);
				
					for(int i=0;i < fileAddList.size();i++)
					{
					
						// 이미지등록
						Map<String, Object> imgParam = new HashMap<String, Object>();
						imgParam.put("FILE_PATH", fileAddList.get(i));
						imgParam.put("CRE_SEQ", creSeq);
						imgParam.put("ORIG_FILE_NM", "ORIG_FILE_NM");
						imgParam.put("MOD_FILE_NM", "MOD_FILE_NM");	


						// 이미지 파일 등록
						session.insert("uni-home-mapping.insertOpenImgInfo",imgParam);
					}
				
				}
			
				// 02 수정
				if ("02".equals(jobj.getString("PROC_TYPE")))
				{
					// 공지사항 수정
					param.put("CRE_SEQ", jobj.get("CRE_SEQ"));
					session.update("uni-home-mapping.updateOpenBubInfo",param);
							
					for(int i=0;i<fileAddList.size();i++)
					{
								
						// 이미지등록
						Map<String, Object> imgParam = new HashMap<String, Object>();
						imgParam.put("FILE_PATH", fileAddList.get(i));
						imgParam.put("CRE_SEQ", jobj.get("CRE_SEQ"));
						imgParam.put("ORIG_FILE_NM", "ORIG_FILE_NM");
						imgParam.put("MOD_FILE_NM", "MOD_FILE_NM");	

						// 이미지 파일 등록
						session.insert("uni-home-mapping.insertOpenImgInfo",imgParam);
					}
					
					for(int i=0;i<fileDelList.size();i++)
					{
						
						// 이미지삭제
						Map<String, Object> imgParam = new HashMap<String, Object>();
						imgParam.put("IMG_SEQ", fileDelList.get(i));
						imgParam.put("CRE_SEQ", jobj.get("CRE_SEQ"));	

						// 이미지 파일명 가져오기
						String filePath = session.selectOne("uni-home-mapping.selectOpenDelImgInfo",imgParam);
						
						// 파일 삭제
						try {
							File file = new File(filePath);
							if (file.exists()) file.delete();
						} catch(Exception e) {
							//e.printStackTrace();
						}
						
						// 이미지 파일 삭제
						session.delete("uni-home-mapping.deleteOpenImgInfo",imgParam);
					}

				}
			}
			
			// 03 삭제
			if ("03".equals(jobj.getString("PROC_TYPE")))
			{
				// MAIN 데이터 MAP 먼저등록
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("MEMB_ID", jobj.get("LOGIN_ID"));
				param.put("MEMB_SC_CD", jobj.get("MEMB_SC_CD"));
				param.put("MEMB_DEP_CD", jobj.get("MEMB_DEP_CD"));
				param.put("CRE_SEQ", jobj.get("CRE_SEQ"));	
				
				// 삭제할 이미지 목록 가져오기
				List<String> rtnList = null;	
				rtnList = session.selectList("uni-home-mapping.selectOpenDelAllImgInfo",param);
				
				// 실제경로 파일 삭제
				for (int i=0; i < rtnList.size() ;i++)
				{
					// 파일 삭제
					try {
						File file = new File(rtnList.get(i));
						if (file.exists()) file.delete();
					} catch(Exception e) {
						//e.printStackTrace();
					}
				}
				
				// 공지사항 삭제
				session.delete("uni-home-mapping.deleteOpenBubInfo",param);	
				// 공지사항 이미지 파일 삭제
				session.delete("uni-home-mapping.deleteOpenAllImgInfo",param);
				
				
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