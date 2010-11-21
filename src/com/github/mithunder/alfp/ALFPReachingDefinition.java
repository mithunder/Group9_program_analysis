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
		if(statementType == StatementType.IF) {
			System.err.println("ENTER IF");
		} else if(statementType == StatementType.DO) {
			System.err.println("ENTER DO");
		}
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
				System.err.println("ADDING: " + labelStack.peek() + " -> " + newLabel);
				flowList.add(new FlowElement(labelStack.peek(), newLabel));
				labelStack.add(newLabel);
				System.err.println("PUSH: " + newLabel + " " + labelStack);
				iterate(statement.getChildren(), statement.getStatementType());
				s = statements.get(i + limit);
				fromReturn = iterate(s.getChildren(), s.getStatementType());
				if(fromReturn.size() > 0) {
					toReturn.addAll(fromReturn);
					System.err.println("toReturn CONTAINS: " + toReturn);
				} else {
					toReturn.add(labelStack.pop());
					System.err.println("POP: " + toReturn.get(toReturn.size() - 1) + " " + labelStack);
					System.err.println("toReturn CONTAINS: " + toReturn);
				}
				break;
			case StatementType.DO:
				newLabel = Integer.toHexString(System.identityHashCode(statement.getStatement()));
				System.err.println("ADDING: " + labelStack.peek() + " -> " + newLabel);
				flowList.add(new FlowElement(labelStack.peek(), newLabel));
				labelStack.add(newLabel);
				System.err.println("PUSH: " + newLabel + " " + labelStack);
				iterate(statement.getChildren(), statement.getStatementType());
				s = statements.get(i + limit);
				fromReturn = iterate(s.getChildren(), s.getStatementType());
				//toReturn.addAll(returned);
				System.err.println("RETURNED LIST CONTAINS: " + fromReturn);
				if(fromReturn.size() > 0) {
					String tmpLabel = labelStack.peek();
					for(int j = 0; j < fromReturn.size(); j++) {
						System.err.println("ADDING: " + fromReturn.get(j) + " -> " + tmpLabel);
						flowList.add(new FlowElement(fromReturn.get(j), tmpLabel));
					}
					fromReturn.clear();
				} else {
					String tmpLabel = labelStack.pop();
					System.err.println("POP: " + tmpLabel + " " + labelStack);
					System.err.println("ADDING: " + tmpLabel + " -> " + labelStack.peek());
					flowList.add(new FlowElement(tmpLabel, labelStack.peek()));
				}
				break;
			default:
				newLabel = Integer.toHexString(System.identityHashCode(statement.getStatement()));
				if(ifOnIndex+1 == i && fromReturn != null) {
					for(int j = 0; j < fromReturn.size(); j++) {
						System.err.println("ADDING: " + fromReturn.get(j) + " -> " + newLabel);
						flowList.add(new FlowElement(fromReturn.get(j), newLabel));
					}
				} else {
					String oldLabel = labelStack.pop();
					System.err.println("POP: " + oldLabel + " " + labelStack);
					System.err.println("ADDING: " + oldLabel + " -> " + newLabel);
					flowList.add(new FlowElement(oldLabel, newLabel));
				}
				labelStack.push(newLabel);
				System.err.println("PUSH: " + newLabel + " " + labelStack);
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
			System.err.println("POP: " + labelStack.pop() + " " + labelStack);
			//labelStack.pop();
		}
		if(statementType == StatementType.IF) {
			System.err.println("LEAVING IF");
		} else if(statementType == StatementType.DO) {
			System.err.println("LEAVING DO");
		}
		System.err.println("toReturn CONTAINS (at return): " + toReturn);
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
