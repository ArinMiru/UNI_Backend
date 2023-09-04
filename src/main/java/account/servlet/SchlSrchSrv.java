/*
 * SchlSrchSrv : 대학교 검색
 * (2023.09.03 김도원 생성)
 */

package account.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import account.process.SchlSrch;

/**
 * Servlet implementation class LoginSrv
 */

@WebServlet("/SchlSrch")
public class SchlSrchSrv extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SchlSrchSrv() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String sch_nm = " ";
        StringBuffer jb = new StringBuffer();
        String line = null;

        System.out.println("start");

        JSONObject resltObj = new JSONObject();

        System.out.println("start2");

        request.setCharacterEncoding("utf8");
        response.setContentType("application/x-json; charset=UTF-8");

        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            jb.append(line);
        }

        JSONObject jobj = JSONObject.fromObject(jb.toString());

        sch_nm = (jobj.get("SCH_NM") == null) ? "" : jobj.getString("SCH_NM");

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("SCH_NM", sch_nm);

        System.out.println("sch_nm :".concat(sch_nm));

        SchlSrch schlsrch = new SchlSrch(param);
        
        resltObj = schlsrch.getResult();

        System.out.println("resltObj :".concat(resltObj.toString()));

        response.getWriter().print(resltObj);
    }
}
