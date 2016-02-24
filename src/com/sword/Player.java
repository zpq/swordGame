package com.sword;

import java.util.Random;

public class Player implements Runnable {

	// use public just to easy use, not recommand
	public String name;
	public int health;
	public int minAttack;
	public int maxAttack;
	public int defense;
	public int attackInterval;
	public Boss target;
	public final Object object = new Object();
	volatile boolean keepRunning = true;

	public Player(String name, int health, int minAttack, int maxAttack,
			int defense, int attackInterval) {
		this.name = name;
		this.health = health;
		this.minAttack = minAttack;
		this.maxAttack = maxAttack;
		this.defense = defense;
		this.attackInterval = attackInterval;
	}
	
//	public synchronized void attack(){
	public void attack() {
		Random random = new Random();
		int s = random.nextInt(this.maxAttack)
				% (this.maxAttack - this.minAttack + 1) + this.minAttack;
		int damage = s - this.target.defense;
		synchronized (object) {
			int hp = this.target.health;
//				try {
//					object.wait();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				hp = this.target.health - damage;
				System.out.println(this.name + " attack " + this.target.name
						+ " damaged " + damage + "; " + this.target.name + " left "
						+ hp + " hp");
				this.target.health -= damage;
				object.notifyAll();
		}
		
	}

	public void addTarget(Boss target) {
		this.target = (Boss) target;
	}

	public void run() {
		while (keepRunning) {
			this.attack();
			try {
				Thread.sleep(this.attackInterval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (this.target.health <= 0) {
				keepRunning = false;
//				System.out.println(this.target.name + " dead");
			} else if (this.health <= 0) {
				keepRunning = false;
				System.out.println(this.name + " dead");
			}
		}
	}

}
