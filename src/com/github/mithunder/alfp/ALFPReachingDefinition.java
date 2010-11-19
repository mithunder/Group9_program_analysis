package com.github.mithunder.alfp;

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
	public void convertToALFP(List<EvaluatedStatement> statements, CompilationUnit unit) {
		labelStack = new Stack<String>();
		labelStack.push("0");
		variableList = new TreeSet<Variable>();
		labelList = new TreeSet<String>();
		genList = new TreeSet<GenKillElement>();
		killList = new TreeSet<GenKillElement>();
		flowList = new TreeSet<FlowElement>();
		table = unit.getVariableTable();
		iterate(statements);
		print();
	}

	private void iterate(List<EvaluatedStatement> statements) {
		for(int i = 0; i < statements.size(); i++) {
			EvaluatedStatement statement = statements.get(i);
			switch(statement.getStatementType()) {
			case StatementType.IF:
				break;
			case StatementType.DO:
				break;
			default:
				String oldLabel = labelStack.pop();
				String newLabel = Integer.toHexString(System.identityHashCode(statement.getStatement()));
				labelStack.push(newLabel);
				if(statement.getChildCount() > 0) {
					iterate(statement.getChildren());
				} else {
					Variable assign = statement.getAssign();
					labelList.add(newLabel);
					flowList.add(new FlowElement(oldLabel, newLabel));
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
