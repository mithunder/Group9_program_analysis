package com.github.mithunder.statements.visitor;

import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.Statement;

public interface StatementVisitor {

	public static final int ROOT_STATEMENT = -1;
	public static final int VITYPE_GUARD = 1;
	public static final int VITYPE_COMMAND = 2;

	public void startTour(CompilationUnit unit) throws Exception;
	public void endTour(CompilationUnit unit) throws Exception;

	public void visitStatement(Statement s, Statement parent, int cno) throws Exception;

	public void enterCompound(Statement compound, Statement parent, int cno) throws Exception;
	public void leaveCompound(Statement compound, Statement parent, int cno) throws Exception;

	public void enter(int vitype, Statement compound, Statement parent, int cno) throws Exception;
	public void leave(int vitype, Statement compound, Statement parent, int cno) throws Exception;
}
