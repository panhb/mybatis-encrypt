package com.hengheng.mybatis.encrypt.typehandler.handler;

import com.hengheng.mybatis.encrypt.typehandler.alias.Encrypt;
import com.hengheng.utils.AesUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author hongbo.pan
 * @date 2021/11/23
 */
@MappedTypes(Encrypt.class)
public class EncryptTypeHandler extends BaseTypeHandler<String> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, AesUtils.encrypt(s));
    }

    @Override
    public String getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return AesUtils.decrypt(resultSet.getString(s));
    }

    @Override
    public String getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return AesUtils.decrypt(resultSet.getString(i));
    }

    @Override
    public String getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return AesUtils.decrypt(callableStatement.getString(i));
    }
}
