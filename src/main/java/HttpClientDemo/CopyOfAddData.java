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
        //�����õ����ݿ������
        Connection conn=DBUtil.getConnection();
        String sql="" + 
                "insert into CEDataTest"+
                "(ChineseData,EnglishData,URL,title,Memo,urlType,titleID) "+
                "values(?,?,?,?,?,?,?)";//������?��ʾ���൱��ռλ��;
        
        //Ԥ����sql���
        PreparedStatement psmt = conn.prepareStatement(sql);
        
        //�ȶ�ӦSQL��䣬��SQL��䴫�ݲ���
        psmt.setString(1, Data.getChineseData());
        psmt.setString(2, Data.getEnglishData());
        psmt.setString(3, Data.getURL());
        psmt.setString(4, Data.getTitle());
        psmt.setString(5, Data.getMemo());
        psmt.setInt(6, Data.getUrlType());
        psmt.setInt(7, Data.getTitleID());
        //ִ��SQL���
        psmt.execute();
    }
	public void Add(ChineseModel Data)throws SQLException{
        //�����õ����ݿ������
        Connection conn=DBUtil.getConnection();
        String sql="" + 
                "insert into CDataTest"+
                "(ChineseData,URL,title,Memo,urlType,titleID) "+
                "values(?,?,?,?,?,?)";//������?��ʾ���൱��ռλ��;
        
        //Ԥ����sql���
        PreparedStatement psmt = conn.prepareStatement(sql);
        
        //�ȶ�ӦSQL��䣬��SQL��䴫�ݲ���
        psmt.setString(1, Data.getChineseData());
        psmt.setString(2, Data.getURL());
        psmt.setString(3, Data.getTitle());
        psmt.setString(4, Data.getMemo());
        psmt.setInt(5, Data.getUrlType());
        psmt.setInt(6, Data.getTitleID());
        //ִ��SQL���
        psmt.execute();
    }
	public void Add(EnglishModel Data)throws SQLException{
        //�����õ����ݿ������
        Connection conn=DBUtil.getConnection();
        String sql="" + 
                "insert into EDataTest"+
                "(ChineseId,EnglishData,URL,title,Memo,urlType,titleID) "+
                "values(?,?,?,?,?,?,?)";//������?��ʾ���൱��ռλ��;
        
        //Ԥ����sql���
        PreparedStatement psmt = conn.prepareStatement(sql);
        
        //�ȶ�ӦSQL��䣬��SQL��䴫�ݲ���
        psmt.setInt(1, Data.getChineseId());
        psmt.setString(2, Data.getEnglishData());
        psmt.setString(3, Data.getURL());
        psmt.setString(4, Data.getTitle());
        psmt.setString(5, Data.getMemo());
        psmt.setInt(6, Data.getUrlType());
        psmt.setInt(7, Data.getTitleID());
        //ִ��SQL���
        psmt.execute();
    }
	public void Add(ErrorUrlModel Data)throws SQLException{
        //�����õ����ݿ������
        Connection conn=DBUtil.getConnection();
        String sql="" + 
                "insert into UrlList"+
                "(URL,urlType) "+
                "values(?,?)";//������?��ʾ���൱��ռλ��;
        
        //Ԥ����sql���
        PreparedStatement psmt = conn.prepareStatement(sql);
        
        //�ȶ�ӦSQL��䣬��SQL��䴫�ݲ���
        psmt.setString(1, Data.getURL());
        psmt.setInt(2, Data.getUrlType());
        
        //ִ��SQL���
        psmt.execute();
    }
	
    
}
