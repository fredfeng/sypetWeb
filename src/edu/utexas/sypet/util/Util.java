package edu.utexas.sypet.util;

import java.util.ArrayList;

import edu.utexas.sypet.synthesis.model.Benchmark;

public class Util {
	public static String genMethodHeader(Benchmark bench) {
		StringBuilder builder = new StringBuilder();
		builder.append(bench.getTgtType().replaceAll("\\$", ".")).append(' ');
		builder.append(bench.getMethodName()).append('(');
		ArrayList<String> paramTypes = new ArrayList<String>(bench.getSrcTypes());
		ArrayList<String> paramNames = new ArrayList<String>(bench.getParamNames());
		assert paramTypes.size() == paramNames.size();
		for (int i = 0; i < paramTypes.size(); ++i) {
			builder.append(paramTypes.get(i)).append(' ').append(paramNames.get(i));
			if (i < paramTypes.size() - 1) {
				builder.append(", ");
			}
		}
		builder.append(')');
		return builder.toString();
	}
}
