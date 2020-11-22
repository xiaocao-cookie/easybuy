package com.dao;

import java.sql.Connection;

public class BaseDao {
    protected Connection connection;

    public BaseDao(Connection connection) {
        this.connection = connection;
    }
}