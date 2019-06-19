package com.example.impostoderenda.database;

public class ScriptDLL {
    public static String getCreateTable(){
    StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE IF NOT EXISTS CLIENTE ( ");
        sql.append("        CODIGO INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sql.append("        SALARIO VARCHAR (50) NOT NULL DEFAULT (''), ");
        sql.append("        RESULTSALARIO VARCHAR (50) NOT NULL DEFAULT (''),");
        sql.append("        DATAS TEXT ) " );

        return sql.toString();
    }
}
