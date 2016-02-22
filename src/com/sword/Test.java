package com.sword;

public class Test extends Thread {

	public static void main(String[] args) {
		Thread t = new Test();
		t.setName("main thread");
		t.start();
	}

	public void run() {
		Boss b = new Boss("Boss", 5000, 150, 200, 80, 1000);
		Player p = new Player("jack", 500, 200, 300, 100, 500);
		Player p2 = new Player("Peter", 600, 250, 300, 50, 400);
		b.addTarget(p);
		b.addTarget(p2);
		p.addTarget(b);
		p2.addTarget(b);
		Thread bs = new Thread(b, "boss");
		Thread jack = new Thread(p, "jack");
		Thread peter = new Thread(p2, "peter");
		bs.start();
		jack.start();
		peter.start();
	}

}
