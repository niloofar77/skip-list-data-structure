
import java.util.Random;
import java.util.Scanner;

class Node {
     Node up;
     Node down;
     Node prev;
     Node next;
     long key;
     int level;

     public Node(long key, int level, Node next, Node down) {
         this.key = key;
         this.level = level;
         this.next = null;
         this.down = null;
     }

     //for head
     public Node(int level, Node next, Node down) {
         this.next = null;
         this.down = null;
         this.level = level;
     }

     public void insert(long key, int level, Node parent) {
    	 if (this.level <= level && (next == null || next.key > key)) {
             Node newNode = new Node(key, this.level, next, null);
             if (next != null) {
                 next.prev = newNode;
                 newNode.next = next;
             }
             next = newNode;
             prev = newNode;
             if (parent != null) {
                 newNode.up = parent;
                 parent.down = newNode;
             }
             if (down != null) {
                 down.insert(key, level, newNode);
             }
	         } else if (next != null && next.key < key) {
	             next.insert(key, level, parent);
	         } else if (next != null && next.key == key){
             return;
             }
         }
     }

class SkipList {
         Node head;
         Random random;
         long size;
         double p;
         int maxlevel;

         public SkipList() {
             head = new Node(0, null, null);
             this.maxlevel = 10;
             this.size = 0;
             random=new Random(50);

         }

         public int randomlevel() {
             int level = 0;
             while (level <this. maxlevel && random.nextDouble() < p) {
                 level++;
             }
             return level;
         }

         public void insert(long key) {
             int newlevel = 0;
             newlevel = randomlevel();
             while (head.level < newlevel) {
                 Node newHead = new Node(head.level, null, head);
                 head.up = newHead;
                 head = newHead;
             }
             head.insert(key, newlevel, null);
             size++;
         }

         public Node find(long key) {
             Node current = head, node = null;
             while (current != null) {
                 if (current.next == null || current.next.key > key) {
                     current = current.down;
                     continue;
                 } else if (current.next != null && current.next.key == key){
                	 node = current.next;
                 }
                 current = current.next;
             }
             return node;
         }

         public boolean search(long key) {
             Node temp = find(key);
             if (temp != null && temp.key == key) {
                 return true;
             }
             else
                 return false;
         }

         public void delete(long key) {
        	 if(!search(key))
        		 System.out.println("error");
        	 else {
        		 Node current = head;
        		 while (current != null) {
     				if (current.next == null || current.next.key >= key) {
     					if (current.next != null && current.next.key == key) {
     						current.next = current.next.next;
     						size--;
     					}
     					current = current.down;
     					continue;
     				}
     				current = current.next;
     			}
        	 }
         }

         public void print(){
        	String str = "";
      		Node cur = head;
      		if(size == 0){
      			str = "empty ";
      		}else{
      			while(cur != null){
      				if(cur.down != null){
      					cur = cur.down;
      					continue;
      				}else{
      					cur = cur.next;
      				}
      				if(cur != null){
      					str += cur.key+" ";
      				}
      			}
      		}
      		System.out.println(str.substring(0, str.length()-1));
         }
         public static void main(String[] args) {
             SkipList list = new SkipList();
             Scanner input = new Scanner(System.in);
             while(input.hasNextLine()){
                 String[] s = input.nextLine().split(" ");
                 long num = 0;
                 switch (s[0]) {
                     case "Search":
                     case "search":
                         num = Long.parseLong(s[1]);
                         if(list.search(num))
         					System.out.println("true");
         				 else
         					System.out.println("false");
                         break;
                     case "Insert":
                     case "insert":
                         num = Long.parseLong(s[1]);
                         list.insert(num);
                         break;
                     case "Delete":
                     case "delete":
                         num = Long.parseLong(s[1]);
                         list.delete(num);
                         break;
                     case "Print":
                     case "print":
                         list.print();
                         break;
                     default:
                         break;
                 }
             }
             input.close();
         }
     }
