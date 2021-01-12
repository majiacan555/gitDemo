package HttpClientDemo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.DataModel;
import Model.Model;
import Model.ParFre;
import Model.RelationModel;
import TransLationModel.ChineseAndEnglishModel;
import TransLationModel.ChineseModel;
import TransLationModel.EnglishModel;
import TransLationModel.ErrorUrlModel;
import TransLationModel.UrlList;

public class CopyOfAddData {

	public void CreateTable(String tableName) throws SQLException {
		Connection connection = DBUtil.getConnection();
		String sqlString = "CREATE TABLE " +
				tableName +
				"(Id int identity(1,1) primary key, " +
				"word varchar(MAX),times varchar(MAX))";
		PreparedStatement psmt = connection.prepareStatement(sqlString);
		psmt.execute();
	}
	public void Add(ChineseAndEnglishModel Data)throws SQLException{
        //首先拿到数据库的连接
        Connection conn=DBUtil.getConnection();
        String sql="" + 
                "insert into CEDataTest"+
                "(ChineseData,EnglishData,URL,title,Memo,urlType,titleID) "+
                "values(?,?,?,?,?,?,?)";//参数用?表示，相当于占位符;
        
        //预编译sql语句
        PreparedStatement psmt = conn.prepareStatement(sql);
        
        //先对应SQL语句，给SQL语句传递参数
        psmt.setString(1, Data.getChineseData());
        psmt.setString(2, Data.getEnglishData());
        psmt.setString(3, Data.getURL());
        psmt.setString(4, Data.getTitle());
        psmt.setString(5, Data.getMemo());
        psmt.setInt(6, Data.getUrlType());
        psmt.setInt(7, Data.getTitleID());
        //执行SQL语句
        psmt.execute();
    }
	public void Add(ChineseModel Data)throws SQLException{
        //首先拿到数据库的连接
        Connection conn=DBUtil.getConnection();
        String sql="" + 
                "insert into CDataTest"+
                "(ChineseData,URL,title,Memo,urlType,titleID) "+
                "values(?,?,?,?,?,?)";//参数用?表示，相当于占位符;
        
        //预编译sql语句
        PreparedStatement psmt = conn.prepareStatement(sql);
        
        //先对应SQL语句，给SQL语句传递参数
        psmt.setString(1, Data.getChineseData());
        psmt.setString(2, Data.getURL());
        psmt.setString(3, Data.getTitle());
        psmt.setString(4, Data.getMemo());
        psmt.setInt(5, Data.getUrlType());
        psmt.setInt(6, Data.getTitleID());
        //执行SQL语句
        psmt.execute();
    }
	public void Add(EnglishModel Data)throws SQLException{
        //首先拿到数据库的连接
        Connection conn=DBUtil.getConnection();
        String sql="" + 
                "insert into EDataTest"+
                "(ChineseId,EnglishData,URL,title,Memo,urlType,titleID) "+
                "values(?,?,?,?,?,?,?)";//参数用?表示，相当于占位符;
        
        //预编译sql语句
        PreparedStatement psmt = conn.prepareStatement(sql);
        
        //先对应SQL语句，给SQL语句传递参数
        psmt.setInt(1, Data.getChineseId());
        psmt.setString(2, Data.getEnglishData());
        psmt.setString(3, Data.getURL());
        psmt.setString(4, Data.getTitle());
        psmt.setString(5, Data.getMemo());
        psmt.setInt(6, Data.getUrlType());
        psmt.setInt(7, Data.getTitleID());
        //执行SQL语句
        psmt.execute();
    }
	public void Add(ErrorUrlModel Data)throws SQLException{
        //首先拿到数据库的连接
        Connection conn=DBUtil.getConnection();
        String sql="" + 
                "insert into UrlList"+
                "(URL,urlType) "+
                "values(?,?)";//参数用?表示，相当于占位符;
        
        //预编译sql语句
        PreparedStatement psmt = conn.prepareStatement(sql);
        
        //先对应SQL语句，给SQL语句传递参数
        psmt.setString(1, Data.getURL());
        psmt.setInt(2, Data.getUrlType());
        
        //执行SQL语句
        psmt.execute();
    }
	
    
}
