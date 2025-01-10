import java.util.Scanner;

public class Main {
    public static Scanner reader = new Scanner(System.in);

    public static void main(String[] args) {
    	//,writer,singer,composer,times
        Node<Song> s1 = new Node<Song>(new Song("haim leiot bah mehohav","aviv gefen","eyal golan","aviv gefen",1000000));
        Node<Song> s2 = new Node<Song>(new Song("noetzet mabat","moshe ben avraham","omer adam","moshhe ben avraham",50000));
        Node<Song> s3 = new Node<Song>(new Song("bati elaich","doli and pen","eyal golan","doli and pen",123456));
        Node<Song> s4 = new Node<Song>(new Song("In the Air Tonight", "Phil Collins", "Phil Collins", "Phil Collins", 1200000));
        
    	s1.setNext(s2);
        s2.setNext(s3);
        s3.setNext(s4);
        
    }    
}
