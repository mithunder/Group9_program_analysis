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
					doVisit(c, s, i);
					i++;
				}
			} else {
				boolean guard = true;
				for(Statement c : children){
					int vitype;
					if(guard){
						vitype = StatementVisitor.VITYPE_COMMAND;
					} else {
						vitype = StatementVisitor.VITYPE_GUARD;
					}
					guard = !guard;
					visitor.enter(vitype, s, parent, cno);
					doVisit(c, parent, cno);
					visitor.leave(vitype, s, parent, cno);
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
