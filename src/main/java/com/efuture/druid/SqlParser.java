package com.efuture.druid;

import java.sql.SQLSyntaxErrorException;
import java.util.List;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLIntegerExpr;
import com.alibaba.druid.sql.ast.statement.SQLDeleteStatement;
import com.alibaba.druid.sql.ast.statement.SQLInsertStatement;
import com.alibaba.druid.sql.ast.statement.SQLInsertStatement.ValuesClause;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.ast.statement.SQLUpdateStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.util.FnvHash;
import com.alibaba.druid.util.StringUtils;

public class SqlParser {

	public static void main(String[] args) {
		try {
			// SqlParser.selectStatement();
			 SqlParser.insertStatement();
			// SqlParser.deleteStatement();
			// SqlParser.updateStatement();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private static void selectStatement() throws SQLSyntaxErrorException {
//		String sql = "select a.custid, a.ctype, b.cmname, (select money from user) as money from customer a, custmember b where a.custid = b.custid and a.custid = '0001' and b.cmflag = '04'";
//		String sql = "select a.custid, a.ctype, b.cmname from customer a, (select custid, cmname, cmflag from custmember) b where a.custid = b.custid and a.custid = '0001' and b.cmflag = '04'";
//		String sql = "select a.custid, a.ctype from customer a, custmember b, cardmain c, (select ctcode, ctname from cardtype) d where a.custid = b.custid and b.cid = c.cid and c.cdmtype = d.ctcode and a.custid = '0001' and a.custid = ${custid}";
		String sql = "select a.custid, a.ctype from customer a, (select custid, cmname from custmember) b where a.custid = b.custid and a.custid = '0001'";
		String dbType = "mysql";
		SQLSelectStatement statement = (SQLSelectStatement) SqlParser.parser(sql, dbType);
//		SQLSelect select = statement.getSelect();

//		MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
//		statement.accept(visitor);
//		System.out.println(visitor.getTables().toString());

		ShardingSelectVisitor visitor = new ShardingSelectVisitor();
		statement.accept(visitor);
		System.out.println(statement.toString());
	}

	private static void insertStatement() throws SQLSyntaxErrorException {
//		String sql = "insert into customer(custid, ctype, csex) values ('0001', '1', null)";
		String sql = "insert into customer(custid, ctype, csex) values ('0001', '1', null), ('0002', '2', null), ('0003', '3', null)";
		String dbType = "mysql";

		SQLInsertStatement statement = (SQLInsertStatement) SqlParser.parser(sql, dbType);
		System.out.println(statement.toString());

		List<SQLExpr> columes = statement.getColumns();
		SQLExpr expr = new SQLIdentifierExpr("shardingcode", FnvHash.fnv1a_64_lower("shardingcode"));
		expr.setParent(statement);
		columes.add(expr);

		List<ValuesClause> valuesList = statement.getValuesList();
		for (ValuesClause values : valuesList) {
			expr = new SQLIntegerExpr(1L);
			expr.setParent(values);
			values.getValues().add(expr);
		}

		System.out.println(statement.toString());
	}

	private static void deleteStatement() throws SQLSyntaxErrorException {
		//String sql = "delete from customer a where a.csex = '1' and (a.ctype = '1' or a.ctype = '2')";
		String sql = "delete a from customer a, custmember b where a.cid = b.cid and a.cid = 1";
		String dbType = "mysql";

		SQLDeleteStatement statement = (SQLDeleteStatement) SqlParser.parser(sql, dbType);
		System.out.println(statement.toString());
		
		SQLTableSource tableSource = statement.getTableSource();
		String conditionSql;
		if (StringUtils.isEmpty(tableSource.getAlias())) {
			conditionSql = "shardingcode = 1";
		} else {
			conditionSql = tableSource.getAlias() + ".shardingcode = 1";
		}
		statement.addCondition(conditionSql);
		System.out.println(statement.toString());
	}

	private static void updateStatement() throws SQLSyntaxErrorException {
		String sql = "update customer a set csex = '1' where a.custid = '0001'";
		String dbType = "mysql";

		SQLUpdateStatement statement = (SQLUpdateStatement) SqlParser.parser(sql, dbType);
		System.out.println(statement.toString());
		
		SQLTableSource tableSource = statement.getTableSource();
		String conditionSql;
		if (StringUtils.isEmpty(tableSource.getAlias())) {
			conditionSql = "shardingcode = 1";
		} else {
			conditionSql = tableSource.getAlias() + ".shardingcode = 1";
		}
		statement.addCondition(conditionSql);
		System.out.println(statement.toString());
		
		
		MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
		statement.accept(visitor);
		System.out.println(visitor.getTables().toString());
	}

	public static SQLStatement parser(String sql, String dbType) throws SQLSyntaxErrorException {
		List<SQLStatement> list = SQLUtils.parseStatements(sql, dbType);
		if (list.size() > 1) {
			throw new SQLSyntaxErrorException("MultiQueries is not supported, use single query instead.");
		}

		return list.get(0);
	}

}
