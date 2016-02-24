package com.sword;

public class Test extends Thread {

	public static void main(String[] args) {
		Thread t = new Test();
		t.setName("main thread");
		t.start();
	}

	public void run() {
		Boss b = new Boss("Boss", 5000, 250, 400, 100, 1000);
		Player p = new Player("jack", 500, 200, 300, 100, 500);
		Player p2 = new Player("Peter", 600, 300, 350, 80, 500);
		Player p3 = new Player("Bob", 800, 200, 300, 50, 500);
		b.addTarget(p);
		b.addTarget(p2);
		b.addTarget(p3);
		p.addTarget(b);
		p2.addTarget(b);
		p3.addTarget(b);
		Thread bs = new Thread(b, "boss");
		Thread jack = new Thread(p, "jack");
		Thread peter = new Thread(p2, "peter");
		Thread bob = new Thread(p3, "bob");
		bs.start();
		jack.start();
		peter.start();
		bob.start();
	}

}
