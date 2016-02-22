package com.sword;

import java.util.ArrayList;
import java.util.Random;

public class Boss implements Runnable {

	// use public just easy to use, not recommand in production
	public String name;
	public int health;
	public int minAttack;
	public int maxAttack;
	public int defense;
	public int attackInterval;
	public ArrayList<Player> target;
	public Object object;
	volatile boolean keepRunning = true;

	public Boss(String name, int health, int minAttack, int maxAttack,
			int defense, int attackInterval) {
		this.name = name;
		this.health = health;
		this.minAttack = minAttack;
		this.maxAttack = maxAttack;
		this.defense = defense;
		this.attackInterval = attackInterval;
		this.target = new ArrayList<Player>();
	}

	public void addTarget(Player target) {
		this.target.add(target);
		System.out.println(target.name + " add target");
	}

	public void attack() {
		Random random = new Random();
		int s = random.nextInt(this.maxAttack)
				% (this.maxAttack - this.minAttack + 1) + this.minAttack;
		Player tp = this.getTarget();
		int damage = s - tp.defense;
		tp.health -= damage;
		System.out.println(this.name + " attack " + tp.name
				+ " damaged " + damage + "; " + tp.name + " left "
				+ tp.health + " hp");
	}
	
	protected Player getTarget() { //select an alive target at random
		int index = (int)(Math.random() * this.target.size());
		System.out.println("index = " + index);
		return this.target.get(index);
	}
	
	protected void removeDeadTargetByName(String name) {
		int size = this.target.size();
		Player player;
		for (int i = 0; i < size; i++) {
			player = this.target.get(i);
			if(player.name.equals(name)) {
				System.out.println("remove " + name);
				this.target.remove(i);
				return;
			}
		}
		System.out.println("no matched target");
	}
	
	protected boolean isLeftTarget() {
		return this.target.size() > 0 ? true : false;
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
			if (this.getTarget().health <= 0) {
				System.out.println(this.getTarget().name + " dead");
				//remove the dead target
				this.removeDeadTargetByName(this.getTarget().name);
				
				//judge left target
				if(!isLeftTarget()) {
					keepRunning = false;
				}
			} else if (this.health <= 0) {
				keepRunning = false;
			}
		}
	}
}
