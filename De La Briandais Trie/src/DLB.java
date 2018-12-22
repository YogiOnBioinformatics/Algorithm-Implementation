import java.lang.*;
import java.util.ArrayList;



public class DLB{ 	//DOES THIS NEED TO BE STATIC?????
		
		ArrayList<String> returnStrings = new ArrayList<String>();
		
		public Node root;
		
		public DLB() {
			root = new Node();
			
		}
		
		public boolean add(String word){
		
			char[] wordArr = word.toCharArray();
			Node thisNode = root;
			Node newNode;
			
			for(int i =0; i<wordArr.length; i++){
				if(thisNode.bottomNode ==null){
					if(i == wordArr.length-1){
						newNode = new Node(wordArr[i], word, null, null);
						
					}
					else{
						newNode = new Node(wordArr[i]);
						
					}
					
					thisNode.bottomNode = newNode;
					thisNode = thisNode.bottomNode;
				}
				else {
					thisNode = thisNode.bottomNode;
					if(thisNode.character!=wordArr[i]){
						if (thisNode.rightNode == null){
							if(i == wordArr.length-1){
								newNode = new Node(wordArr[i], word, null, null);
								
							}
							else{
								newNode = new Node(wordArr[i]);
								
							}
							thisNode.rightNode = newNode;
							thisNode = thisNode.rightNode;
						}
						else{
							while(thisNode.rightNode!=null){  	//CHECK IF I NEED TO USE BREAKLOOP DURING TESTING
								thisNode= thisNode.rightNode;
								if(thisNode.character==wordArr[i]){
									break;
								}
								if(thisNode.rightNode==null){
									if(i==wordArr.length-1){
										newNode = new Node(wordArr[i], word, null, null);
										
									}
									else{
										newNode = new Node(wordArr[i]);
										
									}
									thisNode.rightNode = newNode;
									thisNode = thisNode.rightNode;
								}
							}
						}
					}
					else if(i == wordArr.length-1){			//CHECK THIS CODE RIGHT HERE!!!!!!!!!!!!!!!
						thisNode.value = word;
					}
				}	
			}
		return true;
		}
		
		public Node findNode(String prefix){
			char[] wordArr = prefix.toCharArray();
			Node thisNode = root;
			//thisNode = thisNode.bottomNode;
			
			for(int i =0; i<wordArr.length; i++){
				thisNode= thisNode.bottomNode;
				if(thisNode.character!=wordArr[i]){
					while(thisNode.rightNode!=null){
						thisNode = thisNode.rightNode;
						if(thisNode.character==wordArr[i]){
						
							break;
							
						}
						if(thisNode.rightNode==null){
							return null;
							
						}
					}
				}
				if((thisNode.character==wordArr[i]) && (i == wordArr.length-1)){
					return thisNode;
				}
				
			}
			
			return null; //CHECK THIS CODE RIGHT HERE!!!!!!!1
		}
		
		public ArrayList<String> findWordsWithPrefix(String prefix){
			Node foundNode = findNode(prefix);
			Node thisNode= foundNode;
			
			Node nodeForRightLoop = foundNode;
			//nodeForRightLoop = nodeForRightLoop.bottomNode;
			
			StringBuilder builder = new StringBuilder(prefix);
			
			if(foundNode==null){
				return null;
			}
			
			if(thisNode.value!=null){
				returnStrings.add(thisNode.value);
			}
			
			while(thisNode.bottomNode!=null){
				
				thisNode = thisNode.bottomNode;
				if(thisNode.value!=null){
					returnStrings.add(thisNode.value);
				}
				else{
					
				}
			}
			
			while(nodeForRightLoop.rightNode!=null){
				nodeForRightLoop = nodeForRightLoop.rightNode;
				
				if(nodeForRightLoop.value!=null){
					returnStrings.add(nodeForRightLoop.value);
					getBottomNodeValues(nodeForRightLoop);
					
				}
				else{
					getBottomNodeValues(nodeForRightLoop);

				}
				
				
			}
			
			
		return returnStrings;	
	}
	
	/* public ArrayList<String> findWordsWithPrefix(Node daNode){
			Node foundNode = daNode;
			Node thisNode= foundNode;
			
			Node nodeForRightLoop = foundNode;
			nodeForRightLoop = nodeForRightLoop.bottomNode;
			
			StringBuilder builder = new StringBuilder(prefix);
			
			if(foundNode==null){
				return null;
			}
			
			if(thisNode.value!=null){
				returnStrings.add(thisNode.value);
			}
			
			while(thisNode.bottomNode!=null){
				
				thisNode = thisNode.bottomNode;
				if(thisNode.value!=null){
					returnStrings.add(thisNode.value);
				}
				else{
					
				}
			}
			
			while(nodeForRightLoop.rightNode!=null){
				nodeForRightLoop = nodeForRightLoop.rightNode;
				
				if(nodeForRightLoop.value!=null){
					returnStrings.add(nodeForRightLoop.value);
					getBottomNodeValues(nodeForRightLoop);
				}
				else{
					getBottomNodeValues(nodeForRightLoop);
				}
				
				
			}
			
			
		return returnStrings;	
	} */
	
		public void getBottomNodeValues(Node getMoreNodes){
			Node myNode = getMoreNodes;
			while(myNode.bottomNode!=null){
				
					myNode= myNode.bottomNode;
					if(myNode.value!=null){
						returnStrings.add(myNode.value);
					}
					else{
					
					}
				}
	}

			
	}
