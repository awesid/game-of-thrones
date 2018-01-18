import java.util.*;
import java.io.*;

class userid implements Serializable{
		
		String username;
		Stack<Integer> attack = new Stack<Integer>();
		Stack<Integer> defence = new Stack<Integer>();
		int score;
		int[] avail = new int[3];
		int lose;
		int win;
		int def_cnt;
		int att_cnt;	

		userid(String str,int sc,int[] av,int w, int l,int def,int att){
			username = str;
			score = sc;
			avail = av;
			win = w;
			lose = l;
			def_cnt = def;
			att_cnt = att;
			attack = new Stack<Integer>();
			defence = new Stack<Integer>();

		}
}


class game implements Serializable{
	
	ArrayList<userid> user = new ArrayList<userid>();
	game(){
		
	}
	
	void newgame(){	

		Scanner sc = new Scanner(System.in);
		System.out.println("ENTER YOUR USER NAME:");
		String a = sc.nextLine();
		int[] av = new int[] {1,1,1};
		userid t = new userid(a,0,av,0,0,0,0);

		village t2 = new village(t);
		t = t2.start(t);
		//sc.close();

		user.add(t);
	}


	void loadgame(){
	//search the user using itearable of object user id through the vector user
	//if can not give another choice

		Iterator<userid> i = user.iterator();
		Scanner sc = new Scanner(System.in);

		for(userid temp : user)
		{
			System.out.println(temp.username);
		}
		System.out.println("ENTER YOUR USER NAME:");
		String a = sc.nextLine();
		boolean found=false;
		int count=-1;

		for(userid temp : user)
		{
			count++;
			if(temp.username.equals(a)){
				
				found = true;
			}
			if(found==true) break;

		}
		if(found == true)
		{
			userid temp = user.get(count);
			village t2 = new village(temp);
			temp = t2.start(temp);
			try{
					user.remove(count);
				}catch(IndexOutOfBoundsException e){
			}
			user.add(temp);	
		}
		if(found==false)
			System.out.println("THE USER NAME IS NOT FOUND");

	}
}

interface defence{
	int guard=1;
	int shield=2;
	int wall=3;

	//void create_defence();
	//void defence_display();
}

interface attack{
	int soldier=1;
	int knight=2;
	int commander=3;

	//void create_attack();
	//void attack_display();
}

class army implements defence,attack{
	
	Stack<Integer> attack = new Stack();
	Stack<Integer> defence = new Stack();
	int def_cnt;
	int att_cnt;


	void create_defence(int n){
		
		//creates defence unit for the user
		if(n<=3){
			defence.push(n);
			def_cnt  =  def_cnt + n;
		}
		else
			System.out.println("ENTERED CHARACTER EITHER DOES NOT BELONG TO THE VILLAGE OR ISN'T UPGRADED YET.");
	}
	void create_attack(int n){

		//creates attack units for the user
		if(n<=3){
			attack.push(n);
			att_cnt = att_cnt + n;
		}
		else
			System.out.println("ENTERED CHARACTER EITHER DOES NOT BELONG TO THE VILLAGE OR ISN'T UPGRADED YET.");
	}

	void defence_display(){
		// displays the user's defence army
		int g=0,s=0,w=0;
		System.out.println("YOUR DEFENCE ARMY IS:");
		for(Integer i : defence){
			if(i.equals(1)) g++;
			else if(i.equals(2)) s++;
			else if(i.equals(3)) w++;
		}
		System.out.println("GUARD:"+g);
		System.out.println("SHIELD:"+s);
		System.out.println("WALL:"+w);
	}

	void attack_display(){
		// displays the user's attack army
		int s=0,k=0,c=0;
		System.out.println("YOUR ATTACK ARMY IS:");
		for(Integer i : attack){
			if(i.equals(1)) s++;
			else if(i.equals(2)) k++;
			else if(i.equals(3)) c++;
		}
		System.out.println("SOLDIER:"+s);
		System.out.println("KNIGHT:"+k);
		System.out.println("COMMANDER:"+c);
	}

	void display_army(){
		System.out.println("YOUR WHOLE ARMY IS:");
		defence_display();
		attack_display();
	}

}

class village extends army{
	int population=12569;
	int area=5558;
	String name = "WINTERFELL";
	int[] avail= new int[3];
	int win;
	int lose;
	int score;

