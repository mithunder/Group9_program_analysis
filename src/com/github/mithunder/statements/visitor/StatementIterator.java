package com.github.mithunder.statements.visitor;

import static com.github.mithunder.statements.StatementType.DO;
import static com.github.mithunder.statements.StatementType.IF;
import static com.github.mithunder.statements.StatementType.SCOPE;

import java.util.List;

import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.Statement;

public class StatementIterator {

	protected StatementVisitor visitor;

	public StatementIterator(StatementVisitor visitor){
		this.visitor = visitor;
	}

	public void tour(CompilationUnit unit) throws Exception{
		visitor.startTour(unit);
		doVisit(unit.getRootStatement(), null, StatementVisitor.ROOT_STATEMENT);
		visitor.endTour(unit);
	}

	protected void doVisit(Statement s, Statement parent, int cno) throws Exception{
		int stype = s.getStatementType();
		switch(stype){
		case DO:
		case IF:
		case SCOPE:{
			List<? extends Statement> children = s.getChildren();
			int i = 0;
			visitor.enterCompound(s, parent, cno);
			if(stype == SCOPE){
				for(Statement c : children){
					doVisit(c, s, i++);
				}
			} else if(stype == IF || stype == DO){
				final int hmax = children.size() / 2;
				for(i = 0 ; i < hmax ; i++){
					visitor.enter(StatementVisitor.VITYPE_GUARD, s, parent, cno);
					doVisit(children.get(i), parent, i);
					visitor.leave(StatementVisitor.VITYPE_GUARD, s, parent, cno);
					visitor.enter(StatementVisitor.VITYPE_COMMAND, s, parent, cno);
					doVisit(children.get(i+hmax), parent, i+hmax);
					visitor.leave(StatementVisitor.VITYPE_COMMAND, s, parent, cno);
				}
			}
			visitor.leaveCompound(s, parent, cno);
			break;}
		default:
			visitor.visitStatement(s, parent, cno);
			break;
		}
	}
}
