package com.github.mithunder.alfp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.EvaluatedStatement;
import com.github.mithunder.statements.StatementType;
import com.github.mithunder.statements.Variable;
import com.github.mithunder.statements.VariableTable;
import com.github.mithunder.statements.simple.SimpleVariable;

public class ALFPReachingDefinition extends ALFP {

	private Stack<String> labelStack;
	private VariableTable table;
	private Set<Variable> variableList;
	private Set<String> labelList;
	private Set<GenKillElement> genList;
	private Set<GenKillElement> killList;
	private Set<FlowElement> flowList;

	@Override
	public void convertToALFP(EvaluatedStatement statement, CompilationUnit unit) {
		labelStack = new Stack<String>();
		labelStack.push("0");
		variableList = new TreeSet<Variable>();
		labelList = new TreeSet<String>();
		genList = new TreeSet<GenKillElement>();
		killList = new TreeSet<GenKillElement>();
		flowList = new TreeSet<FlowElement>();
		table = unit.getVariableTable();
		iterate(statement.getChildren(), statement.getStatementType());
		print();
	}

	private ArrayList<String> iterate(List<EvaluatedStatement> statements, int statementType) {
		int limit;
		if(statementType != StatementType.IF && statementType != StatementType.DO) {
			limit = statements.size();
		} else {
			limit = statements.size()/2;
		}
		ArrayList<String> toReturn = new ArrayList<String>();
		int ifOnIndex = -2;
		ArrayList<String> fromReturn = new ArrayList<String>();
		for(int i = 0; i < limit; i++) {
			EvaluatedStatement statement = statements.get(i);
			EvaluatedStatement s;
			String newLabel;
			switch(statementType) {
			case StatementType.IF:
				newLabel = Integer.toHexString(System.identityHashCode(statement.getStatement()));
				flowList.add(new FlowElement(labelStack.peek(), newLabel));
				labelStack.add(newLabel);
				iterate(statement.getChildren(), statement.getStatementType());
				s = statements.get(i + limit);
				fromReturn = iterate(s.getChildren(), s.getStatementType());
				if(fromReturn.size() > 0) {
					toReturn.addAll(fromReturn);
				} else {
					toReturn.add(labelStack.pop());
				}
				break;
			case StatementType.DO:
				newLabel = Integer.toHexString(System.identityHashCode(statement.getStatement()));
				flowList.add(new FlowElement(labelStack.peek(), newLabel));
				labelStack.add(newLabel);
				iterate(statement.getChildren(), statement.getStatementType());
				s = statements.get(i + limit);
				fromReturn = iterate(s.getChildren(), s.getStatementType());
				if(fromReturn.size() > 0) {
					String tmpLabel = labelStack.peek();
					for(int j = 0; j < fromReturn.size(); j++) {
						flowList.add(new FlowElement(fromReturn.get(j), tmpLabel));
					}
					fromReturn.clear();
				} else {
					String tmpLabel = labelStack.pop();
					flowList.add(new FlowElement(tmpLabel, labelStack.peek()));
				}
				break;
			default:
				newLabel = Integer.toHexString(System.identityHashCode(statement.getStatement()));
				if(ifOnIndex+1 == i && fromReturn != null) {
					for(int j = 0; j < fromReturn.size(); j++) {
						flowList.add(new FlowElement(fromReturn.get(j), newLabel));
					}
				} else {
					String oldLabel = labelStack.pop();
					flowList.add(new FlowElement(oldLabel, newLabel));
				}
				labelStack.push(newLabel);
				labelList.add(newLabel);
				if(statement.getChildCount() > 0) {
					fromReturn = iterate(statement.getChildren(), statement.getStatementType());
					if(statement.getStatementType() == StatementType.IF) {
						ifOnIndex = i;
					}
					if(i == limit - 1) {
						toReturn.addAll(fromReturn);
					}
				} else {
					Variable assign = statement.getAssign();
					if(assign != null) {
						variableList.add(assign);
						GenKillElement gke = new GenKillElement(assign, newLabel);
						genList.add(gke);
						if(statement.getStatementType() != StatementType.READ) {
							killList.add(gke);
						}
					}
				}
				break;
			}
		}
		if(statementType == StatementType.IF) {
			labelStack.pop();
		}
		return toReturn;
	}

	private void print() {
		for(Variable v : variableList) {
			System.out.print("var(" + table.getVariableName(v) + ") & ");
		}
		System.out.println();
		for(String l : labelList) {
			System.out.print("label(" + l + ") & ");
		}
		System.out.println();
		for(FlowElement fe : flowList) {
			System.out.print("flow(" + fe.getLabelFrom() + "," + fe.getLabelTo() + ") & ");
		}
		System.out.println();
		for(GenKillElement gke : killList) {
			System.out.print("(A lab. label(lab) => kill(" + gke.getLabel() + "," + table.getVariableName(gke.getVariable()) + ",lab)) & ");
		}
		System.out.println();
		for(GenKillElement gke : genList) {
			System.out.print("gen(" + gke.getLabel() + "," + table.getVariableName(gke.getVariable()) + "," + gke.getLabel() + ") & ");
		}
		System.out.println();
		System.out.println("(A lab. A x. A d. var(x) & label(d) & (RDentry(lab,x,d) & ! kill(lab,x,d)) | gen(lab,x,d) => RDexit(lab,x,d)) & ");
		System.out.println("(A lab1. A lab2. A x. A d. flow(lab1,lab2) & RDexit(lab1,x,d) => RDentry(lab2,x,d)) & ");
		System.out.println("(A x. var(x) => RDentry(1,x,0))");
	}

	private static class GenKillElement implements Comparable<GenKillElement> {

		final Variable v;
		final String label;

		public GenKillElement(Variable v, String label) {
			this.v = v;
			this.label = label;
		}

		public Variable getVariable() {
			return v;
		}

		public String getLabel() {
			return label;
		}

		@Override
		public int compareTo(GenKillElement o) {
			return (o.getLabel().compareTo(label)) | (((SimpleVariable) o.getVariable()).compareTo((SimpleVariable)v));
		}
	}

	private static class FlowElement implements Comparable<FlowElement> {

		final String labelFrom;
		final String labelTo;

		public FlowElement(String from, String to) {
			labelFrom = from;
			labelTo = to;
		}

		public String getLabelFrom() {
			return labelFrom;
		}

		public String getLabelTo() {
			return labelTo;
		}

		@Override
		public int compareTo(FlowElement arg0) {
			return (arg0.getLabelFrom().compareTo(labelFrom)) | (arg0.getLabelTo().compareTo(labelTo));
		}
	}
}