	village(userid temp){
		attack = temp.attack;
		defence = temp.defence;
		avail = temp.avail;
		win = temp.win;
		lose = temp.lose;
		score = temp.score;
		def_cnt = temp.def_cnt;
		att_cnt = temp.att_cnt;
	}

	userid start(userid temp){

		Scanner sc = new Scanner(System.in);
		boolean exit = false;
		int c =0;
		do{
			System.out.println("WHAT DO YOU WANT TO DO?\n1.START\n2.SAVE AND RETURN");
			int n = sc.nextInt();
			int tot = 0;
			int sco =0;
			if(n==1){
				boolean quit = false;
				do{
				System.out.println("SCORE:"+score);
				System.out.println("WHAT DO YOU WANT TO DO?\n1.ATTACK\n2.BARRACKS\n3.ARMY\n4.QUIT");
				int k = sc.nextInt();
				switch(k){
					case 1:
						int w = attack_own();
						if(w!=0)
							score = score + w;
						if(avail[0]==0 && avail[1]==0 && avail[2]==0){
							win=1 ;
							quit = true;
							exit = true;
							System.out.println("YOU HAVE WON THE GAME OF THRONES.");
						}
						break;
					case 2:
						System.out.println("WHAT DO YOU WANT TO CREATE?\n1.ATTACK\n2.DEFENCE");
						c = sc.nextInt();
						int p =0;
						if(c==1){
							System.out.println("ENTER A MAXIMUM OF 10 CHARACTER YOU WANT TO CREATE. HIT 0 TO STOP.");
							System.out.println("1.SOLDIER\n2.KNIGHT\n3.COMMANDER");
							for (int i=0;i<10;i++) {
								p = sc.nextInt();
								if(p==0)
									break;
								else{
									create_attack(p);
								}
								
							}
							tot = def_cnt + att_cnt;
							sco = 0;
							if(tot>20){
								if(avail[0]==1)
									sco = defence_own(0);
							}
							else if(tot>40){
								if(avail[1]==1)
									sco = defence_own(1);
							}
							else if(tot>60){
								if(avail[2]==1)
									sco = defence_own(2);
							}
						}
						else if(c==2){
							System.out.println("ENTER A MAXIMUM OF 10 CHARACTER YOU WANT TO CREATE. HIT 0 TO STOP.");
							System.out.println("1.GUARD\n2.SHIELD\n3.WALL");
							for (int i=0;i<10;i++) {
								p = sc.nextInt();
								if(p==0)
									break;
								else
									create_defence(p);
							}
							tot = def_cnt + att_cnt;
							sco = 0;
							if(tot>15){
								if(avail[0]==1)
									sco = defence_own(0);
							}
							else if(tot>35){
								if(avail[1]==1)
									sco = defence_own(1);
							}
							else if(tot>55){
								if(avail[2]==1)
									sco = defence_own(2);
							}
							if(sco == -1){
								exit = true;
								quit = true;
								lose = 1;
								System.out.println("YOU LOST YOUR VILLAGE IN A ATTACK.");
							}
							else{
								
								score = score+sco;
							}
						}
						break;
					case 3:
						System.out.println("WHAT DO YOU WANT TO SEE?\n1.DEFENCE ARMY\n2.ATTACK ARMY\n3.FULL ARMY");
						c = sc.nextInt();
						if(c==1)
							defence_display();
						else if(c==2)
							attack_display();
						else if(c==3)
							display_army();
						else
							System.out.println("YOU'VE ENTERED A WRONG VALUE.");
						break;
					case 4:
						quit = true;
						break;
					default:
						System.out.println("YOU'VE ENTERED A INCORRECT VALUE.");
					
					}
				}while(quit!=true);
			}
			else
				exit = true;

		}while(exit != true);

		temp.attack = attack;
		temp.defence = defence;
		temp.avail = avail;
		temp.att_cnt = att_cnt;
		temp.def_cnt = def_cnt;
		temp.win = win;
		temp.lose = lose;
		temp.score = score;
		return temp;
	}
	@Override
	void create_defence(int n){
		super.create_defence(n);
	}
	void create_attack(int n){
		super.create_attack(n);
	}
	void defence_display(){
		super.defence_display();
	}
	void attack_display(){
		super.attack_display();
	}
	void display_army(){
		super.display_army();
	}
	int defence_own(int n){
		Scanner sc = new Scanner(System.in);
		int l=-1;
		int need = 0;
		int mul =0;
		int cnt = 0;

		if(n==0){
			need = 15;
			mul = 12;
		}
		if(n==1){
			need = 25;
			mul = 26;
		}
		if(n==2){
			need = 35;
			mul = 39;
		}
		while(need>0)
		{
			if(!defence.empty())
			{
				int k = defence.pop();
				def_cnt = def_cnt - k;
				need = need - k;
				cnt = cnt+k;
			}
			else{
				l=-1;
			}

		}
		if(need <= 0){
			l=mul*cnt;
			String[] ccc =new String[]{"KING'S LANDING","DRAGONSTONE","LAND OF FOREVER WINTER"};
			System.out.println("YOU DEFENDED YOUR VILLAGE AGAINST " + ccc[n-1]);
		}

		return l;
	}
	int attack_own(){
		Scanner sc = new Scanner(System.in);
		System.out.println("WHOM DO YOU WANT TO ATTACK?\n1.KING'S LANDING\n2.DRAGONSTONE\n3.LAND OF FOREVER WINTER");
		int n = sc.nextInt();
		int w=0;
		int need = 0;
		int mul =0;
		int cnt = 0;

		if(n==1){
			need = 15;
			mul = 25;
		}
		if(n==2){
			need = 30;
			mul = 36;
		}
		if(n==3){
			need = 50;
			mul = 57;
		}

		if(avail[n-1]==0){
			System.out.println("THE VILLAGE IS ALREADY DESTROYED.");
			return 0;
		}

		while(need>0)
		{
			if(!attack.empty())
			{
				int k = attack.pop();
				att_cnt = att_cnt - k;
				need = need - k;
				cnt = cnt+k;
			}
			else{
				System.out.println("YOU LOST THE ATTACK");
				break;
			}

		}
		if(need <= 0){
			w=mul*cnt;
			avail[n-1] = 0;
			String[] ccc =new String[]{"KING'S LANDING","DRAGONSTONE","LAND OF FOREVER WINTER"};
			System.out.println("YOU WON THE ATTACK AGAINST " + ccc[n-1]);

		}

		return w;
	}


