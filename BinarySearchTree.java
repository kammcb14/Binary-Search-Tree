public class BinarySearchTree implements BSTInterface {
	public BSTNode root;

	protected class BSTNode{
	    protected String item;
	    protected BSTNode left;
	    protected BSTNode right;
	    public BSTNode(String data){
	        item = data;
	        left =null;
	        right=null;
        }
    }

    public BinarySearchTree(){
	    root = null;
    }

    public boolean isEmpty(){
	    return root==null;
    }

    public void makeEmpty(){
	    root=null;
    }

	protected boolean recursiveSearch(BSTNode node, String s) {
		if(node==null) return false;
		if(node.item.equals(s)) return true;
		if(s.compareTo(node.item)<0){
		    return recursiveSearch(node.left, s);
        }
        return recursiveSearch(node.right, s);
	}

	public boolean contains(String s){
	    return recursiveSearch(root, s);
    }

	protected BSTNode recursiveInsert(BSTNode node, String s){
	    if(node==null){
	        return new BSTNode(s);
        }
        if(node!=null){
	        if(s.compareTo(node.item)<0){
	            node.left = recursiveInsert(node.left, s);
            }
            if(s.compareTo(node.item)>0){
	            node.right = recursiveInsert(node.right, s);
            }
        }
        return node;
	}

	public void put(String s){
	    root = recursiveInsert(root, s);

    }

	// TODO: Fill this in and call it from delete()
	protected BSTNode recursiveRemove(BSTNode node, String s) {
        if(node!=null){
            if(s.compareTo(node.item)<0){
                node.left = recursiveRemove(node.left, s);
            }
            if(s.compareTo(node.item)>0){
                node.right = recursiveRemove(node.right, s);
            }
            if(s.equals(node.item)){
                node = deleteNode(node);
            }
        }
        return node;

	}
	
	// TODO: Fill this in and call it from recursiveRemove()
	protected BSTNode deleteNode(BSTNode node) {
        if(node.right==null && node.left==null){
            node = null;
        }
        else if(node.right==null && node.left!=null){
            node = node.left;
        }
        else if(node.right!=null && node.left==null){
            node = node.right;
        }
        else if(node.right!=null && node.left!=null){
            String smallRight = getSmallest(node.right);
            node.item = smallRight;
            node.right = recursiveRemove(node.right, smallRight);
        }
        return node;
	}

	// TODO: Fill this in and call it from deleteNode()
	protected String getSmallest(BSTNode node) {
        String smallest = node.item;
        while(node.left!=null){
            smallest = node.left.item;
            node = node.left;
        }
        return smallest;
	}

    public void delete(String s){
        root = recursiveRemove(root, s);
    }


	// TODO: Fill this in and call it from inOrder()
	protected void inOrderRecursive(BSTNode node, MyQueue queue) {
        if(node!=null){
            inOrderRecursive(node.left,queue);
            queue.enqueue(node.item);
            inOrderRecursive(node.right,queue);
        }
	}

	public MyQueue inOrder(){
	    MyQueue ans = new MyQueue();
	    inOrderRecursive(root, ans);
	    return ans;
    }


	// TODO: Fill this in and call it from preOrder()
	protected void preOrderRecursive(BSTNode node, MyQueue queue) {
        if(node!=null){
            queue.enqueue(node.item);
            preOrderRecursive(node.left, queue);
            preOrderRecursive(node.right, queue);
        }
	}

	public MyQueue preOrder(){
        MyQueue ans = new MyQueue();
        preOrderRecursive(root, ans);
        return ans;
    }

	// TODO: Fill this in and call it from postOrder()
	protected void postOrderRecursive(BSTNode node, MyQueue queue) {
        if(node!=null){
            postOrderRecursive(node.left, queue);
            postOrderRecursive(node.right, queue);
            queue.enqueue(node.item);
        }
	}

	public MyQueue postOrder(){
        MyQueue ans = new MyQueue();
        postOrderRecursive(root, ans);
        return ans;
    }

	// Prints out the tree structure, using indenting to show the different levels of the tree
	public void printTreeStructure() { 
		printTree(0, root);
	}

	// Recursive helper for printTreeStructure()
	protected void printTree(int depth, BSTNode node) {
		indent(depth);
		if (node != null) {
	    	System.out.println(node.item);
	    	printTree(depth + 1, node.left);
	    	printTree(depth + 1, node.right);
	 	} 
	 	else {
	  		System.out.println("null");
	  	}
	}

	// Indents with with spaces 
	protected void indent(int depth) {
		for(int i = 0; i < depth; i++)
			System.out.print("  "); // Indents two spaces for each unit of depth
	}


	// Extra Credit 

	// TODO: If doing the extra credit, fill this in and call it from balanceBST()
	 protected BSTNode balanceRecursive(String[] array, int first, int last) {
	    if(first>last){
	        return null;
        }
        int med = (first+last)/2;
        BSTNode node = new BSTNode(array[med]);
        node.left = balanceRecursive(array, first, med-1);
        node.right = balanceRecursive(array, med+1, last);
        return node;
	}
	public void balanceBST(){
        MyQueue balancedQueue = inOrder();
        MyQueue temp = new MyQueue();
        int count=0;
        while(!balancedQueue.isEmpty()){
            temp.enqueue(balancedQueue.dequeue());
            count++;
        }
        String[] orderedArray = new String[count];
        for(int i=0; i<count; i++){
            orderedArray[i] = temp.dequeue().toString();
        }
        makeEmpty();
        root = balanceRecursive(orderedArray, 0, count-1);
    }
}