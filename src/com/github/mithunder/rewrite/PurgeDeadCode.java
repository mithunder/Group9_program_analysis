package com.github.mithunder.rewrite;

import static com.github.mithunder.statements.StatementType.ABORT;
import static com.github.mithunder.statements.StatementType.DO;
import static com.github.mithunder.statements.StatementType.IF;
import static com.github.mithunder.statements.StatementType.SCOPE;
import static com.github.mithunder.statements.StatementType.SKIP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.StatementFactory;


/* Does not require that the statements are EvaluatedStatements*/
public class PurgeDeadCode extends CodeRewriter {

	protected Map<Statement, Boolean> finalStatements;
	protected StatementFactory factory;

	@Override
	public CompilationUnit rewrite(CompilationUnit unit) throws IllegalArgumentException {
		List<Statement> fs = unit.getFinalStatements();
		Statement root = unit.getRootStatement();
		finalStatements = new HashMap<Statement, Boolean>(fs.size());
		for(Statement s : fs) {
			finalStatements.put(s, Boolean.TRUE);
		}
		factory = unit.getFactory();
		root = check(root);
		if(finalStatements.size() != fs.size()) {
			fs = new ArrayList<Statement>(finalStatements.size());
			for(Statement s : finalStatements.keySet()) {
				fs.add(s);
			}
		}
		return new CompilationUnit(unit.getUnitName(), root, unit.getVariableTable(), fs, factory);
	}

	protected Statement check(Statement scope){
		final int stype = scope.getStatementType();
		List<? extends Statement> children = scope.getChildren();
		final int size = children != null ? children.size() : 0;
		Statement[] nc = new Statement[size];
		int cr = 0;
		Statement result;

		if(stype == IF || stype == DO){
			final int half = size /2;
			for(int i = 0 ; i < half ; i++){
				Statement s = children.get(i);
				if(!s.isKilled()){
					Statement c = children.get(i + half);
					nc[cr] = check(s);
					if(c.isKilled()){
						nc[cr + half] = factory.createSimpleStatement(SKIP,
								c.getCodeLocation(), c.getAnnotations());
					} else {
						nc[cr + half] = check(c);
					}
					cr++;
				}
			}
		} else if(size > 0) {
			for(int i = 0 ; i < size; i++) {
				Statement s = children.get(i);
				if(!s.isKilled()){
					nc[cr++] = check(s);
				}
			}
		}
		if(cr == 0){
			if(stype == IF){
				result = factory.createSimpleStatement(ABORT,
						scope.getCodeLocation(), scope.getAnnotations());
			} else if(stype == DO || stype == SCOPE || scope.isKilled()) {
				result = factory.createSimpleStatement(SKIP,
						scope.getCodeLocation(), scope.getAnnotations());
			} else {
				result = factory.createSimpleStatement(stype, scope.getCodeLocation(),
						scope.getAnnotations(), scope.getAssign(), scope.getValues());
			}
		} else {
			List<Statement> resc = new ArrayList<Statement>(cr * (stype == SCOPE?1:2));
			for(int i = 0 ; i < size ; i++){
				if(nc[i] != null) {
					resc.add(nc[i]);
				}
			}
			result = factory.createCompoundStatement(stype, scope.getCodeLocation(),
					scope.getAnnotations(), resc);
		}
		return result;

	}

}
