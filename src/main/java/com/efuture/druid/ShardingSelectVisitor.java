package com.efuture.druid;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.visitor.SQLASTVisitorAdapter;
import com.alibaba.druid.util.StringUtils;

public class ShardingSelectVisitor extends SQLASTVisitorAdapter {

	@Override
	public boolean visit(SQLSelectQueryBlock x) {
		SQLTableSource tableSource = x.getFrom();

		List<SQLTableSource> tableList = new ArrayList<SQLTableSource>();
		if (tableSource instanceof SQLExprTableSource) {
			tableList.add(tableSource);
		} else if (tableSource instanceof SQLJoinTableSource) {
			Stack<SQLJoinTableSource> tableStack = new Stack<SQLJoinTableSource>();
			tableStack.push((SQLJoinTableSource) tableSource);
			while (tableStack.size() > 0) {
				SQLJoinTableSource item = tableStack.pop();

				if (item.getRight() instanceof SQLExprTableSource) {
					tableList.add(item.getRight());
				} else if (item.getRight() instanceof SQLJoinTableSource) {
					tableStack.push((SQLJoinTableSource) item.getRight());
				}

				if (item.getLeft() instanceof SQLExprTableSource) {
					tableList.add(item.getLeft());
				} else if (item.getLeft() instanceof SQLJoinTableSource) {
					tableStack.push((SQLJoinTableSource) item.getLeft());
				}

			}
		}

		for (int i = tableList.size() - 1; i >= 0; i--) {
			String conditionSql;
			if (StringUtils.isEmpty(tableList.get(i).getAlias())) {
				if (tableList.size() == 1) {
					conditionSql = "shardingcode = 1";
				} else {
					conditionSql = tableList.get(i).toString() + ".shardingcode = 1";
				}
			} else {
				conditionSql = tableList.get(i).getAlias() + ".shardingcode = 1";
			}

			x.addCondition(conditionSql);
		}

		return true;
	}

}
