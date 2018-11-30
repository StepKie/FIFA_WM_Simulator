package socsim;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.BinaryOperator;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class BinaryTree<V> {
	
	V root;
	BinaryTree<V> left;
	BinaryTree<V> right;
	
	public static <V> BinaryTree<V> generateTree(List<V> leafs, BinaryOperator<V> merger) {
		Queue<BinaryTree<V>> qLeft = new LinkedList<BinaryTree<V>>();
		Queue<BinaryTree<V>> qRight = new LinkedList<BinaryTree<V>>();
		for (int i = 0; i < leafs.size(); i++) {
			var leafTree = new BinaryTree<>(leafs.get(i), null, null);
			if (i % 4 < 2)
				qLeft.add(leafTree);
			else
				qRight.add(leafTree);
		}
		
		while (qLeft.size() > 1) {
			mergeStep(qLeft, merger);
			mergeStep(qRight, merger);
			
		}
		qLeft.add(qRight.poll());
		mergeStep(qLeft, merger);
		BinaryTree<V> finalTree = qLeft.poll();
		assert (qLeft.isEmpty() && qRight.isEmpty()) : "Tree is not balanced";
		log.info("Tree creation complete");
		return finalTree;
	}
	
	private static <V> void mergeStep(Queue<BinaryTree<V>> queue, BinaryOperator<V> merger) {
		BinaryTree<V> left = queue.poll();
		BinaryTree<V> right = queue.poll();
		BinaryTree<V> parent = new BinaryTree<V>(merger.apply(left.root, right.root), left, right);
		queue.add(parent);
		
	}
	
	// TODO Traversal strategies, depth limiter etc.
	public List<V> getAll(Comparator<V> c) {
		
		var history = new ArrayList<V>();
		if (left != null)
			history.addAll(left.getAll(c));
		if (right != null)
			history.addAll(right.getAll(c));
		history.add(root);
		
		if (c != null)
			history.sort(c);
		return history;
	}
}