	void display(){
		System.out.println("THE NAME OF THE VILLAGE IS:" + name);
		System.out.println("THE AREA AND POPULATION OF THE VILLAGE IS:" + area + "SQKMS " + population+" PEOPLE.");
	}
}


public class project{

	
	static void startgame(int n){

		try{
			File f1 = new File("user.txt");
			File f2 = new File("t1.txt");
			FileInputStream fis = new FileInputStream(f1);
			ObjectInputStream ois = new ObjectInputStream(fis);
			game temp = new game();

			temp = (game)ois.readObject();

			if(n==1)
				temp.newgame();
			else if(n==2)
				temp.loadgame();

			FileOutputStream fos = new FileOutputStream(f2);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(temp);
			oos.close();
			fos.close();
			f2.renameTo(f1);
		}
		catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        } 


	}
	/*static void startfirst(){
		try{
			File f1 = new File("user.txt");
			File f2 = new File("t1.txt");
			FileInputStream fis = new FileInputStream(f1);
			//ObjectInputStream ois = new ObjectInputStream(fis);
			game temp = new game();

			//temp = (game)ois.readObject();

			temp.newgame();

			FileOutputStream fos = new FileOutputStream(f2);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(temp);
			oos.close();
			fos.close();
			f2.renameTo(f1);
		}
		catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
	}*/	

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);

		int n;
		char a;
		boolean quit=false;

		//startfirst();

		do
		{
			System.out.println("WELCOME TO GAME OF THRONES.");
			System.out.println("WHAT DO YOU WANT TO DO?\n1.START A NEW GAME\n2.LOAD A SAVED GAME\n3.EXIT");
			n = sc.nextInt();
			switch (n) {
				case 1:
					startgame(n);
					break;
				case 2:
					startgame(n);
					break;
				case 3:
					System.out.println("ARE YOU SURE YOU WANT TO QUIT.(Y/N)");
					a = sc.next().charAt(0);
					if((a=='y') || (a=='Y'))
						quit = true;
					break;
				default:
					System.out.println("THAT IS NOT AN VALID OPTION.");
					break;
			}
		}while (!quit);
	}
}