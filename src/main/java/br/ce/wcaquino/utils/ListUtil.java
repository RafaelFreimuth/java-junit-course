package br.ce.wcaquino.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

	public static <T> List<T> toList(T... itens) {
		List<T> list = new ArrayList<T>();
		
		for (T t : itens) {
			list.add(t);
		}
		
		return list;
		
	}
}
