import java.lang.*;

public class Node{
				public char character;
				public String value;
				public Node rightNode;
				public Node bottomNode;
				
				public Node(){
					this('\u0000', null, null, null);
				}
				public Node(char a){
					this(a, null, null, null);
				
				}
				public Node (char a, String val){
					this(a, val, null, null);
				}
				public Node(char a, String val, Node rNode, Node bNode){
					character = a;
					value = val;
					rightNode = rNode;
					bottomNode = bNode;
				}
				
				public Node getRightNode(){
					if(rightNode==null){
						return null;
					}
					else{
						return rightNode;
					}
				}
				public Node getBottomNode(){
					if(bottomNode==null){
						return null;
					}
					else{
						return bottomNode;
					}
				}
		
			}