package LiberyUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import TransLationModel.CEDataNLP2;
import TransLationModel.ChineseAndEnglishModel;

public class AddData {

    public void Add(DataModel Data)throws SQLException{ 
        Connection conn=DBUtil.getConnection();
        String sql="" + 
                "insert into BasicInfo1"+
                "(data) "+
                "values(?)";  
        PreparedStatement psmt = conn.prepareStatement(sql); 
        psmt.setString(1, Data.getData()); 
        psmt.execute(); 
    } 
 public void Add(String TableName, ChineseAndEnglishModel Data)throws SQLException{ 
        Connection conn=DBUtil.getConnection();
        String sql="" + 
                "insert into "+TableName+
                " (Chinesedata,EnglishData,URL,title,Memo,urlType,titleID) "+
                "values(?,?,?,?,?,?,?)";  
        PreparedStatement psmt = conn.prepareStatement(sql); 
        psmt.setString(1, Data.getChineseData()); 
        psmt.setString(2, Data.getEnglishData()); 
        psmt.setString(3, Data.getURL()); 
        psmt.setString(4, Data.getTitle()); 
        psmt.setString(5, Data.getMemo()); 
        psmt.setInt(6, Data.getUrlType()); 
        psmt.setInt(7, Data.getTitleID()); 
        psmt.execute(); 
    } 
    public void update(DataModel Data)throws SQLException{ 
        Connection conn=DBUtil.getConnection();
        String sql="" +   "update BasicInfo set data = ? where data = ?";  
        PreparedStatement psmt = conn.prepareStatement(sql); 
        psmt.setString(1, Data.getData()); 
        psmt.execute();
    } 
    public void delete(DataModel Data) throws SQLException{
        Connection conn=DBUtil.getConnection();
        String sql="" +    "delete from BasicInfo where data = ?";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1,Data.getData()); 
        psmt.execute();
    } 
    public DataModel Search(DataModel Data) throws SQLException{
        DataModel p = null;
        Connection conn=DBUtil.getConnection();
        String sql="" + 
                "select * from BasicInfo where data = ?";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1,Data.getData()); 
        ResultSet rs = psmt.executeQuery();
        while(rs.next()){
            p = new DataModel();
            p.setData(rs.getString("data"));
        }
        return p;
    } 
    public List<DataModel> Search() throws SQLException{
        Connection conn = DBUtil.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs =  stmt.executeQuery("select id,name,age from BasicInfo");
        List<DataModel> people = new ArrayList<DataModel>();
        DataModel p = null;
        while(rs.next()){//��������������ݣ��ͻ�ѭ����ӡ����
            p = new DataModel();
            p.setData(rs.getString("data"));
            people.add(p);
        }
        return people;
        
    }
	public ChineseAndEnglishModel SearchData( Connection conn ,int id, String TableName) throws SQLException { 
        Statement stmt = conn.createStatement();
        ResultSet rs =  stmt.executeQuery("select Chinesedata,EnglishData,URL,title,Memo,urlType,titleID from  "+TableName  +" where Id = "+ id);
        ChineseAndEnglishModel  model  = null; 
        while(rs.next()){ 
        	model = new ChineseAndEnglishModel();
        	model.setChineseData(rs.getString("Chinesedata"));
        	model.setEnglishData(rs.getString("EnglishData")) ;
        	model.setId(id) ;
        	model.setMemo(rs.getString("Memo")) ;
        	model.setTitle(rs.getString("title")) ;
        	model.setTitleID(rs.getInt("titleID")) ;
        	model.setURL(rs.getString("URL")) ;
        	model.setUrlType(rs.getInt("urlType")) ; 
        }
        return model;
	}
	public void Add(CEDataNLP2 Data, String tableName) throws SQLException {
		 Connection conn=DBUtil.getConnection();
	        String sql="" + 
	                "insert into "+tableName+
	                " (Chinesedata,EnglishData,URL,title,Memo,urlType,titleID,EnglishNLP,ChineseNLP,DataId) "+
	                "values(?,?,?,?,?,?,?,?,?,?)";  
	        PreparedStatement psmt = conn.prepareStatement(sql); 
	        psmt.setString(1, Data.getChineseData()); 
	        psmt.setString(2, Data.getEnglishData()); 
	        psmt.setString(3, Data.getURL()); 
	        psmt.setString(4, Data.getTitle()); 
	        psmt.setString(5, Data.getMemo()); 
	        psmt.setInt(6, Data.getUrlType()); 
	        psmt.setInt(7, Data.getTitleID()); 
	        psmt.setString(8, Data.getEnglishNLP()); 
	        psmt.setString(9, Data.getChineseNLP()); 
	        psmt.setInt(10, Data.getDataId()); 
	        psmt.execute(); 
		
	}
	public  String  SearchChineseData(String SearchName, int id, Connection conn,String TableName) throws SQLException{
		Statement stmt = conn.createStatement();
	    ResultSet rs =  stmt.executeQuery("select "+SearchName+" from  "+TableName  +" where Id = "+ id);
	    String  Chinesedata = "";
	    while(rs.next()){  
	    	Chinesedata = (rs.getString(SearchName));
	    }
	    return Chinesedata; 
	}
	public void UpdateChineseDataNlp(int Id, String jaString, Connection conn, String tableName) throws SQLException {
        String sql="" +   "update "+tableName+" set ChineseNLP = ? where Id = ?";  
        PreparedStatement psmt = conn.prepareStatement(sql); 
        psmt.setString(1, jaString); 
        psmt.setInt (2, Id); 
        psmt.execute();
	}
	public List<Integer> SearchIDList(String SearchName, String CoulmName, int urlType, Connection conn, String  TableName) throws SQLException {
		Statement stmt = conn.createStatement();
	    ResultSet rs =  stmt.executeQuery("select "+SearchName+" from   "+TableName  +" where "+CoulmName+" = "+ urlType);
	    List<Integer>  idList = new ArrayList<>();
	    while(rs.next()){  
	    	int id = (rs.getInt(SearchName));
	    	idList.add(id);
	    }
	    return idList; 
	} 
	public List<Integer> searchIDListWhereNLPisNull(String SearchName, String CoulmName, int urlType, Connection conn, String  TableName) throws SQLException {
		Statement stmt = conn.createStatement();
	    ResultSet rs =  stmt.executeQuery("select "+SearchName+" from   "+TableName  +" where "+CoulmName+" = "+ urlType + "and ChineseNLP is null ");
	    List<Integer>  idList = new ArrayList<>();
	    while(rs.next()){  
	    	int id = (rs.getInt(SearchName));
	    	idList.add(id);
	    }
	    return idList; 
	} 
}
